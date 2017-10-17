package com.szxb.entity;

/**
 * 作者: Tangren on 2017-10-11
 * 包名：com.szxb.entity
 * 邮箱：996489865@qq.com
 * TODO:订单参数
 */

public class OrderParamEntity {

    private String orderid;
    private String trantype;
    private String paytype;
    private String devno;
    private String memno;
    private String member_status;
    private String mchid;
    private String mername;
    private String goodname;
    private String goodcode;
    private String qty;
    private String total_fee;
    private String amount;
    private String trantime;
    private String saletime;
    private String classs;
    private String operaterno;
    private String salerno;
    private String mchineno;
    private String gmchineno;
    private String shortCode;
    private String rmk;

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getTrantype() {
        return trantype;
    }

    public void setTrantype(String trantype) {
        this.trantype = trantype;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getDevno() {
        return devno;
    }

    public void setDevno(String devno) {
        this.devno = devno;
    }

    public String getMemno() {
        return memno;
    }

    public void setMemno(String memno) {
        this.memno = memno;
    }

    public String getMember_status() {
        return member_status;
    }

    public void setMember_status(String member_status) {
        this.member_status = member_status;
    }

    public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    public String getMername() {
        return mername;
    }

    public void setMername(String mername) {
        this.mername = mername;
    }

    public String getGoodname() {
        return goodname;
    }

    public void setGoodname(String goodname) {
        this.goodname = goodname;
    }

    public String getGoodcode() {
        return goodcode;
    }

    public void setGoodcode(String goodcode) {
        this.goodcode = goodcode;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTrantime() {
        return trantime;
    }

    public void setTrantime(String trantime) {
        this.trantime = trantime;
    }

    public String getSaletime() {
        return saletime;
    }

    public void setSaletime(String saletime) {
        this.saletime = saletime;
    }

    public String getClasss() {
        return classs;
    }

    public void setClasss(String classs) {
        this.classs = classs;
    }

    public String getOperaterno() {
        return operaterno;
    }

    public void setOperaterno(String operaterno) {
        this.operaterno = operaterno;
    }

    public String getSalerno() {
        return salerno;
    }

    public void setSalerno(String salerno) {
        this.salerno = salerno;
    }

    public String getMchineno() {
        return mchineno;
    }

    public void setMchineno(String mchineno) {
        this.mchineno = mchineno;
    }

    public String getGmchineno() {
        return gmchineno;
    }

    public void setGmchineno(String gmchineno) {
        this.gmchineno = gmchineno;
    }

    public String getRmk() {
        return rmk;
    }

    public void setRmk(String rmk) {
        this.rmk = rmk;
    }

    @Override
    public String toString() {
        return "OrderParamEntity{" +
                "orderid='" + orderid + '\'' +
                ", trantype='" + trantype + '\'' +
                ", paytype='" + paytype + '\'' +
                ", devno='" + devno + '\'' +
                ", memno='" + memno + '\'' +
                ", member_status='" + member_status + '\'' +
                ", mchid='" + mchid + '\'' +
                ", mername='" + mername + '\'' +
                ", goodname='" + goodname + '\'' +
                ", goodcode='" + goodcode + '\'' +
                ", qty='" + qty + '\'' +
                ", total_fee='" + total_fee + '\'' +
                ", amount='" + amount + '\'' +
                ", trantime='" + trantime + '\'' +
                ", saletime='" + saletime + '\'' +
                ", classs='" + classs + '\'' +
                ", operaterno='" + operaterno + '\'' +
                ", salerno='" + salerno + '\'' +
                ", mchineno='" + mchineno + '\'' +
                ", gmchineno='" + gmchineno + '\'' +
                ", rmk='" + rmk + '\'' +
                ",shortCode=" + shortCode + '\'' +
                '}';
    }
}
