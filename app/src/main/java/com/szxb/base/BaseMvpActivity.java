package com.szxb.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.Map;

/**
 * 作者：Tangren on 2017/6/9 13:15
 * 邮箱：wu_tangren@163.com
 * TODO:一句话描述
 */
public abstract class BaseMvpActivity<T extends BasePresenter> extends BaseActivity implements BaseView {

    protected T mPresenter;

    protected T getChildPresenter() {
        return null;
    }

    protected Map<String, Object> getRequestParams() {
        return null;
    }

    protected Map<String, Object> getLoopRequestParams() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (null != getChildPresenter()) {
            mPresenter = getChildPresenter();
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSuccess(String str) {

    }

    @Override
    public void onPaySuccess() {

    }

    @Override
    public void onFail(String str) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter = null;
        }
    }
}
