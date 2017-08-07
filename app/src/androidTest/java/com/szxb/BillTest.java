package com.szxb;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.szxb.entity.BillEntity;
import com.szxb.utils.Config;
import com.szxb.xblog.XBLog;
import com.yanzhenjie.nohttp.rest.Response;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import szxb.com.poslibrary.http.CallServer;
import szxb.com.poslibrary.http.HttpListener;
import szxb.com.poslibrary.http.JsonRequest;

/**
 * 作者: Tangren on 2017/8/5
 * 包名：com.szxb
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
@RunWith(AndroidJUnit4.class)
public class BillTest {

    @Test
    public void billFet() throws Exception {
        JsonRequest request = new JsonRequest(Config.LOOP_URL);
        Map<String, Object> map = new HashMap<>();
        map.put("BEGINTIME", "2017-08-03");
        map.put("ENDTIME", "2017-08-05");
        map.put("MCHID", "100100100101");
        request.add(map);
        CallServer.getHttpclient().add(0, request, new HttpListener<JSONObject>() {
            @Override
            public void success(int what, Response<JSONObject> response) {
                JSONArray array = response.get().getJSONArray("varList");
                Log.d("BillTest",
                        "success(BillTest.java:46)" + array.size());
                BillEntity billEntity = new Gson().fromJson(response.get().toJSONString(), BillEntity.class);
                Log.d("BillTest",
                        "success(BillTest.java:44)" + billEntity.toString());
                XBLog.d("onAllSuccess(BillPresenter.java:33)" + billEntity.toString());
            }

            @Override
            public void fail(int what, String e) {
                XBLog.d("fail(BillTest.java:48)" + e);
            }
        });
    }

}
