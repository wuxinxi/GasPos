package com.szxb.utils;


public class Config {


    private static final String IP = "http://120.24.212.72:8080";

    public static final String SCANPAY_URL = IP + "/bippay/interaction/hsScanPay";

    public static final String QUERY_URL = IP + "/bippay/interaction/queryorder";

    public static final String LOGIN_URL = IP + "/bippay/interaction/emp_Login";


    public static final String REFUND_RUL = IP + "/bippay/interaction/mlogin";

    public static final int REQUESTQRCODE_WHAT = 1;

    public static final int LOOP_WHAT = 2;

    public static final int REFUND_WHAT = 6;
    public static final int QUERY_WHAT = 7;
    public static final int BILLNORMAL = 3;//正常加载
    public static final int BILLREFRESH = 4;//刷新
    public static final int BILLMORE = 5;//加载更多

}
