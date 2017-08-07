package com.szxb.utils;


public class Config {

    public static final String SCANPAY_URL = "http://139.199.158.253:8080/bippay/interaction/scanpay";

    public static final String LOOP_URL = "http://139.199.158.253:8080/bippay/interaction/queryorder";

    public static final String LOGIN_RUL = "http://139.199.158.253:8080/bippay/interaction/mlogin";

    public static final int REQUESTQRCODE_WHAT = 1;

    public static final int LOOP_WHAT = 2;

    public static final int BILLNORMAL = 3;//正常加载
    public static final int BILLREFRESH = 4;//刷新
    public static final int BILLMORE = 5;//加载更多

}
