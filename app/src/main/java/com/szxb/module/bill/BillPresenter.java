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

    private WeakReference<NetWorkBillFragment> weakReference;

    public BillPresenter(NetWorkBillFragment netWorkBillFragment) {
        weakReference = new WeakReference<NetWorkBillFragment>(netWorkBillFragment);
    }

    @Override
    protected void onAllSuccess(int what, JSONObject result) {
        NetWorkBillFragment fragment = weakReference.get();
        if (fragment != null && result.getJSONArray("varList").size() > 0) {
            BillEntity billEntity = new Gson().fromJson(result.toJSONString(), BillEntity.class);
            if (what == Config.BILLNORMAL)
                fragment.loadSuccess(billEntity.getVarList());
            else if (what == Config.BILLREFRESH)
                fragment.loadRefreshSuccess(billEntity.getVarList());
            else if (what == Config.BILLMORE)
                fragment.loadMoreSuccess(billEntity.getVarList());
        }
    }

    @Override
    protected void onFail(String failStr) {

    }
}
