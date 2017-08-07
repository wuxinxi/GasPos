package com.szxb.adapter.manager;

import android.content.Context;
import android.view.LayoutInflater;

import com.szxb.holder.BaseViewHolder;

import java.util.List;

/**
 * 作者: Tangren on 2017/7/4
 * 包名：com.commonlylib.adapter
 * 邮箱：996489865@qq.com
 * TODO:通用的Adapter
 */

public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {

    protected int mLayoutId;

    protected List<T> mDatas;

    protected LayoutInflater mInflater;

    public CommonAdapter(Context mContext, final int mLayoutId, List<T> mDatas) {
        super(mContext, mDatas);
        mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
        this.mLayoutId = mLayoutId;

        addItemViewDelegate(new ItemViewType<T>() {
            @Override
            public int getItemViewLayoutID() {
                return mLayoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(BaseViewHolder holder, T t, int position) {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    public CommonAdapter(Context mContext, final int mLayoutId) {
        super(mContext);
        mInflater = LayoutInflater.from(mContext);
        this.mLayoutId = mLayoutId;

        addItemViewDelegate(new ItemViewType<T>() {
            @Override
            public int getItemViewLayoutID() {
                return mLayoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(BaseViewHolder holder, T t, int position) {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }


    protected abstract void convert(BaseViewHolder holder, T t, int position);


}
