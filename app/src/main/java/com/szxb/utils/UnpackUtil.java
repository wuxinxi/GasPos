package com.szxb.utils;

import android.text.TextUtils;
import android.util.Log;

import com.szxb.db.sp.FetchAppConfig;
import com.szxb.entity.SeriaInformation;
import com.szxb.tanker.TrankerInterface;
import com.yanzhenjie.nohttp.Logger;

import static com.szxb.utils.Util.bcd2Str;

/**
 * 作者: Tangren on 2017-09-06
 * 包名：com.szxb.utils
 * 邮箱：996489865@qq.com
 * TODO:解析串口数据
 */

public class UnpackUtil {

    public static SeriaInformation unpack(byte[] recv) {
        try {

            SeriaInformation seriaInformation = new SeriaInformation();
            //油枪号
            byte[] gasNo_bytes = new byte[1];
            System.arraycopy(recv, 1, gasNo_bytes, 0, 1);
            if (TextUtils.equals(TrankerInterface.getResult2String(gasNo_bytes), "0"))
                seriaInformation.setGasNo(FetchAppConfig.ser_0());
            else if (TextUtils.equals(TrankerInterface.getResult2String(gasNo_bytes), "1"))
                seriaInformation.setGasNo(FetchAppConfig.ser_1());
            Logger.d("油枪号：" + TrankerInterface.getResult2String(gasNo_bytes));

            byte[] snNo = new byte[4];
            System.arraycopy(recv, 3, snNo, 0, 4);
            seriaInformation.setRemark_1(TrankerInterface.getResult2String(snNo));
            Logger.d("终端号:" + TrankerInterface.getResult2String(snNo));

            //交易流水号
            byte[] gasOrder_bytes = new byte[4];
            System.arraycopy(recv, 7, gasOrder_bytes, 0, 4);
            seriaInformation.setSeriaOrderNo(TrankerInterface.getResult2String(gasOrder_bytes));
            Logger.d("交易流水号：" + TrankerInterface.getResult2String(gasOrder_bytes));

            //油机流水号
            byte[] gasNoOrder_bytes = new byte[2];
            System.arraycopy(recv, 11, gasNoOrder_bytes, 0, 2);
            seriaInformation.setMachineOrderNo(TrankerInterface.getResult2String(gasNoOrder_bytes));
            Logger.d("油枪流水号：" + TrankerInterface.getResult2String(gasNoOrder_bytes));

            //交易类型
            byte[] type = new byte[1];
            System.arraycopy(recv, 13, type, 0, 1);
            seriaInformation.setTraType(TrankerInterface.getResult2String(type));
            Logger.d("交易类型：" + TrankerInterface.getResult2String(type));

            //交易时间
            byte[] time = new byte[7];
            System.arraycopy(recv, 14, time, 0, 7);
            seriaInformation.setTraStartTime(Util.bcd2Str(time));
            Logger.d("交易时间：" + Util.bcd2Str(time));

            //逻辑卡号
            byte[] cardNo = new byte[10];
            System.arraycopy(recv, 21, cardNo, 0, 10);
            seriaInformation.setLogicalCardNo(Util.bcd2Str(cardNo));
            Logger.d("逻辑卡号:" + Util.bcd2Str(cardNo));

            //卡金额
            byte[] bal = new byte[4];
            System.arraycopy(recv, 31, bal, 0, 4);
            seriaInformation.setCardMoney(TrankerInterface.getResult2String(bal));
            Logger.d("卡金额：" + TrankerInterface.getResult2String(bal));

            //加油金额
            byte[] gasMoney_bytes = new byte[3];
            System.arraycopy(recv, 35, gasMoney_bytes, 0, 3);
            seriaInformation.setGasMoney(TrankerInterface.getResult2String(gasMoney_bytes));
            Logger.d("加油金额：" + TrankerInterface.getResult2String(gasMoney_bytes));

            //交易金额
            byte[] trans_bytes = new byte[3];
            System.arraycopy(recv, 38, trans_bytes, 0, 3);
            seriaInformation.setTraMoney(TrankerInterface.getResult2String(trans_bytes));
            Logger.d("交易金额：" + TrankerInterface.getResult2String(trans_bytes));

            //付款方式
            byte[] payType = new byte[1];
            System.arraycopy(recv, 41, payType, 0, 1);
            seriaInformation.setPayType(TrankerInterface.getResult2String(payType));
            Logger.d("付款方式:" + TrankerInterface.getResult2String(payType));

            //优惠代码规则
            byte[] discount_no = new byte[2];
            System.arraycopy(recv, 42, discount_no, 0, 2);
            seriaInformation.setCodeRule(TrankerInterface.getResult2String(discount_no));
            Logger.d("优惠代码规则:" + TrankerInterface.getResult2String(discount_no));

            //卡类型
            byte[] cardtype = new byte[1];
            System.arraycopy(recv, 44, cardtype, 0, 1);
            seriaInformation.setCardType(TrankerInterface.getResult2String(cardtype));
            Logger.d("卡类型:" + TrankerInterface.getResult2String(cardtype));

            //卡片交易计数器
            byte[] ardctc = new byte[2];
            System.arraycopy(recv, 45, ardctc, 0, 2);
            seriaInformation.setArdctc(TrankerInterface.getResult2String(ardctc));
            Logger.d("卡片交易计数器:" + TrankerInterface.getResult2String(ardctc));

            //加油点
            byte[] fuelcode = new byte[2];
            System.arraycopy(recv, 47, fuelcode, 0, 2);
            seriaInformation.setRefuelPoint(TrankerInterface.getResult2String(fuelcode));
            Logger.d("加油点:" + TrankerInterface.getResult2String(fuelcode));

            //单价
            byte[] prices_bytes = new byte[2];
            System.arraycopy(recv, 49, prices_bytes, 0, 2);
            seriaInformation.setPrices(TrankerInterface.getResult2String(prices_bytes));
            Logger.d("单价：" + TrankerInterface.getResult2String(prices_bytes));

            //油品编码
            byte[] g_code = new byte[2];
            System.arraycopy(recv, 51, g_code, 0, 2);
            seriaInformation.setOilCode(TrankerInterface.getResult2String(g_code));
            Logger.d("油品编码:" + TrankerInterface.getResult2String(g_code));

            //加油升数
            byte[] gasCapacity_bytes = new byte[3];
            System.arraycopy(recv, 53, gasCapacity_bytes, 0, 3);
            seriaInformation.setFuelingUp(TrankerInterface.getResult2String(gasCapacity_bytes));
            Logger.d("加油升数：" + TrankerInterface.getResult2String(gasCapacity_bytes));

            //升累计
            byte[] gasCount_bytes = new byte[4];
            System.arraycopy(recv, 56, gasCount_bytes, 0, 4);
            seriaInformation.setQtyCount(TrankerInterface.getResult2String(gasCount_bytes));
            Logger.d("升累计：" + TrankerInterface.getResult2String(gasCount_bytes));

            //交易结束时
            byte[] end_transtime = new byte[7];
            System.arraycopy(recv, 60, end_transtime, 0, 7);
            seriaInformation.setTraEndTime(bcd2Str(end_transtime));
            Logger.d("交易结束时间：" + bcd2Str(end_transtime));

            //支付状态
            seriaInformation.setGasPayStatus("未支付");
            //当前时间
            seriaInformation.setCurrentTime(DateUtil.getCurrentDate());
            //小兵流水
            seriaInformation.setXbOrderNo(Util.getOrderNo());

            return seriaInformation;
        } catch (Exception e) {
            e.printStackTrace();

            Log.d("UnpackUtil",
                    "unpack(UnpackUtil.java:27)" + e.toString());
            return null;
        }
    }
}
