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

}
