package com.szxb.widget;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.szxb.R;
import com.szxb.base.BaseDialog;

/**
 * 作者: Tangren on 2017-09-22
 * 包名：com.szxb.widget
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class WaitDialog extends BaseDialog implements View.OnClickListener, DialogInterface.OnKeyListener {

    private CircleProgressBar progressBar;
    private Button disDialog;
    private OnDialogListener listener;
    private boolean cancelable = true;

    public static WaitDialog newInstance(boolean cancelable) {
        WaitDialog waitDialog = new WaitDialog();
        Bundle bundle = new Bundle();
        bundle.putBoolean("cancelable", cancelable);
        waitDialog.setArguments(bundle);
        return waitDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cancelable = getArguments().getBoolean("cancelable");
    }

    @Override
    protected View setView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_item_wait, container, false);
        progressBar = (CircleProgressBar) view.findViewById(R.id.progress);
        disDialog = (Button) view.findViewById(R.id.dis_btn);
        if (cancelable)
            disDialog.setVisibility(View.VISIBLE);
        else disDialog.setVisibility(View.GONE);
        disDialog.setOnClickListener(this);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setOnKeyListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (getDialog() != null && getDialog().isShowing()) {
            progressBar.setVisibility(View.GONE);
            getDialog().dismiss();
            if (listener != null)
                listener.close();
        }
    }

    public void disDialog() {
        if (getDialog() != null && getDialog().isShowing()) {
            progressBar.setVisibility(View.GONE);
            getDialog().dismiss();
        }
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return true;
        return false;
    }

    public interface OnDialogListener {
        void close();
    }

    public void setCloseListener(OnDialogListener listener) {
        this.listener = listener;
    }
}
