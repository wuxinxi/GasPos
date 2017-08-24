package com.szxb.entity;

import java.util.List;

/**
 * 作者: Tangren on 2017/8/5
 * 包名：com.szxb.entity
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class BillEntity {

    /**
     * result : success
     * jourList : [{"CLASS":"001","SALETIME":"2017-08-22 12:41:42","PAYSTS":"1","GMCHINENO":"1","TRANTIME":"2017-08-22 12:41:42","TRANDATE":"2017-08-22","MERNAME":"托肯恒山","AMOUNT":0.01,"SCANWAY":"0","TRANTYPE":"1","MCHID":"100100100101","RMK":"无","PAYWAY":"2","TOTAL_FEE":0.01,"DEVNO":"001","HSORDER_ID":"dcb0781bef0c426fa0d3b3b79b4ec05a","MCHINENO":"12","HSORDERID":"gas2017082212414275199","OPERATERNO":"110"}]
     * rescode : 0000
     */

    private String result;
    private String rescode;
    private List<JourListBean> jourList;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRescode() {
        return rescode;
    }

    public void setRescode(String rescode) {
        this.rescode = rescode;
    }

    public List<JourListBean> getJourList() {
        return jourList;
    }

    public void setJourList(List<JourListBean> jourList) {
        this.jourList = jourList;
    }

    public static class JourListBean {
        /**
         * CLASS : 001
         * SALETIME : 2017-08-22 12:41:42
         * PAYSTS : 1
         * GMCHINENO : 1
         * TRANTIME : 2017-08-22 12:41:42
         * TRANDATE : 2017-08-22
         * MERNAME : 托肯恒山
         * AMOUNT : 0.01
         * SCANWAY : 0
         * TRANTYPE : 1
         * MCHID : 100100100101
         * RMK : 无
         * PAYWAY : 2
         * TOTAL_FEE : 0.01
         * DEVNO : 001
         * HSORDER_ID : dcb0781bef0c426fa0d3b3b79b4ec05a
         * MCHINENO : 12
         * HSORDERID : gas2017082212414275199
         * OPERATERNO : 110
         */

        private String CLASS;
        private String SALETIME;
        private String PAYSTS;
        private String GMCHINENO;
        private String TRANTIME;
        private String TRANDATE;
        private String MERNAME;
        private double AMOUNT;
        private String SCANWAY;
        private String TRANTYPE;
        private String MCHID;
        private String RMK;
        private String PAYWAY;
        private double TOTAL_FEE;
        private String DEVNO;
        private String HSORDER_ID;
        private String MCHINENO;
        private String HSORDERID;
        private String OPERATERNO;

        public String getCLASS() {
            return CLASS;
        }

        public void setCLASS(String CLASS) {
            this.CLASS = CLASS;
        }

        public String getSALETIME() {
            return SALETIME;
        }

        public void setSALETIME(String SALETIME) {
            this.SALETIME = SALETIME;
        }

        public String getPAYSTS() {
            return PAYSTS;
        }

        public void setPAYSTS(String PAYSTS) {
            this.PAYSTS = PAYSTS;
        }

        public String getGMCHINENO() {
            return GMCHINENO;
        }

        public void setGMCHINENO(String GMCHINENO) {
            this.GMCHINENO = GMCHINENO;
        }

        public String getTRANTIME() {
            return TRANTIME;
        }

        public void setTRANTIME(String TRANTIME) {
            this.TRANTIME = TRANTIME;
        }

        public String getTRANDATE() {
            return TRANDATE;
        }

        public void setTRANDATE(String TRANDATE) {
            this.TRANDATE = TRANDATE;
        }

        public String getMERNAME() {
            return MERNAME;
        }

        public void setMERNAME(String MERNAME) {
            this.MERNAME = MERNAME;
        }

        public double getAMOUNT() {
            return AMOUNT;
        }

        public void setAMOUNT(double AMOUNT) {
            this.AMOUNT = AMOUNT;
        }

        public String getSCANWAY() {
            return SCANWAY;
        }

        public void setSCANWAY(String SCANWAY) {
            this.SCANWAY = SCANWAY;
        }

        public String getTRANTYPE() {
            return TRANTYPE;
        }

        public void setTRANTYPE(String TRANTYPE) {
            this.TRANTYPE = TRANTYPE;
        }

        public String getMCHID() {
            return MCHID;
        }

        public void setMCHID(String MCHID) {
            this.MCHID = MCHID;
        }

        public String getRMK() {
            return RMK;
        }

        public void setRMK(String RMK) {
            this.RMK = RMK;
        }

        public String getPAYWAY() {
            return PAYWAY;
        }

        public void setPAYWAY(String PAYWAY) {
            this.PAYWAY = PAYWAY;
        }

        public double getTOTAL_FEE() {
            return TOTAL_FEE;
        }

        public void setTOTAL_FEE(double TOTAL_FEE) {
            this.TOTAL_FEE = TOTAL_FEE;
        }

        public String getDEVNO() {
            return DEVNO;
        }

        public void setDEVNO(String DEVNO) {
            this.DEVNO = DEVNO;
        }

        public String getHSORDER_ID() {
            return HSORDER_ID;
        }

        public void setHSORDER_ID(String HSORDER_ID) {
            this.HSORDER_ID = HSORDER_ID;
        }

        public String getMCHINENO() {
            return MCHINENO;
        }

        public void setMCHINENO(String MCHINENO) {
            this.MCHINENO = MCHINENO;
        }

        public String getHSORDERID() {
            return HSORDERID;
        }

        public void setHSORDERID(String HSORDERID) {
            this.HSORDERID = HSORDERID;
        }

        public String getOPERATERNO() {
            return OPERATERNO;
        }

        public void setOPERATERNO(String OPERATERNO) {
            this.OPERATERNO = OPERATERNO;
        }

        @Override
        public String toString() {
            return "JourListBean{" +
                    "CLASS='" + CLASS + '\'' +
                    ", SALETIME='" + SALETIME + '\'' +
                    ", PAYSTS='" + PAYSTS + '\'' +
                    ", GMCHINENO='" + GMCHINENO + '\'' +
                    ", TRANTIME='" + TRANTIME + '\'' +
                    ", TRANDATE='" + TRANDATE + '\'' +
                    ", MERNAME='" + MERNAME + '\'' +
                    ", AMOUNT=" + AMOUNT +
                    ", SCANWAY='" + SCANWAY + '\'' +
                    ", TRANTYPE='" + TRANTYPE + '\'' +
                    ", MCHID='" + MCHID + '\'' +
                    ", RMK='" + RMK + '\'' +
                    ", PAYWAY='" + PAYWAY + '\'' +
                    ", TOTAL_FEE=" + TOTAL_FEE +
                    ", DEVNO='" + DEVNO + '\'' +
                    ", HSORDER_ID='" + HSORDER_ID + '\'' +
                    ", MCHINENO='" + MCHINENO + '\'' +
                    ", HSORDERID='" + HSORDERID + '\'' +
                    ", OPERATERNO='" + OPERATERNO + '\'' +
                    '}';
        }
    }
}
