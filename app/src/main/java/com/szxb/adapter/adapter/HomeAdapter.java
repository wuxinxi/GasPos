package com.szxb.adapter.adapter;

import android.content.Context;

import com.szxb.R;
import com.szxb.adapter.manager.CommonAdapter;
import com.szxb.entity.HomeInfoEntity;
import com.szxb.holder.BaseViewHolder;

import java.util.List;

/**
 * 作者: Tangren on 2017/8/1
 * 包名：com.szxb.adapter.adapter
 * 邮箱：996489865@qq.com
 * TODO:主页Adapter
 */

public class HomeAdapter extends CommonAdapter<HomeInfoEntity> {

    public HomeAdapter(Context mContext, int mLayoutId, List<HomeInfoEntity> mDatas) {
        super(mContext, mLayoutId, mDatas);
    }

    @Override
    protected void convert(BaseViewHolder holder, HomeInfoEntity infoEntity, int position) {
        holder.setText(R.id.gasNo, infoEntity.getGasNo());
        holder.setText(R.id.gasCapacity, infoEntity.getGasCapacity());
        holder.setText(R.id.gasMoney, infoEntity.getGasMoney());
        holder.setText(R.id.gasMemberMoney, infoEntity.getGasMemberMoney());
        holder.setText(R.id.gasPayStatus, infoEntity.getGasPayStatus());
    }
}
