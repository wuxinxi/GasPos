package com.szxb.adapter.manager;

import android.support.v4.util.SparseArrayCompat;

import com.szxb.holder.BaseViewHolder;


/**
 * 作者: Tangren on 2017/7/4
 * 包名：com.commonlylib.adapter
 * 邮箱：996489865@qq.com
 * TODO:itemType 管理类
 */

public class ItemViewTypeManager<T> {

    SparseArrayCompat<ItemViewType<T>> types = new SparseArrayCompat<>();

    public int getItemViewTypeCount() {
        return types.size();
    }

    /**
     * 添加新的item
     *
     * @param type type
     */
    public ItemViewTypeManager<T> addType(ItemViewType<T> type) {
        int viewType = types.size();
        if (type != null) {
            types.put(viewType, type);
            viewType++;
        }
        return this;
    }

    public ItemViewTypeManager<T> addType(int viewType, ItemViewType<T> type) {
        //判断是否已经存在
        if (types.get(viewType) != null)
            throw new IllegalArgumentException("already exit…… ");
        types.put(viewType, type);
        return this;
    }

    //移除item
    public ItemViewTypeManager<T> removeType(ItemViewType<T> type) {
        if (type == null)
            throw new IllegalArgumentException("type not null……");
        int index = types.indexOfValue(type);
        if (index >= 0)
            types.removeAt(index);
        return this;
    }

    public ItemViewTypeManager<T> removeType(int viewType) {
        int index = types.indexOfKey(viewType);
        if (index >= 0)
            types.removeAt(index);
        return this;
    }

    //获取item
    public int getItemViewType(T item, int position) {
        int typeCount = types.size();
        for (int i = typeCount - 1; i >= 0; i--) {
            ItemViewType<T> delegate = types.valueAt(i);
            if (delegate.isForViewType(item, position)) {
                return types.keyAt(i);
            }
        }
        throw new IllegalArgumentException("no ItemViewType add……position=" + position);
    }

    public void convert(BaseViewHolder holder, T item, int position) {
        int delegatesCount = types.size();
        for (int i = 0; i < delegatesCount; i++) {
            ItemViewType<T> delegate = types.valueAt(i);

            if (delegate.isForViewType(item, position)) {
                delegate.convert(holder, item, position);
                return;
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewType added that matches position=" + position + " in data source");
    }


    public ItemViewType getItemViewDelegate(int viewType) {
        return types.get(viewType);
    }

    public int getItemViewLayoutId(int viewType) {
        return getItemViewDelegate(viewType).getItemViewLayoutID();
    }

    public int getItemViewType(ItemViewType type) {
        return types.indexOfValue(type);
    }

}
