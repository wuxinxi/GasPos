package com.szxb.base;

import android.util.Log;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.szxb.utils.comm.Constant;
import com.szxb.utils.interceptor.MainLooper;
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
    private int count = 0;
    private int ORDECCOUNT = 12;

    //是否取消轮训任务的标识符
    private boolean cancel = false;

    public void requestData(int what, Map<String, Object> map, String url) {
        if (null == map)
            throw new IllegalArgumentException("no enity");
        if (what == Constant.LOOP_WHAT) {
            timer = new Timer();
            timer.schedule(new LoopTimerTask(what, map, url, null), 2000, 2500);
        } else {
            requestPost(what, map, url);
        }
    }

    public void requestData(int what, Map<String, Object> map, String url, TextView tip) {
        if (null == map)
            throw new IllegalArgumentException("no enity");
        if (what == Constant.LOOP_WHAT) {
            timer = new Timer();
            timer.schedule(new LoopTimerTask(what, map, url, tip), 2000, 2500);
        } else {
            requestPost(what, map, url);
        }
    }

    private class LoopTimerTask extends TimerTask {
        int what;
        Map<String, Object> map;
        String url;
        TextView tip;

        LoopTimerTask(int what, Map<String, Object> map, String url, TextView tip) {
            this.what = what;
            this.map = map;
            this.url = url;
            this.tip = tip;
        }

        @Override
        public void run() {
            //有效期5分钟
            if (count >= 12) {
                cancel();
                cancel = true;
                Log.d("LoopTimerTask",
                        "success(LoopTimerTask.java:64)停止轮训任务=");
                return;
            }
            Log.d("LoopTimerTask",
                    "success(LoopTimerTask.java:85)" + ORDECCOUNT);
            requestPost(what, map, url);
            if (tip != null) {
                MainLooper.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tip.setText("支付倒计时:" + ORDECCOUNT + "次");
                    }
                });
            }
            Log.d("LoopTimerTask",
                    "success(LoopTimerTask.java:69)第" + (count + 1) + "次轮训=");
            count++;
            ORDECCOUNT--;
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
                        "success(BasePresenter.java:82)" + response.get().toJSONString());
                if (response.get() != null) {
                    if (what == Constant.LOOP_WHAT) {//轮训任务
                        String rescode = response.get().getString("rescode");
                        switch (rescode) {
                            case "0000":
                                onAllSuccess(what, response.get());
                                cancelTimerTask();
                                break;
                            case "0001":
                                if (count >= 12) {
                                    //true表示轮训结束
                                    onFail(what, true, "5分钟内未支付完成");
                                }
                                //支付中不做处理
                                break;
                            default:
                                onFail(what, false, response.get().toJSONString());
                                break;
                        }

                    } else//其他网络任务
                        onAllSuccess(what, response.get());
                } else {
                    onFail(what, false, "请求服务器异常!");
                }
            }

            @Override
            public void fail(int what, String e) {
                if (what == Constant.LOOP_WHAT) {
                    if (count >= 12) {
                        onFail(what, true, "网络超时且轮训超时");
                    } else onFail(what, false, "网络或服务器异常");
                } else {
                    onFail(what, false, "网络或服务器异常");
                }

            }
        });

    }

    protected abstract void onAllSuccess(int what, JSONObject result);

    protected abstract void onFail(int what, boolean isOk, String failStr);

    public void cancelTimerTask() {
        if (timer != null) {
            Log.d("BasePresenter",
                    "success(BasePresenter.java:105)TimerTask stop ……");
            count = 0;
            ORDECCOUNT = 12;
            timer.cancel();
            timer = null;
            request.cancelBySign(Constant.LOOP_WHAT);
            cancel = true;
        } else {
            Log.d("BasePresenter",
                    "success(BasePresenter.java:127)time=null");
        }
    }


    public void cancelBySign(int what) {
        request.cancelBySign(what);
    }

    //为了避免轮训任务与手动查询冲突
    //如果没有取消轮训任务不允许手动查询
    public boolean isCancleTask() {
        return cancel;
    }

}
