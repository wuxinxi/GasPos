package com.szxb.utils.interceptor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.szxb.entity.SeriaInformation;
import com.szxb.module.home.HomeActivity;

/**
 * 作者: Tangren on 2017/8/3
 * 包名：com.szxb.utils.interceptor
 * 邮箱：996489865@qq.com
 * TODO:全局拦截器
 */
@Interceptor(priority = 100, name = "verify")
public class MyIInterceptor implements IInterceptor {

    @Override
    public void process(final Postcard postcard, final InterceptorCallback callback) {
        if (TextUtils.equals(postcard.getPath(), "/gas/member")
                || TextUtils.equals(postcard.getPath(), "/gas/order")) {
            //如果是支付路由
            //则判断HomeInfoEntity 的支付状态，如果已经支付则询问是否再次支付
            Bundle bundle = postcard.getExtras();
            SeriaInformation infoEntity = bundle.getParcelable("infoEntity");
            if (TextUtils.equals("已支付", infoEntity.getGasPayStatus())) {

                final AlertDialog builder;
                MainLooper.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final AlertDialog builder = new AlertDialog.Builder(HomeActivity.getThis())
                                .setTitle("提示")
                                .setMessage("已完成支付,是否继续?")
                                .setPositiveButton("继续", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        callback.onContinue(postcard);
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        callback.onInterrupt(null);
                                    }
                                }).create();

                        builder.show();

                        builder.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                        builder.getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                            @Override
                            public void onSystemUiVisibilityChange(int visibility) {
                                int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                                        //布局位于状态栏下方
                                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                        //全屏
                                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                                        //隐藏导航栏
                                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
                                if (Build.VERSION.SDK_INT >= 19) {
                                    uiOptions |= 0x00001000;
                                } else {
                                    uiOptions |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
                                }
                                builder.getWindow().getDecorView().setSystemUiVisibility(uiOptions);
                            }
                        });
                    }
                });


            } else callback.onContinue(postcard);
        } else
            callback.onContinue(postcard);

    }

    @Override
    public void init(Context context) {

    }

}
