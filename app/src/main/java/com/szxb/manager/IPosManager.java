package com.szxb.manager;

/**
 * 作者: Tangren on 2017-10-10
 * 包名：com.szxb.utils.comm
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public interface IPosManager {

    void setURLIP(String urlip);

    String getURlIP();

    void setSeri0(String no);

    String getSeri0();

    void setSeri1(String no);

    String getSeri1();

    void setMchID(String mch_id);

    String getMchID();

    void setUserNo(String user_no);

    String getUserNo();

    void setVerifyCode(String code);

    String getVerifyCode();

    void setClearZero(boolean var);

    boolean getClearZero();

    void setSupportMember(boolean var);

    boolean getSupportMember();

    String getOrderDec();

    void setShortCode1(String var);

    String getShortCode1();

    void setShortCode2(String var);

    String getShortCode2();

    void setShortCode3(String var);

    String getShortCode3();

    void initK21(boolean fin);

    boolean getInitK21();

    void setFirstStart(boolean yes);

    boolean getFirstStart();

    void setDefaultPay(int var);

    int getDefaultPay();

    void setDevice(String device);

    String getDevice();

    void setVersionName(String versionName);

    String getVersionName();


    void loadFromPrefs();
}
