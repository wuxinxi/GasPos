package com.szxb.adapter.adapter;

import android.content.Context;
import android.widget.TextView;

import com.szxb.R;
import com.szxb.adapter.manager.CommonAdapter;
import com.szxb.entity.SeriaInformation;
import com.szxb.holder.BaseViewHolder;
import com.szxb.utils.Util;

import java.util.List;

/**
 * 作者: Tangren on 2017/8/1
 * 包名：com.szxb.adapter.adapter
 * 邮箱：996489865@qq.com
 * TODO:主页Adapter
 */

public class HomeAdapter extends CommonAdapter<SeriaInformation> {

    public HomeAdapter(Context mContext, int mLayoutId, List<SeriaInformation> mDatas) {
        super(mContext, mLayoutId, mDatas);
    }

    @Override
    protected void convert(BaseViewHolder holder, SeriaInformation infoEntity, int position) {
        TextView gasNoView = holder.getView(R.id.gasNo);
        gasNoView.setText(infoEntity.getGasNo());
        String payStatus = infoEntity.getGasPayStatus().trim();
        gasNoView.setBackgroundResource(payStatus.equals("已支付") ? R.drawable.home_grid_item_no_shape : R.drawable.home_grid_item_no_shape2);
        holder.setText(R.id.gasCapacity, Util.getFuelingUp(infoEntity.getFuelingUp()) + "L");
        holder.setText(R.id.gasMoney, Util.getMoney(infoEntity.getGasMoney()));
        holder.setText(R.id.gasPrice, Util.getMoney(infoEntity.getPrices()) + "元/L");
//        holder.setText(R.id.gasPayStatus, infoEntity.getGasPayStatus());
    }
}
