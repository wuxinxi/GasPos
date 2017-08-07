package com.szxb.adapter.adapter;

import android.content.Context;

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

public class BIllAdapter extends CommonAdapter<BillEntity.VarListBean> {

    public BIllAdapter(Context mContext, int mLayoutId, List<BillEntity.VarListBean> mDatas) {
        super(mContext, mLayoutId, mDatas);
    }

    @Override
    protected void convert(BaseViewHolder holder, BillEntity.VarListBean varListBean, int position) {

    }
}
