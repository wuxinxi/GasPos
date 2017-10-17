package com.szxb;

import com.alibaba.android.arouter.launcher.ARouter;
import com.szxb.db.manager.DBCore;
import com.szxb.utils.AppUtil;
import com.szxb.utils.comm.IPosManager;
import com.szxb.utils.comm.PosManager;
import com.szxb.xblog.AndroidLogAdapter;
import com.szxb.xblog.CsvFormatStrategy;
import com.szxb.xblog.DiskLogAdapter;
import com.szxb.xblog.FormatStrategy;
import com.szxb.xblog.PrettyFormatStrategy;
import com.szxb.xblog.XBLog;
import com.tencent.bugly.crashreport.CrashReport;

import szxb.com.poslibrary.LibApp;

/**
 * 作者: Tangren on 2017/7/26
 * 包名：com.szxb
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class App extends LibApp {

    private static App instance = null;

    private static final String DB_NAME = "gasPos";
    private static PosManager manager;
    private boolean saveLog = false;

    public static App getInstance() {
        synchronized (App.class) {
            if (instance == null) {
                instance = new App();
            }
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        DBCore.init(this, DB_NAME);

        CrashReport.initCrashReport(getApplicationContext(), "3356395c44", true);
        CrashReport.putUserData(getApplicationContext(), "device_no", "00000000");

        manager = new PosManager();
        manager.loadFromPrefs();
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this);

        AppUtil.syncISDebug(this);
//        if (AppUtil.isDebug()) {
//            //日志操作
//            initLog();
//        }

        initLog(true);
//
//        Cockroach.install(new Cockroach.ExceptionHandler() {
//            @Override
//            public void handlerException(final Thread thread, final Throwable throwable) {
//                new Handler(Looper.getMainLooper()).post(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            XBLog.d("run(App.java:70)" + "--->CockroachException:" + thread + "<---", throwable.getMessage());
//                            Log.d("App",
//                                    "run(App.java:70)" + throwable.getMessage());
////                            Tip.show(App.this, "Exception Happend\n" + thread + "\n" + throwable.toString(), false);
//                        } catch (Throwable e) {
//
//                        }
//                    }
//                });
//            }
//        });
    }


    public static IPosManager getPosManager() {
        if (manager == null) {
            manager = new PosManager();
            manager.loadFromPrefs();
        }
        return manager;
    }

    private void initLog() {
        //日志策略
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag("加油日志信息:")
                .build();
        //本地打印日志
        XBLog.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    //日志处理
    public static void initLog(boolean printLog) {
        if (printLog) {
            FormatStrategy formatStrategysave = CsvFormatStrategy.newBuilder()
                    .tag("加油日志信息:")
                    .build();
            //日志本地同步到文件
            XBLog.addLogAdapter(new DiskLogAdapter(formatStrategysave));
        } else XBLog.clearLogAdapters();
    }
}
