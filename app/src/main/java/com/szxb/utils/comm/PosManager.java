package com.szxb.utils.comm;

import com.szxb.db.sp.CommonSharedPreferences;
import com.szxb.db.sp.FetchAppConfig;

/**
 * 作者: Tangren on 2017-10-10
 * 包名：com.szxb.utils.comm
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class PosManager implements IPosManager {

    private String urlip;
    private String seri0;
    private String seri1;
    private String mch_id;
    private String user_no;
    private String code;
    private boolean clearZero;
    private boolean supportMember;
    private String shortCode1;
    private String shortCode2;
    private String shortCode3;
    private boolean initK21;
    private boolean firstStart;
    private int defaultPay;

    @Override
    public void loadFromPrefs() {

        urlip = FetchAppConfig.ip();
        seri0 = FetchAppConfig.ser_0();
        seri1 = FetchAppConfig.ser_1();
        mch_id = FetchAppConfig.mchId();
        user_no = FetchAppConfig.userNo();
        code = FetchAppConfig.verifyCode();
        clearZero = FetchAppConfig.getClearZero();
        supportMember = FetchAppConfig.getSupportMember();
        shortCode1 = FetchAppConfig.getShortCode1();
        shortCode2 = FetchAppConfig.getShortCode2();
        shortCode3 = FetchAppConfig.getShortCode3();
        initK21 = FetchAppConfig.getInitK21();
        firstStart = FetchAppConfig.getFirstStart();
        defaultPay = FetchAppConfig.getDefaultPay();

    }

    @Override
    public void setURLIP(String urlip) {
        this.urlip = urlip;
        CommonSharedPreferences.put("ip", urlip);
    }

    @Override
    public String getURlIP() {
        return urlip;
    }

    @Override
    public void setSeri0(String no) {
        this.seri0 = no;
        CommonSharedPreferences.put("seri_0", no);
    }

    @Override
    public String getSeri0() {
        return seri0;
    }

    @Override
    public void setSeri1(String no) {
        this.seri1 = no;
        CommonSharedPreferences.put("seri_1", no);
    }

    @Override
    public String getSeri1() {
        return seri1;
    }

    @Override
    public void setMchID(String mch_id) {
        this.mch_id = mch_id;
        CommonSharedPreferences.put("mch_id", mch_id);
    }

    @Override
    public String getMchID() {
        return mch_id;
    }

    @Override
    public void setUserNo(String user_no) {
        this.user_no = user_no;
        CommonSharedPreferences.put("user_no", user_no);
    }

    @Override
    public String getUserNo() {
        return user_no;
    }

    @Override
    public void setVerifyCode(String code) {
        this.code = code;
        CommonSharedPreferences.put("verifyCode", code);
    }

    @Override
    public String getVerifyCode() {
        return code;
    }

    @Override
    public void setClearZero(boolean var) {
        this.clearZero = var;
        CommonSharedPreferences.put("clearZero", var);
    }

    @Override
    public boolean getClearZero() {
        return clearZero;
    }

    @Override
    public void setSupportMember(boolean var) {
        this.supportMember = var;
        CommonSharedPreferences.put("supportMember", var);
    }

    @Override
    public boolean getSupportMember() {
        return supportMember;
    }

    @Override
    public String getOrderDec() {
        return "托肯衡山";
    }

    @Override
    public void setShortCode1(String var) {
        this.shortCode1 = var;
        CommonSharedPreferences.put("short1", var);
    }

    @Override
    public String getShortCode1() {
        return shortCode1;
    }

    @Override
    public void setShortCode2(String var) {
        this.shortCode2 = var;
        CommonSharedPreferences.put("short2", var);
    }

    @Override
    public String getShortCode2() {
        return shortCode2;
    }

    @Override
    public void setShortCode3(String var) {
        this.shortCode3 = var;
        CommonSharedPreferences.put("short3", var);
    }

    @Override
    public String getShortCode3() {
        return shortCode3;
    }

    @Override
    public void initK21(boolean fin) {
        this.initK21 = fin;
        CommonSharedPreferences.put("initK21", fin);
    }

    @Override
    public boolean getInitK21() {
        return initK21;
    }

    @Override
    public void setFirstStart(boolean yes) {
        this.firstStart = yes;
        CommonSharedPreferences.put("firstStart", yes);
    }

    @Override
    public boolean getFirstStart() {
        return firstStart;
    }

    @Override
    public void setDefaultPay(int var) {
        this.defaultPay = var;
        CommonSharedPreferences.put("type", var);
    }

    @Override
    public int getDefaultPay() {
        return defaultPay;
    }


}
