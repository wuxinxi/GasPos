package com.szxb.module.bill;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.szxb.base.BasePresenter;
import com.szxb.entity.BillEntity;
import com.szxb.utils.comm.Constant;

import java.lang.ref.WeakReference;

/**
 * 作者: Tangren on 2017/8/5
 * 包名：com.szxb.module.bill
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class BillPresenter extends BasePresenter {

    private WeakReference<BillActivity2> weakReference;

    public BillPresenter(BillActivity2 activity2) {
        weakReference = new WeakReference<BillActivity2>(activity2);
    }

    @Override
    protected void onAllSuccess(int what, JSONObject result) {
        BillActivity2 activity2 = weakReference.get();
        if (activity2 != null) {
            if (result.getString("rescode").equals("0000")) {
                if (what == Constant.BILLNORMAL) {
                    //流水
                    BillEntity billEntity = new Gson().fromJson(result.toJSONString(), BillEntity.class);
                    activity2.loadSuccess(billEntity.getJourList());
                } else if (what == Constant.REFUND_WHAT) {
                    //退款
                    activity2.onSuccess(Constant.REFUND_WHAT, null);
                }

            } else activity2.onFail(what, false, result.getString("result"));
        }
    }

    @Override
    protected void onFail(int what, boolean isOK, String failStr) {
        BillActivity2 activity2 = weakReference.get();
        if (activity2 != null) {
            activity2.onFail(what, isOK, failStr);
        }
    }
}
