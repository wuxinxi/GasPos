package szxb.com.m680;

import com.alibaba.android.arouter.launcher.ARouter;

import szxb.com.poslibrary.LibApp;

/**
 * 作者: Tangren on 2017/8/19
 * 包名：szxb.com.m680
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class App extends LibApp {
    @Override
    public void onCreate() {
        super.onCreate();

        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this);
    }
}
