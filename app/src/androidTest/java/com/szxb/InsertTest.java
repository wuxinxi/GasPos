package com.szxb;

import android.support.test.runner.AndroidJUnit4;

import com.szxb.db.manager.DBCore;
import com.szxb.entity.HomeInfoEntity;
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
        HomeInfoEntity infoEntity = new HomeInfoEntity();
        infoEntity.setGasNo("1");
        infoEntity.setGasMemberMoney("10");
        infoEntity.setGasPayStatus("未支付");
        infoEntity.setGasMoney("20");
        infoEntity.setGasCapacity("100L");
        infoEntity.setGasUnitPrice("5");
        infoEntity.setGasDate(DateUtil.getCurrentDate());
        infoEntity.setGasOrderNo(Util.getOrderNo());
        infoEntity.setGasMemberUnitPrice("10");
        infoEntity.setXbOrderNo(Util.getOrderNo());
        infoEntity.setGasTime(DateUtil.getCurrentTime());
        DBCore.getDaoSession().getHomeInfoEntityDao().insert(infoEntity);
    }
}
