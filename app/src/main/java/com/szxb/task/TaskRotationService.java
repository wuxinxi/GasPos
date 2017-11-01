package com.szxb.task;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.szxb.App;
import com.szxb.entity.SeriaInformation;
import com.szxb.tanker.TrankerInterface;
import com.szxb.utils.UnpackUtil;
import com.szxb.utils.rx.RxBus;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 作者: Tangren on 2017/7/31
 * 包名：com.szxb.task
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class TaskRotationService extends Service {

    private ScheduledExecutorService service;

    private SimpleDateFormat format = new SimpleDateFormat("m:ss-SSS", new Locale("zh", "CN"));

    private StringBuffer temOrderNo = new StringBuffer("0000");

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    if (RxBus.getInstance().hasObservers() && App.getPosManager().getInitK21()) {
                        byte[] recv = new byte[1024];
                        int ret = TrankerInterface.getTrankerData(recv);
                        Log.d("TaskRotationService",
                                "run(TaskRotationService.java:51)" + ret);
                        if (0 <= ret) {
                            if (0x1a == recv[0]) {
                                SeriaInformation unpack = UnpackUtil.unpack(recv);
                                Log.d("TaskRotationService",
                                        "run(TaskRotationService.java:59)" + unpack);
                                if (unpack != null)
                                    RxBus.getInstance().send(unpack);
                            }
                        }
                    } else {
                        Log.d("TaskRotationService",
                                "run(TaskRotationService.java:61)还未完成订阅");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1, 200, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!service.isShutdown())
            service.shutdown();
    }


}
