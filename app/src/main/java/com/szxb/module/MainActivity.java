package com.szxb.module;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.szxb.R;
import com.szxb.utils.Util;
import com.szxb.widget.PayStatusView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.qr_code)
    ImageView qrCode;
    @BindView(R.id.pay_result)
    PayStatusView payStatusView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_new);
        ButterKnife.bind(this);
        Button button = (Button) findViewById(R.id.wechat_pay);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.create(new Observable.OnSubscribe<Bitmap>() {
                    @Override
                    public void call(Subscriber<? super Bitmap> subscriber) {
                        Bitmap bitmap = Util.Create2DCode(System.currentTimeMillis() + "", 600, 600);
                        subscriber.onNext(bitmap);
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Bitmap>() {
                            @Override
                            public void call(Bitmap bitmap) {
                                qrCode.setImageBitmap(bitmap);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                qrCode.setImageDrawable(getResources().getDrawable(R.mipmap.load_fail));
                            }
                        });

            }
        });

        Button button1 = (Button) findViewById(R.id.ali_pay);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payStatusView.loadFailure();
            }
        });

        Button button2 = (Button) findViewById(R.id.cash_pay);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payStatusView.loadSuccess();
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT > 19) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
