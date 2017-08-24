package com.szxb.module.bill;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.szxb.base.BasePresenter;
import com.szxb.entity.BillEntity;
import com.szxb.utils.Config;

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

                BillEntity billEntity = new Gson().fromJson(result.toJSONString(), BillEntity.class);
                if (what == Config.BILLNORMAL)
                    activity2.loadSuccess(billEntity.getJourList());
                else if (what == Config.BILLREFRESH)
                    activity2.loadRefreshSuccess(billEntity.getJourList());
                else if (what == Config.BILLMORE)
                    activity2.loadMoreSuccess(billEntity.getJourList());
            }
        }
    }

    @Override
    protected void onFail(int what, String failStr) {

    }
}
