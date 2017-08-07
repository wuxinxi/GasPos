package com.szxb.module.login;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.szxb.base.BasePresenter;

import java.lang.ref.WeakReference;

/**
 * 作者: Tangren on 2017/6/28
 * 包名：com.szxb.module.login
 * 邮箱：996489865@qq.com.com
 * TODO:一句话描述
 */

public class LoginPresenter extends BasePresenter {

    private WeakReference<LoginActivity> weakReference;

    public LoginPresenter(LoginActivity activity) {
        weakReference = new WeakReference<LoginActivity>(activity);
    }

    @Override
    protected void onAllSuccess(int what, JSONObject result) {
        LoginActivity activity = weakReference.get();
        if (activity != null) {

            String rescode = result.getString("rescode");
            if (TextUtils.equals(rescode, "00")) {
                activity.onSuccess("登录成功!");
            } else activity.onFail(result.toString());

        }
    }

    @Override
    protected void onFail(String failStr) {
        LoginActivity activity = weakReference.get();
        if (activity != null)
            activity.onFail(failStr);
    }
}
