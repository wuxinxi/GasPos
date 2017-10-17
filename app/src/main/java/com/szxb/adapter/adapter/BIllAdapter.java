package com.szxb.adapter.adapter;

import android.content.Context;

import com.szxb.R;
import com.szxb.adapter.manager.CommonAdapter;
import com.szxb.entity.BillEntity;
import com.szxb.holder.BaseViewHolder;

import java.util.List;

/**
 * 作者: Tangren on 2017/8/5
 * 包名：com.szxb.adapter.adapter
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class BIllAdapter extends CommonAdapter<BillEntity.JourListBean> {

    public BIllAdapter(Context mContext, int mLayoutId, List<BillEntity.JourListBean> mDatas) {
        super(mContext, mLayoutId, mDatas);
    }

    @Override
    protected void convert(BaseViewHolder holder, final BillEntity.JourListBean varListBean, final int position) {
        holder.setText(R.id.orderTime, varListBean.getTRANTIME());
        holder.setText(R.id.orderMoney, "¥ " + varListBean.getAMOUNT());
        holder.setText(R.id.orderNo, varListBean.getHSORDERID());
    }

}
