package com.szxb.utils.comm;

import com.szxb.App;

/**
 * 作者: Tangren on 2017-10-14
 * 包名：com.szxb.utils.comm
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class UrlComm {

    private volatile static UrlComm instance = null;

    private UrlComm() {
    }

    public static UrlComm getInstance() {
        if (instance == null) {
            synchronized (UrlComm.class) {
                if (instance == null) {
                    instance = new UrlComm();
                }
            }
        }
        return instance;
    }

    private String IP() {
        return "http://" + App.getPosManager().getURlIP();
    }

    //支付
    public String PAYURL() {
        return IP() + "/bipjas/interaction/hsScanPay";
    }

    //登录
    public String LOGINURL() {
        return IP() + "/bipjas/interaction/emp_Login";
    }

    //会员登录
    public String MEMBERLOGINURL() {
        return IP() + "/bipjas/interaction/memberCheck";
    }

    //现金、IC……
    public String CASHURL() {
        return IP() + "/bipjas/interaction/transfer";
    }

    //扫码订单查询
    public String QUERYURL() {
        return IP() + "/bipjas/interaction/scan_Orderquery";
    }

    //退款接口
    public String REFUNDURL() {
        return IP() + "/bipjas/interaction/hsReturn";
    }

    //交易记录
    public String ORDERURL() {
        return IP() + "/bipjas/interaction/jourList";
    }

    //短码付地址
    public String SHORTURL() {
        return IP() + "/bipjas/interaction/creatOrder";
    }

    //短码订单查询
    public String SHORTORDERQUERYURL() {
        return IP() + "/bipjas/interaction/orderResult";
    }

    //短码取消订单接口
    public String ORDERCANCEURL() {
        return IP() + "/bipjas/interaction/orderCancel";
    }

    //检查版本更新
    public String CHECKVERSIONURL() {
        return IP() + "/bipjas/interaction/PosVersionCheck";
    }



}
