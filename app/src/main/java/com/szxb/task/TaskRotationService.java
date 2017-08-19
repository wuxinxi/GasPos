package com.szxb.task;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.szxb.entity.HomeInfoEntity;
import com.szxb.tanker.TrankerInterface;
import com.szxb.utils.Util;
import com.szxb.utils.rx.RxBus;
import com.yanzhenjie.nohttp.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
                    byte[] recv = new byte[1024];
                    int ret = TrankerInterface.getTrankerData(recv);
                    if (0 <= ret) {
                        Log.i("TaskRotationService-----", "收到数据：" + String.format("%02x", recv[0]));
                        if (0x1a == recv[0]) {
                            //油枪号
                            byte[] gasNo_bytes = new byte[1];
                            System.arraycopy(recv, 1, gasNo_bytes, 0, 1);
                            String currentTime = format.format(Calendar.getInstance().getTime());
                            Logger.d("油枪号：" + currentTime);

                            //交易流水号
                            byte[] gasOrder_bytes = new byte[4];
                            System.arraycopy(recv, 7, gasOrder_bytes, 0, 4);
                            Logger.d("交易流水号：" + TrankerInterface.getResult2String(gasOrder_bytes));

                            //油机流水号
                            byte[] gasNoOrder_bytes = new byte[2];
                            System.arraycopy(recv, 11, gasNoOrder_bytes, 0, 2);
                            Logger.d("油枪流水号：" + TrankerInterface.getResult2String(gasNoOrder_bytes));

                            //加油金额
                            byte[] gasMoney_bytes = new byte[3];
                            System.arraycopy(recv, 35, gasMoney_bytes, 0, 3);
                            Logger.d("加油金额：" + TrankerInterface.getResult2String(gasMoney_bytes));

                            //交易金额
                            byte[] trans_bytes = new byte[3];
                            System.arraycopy(recv, 38, trans_bytes, 0, 3);
                            Logger.d("交易金额：" + TrankerInterface.getResult2String(trans_bytes));

                            //单价
                            byte[] prices_bytes = new byte[2];
                            System.arraycopy(recv, 49, prices_bytes, 0, 2);
                            Logger.d("单价：" + TrankerInterface.getResult2String(prices_bytes));

                            //加油升数
                            byte[] gasCapacity_bytes = new byte[3];
                            System.arraycopy(recv, 53, gasCapacity_bytes, 0, 3);
                            Logger.d("加油升数：" + TrankerInterface.getResult2String(gasCapacity_bytes));


                            HomeInfoEntity homeData = new HomeInfoEntity();
                            homeData.setGasNo(TrankerInterface.getResult2String(gasNo_bytes));
                            homeData.setGasPayStatus("未支付");
//                            homeData.setGasCapacity(Util.String2Float2String(TrankerInterface.getResult2String(gasCapacity_bytes)));
                            homeData.setGasCapacity(Util.buildRadom(2) + "");
                            homeData.setGasMoney(Util.String2Float2String(TrankerInterface.getResult2String(gasMoney_bytes)));
                            homeData.setGasUnitPrice(Util.String2Float2String(TrankerInterface.getResult2String(prices_bytes)));
//                            homeData.setGasOrderNo(TrankerInterface.getResult2String(gasOrder_bytes));
                            homeData.setGasOrderNo(Util.getOrderNo());
                            homeData.setXbOrderNo(Util.getOrderNo());

                            RxBus.getInstance().send(homeData);
                        }
                    }
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
        Log.d("TaskRotationService",
                "onDestroy(TaskRotationService.java:120)停止串口通信");
        if (!service.isShutdown())
            service.shutdown();
    }
}
