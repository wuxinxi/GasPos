package com.szxb.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者: Tangren on 2017-09-06
 * 包名：com.szxb.entity
 * 邮箱：996489865@qq.com
 * TODO:油机流水参数
 */
@Entity
public class SeriaInformation implements Parcelable {
    @Id(autoincrement = true)
    private Long id;
    //油枪号
    private String gasNo;
    //串口传递的交易流水号
    private String seriaOrderNo;
    //油机流水号
    private String machineOrderNo;
    //交易类型
    private String traType;
    //交易时间
    private String traStartTime;
    //逻辑卡号
    private String logicalCardNo;
    //卡金额
    private String cardMoney;
    //加油金额
    private String gasMoney;
    //交易金额
    private String traMoney;
    //付款方式
    private String payType;
    //优惠代码规则
    private String codeRule;
    //卡类型
    private String cardType;
    //卡片交易计数器
    private String ardctc;
    //加油点
    private String refuelPoint;
    //单价
    private String prices;
    //油品编码
    private String oilCode;
    //加油升数
    private String fuelingUp;
    //交易结束时间
    private String traEndTime;
    //小兵流水号
    private String xbOrderNo;
    //支付状态
    private String gasPayStatus;
    //会员账号
    private String memnerNo;
    //终端号
    private String deviceNo;
    //升累计
    private String qtyCount;
    //当前时间
    private String currentTime;
    //会员单价
    private String memberPrices;
    //会员加油金额
    private String memberGasMoney;
    //终端号
    private String remark_1;
    private String remark_2;
    @Generated(hash = 186528715)
    public SeriaInformation(Long id, String gasNo, String seriaOrderNo,
            String machineOrderNo, String traType, String traStartTime,
            String logicalCardNo, String cardMoney, String gasMoney,
            String traMoney, String payType, String codeRule, String cardType,
            String ardctc, String refuelPoint, String prices, String oilCode,
            String fuelingUp, String traEndTime, String xbOrderNo,
            String gasPayStatus, String memnerNo, String deviceNo, String qtyCount,
            String currentTime, String memberPrices, String memberGasMoney,
            String remark_1, String remark_2) {
        this.id = id;
        this.gasNo = gasNo;
        this.seriaOrderNo = seriaOrderNo;
        this.machineOrderNo = machineOrderNo;
        this.traType = traType;
        this.traStartTime = traStartTime;
        this.logicalCardNo = logicalCardNo;
        this.cardMoney = cardMoney;
        this.gasMoney = gasMoney;
        this.traMoney = traMoney;
        this.payType = payType;
        this.codeRule = codeRule;
        this.cardType = cardType;
        this.ardctc = ardctc;
        this.refuelPoint = refuelPoint;
        this.prices = prices;
        this.oilCode = oilCode;
        this.fuelingUp = fuelingUp;
        this.traEndTime = traEndTime;
        this.xbOrderNo = xbOrderNo;
        this.gasPayStatus = gasPayStatus;
        this.memnerNo = memnerNo;
        this.deviceNo = deviceNo;
        this.qtyCount = qtyCount;
        this.currentTime = currentTime;
        this.memberPrices = memberPrices;
        this.memberGasMoney = memberGasMoney;
        this.remark_1 = remark_1;
        this.remark_2 = remark_2;
    }
    @Generated(hash = 1762890056)
    public SeriaInformation() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getGasNo() {
        return this.gasNo;
    }
    public void setGasNo(String gasNo) {
        this.gasNo = gasNo;
    }
    public String getSeriaOrderNo() {
        return this.seriaOrderNo;
    }
    public void setSeriaOrderNo(String seriaOrderNo) {
        this.seriaOrderNo = seriaOrderNo;
    }
    public String getMachineOrderNo() {
        return this.machineOrderNo;
    }
    public void setMachineOrderNo(String machineOrderNo) {
        this.machineOrderNo = machineOrderNo;
    }
    public String getTraType() {
        return this.traType;
    }
    public void setTraType(String traType) {
        this.traType = traType;
    }
    public String getTraStartTime() {
        return this.traStartTime;
    }
    public void setTraStartTime(String traStartTime) {
        this.traStartTime = traStartTime;
    }
    public String getLogicalCardNo() {
        return this.logicalCardNo;
    }
    public void setLogicalCardNo(String logicalCardNo) {
        this.logicalCardNo = logicalCardNo;
    }
    public String getCardMoney() {
        return this.cardMoney;
    }
    public void setCardMoney(String cardMoney) {
        this.cardMoney = cardMoney;
    }
    public String getGasMoney() {
        return this.gasMoney;
    }
    public void setGasMoney(String gasMoney) {
        this.gasMoney = gasMoney;
    }
    public String getTraMoney() {
        return this.traMoney;
    }
    public void setTraMoney(String traMoney) {
        this.traMoney = traMoney;
    }
    public String getPayType() {
        return this.payType;
    }
    public void setPayType(String payType) {
        this.payType = payType;
    }
    public String getCodeRule() {
        return this.codeRule;
    }
    public void setCodeRule(String codeRule) {
        this.codeRule = codeRule;
    }
    public String getCardType() {
        return this.cardType;
    }
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
    public String getArdctc() {
        return this.ardctc;
    }
    public void setArdctc(String ardctc) {
        this.ardctc = ardctc;
    }
    public String getRefuelPoint() {
        return this.refuelPoint;
    }
    public void setRefuelPoint(String refuelPoint) {
        this.refuelPoint = refuelPoint;
    }
    public String getPrices() {
        return this.prices;
    }
    public void setPrices(String prices) {
        this.prices = prices;
    }
    public String getOilCode() {
        return this.oilCode;
    }
    public void setOilCode(String oilCode) {
        this.oilCode = oilCode;
    }
    public String getFuelingUp() {
        return this.fuelingUp;
    }
    public void setFuelingUp(String fuelingUp) {
        this.fuelingUp = fuelingUp;
    }
    public String getTraEndTime() {
        return this.traEndTime;
    }
    public void setTraEndTime(String traEndTime) {
        this.traEndTime = traEndTime;
    }
    public String getXbOrderNo() {
        return this.xbOrderNo;
    }
    public void setXbOrderNo(String xbOrderNo) {
        this.xbOrderNo = xbOrderNo;
    }
    public String getGasPayStatus() {
        return this.gasPayStatus;
    }
    public void setGasPayStatus(String gasPayStatus) {
        this.gasPayStatus = gasPayStatus;
    }
    public String getMemnerNo() {
        return this.memnerNo;
    }
    public void setMemnerNo(String memnerNo) {
        this.memnerNo = memnerNo;
    }
    public String getDeviceNo() {
        return this.deviceNo;
    }
    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }
    public String getQtyCount() {
        return this.qtyCount;
    }
    public void setQtyCount(String qtyCount) {
        this.qtyCount = qtyCount;
    }
    public String getCurrentTime() {
        return this.currentTime;
    }
    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
    public String getMemberPrices() {
        return this.memberPrices;
    }
    public void setMemberPrices(String memberPrices) {
        this.memberPrices = memberPrices;
    }
    public String getMemberGasMoney() {
        return this.memberGasMoney;
    }
    public void setMemberGasMoney(String memberGasMoney) {
        this.memberGasMoney = memberGasMoney;
    }
    public String getRemark_1() {
        return this.remark_1;
    }
    public void setRemark_1(String remark_1) {
        this.remark_1 = remark_1;
    }
    public String getRemark_2() {
        return this.remark_2;
    }
    public void setRemark_2(String remark_2) {
        this.remark_2 = remark_2;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.gasNo);
        dest.writeString(this.seriaOrderNo);
        dest.writeString(this.machineOrderNo);
        dest.writeString(this.traType);
        dest.writeString(this.traStartTime);
        dest.writeString(this.logicalCardNo);
        dest.writeString(this.cardMoney);
        dest.writeString(this.gasMoney);
        dest.writeString(this.traMoney);
        dest.writeString(this.payType);
        dest.writeString(this.codeRule);
        dest.writeString(this.cardType);
        dest.writeString(this.ardctc);
        dest.writeString(this.refuelPoint);
        dest.writeString(this.prices);
        dest.writeString(this.oilCode);
        dest.writeString(this.fuelingUp);
        dest.writeString(this.traEndTime);
        dest.writeString(this.xbOrderNo);
        dest.writeString(this.gasPayStatus);
        dest.writeString(this.memnerNo);
        dest.writeString(this.deviceNo);
        dest.writeString(this.qtyCount);
        dest.writeString(this.currentTime);
        dest.writeString(this.memberPrices);
        dest.writeString(this.memberGasMoney);
        dest.writeString(this.remark_1);
        dest.writeString(this.remark_2);
    }

    protected SeriaInformation(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.gasNo = in.readString();
        this.seriaOrderNo = in.readString();
        this.machineOrderNo = in.readString();
        this.traType = in.readString();
        this.traStartTime = in.readString();
        this.logicalCardNo = in.readString();
        this.cardMoney = in.readString();
        this.gasMoney = in.readString();
        this.traMoney = in.readString();
        this.payType = in.readString();
        this.codeRule = in.readString();
        this.cardType = in.readString();
        this.ardctc = in.readString();
        this.refuelPoint = in.readString();
        this.prices = in.readString();
        this.oilCode = in.readString();
        this.fuelingUp = in.readString();
        this.traEndTime = in.readString();
        this.xbOrderNo = in.readString();
        this.gasPayStatus = in.readString();
        this.memnerNo = in.readString();
        this.deviceNo = in.readString();
        this.qtyCount = in.readString();
        this.currentTime = in.readString();
        this.memberPrices = in.readString();
        this.memberGasMoney = in.readString();
        this.remark_1 = in.readString();
        this.remark_2 = in.readString();
    }

    public static final Parcelable.Creator<SeriaInformation> CREATOR = new Parcelable.Creator<SeriaInformation>() {
        @Override
        public SeriaInformation createFromParcel(Parcel source) {
            return new SeriaInformation(source);
        }

        @Override
        public SeriaInformation[] newArray(int size) {
            return new SeriaInformation[size];
        }
    };

    @Override
    public String toString() {
        return "SeriaInformation{" +
                "id=" + id +
                ", gasNo='" + gasNo + '\'' +
                ", seriaOrderNo='" + seriaOrderNo + '\'' +
                ", machineOrderNo='" + machineOrderNo + '\'' +
                ", traType='" + traType + '\'' +
                ", traStartTime='" + traStartTime + '\'' +
                ", logicalCardNo='" + logicalCardNo + '\'' +
                ", cardMoney='" + cardMoney + '\'' +
                ", gasMoney='" + gasMoney + '\'' +
                ", traMoney='" + traMoney + '\'' +
                ", payType='" + payType + '\'' +
                ", codeRule='" + codeRule + '\'' +
                ", cardType='" + cardType + '\'' +
                ", ardctc='" + ardctc + '\'' +
                ", refuelPoint='" + refuelPoint + '\'' +
                ", prices='" + prices + '\'' +
                ", oilCode='" + oilCode + '\'' +
                ", fuelingUp='" + fuelingUp + '\'' +
                ", traEndTime='" + traEndTime + '\'' +
                ", xbOrderNo='" + xbOrderNo + '\'' +
                ", gasPayStatus='" + gasPayStatus + '\'' +
                ", memnerNo='" + memnerNo + '\'' +
                ", deviceNo='" + deviceNo + '\'' +
                ", qtyCount='" + qtyCount + '\'' +
                ", currentTime='" + currentTime + '\'' +
                ", memberPrices='" + memberPrices + '\'' +
                ", memberGasMoney='" + memberGasMoney + '\'' +
                ", remark_1='" + remark_1 + '\'' +
                ", remark_2='" + remark_2 + '\'' +
                '}';
    }
}
