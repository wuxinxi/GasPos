package szxb.com.m680.module.login;

import com.alibaba.fastjson.JSONObject;

import java.lang.ref.WeakReference;

import szxb.com.m680.base.BasePresenter;

/**
 * 作者: Tangren on 2017/8/17
 * 包名：szxb.com.m680.module.login
 * 邮箱：996489865@qq.com
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

        }
    }

    @Override
    protected void onFail(String failStr) {

    }
}
