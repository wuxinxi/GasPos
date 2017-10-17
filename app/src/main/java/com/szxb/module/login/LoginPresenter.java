package com.szxb.module.login;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.szxb.App;
import com.szxb.base.BasePresenter;

import java.lang.ref.WeakReference;

/**
 * 作者: Tangren on 2017/6/28
 * 包名：com.szxb.module.login
 * 邮箱：996489865@qq.com.com
 * TODO:一句话描述
 */

public class LoginPresenter extends BasePresenter {

    private WeakReference<LoginZipActivity> weakReference;

    public LoginPresenter(LoginZipActivity activity) {
        weakReference = new WeakReference<LoginZipActivity>(activity);
    }

    @Override
    protected void onAllSuccess(int what, JSONObject result) {
        LoginZipActivity activity = weakReference.get();
        if (activity != null) {

            String rescode = result.getString("rescode");
            if (TextUtils.equals(rescode, "0000")) {
                activity.onSuccess(what, "登录成功!");
            } else activity.onFail(what, false, result.toString());

        }
    }

    @Override
    protected void onFail(int what, boolean isOK, String failStr) {
        LoginZipActivity activity = weakReference.get();
        if (activity != null)
            activity.onFail(what, isOK, failStr);
    }

    public void chechK21() {
        if (!App.getPosManager().getInitK21()) {

        }
    }
}
