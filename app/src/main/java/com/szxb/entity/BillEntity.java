package com.szxb.entity;

import java.util.List;

/**
 * 作者: Tangren on 2017/8/5
 * 包名：com.szxb.entity
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class BillEntity {

    private List<VarListBean> varList;

    public List<VarListBean> getVarList() {
        return varList;
    }

    public void setVarList(List<VarListBean> varList) {
        this.varList = varList;
    }

    public static class VarListBean {
        /**
         * ORDER_ID : 4d645ef8de054195bebfe735f8766425
         * DEVNO : 110
         * ORDERID : gas2017080419025689892
         * PAYSTS : 0
         * TRANTIME : 2017-08-04 19:02:58
         * TRANDATE : 2017-08-04
         * MERNAME : 武汉小兵
         * AMOUNT : 1
         * PAYFLAG : 1
         * MCHID : 100100100101
         * TRANTYPE : 1
         * PAYWAY : 2
         * MEMNO :
         */

        private String ORDER_ID;
        private String DEVNO;
        private String ORDERID;
        private String PAYSTS;
        private String TRANTIME;
        private String TRANDATE;
        private String MERNAME;
        private String AMOUNT;
        private String PAYFLAG;
        private String MCHID;
        private String TRANTYPE;
        private String PAYWAY;
        private String MEMNO;

        public String getORDER_ID() {
            return ORDER_ID;
        }

        public void setORDER_ID(String ORDER_ID) {
            this.ORDER_ID = ORDER_ID;
        }

        public String getDEVNO() {
            return DEVNO;
        }

        public void setDEVNO(String DEVNO) {
            this.DEVNO = DEVNO;
        }

        public String getORDERID() {
            return ORDERID;
        }

        public void setORDERID(String ORDERID) {
            this.ORDERID = ORDERID;
        }

        public String getPAYSTS() {
            return PAYSTS;
        }

        public void setPAYSTS(String PAYSTS) {
            this.PAYSTS = PAYSTS;
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

        public String getAMOUNT() {
            return AMOUNT;
        }

        public void setAMOUNT(String AMOUNT) {
            this.AMOUNT = AMOUNT;
        }

        public String getPAYFLAG() {
            return PAYFLAG;
        }

        public void setPAYFLAG(String PAYFLAG) {
            this.PAYFLAG = PAYFLAG;
        }

        public String getMCHID() {
            return MCHID;
        }

        public void setMCHID(String MCHID) {
            this.MCHID = MCHID;
        }

        public String getTRANTYPE() {
            return TRANTYPE;
        }

        public void setTRANTYPE(String TRANTYPE) {
            this.TRANTYPE = TRANTYPE;
        }

        public String getPAYWAY() {
            return PAYWAY;
        }

        public void setPAYWAY(String PAYWAY) {
            this.PAYWAY = PAYWAY;
        }

        public String getMEMNO() {
            return MEMNO;
        }

        public void setMEMNO(String MEMNO) {
            this.MEMNO = MEMNO;
        }

        @Override
        public String toString() {
            return "VarListBean{" +
                    "ORDER_ID='" + ORDER_ID + '\'' +
                    ", DEVNO='" + DEVNO + '\'' +
                    ", ORDERID='" + ORDERID + '\'' +
                    ", PAYSTS='" + PAYSTS + '\'' +
                    ", TRANTIME='" + TRANTIME + '\'' +
                    ", TRANDATE='" + TRANDATE + '\'' +
                    ", MERNAME='" + MERNAME + '\'' +
                    ", AMOUNT=" + AMOUNT +
                    ", PAYFLAG='" + PAYFLAG + '\'' +
                    ", MCHID='" + MCHID + '\'' +
                    ", TRANTYPE='" + TRANTYPE + '\'' +
                    ", PAYWAY='" + PAYWAY + '\'' +
                    ", MEMNO='" + MEMNO + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BillEntity{" +
                "varList=" + varList +
                '}';
    }
}
