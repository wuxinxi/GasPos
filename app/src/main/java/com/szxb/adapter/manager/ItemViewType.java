package com.szxb.adapter.manager;


import com.szxb.holder.BaseViewHolder;

/**
 * 作者: Tangren on 2017/7/4
 * 包名：com.commonlylib.adapter
 * 邮箱：996489865@qq.com
 * TODO:item类型
 */

public interface ItemViewType<T> {

    int getItemViewLayoutID();

    boolean isForViewType(T item, int position);

    void convert(BaseViewHolder holder, T t, int position);
}
