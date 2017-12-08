package com.szxb.utils;

import com.alibaba.fastjson.JSONObject;
import com.szxb.App;
import com.szxb.tanker.TrankerInterface;
import com.szxb.utils.tip.Tip;
import com.yanzhenjie.nohttp.rest.Response;

import szxb.com.poslibrary.http.CallServer;
import szxb.com.poslibrary.http.HttpListener;
import szxb.com.poslibrary.http.JsonRequest;

/**
 * 作者: Tangren on 2017-10-19
 * 包名：com.szxb.utils
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class TestUtil {
    private volatile static TestUtil instance = null;

    private TestUtil() {
    }

    public static TestUtil getInstance() {
        if (instance == null) {
            synchronized (TestUtil.class) {
                if (instance == null) {
                    instance = new TestUtil();
                }
            }
        }
        return instance;
    }


    public void send(byte[] recv) {
        String str = TrankerInterface.bytesToHexString(recv, recv.length);
        JsonRequest request = new JsonRequest("http://blackskin.imwork.net/bipbus/interaction/test");
        request.set("data", str);
        CallServer.getHttpclient().add(100, request, new HttpListener<JSONObject>() {
            @Override
            public void success(int what, Response<JSONObject> response) {
                Tip.show(App.getInstance(), "上传成功", true);
            }

            @Override
            public void fail(int what, String e) {
                Tip.show(App.getInstance(), "上传失败", false);
            }
        });
    }

}
