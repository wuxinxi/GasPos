package com.szxb.module.setting;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.szxb.App;
import com.szxb.base.BasePresenter;
import com.szxb.utils.AppUtil;
import com.szxb.utils.ftp.FTP;
import com.szxb.utils.tip.Tip;

import java.lang.ref.WeakReference;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 作者: Tangren on 2017-10-18
 * 包名：com.szxb.module.setting
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class SettingPresenter extends BasePresenter {

    private WeakReference<SettingsActivity> weakReference;

    public SettingPresenter(SettingsActivity activity) {
        weakReference = new WeakReference<SettingsActivity>(activity);
    }

    @Override
    protected void onAllSuccess(final int what, JSONObject result) {
        final SettingsActivity activity = weakReference.get();
        if (activity != null) {
            Log.d("SettingPresenter",
                    "call(SettingPresenter.java:41)" + result.toJSONString());
            String rescode = result.getString("rescode");
            if (rescode.equals("0000")) {
                String version = result.getString("version");
                if (TextUtils.equals(version, AppUtil.getVersionName(activity))) {
                    //如果当前版本跟应用版本一致，不下载
                    activity.onFail(what, false, "暂无最新版本");
                } else {
                    Tip.show(activity, "正在下载,请稍后", true);
                    Observable.create(new Observable.OnSubscribe<Boolean>() {
                        @Override
                        public void call(Subscriber<? super Boolean> subscriber) {
                            subscriber.onNext(new FTP()
                                    .builder(App.getPosManager().getURlIP())
                                    .setPort(2121)
                                    .setLogin("Administrator", "@@#Dzw2016szxb")
                                    .setFileName("gasPos.apk")
                                    .setPath(Environment.getExternalStorageDirectory() + "/")
                                    .setFTPPath(App.getPosManager().getMchID() + "/gasPos.apk")
                                    .download());
                        }
                    }).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<Boolean>() {
                                @Override
                                public void call(Boolean aBoolean) {
                                    Log.d("SettingPresenter",
                                            "call(SettingPresenter.java:67)下载成功=" + aBoolean);
                                    if (aBoolean) {
                                        activity.onSuccess(what, Environment.getExternalStorageDirectory() + "/gasPos.apk");

                                    } else activity.onFail(what, false, "下载新版本失败,请重试!");
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    Log.d("SettingPresenter",
                                            "call(SettingPresenter.java:71)" + throwable.toString());
                                }
                            });
                }

            } else activity.onFail(what, false, result.toJSONString());
        }
    }

    @Override
    protected void onFail(int what, boolean isOk, String failStr) {
        SettingsActivity activity = weakReference.get();
        if (activity != null) {
            activity.onFail(what, isOk, "网络或服务器异常");
        }
    }
}
