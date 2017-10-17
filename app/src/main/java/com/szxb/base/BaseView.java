package com.szxb.base;

/**
 * 作者：Tangren on 2017/6/9 13:10
 * 邮箱：wu_tangren@163.com
 * TODO:一句话描述
 */
public interface BaseView {

    void onSuccess(int what,String str);

    void onFail(int what,boolean isOK, String str);

    void onPaySuccess();
}
