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
        String rescode;
        if (activity != null) {
            switch (what) {
                case Config.LOOP_WHAT:
                    activity.onPaySuccess();
                    break;
                case Config.REQUESTQRCODE_WHAT:
                    rescode = result.getString("rescode");
                    if (TextUtils.equals(rescode, "00")) {
                        if (TextUtils.equals(result.getString("result"), "success"))
                            activity.onSuccess(what, result.getString("codeurl"));
                        else activity.onFail(what, "result!=success\n" + result.toString());
                    } else {
                        activity.onFail(what, result.toString());
                    }
                    break;
                case Config.QUERY_WHAT:
                    rescode = result.getString("rescode");
                    switch (rescode) {
                        case "0000":
                            activity.onPaySuccess();
                            break;
                        case "0001":
                            //支付中不做处理
                            activity.onFail(what,"还在支付中!");
                            break;
                        default:
                            activity.onFail(what, result.toJSONString());
                            break;
                    }
                    break;
                default:

                    break;
            }

        }
    }

    @Override
    protected void onFail(int what, String failStr) {
        OrderActivity activity = weakReference.get();
        if (activity != null)
            activity.onFail(what, failStr);
    }
}
