package com.szxb.module.home;

import com.alibaba.fastjson.JSONObject;
import com.szxb.utils.comm.UrlComm;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SyncRequestExecutor;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import szxb.com.poslibrary.http.JsonRequest;

/**
 * 作者: Tangren on 2017-12-05
 * 包名：com.szxb.module.home
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class PushICManager {
    private volatile static PushICManager instance = null;

    private PushICManager() {
    }

    public static PushICManager getInstance() {
        if (instance == null) {
            synchronized (PushICManager.class) {
                if (instance == null) {
                    instance = new PushICManager();
                }
            }
        }
        return instance;
    }


    public void push(final Map<String, Object> map) {
        Observable.create(new Observable.OnSubscribe<JSONObject>() {
            @Override
            public void call(Subscriber<? super JSONObject> subscriber) {
                JsonRequest request = new JsonRequest(UrlComm.getInstance().ICPUSH());
                request.add(map);
                Response<JSONObject> response = SyncRequestExecutor.INSTANCE.execute(request);
                if (response.isSucceed()) {
                    JSONObject result = response.get();
                    String rescode = result.getString("rescode");

                }
            }
        }).retryWhen(new RetryWithDelay(2, 5000))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JSONObject>() {
                    @Override
                    public void call(JSONObject jsonObject) {

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }




}
