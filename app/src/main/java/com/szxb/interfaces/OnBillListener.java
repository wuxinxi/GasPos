package com.szxb.interfaces;

import com.szxb.entity.BillEntity;

/**
 * 作者: Tangren on 2017/8/22
 * 包名：com.szxb.interfaces
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public interface OnBillListener {
    void onRefund(BillEntity.JourListBean varListBean,int position);

    void onQueryRefund(BillEntity.JourListBean varListBean,int position);

    void onPrint(BillEntity.JourListBean varListBean,int position);
}
