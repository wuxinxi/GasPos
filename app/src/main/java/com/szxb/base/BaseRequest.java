package com.szxb.base;

import com.alibaba.fastjson.JSONObject;
import com.szxb.interfaces.OnRequestListener;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.Map;

import szxb.com.poslibrary.http.CallServer;
import szxb.com.poslibrary.http.HttpListener;
import szxb.com.poslibrary.http.JsonRequest;

/**
 * 作者: Tangren on 2017/8/18
 * 包名：com.szxb.base
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class BaseRequest {

    private OnRequestListener listener;

    public void request(int what, String url, Map<String, Object> map) {
        JsonRequest request = new JsonRequest(url);
        request.add(map);
        request.setCancelSign(what);
        CallServer.getHttpclient().add(what, request, new HttpListener<JSONObject>() {
            @Override
            public void success(int what, Response<JSONObject> response) {
                if (listener != null) {
                    listener.onSuccess(what, response.get());
                }
            }

            @Override
            public void fail(int what, String e) {
                if (listener != null) {
                    listener.onFail(what);
                }
            }
        });
    }

    public void setRuestListener(OnRequestListener listener) {
        this.listener = listener;
    }

}
