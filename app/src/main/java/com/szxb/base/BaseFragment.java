package com.szxb.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者: Tangren on 2017/8/2
 * 包名：com.szxb.base
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView {


    protected T mPresenter;

    protected T getChildPresenter() {
        return null;
    }

    protected Map<String, Object> getRequestParams() {
        return null;
    }

    protected View rootView;

    protected abstract int layoutID();

    protected void initView() {
    }

    protected abstract void initData();

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(layoutID(), null);
            unbinder = ButterKnife.bind(this, rootView);
            initView();
            initData();
        }
        ViewGroup group = (ViewGroup) rootView.getRootView();
        if (group != null) {
            group.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getChildPresenter() != null) {
            mPresenter = getChildPresenter();
        }
    }

    @Override
    public void onSuccess(String str) {

    }

    @Override
    public void onFail(String str) {

    }

    @Override
    public void onPaySuccess() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (mPresenter != null) {
            mPresenter = null;
        }
    }
}
