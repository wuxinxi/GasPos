package com.szxb.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者: Tangren on 2017/7/31
 * 包名：com.szxb.entity
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
@Entity
public class HomeInfoEntity {
    @Id(autoincrement = true)
    private Long id;

    private int noId;

    //油枪号
    private String gasNo;

    //单价
    private String gasUnitPrice;

    //会员单价
    private String gasMemberUnitPrice;

    //加油升数
    private String gasCapacity;

    //加油金额
    private String gasMoney;

    //加油会员价
    private String gasMemberMoney;

    //支付状态
    private String gasPayStatus;

    //加油流水号
    @Unique
    private String gasOrderNo;

    private String xbOrderNo;

    //2017-02-02 12:20:30
    private String gasDate;

    //2017-02-02
    private String gasTime;

    @Generated(hash = 1885823618)
    public HomeInfoEntity(Long id, int noId, String gasNo, String gasUnitPrice,
            String gasMemberUnitPrice, String gasCapacity, String gasMoney,
            String gasMemberMoney, String gasPayStatus, String gasOrderNo, String xbOrderNo,
            String gasDate, String gasTime) {
        this.id = id;
        this.noId = noId;
        this.gasNo = gasNo;
        this.gasUnitPrice = gasUnitPrice;
        this.gasMemberUnitPrice = gasMemberUnitPrice;
        this.gasCapacity = gasCapacity;
        this.gasMoney = gasMoney;
        this.gasMemberMoney = gasMemberMoney;
        this.gasPayStatus = gasPayStatus;
        this.gasOrderNo = gasOrderNo;
        this.xbOrderNo = xbOrderNo;
        this.gasDate = gasDate;
        this.gasTime = gasTime;
    }

    @Generated(hash = 1155982903)
    public HomeInfoEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNoId() {
        return this.noId;
    }

    public void setNoId(int noId) {
        this.noId = noId;
    }

    public String getGasNo() {
        return this.gasNo;
    }

    public void setGasNo(String gasNo) {
        this.gasNo = gasNo;
    }

    public String getGasUnitPrice() {
        return this.gasUnitPrice;
    }

    public void setGasUnitPrice(String gasUnitPrice) {
        this.gasUnitPrice = gasUnitPrice;
    }

    public String getGasMemberUnitPrice() {
        return this.gasMemberUnitPrice;
    }

    public void setGasMemberUnitPrice(String gasMemberUnitPrice) {
        this.gasMemberUnitPrice = gasMemberUnitPrice;
    }

    public String getGasCapacity() {
        return this.gasCapacity;
    }

    public void setGasCapacity(String gasCapacity) {
        this.gasCapacity = gasCapacity;
    }

    public String getGasMoney() {
        return this.gasMoney;
    }

    public void setGasMoney(String gasMoney) {
        this.gasMoney = gasMoney;
    }

    public String getGasMemberMoney() {
        return this.gasMemberMoney;
    }

    public void setGasMemberMoney(String gasMemberMoney) {
        this.gasMemberMoney = gasMemberMoney;
    }

    public String getGasPayStatus() {
        return this.gasPayStatus;
    }

    public void setGasPayStatus(String gasPayStatus) {
        this.gasPayStatus = gasPayStatus;
    }

    public String getGasOrderNo() {
        return this.gasOrderNo;
    }

    public void setGasOrderNo(String gasOrderNo) {
        this.gasOrderNo = gasOrderNo;
    }

    public String getXbOrderNo() {
        return this.xbOrderNo;
    }

    public void setXbOrderNo(String xbOrderNo) {
        this.xbOrderNo = xbOrderNo;
    }

    @Override
    public String toString() {
        return "HomeInfoEntity{" +
                "id=" + id +
                ", noId=" + noId +
                ", gasNo='" + gasNo + '\'' +
                ", gasUnitPrice='" + gasUnitPrice + '\'' +
                ", gasMemberUnitPrice='" + gasMemberUnitPrice + '\'' +
                ", gasCapacity='" + gasCapacity + '\'' +
                ", gasMoney='" + gasMoney + '\'' +
                ", gasMemberMoney='" + gasMemberMoney + '\'' +
                ", gasPayStatus='" + gasPayStatus + '\'' +
                ", gasOrderNo='" + gasOrderNo + '\'' +
                ", xbOrderNo='" + xbOrderNo + '\'' +
                '}';
    }

    public String getGasDate() {
        return this.gasDate;
    }

    public void setGasDate(String gasDate) {
        this.gasDate = gasDate;
    }

    public String getGasTime() {
        return this.gasTime;
    }

    public void setGasTime(String gasTime) {
        this.gasTime = gasTime;
    }
}
