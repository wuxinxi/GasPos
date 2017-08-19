package com.szxb.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.szxb.R;
import com.szxb.interfaces.OnCloseDialogListener;

/**
 * 作者：Tangren on 2017/6/8 11:44
 * 邮箱：wu_tangren@163.com
 * TODO:二维码Dialog
 */
public class ImageDialog extends DialogFragment implements View.OnClickListener, DialogInterface.OnKeyListener {

    private ImageView img;

    private CircleProgressBar progressBar;

    private OnCloseDialogListener listener;

    public ImageDialog() {
    }

    public static ImageDialog getImageDialog() {
        ImageDialog imageDialog = new ImageDialog();
        return imageDialog;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(R.color.transparent);
        View view = inflater.inflate(R.layout.view_dialog, container, false);
        img = (ImageView) view.findViewById(R.id.img);
        Button button = (Button) view.findViewById(R.id.dis);
        Button button2 = (Button) view.findViewById(R.id.dis2);
        TextView payStatus = (TextView) view.findViewById(R.id.payStatus);
        progressBar = (CircleProgressBar) view.findViewById(R.id.progress);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        payStatus.setOnClickListener(this);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setOnKeyListener(this);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onStart() {
        super.onStart();
        Window window = this.getDialog().getWindow();
        window.getDecorView()
                .setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.payStatus:
                if (listener != null)
                    listener.onQueryCurrentOrder(getDialog());
                break;
            default:
                if (listener != null)
                    listener.onCloseDialog(getDialog());
                break;
        }

    }

    public void dismiss() {
        if (getDialog() != null && getDialog().isShowing())
            getDialog().dismiss();
    }


    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return true;
        return false;
    }

    public void setImage(Context context, String url) {
        if (img == null)
            throw new NullPointerException("img null");
        else
            Picasso.with(context)
                    .load(url)
                    .error(R.drawable.ic_loadigm_fail)
                    .into(img, new Callback() {
                        @Override
                        public void onSuccess() {
                            hideProgress();
                        }

                        @Override
                        public void onError() {
                            hideProgress();
                        }
                    });
    }


    public void setImage(Bitmap bitmap) {
        showProgress();
        if (img == null)
            throw new NullPointerException("img null");
        else {
            img.setImageBitmap(bitmap);
            hideProgress();
        }
    }


    public void hideProgress() {
        if (progressBar == null)
            throw new NullPointerException("progressBar null");
        else progressBar.setVisibility(View.GONE);
    }
    public void showProgress() {
        if (progressBar == null)
            throw new NullPointerException("progressBar null");
        else progressBar.setVisibility(View.VISIBLE);
    }


    public void setCloseDialogListener(OnCloseDialogListener listener) {
        this.listener = listener;
    }
}
