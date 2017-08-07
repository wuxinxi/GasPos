package com.szxb.utils.router;

import com.alibaba.android.arouter.launcher.ARouter;
import com.szxb.R;

/**
 * 作者: Tangren on 2017/8/3
 * 包名：com.szxb.utils.router
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class Router {

    //直接跳转,可能有拦截
    public static void jump(String url) {
        ARouter.getInstance()
                .build(url)
                .withTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain)
                .navigation();
    }

    //绿色跳转无拦截
    public static void jumpL(String url) {
        ARouter.getInstance()
                .build(url)
                .withTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain)
                .greenChannel()
                .navigation();
    }
}
