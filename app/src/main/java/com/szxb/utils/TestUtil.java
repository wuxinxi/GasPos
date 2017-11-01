package com.szxb.utils;

import com.szxb.entity.SeriaInformation;
import com.szxb.utils.rx.RxBus;

/**
 * 作者: Tangren on 2017-10-19
 * 包名：com.szxb.utils
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class TestUtil {
    private volatile static TestUtil instance = null;

    private TestUtil() {
    }

    public static TestUtil getInstance() {
        if (instance == null) {
            synchronized (TestUtil.class) {
                if (instance == null) {
                    instance = new TestUtil();
                }
            }
        }
        return instance;
    }


    public void send() {
        SeriaInformation seriaInformation = new SeriaInformation();
        seriaInformation.setGasNo(Util.buildRadom(2) + "");
        seriaInformation.setRemark_1(Util.buildRadom(5) + "");
        seriaInformation.setSeriaOrderNo(Util.buildRadom(5) + "");
        seriaInformation.setMachineOrderNo(Util.getOrderNo());
        seriaInformation.setTraType("00");
        seriaInformation.setTraStartTime(DateUtil.getCurrentTime());
        seriaInformation.setLogicalCardNo("00");
        seriaInformation.setCardMoney(Util.buildRadom(3) + "");
        seriaInformation.setGasMoney(Util.buildRadom(4) + "");
        seriaInformation.setTraMoney(Util.buildRadom(4) + "");
        seriaInformation.setPayType("00");
        seriaInformation.setCodeRule(Util.buildRadom(4) + "");
        seriaInformation.setCardType(Util.buildRadom(4) + "");
        seriaInformation.setArdctc(Util.buildRadom(4) + "");
        seriaInformation.setRefuelPoint(Util.buildRadom(4) + "");
        seriaInformation.setPrices(Util.buildRadom(3) + "");
        seriaInformation.setOilCode(Util.buildRadom(4) + "");
        seriaInformation.setFuelingUp(Util.buildRadom(4) + "");
        seriaInformation.setQtyCount(Util.buildRadom(5) + "");
        seriaInformation.setTraEndTime(DateUtil.getCurrentTime());
        seriaInformation.setGasPayStatus("未支付");
        seriaInformation.setCurrentTime(DateUtil.getCurrentDate());
        seriaInformation.setXbOrderNo(Util.getOrderNo());
        RxBus.getInstance().send(seriaInformation);
    }

}
