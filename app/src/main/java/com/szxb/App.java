package com.szxb;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.zhoukai.modemtooltest.ModemToolTest;
import com.szxb.db.manager.DBCore;
import com.szxb.manager.IPosManager;
import com.szxb.manager.PosManager;
import com.szxb.utils.AppUtil;
import com.szxb.utils.comm.Constant;
import com.szxb.xblog.AndroidLogAdapter;
import com.szxb.xblog.CsvFormatStrategy;
import com.szxb.xblog.DiskLogAdapter;
import com.szxb.xblog.FormatStrategy;
import com.szxb.xblog.PrettyFormatStrategy;
import com.szxb.xblog.XBLog;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
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

        CrashReport.initCrashReport(getApplicationContext(), "b30e070993", true);

        manager = new PosManager();
        manager.loadFromPrefs();
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this);

        //获取设备号
        String device = ModemToolTest.getItem(Constant.MEMBER_LOGIN_WHAT);
        manager.setDevice(device == null ? "000000" : device);

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

        //检查本地是否存在补丁
        SophixManager.getInstance().queryAndLoadNewPatch();
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


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        SophixManager.getInstance().setContext(this)
                .setAppVersion(AppUtil.getVersionName(base))
                .setAesKey(null)
                .setSecretMetaData(AppUtil.AL_APPID, AppUtil.AL_APPSECRET, AppUtil.AL_RSASECRET)
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        // 补丁加载回调通知
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载成功
                            Log.d("App",
                                    "onLoad(App.java:116)补丁加载成功且立即生效");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁，详见1.3.2.3
                            Log.d("App",
                                    "onLoad(App.java:121)补丁加载成功下次重启生效");
                        } else {
                            // 其它错误信息, 查看PatchStatus类说明
                            Log.d("App",
                                    "onLoad(App.java:124)补丁加载失败" + code);
                        }
                    }
                }).initialize();

    }
}
