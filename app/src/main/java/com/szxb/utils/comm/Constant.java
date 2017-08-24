package com.szxb.utils.comm;

/**
 * 作者: Tangren on 2017/8/17
 * 包名：com.szxb.utils.comm
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class Constant {

    public static final String URL = "http://120.24.212.72:8080";

    //登录
    public static final String LOGIN_URL = URL + "/bippay/interaction/emp_Login";

    //支付
    public static final String PAY_URL = URL + "/bippay/interaction/hsScanPay";

    //现金、银行卡、IC卡交易
    public static final String CASH_URL = URL + "/bippay/interaction/transfer";

    //查询接口
    public static final String QUERY_URL = URL + "/bippay/interaction/scan_Orderquery";

    //退款接口
    public static final String REFUND_URL = URL + "/bippay/interaction/hsReturn";

    //交易记录
    public static final String ORDER_URL = URL + "/bippay/interaction/jourList";


    public static final int REQUESTQRCODE_WHAT = 1;
    public static final int LOOP_WHAT = 2;

    public static final int REFUND_WHAT = 6;
    public static final int QUERY_WHAT = 7;
    public static final int BILLNORMAL = 3;//正常加载
    public static final int BILLREFRESH = 4;//刷新
    public static final int BILLMORE = 5;//加载更多
}
