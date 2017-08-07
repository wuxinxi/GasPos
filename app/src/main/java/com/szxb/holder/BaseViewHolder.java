package com.szxb.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.szxb.interfaces.OnItemClick;


/**
 * 作者: Tangren on 2017/7/2
 * 包名：com.commonlylib.activity
 * 邮箱：996489865@qq.com
 * TODO:普通Activity基类
 */
public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final SparseArray<View> views;

    public View convertView;

    private Context mContext;

    private OnItemClick click;

    public BaseViewHolder(Context context, View itemView, OnItemClick click) {
        super(itemView);
        this.convertView = itemView;
        this.mContext = context;
        this.click = click;
        this.views = new SparseArray<>();

        this.convertView.setOnClickListener(this);
    }

    public View getConvertView() {
        return convertView;
    }

    public static BaseViewHolder createViewHolder(Context context, View itemView, OnItemClick click) {
        return new BaseViewHolder(context, itemView, click);
    }

    public static BaseViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId, OnItemClick click) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new BaseViewHolder(context, itemView, click);
    }

    public BaseViewHolder setText(int viewId, CharSequence value) {
        TextView view = getView(viewId);
        view.setText(value);
        return this;
    }

    public BaseViewHolder setImageResource(int viewId, int imageResId) {
        ImageView view = getView(viewId);
        view.setImageResource(imageResId);
        return this;
    }

    public BaseViewHolder setImageUrl(int viewId, Context context, String url) {
        return null;
    }

    public BaseViewHolder setImageUrl(int viewId, Fragment context, String url) {
        return null;
    }

    public BaseViewHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public BaseViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public BaseViewHolder setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public BaseViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }


    public BaseViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public BaseViewHolder setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

    public BaseViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }


    public BaseViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public BaseViewHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }


    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }


    @Override
    public void onClick(View v) {
        if (click != null)
            click.onItemClick(v, getAdapterPosition());
    }
}
