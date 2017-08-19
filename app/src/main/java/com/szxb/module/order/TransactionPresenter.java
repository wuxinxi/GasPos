package com.szxb.module.order;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.szxb.base.BasePresenter;
import com.szxb.utils.Config;
import com.yanzhenjie.nohttp.Logger;

import java.lang.ref.WeakReference;

/**
 * 作者：Tangren on 2017/6/9 14:08
 * 邮箱：wu_tangren@163.com
 * TODO:一句话描述
 */
public class TransactionPresenter extends BasePresenter {

    private WeakReference<OrderActivity> weakReference;

    public TransactionPresenter(OrderActivity activity) {
        weakReference = new WeakReference<OrderActivity>(activity);
    }

    @Override
    protected void onAllSuccess(int what, JSONObject result) {
        Logger.d(result.toString());
        OrderActivity activity = weakReference.get();
        if (activity != null) {
            if (what == Config.LOOP_WHAT) {
                activity.onPaySuccess();
            } else if (what == Config.REQUESTQRCODE_WHAT) {
                String rescode = result.getString("rescode");
                if (TextUtils.equals(rescode, "00")) {
                    if (TextUtils.equals(result.getString("result"), "success"))
                        activity.onSuccess(result.getString("codeurl"));
                    else activity.onFail("result!=success\n" + result.toString());
                } else {
                    activity.onFail(result.toString());
                }
            } else if (what == Config.QUERY_WHAT) {

            }

        }
    }

    @Override
    protected void onFail(String failStr) {
        OrderActivity activity = weakReference.get();
        if (activity != null)
            activity.onFail(failStr);
    }
}
