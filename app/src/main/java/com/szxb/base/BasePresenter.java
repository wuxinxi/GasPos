package com.szxb.base;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.szxb.utils.Config;
import com.szxb.utils.comm.Constant;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.CacheMode;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import szxb.com.poslibrary.http.CallServer;
import szxb.com.poslibrary.http.HttpListener;
import szxb.com.poslibrary.http.JsonRequest;

/**
 * 作者：Tangren on 2017/6/9 13:16
 * 邮箱：wu_tangren@163.com
 * TODO:一句话描述
 */
public abstract class BasePresenter {

    private Timer timer;

    private JsonRequest request;

    //是否取消轮训任务的标识符
    private boolean cancel = false;

    public void requestData(int what, Map<String, Object> map, String url) {
        if (null == map)
            throw new IllegalArgumentException("no enity");
        if (what == Config.LOOP_WHAT) {
            timer = new Timer();
            timer.schedule(new LoopTimerTask(what, map, url), 2000, 2500);
        } else {
            requestPost(what, map, url);
        }
    }

    private class LoopTimerTask extends TimerTask {
        int what;
        Map<String, Object> map;
        String url;
        int count = 0;

        LoopTimerTask(int what, Map<String, Object> map, String url) {
            this.what = what;
            this.map = map;
            this.url = url;
        }

        @Override
        public void run() {
            //有效期2分钟
            if (count > 48) {
                cancel();
                cancel = true;
            }
            requestPost(what, map, url);
            count++;
        }
    }

    private void requestPost(int what, Map<String, Object> map, String url) {
        request = new JsonRequest(url, RequestMethod.POST);
        request.setCancelSign(what);
        request.setCacheMode(CacheMode.ONLY_REQUEST_NETWORK);
        request.set(map);
        CallServer.getHttpclient().add(what, request, new HttpListener<JSONObject>() {
            @Override
            public void success(int what, Response<JSONObject> response) {
                Log.d("BasePresenter",
                        "success(BasePresenter.java:71)" + response.get().toJSONString());
                if (response.get() != null) {
                    if (what == Constant.LOOP_WHAT) {//轮训任务
                        String rescode = response.get().getString("rescode");
                        switch (rescode) {
                            case "0000":
                                onAllSuccess(what, response.get());
                                cancelTimerTask();
                                break;
                            case "0001":
                                //支付中不做处理
                                break;
                            default:
                                //其他均为失败，并且取消轮训任务
                                onFail(what, response.get().toJSONString());
                                cancelTimerTask();
                                break;
                        }

                    } else//其他网络任务
                        onAllSuccess(what, response.get());
                } else {
                    onFail(what, "请求服务器异常!");
                }
            }

            @Override
            public void fail(int what, String e) {
                onFail(what, "网络或服务器异常!");
            }
        });

    }

    protected abstract void onAllSuccess(int what, JSONObject result);

    protected abstract void onFail(int what, String failStr);

    public void cancelTimerTask() {
        if (timer != null) {
            Log.d("BasePresenter",
                    "cancelTimerTask(BasePresenter.java:105)TimerTask stop ……");
            timer.cancel();
            request.cancelBySign(Config.LOOP_WHAT);
            cancel = true;
        }
    }


    //为了避免轮训任务与手动查询冲突
    //如果没有取消轮训任务不允许手动查询
    public boolean isCancleTask() {
        return cancel;
    }

}
