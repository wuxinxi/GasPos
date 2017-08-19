package com.szxb.interfaces;


import com.alibaba.fastjson.JSONObject;

/**
 * 作者: Tangren on 2017/8/18
 * 包名：com.szxb.interfaces
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public interface OnRequestListener {
    void onSuccess(int what, JSONObject object);

    void onFail(int what);
}
