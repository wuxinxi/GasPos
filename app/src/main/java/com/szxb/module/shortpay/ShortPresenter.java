package com.szxb.module.shortpay;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.szxb.base.BasePresenter;
import com.szxb.utils.comm.Constant;

import java.lang.ref.WeakReference;

/**
 * 作者: Tangren on 2017-09-21
 * 包名：com.szxb.module.shortpay
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class ShortPresenter extends BasePresenter {

    private WeakReference<ShortPayActivity> weakReference;

    public ShortPresenter(ShortPayActivity activity) {
        weakReference = new WeakReference<ShortPayActivity>(activity);
    }

    @Override
    protected void onAllSuccess(int what, JSONObject result) {
        Log.d("ShortPresenter",
                "onAllSuccess(ShortPresenter.java:29)" + result.toJSONString());
        ShortPayActivity activity = weakReference.get();
        if (activity != null) {
            String rescode = result.getString("rescode");
            switch (what) {
                case Constant.SHORT_WHAT://订单生成成功
                    if (TextUtils.equals(rescode, "0000")) {
                        activity.onSuccess(what, "订单生成成功");
                    } else {
                        String resultString = result.getString("result");
                        activity.onFail(what, false, resultString);
                    }
                    break;
                case Constant.LOOP_WHAT://支付成功
                    activity.onSuccess(what, "支付成功");
                    break;
                case Constant.SHORT_QUERY_WHAT://单次查询支付成功
                    if (rescode.equals("0000")) {
                        activity.onSuccess(what, "支付成功");
                        cancelTimerTask();
                    } else activity.onFail(what, false, result.toJSONString());
                    break;
                case Constant.SHORT_CANCEL_WHAT://撤销短码订单
                    if (rescode.equals("0000")) {
                        activity.onSuccess(what, "撤销成功");
                    } else {
                        activity.onFail(what, false, result.toJSONString());
                    }
                    break;
                default:

                    break;
            }

        }
    }

    @Override
    protected void onFail(int what, boolean isOK, String failStr) {
        ShortPayActivity activity = weakReference.get();
        if (activity != null) {
            activity.onFail(what, isOK, failStr);
        }
    }
}
