package com.szxb.db.sp;


import com.szxb.utils.MD5;

/**
 * 作者: Tangren on 2017/7/12
 * 包名：com.szxb.onlinbus.util
 * 邮箱：996489865@qq.com
 * TODO:获取全局的SP数据
 */

public class FetchAppConfig {

    //获取
    public static String mchId() {
        return (String) CommonSharedPreferences.get("mch_id", "");
    }

    //获取appId
    public static String mchPsw() {
        return (String) CommonSharedPreferences.get("mch_psw", "");
    }

    //安全码
    public static String verifyCode() {
        return (String) CommonSharedPreferences.get("verifyCode", MD5.md5("888888"));
    }

    //员工编号
    public static String userNo() {
        return (String) CommonSharedPreferences.get("user_no", "");
    }

    public static String ip() {
        return (String) CommonSharedPreferences.get("ip", "");
    }

    //串口0
    public static String ser_0() {
        return (String) CommonSharedPreferences.get("seri_0", "0");
    }

    //串口1
    public static String ser_1() {
        return (String) CommonSharedPreferences.get("seri_1", "1");
    }

    //抹零
    public static boolean getClearZero() {
        return (boolean) CommonSharedPreferences.get("clearZero", false);
    }

    //支持会员支付
    public static boolean getSupportMember() {
        return (boolean) CommonSharedPreferences.get("supportMember", true);
    }


    //短码1
    public static String getShortCode1() {
        return (String) CommonSharedPreferences.get("short1", "");
    }

    //短码2
    public static String getShortCode2() {
        return (String) CommonSharedPreferences.get("short2", "");
    }

    //短码3
    public static String getShortCode3() {
        return (String) CommonSharedPreferences.get("short3", "");
    }

    //是否已经更新了K21
    public static boolean getInitK21() {
        return (boolean) CommonSharedPreferences.get("initK21", false);
    }

    //是否是第一次启动
    public static boolean getFirstStart() {
        return (boolean) CommonSharedPreferences.get("firstStart", true);
    }

    //默认扫码支付方式：0微信，1支付宝,2用户自己选择
    public static int getDefaultPay() {
        return (Integer) CommonSharedPreferences.get("type", 2);
    }


    //版本名称
    public static String getVersionName() {
        return (String) CommonSharedPreferences.get("versionName", "1.0.0");
    }
}
