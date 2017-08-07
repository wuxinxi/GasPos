package com.szxb.task;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.szxb.entity.HomeInfoEntity;
import com.szxb.utils.Util;
import com.szxb.utils.rx.RxBus;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    private byte[] temByte = new byte[1024];

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", new Locale("zh", "CN"));
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (RxBus.getInstance().hasObservers()) {
                    HomeInfoEntity entity = new HomeInfoEntity();
                    entity.setGasOrderNo(format.format(new Date()));
                    entity.setGasMoney("20");
                    entity.setGasPayStatus("未支付");
                    entity.setGasMemberMoney("15");
                    entity.setGasNo("01");
                    entity.setGasCapacity(Util.buildRadom(3)+"");
                    entity.setXbOrderNo(System.currentTimeMillis() + "");
                    entity.setGasPayStatus("未支付");
                    RxBus.getInstance().send(entity);
                } else {
                    Log.d("TaskRotationService",
                            "run(TaskRotationService.java:61)还未完成订阅");
                }
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!service.isShutdown())
            service.shutdown();
    }
}
