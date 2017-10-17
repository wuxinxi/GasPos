package com.szxb;

import android.support.test.runner.AndroidJUnit4;

import com.szxb.db.manager.DBCore;
import com.szxb.entity.SeriaInformation;
import com.szxb.utils.DateUtil;
import com.szxb.utils.Util;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 作者: Tangren on 2017/8/7
 * 包名：com.szxb
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
@RunWith(AndroidJUnit4.class)
public class InsertTest {

    @Test
    public void insert() throws Exception {
        SeriaInformation infoEntity = new SeriaInformation();
        infoEntity.setGasNo("1");
        infoEntity.setGasPayStatus("未支付");
        infoEntity.setGasMoney("20");
        infoEntity.setFuelingUp("100L");
        infoEntity.setPrices("5");
        infoEntity.setCurrentTime(DateUtil.getCurrentDate());
        infoEntity.setSeriaOrderNo(Util.getOrderNo());
        infoEntity.setXbOrderNo(Util.getOrderNo());
        infoEntity.setTraStartTime(DateUtil.getCurrentTime());
        DBCore.getDaoSession().getSeriaInformationDao().insert(infoEntity);
    }
}
