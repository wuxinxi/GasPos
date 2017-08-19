package com.szxb.utils.interceptor;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

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

        callback.onContinue(postcard);

    }

    @Override
    public void init(Context context) {

    }

}
