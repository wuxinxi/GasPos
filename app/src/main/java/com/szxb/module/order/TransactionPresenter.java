package com.szxb.module.order;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.szxb.base.BasePresenter;
import com.szxb.utils.comm.Constant;

import java.lang.ref.WeakReference;

/**
 * 作者：Tangren on 2017/6/9 14:08
 * 邮箱：wu_tangren@163.com
 * TODO:一句话描述
 */
public class TransactionPresenter extends BasePresenter {

    private WeakReference<OrderZipActivity> weakReference;

    public TransactionPresenter(OrderZipActivity activity) {
        weakReference = new WeakReference<OrderZipActivity>(activity);
    }

    @Override
    protected void onAllSuccess(int what, JSONObject result) {
        Log.d("TransactionPresenter",
                "success(TransactionPresenter.java:27)" + result.toJSONString());
        OrderZipActivity activity = weakReference.get();
        String rescode;
        if (activity != null) {
            switch (what) {
                case Constant.LOOP_WHAT:
                    activity.onPaySuccess();
                    break;
                case Constant.REQUESTQRCODE_WHAT:
                    rescode = result.getString("rescode");
                    if (TextUtils.equals(rescode, "00")) {
                        if (TextUtils.equals(result.getString("result"), "success")) {
                            String codeUrl = result.getString("codeurl");
                            if (!TextUtils.isEmpty(codeUrl)) {
                                activity.onSuccess(what, codeUrl);
                            } else activity.onFail(what, false, result.toJSONString());
                        } else
                            activity.onFail(what, false, "result!=success\n" + result.toString());
                    } else {
                        activity.onFail(what, false, result.toString());
                    }
                    break;
                case Constant.QUERY_WHAT:
                    rescode = result.getString("rescode");
                    switch (rescode) {
                        case "0000":
                            //手动查询支付成功
                            activity.onPaySuccess();
                            cancelTimerTask();
                            break;
                        case "0001":
                            //支付中不做处理
                            activity.onFail(what, false, "暂未完成支付!");
                            break;
                        default:
                            activity.onFail(what, false, result.toJSONString());
                            break;
                    }
                    break;
                case Constant.CASH_WHAT:
                    rescode = result.getString("rescode");
                    if (rescode.equals("0000")) {
                        activity.onSuccess(what, "提交成功");
                    } else activity.onFail(what, false, "提交失败\n" + result.toJSONString());

                    activity.onSuccess(what, result.toJSONString());
                    break;
                default:

                    break;
            }

        }
    }

    @Override
    protected void onFail(int what, boolean isOK, String failStr) {
        OrderZipActivity activity = weakReference.get();
        if (activity != null)
            activity.onFail(what, isOK, failStr);
    }
}
