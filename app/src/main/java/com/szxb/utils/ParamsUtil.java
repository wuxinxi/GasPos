package com.szxb.utils;

import com.szxb.entity.OrderParamEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者: Tangren on 2017-10-11
 * 包名：com.szxb.utils
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class ParamsUtil {

    //二维码下单参数
    public static Map<String, Object> getRequestParams(OrderParamEntity paramEntity) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderid", paramEntity.getOrderid());
        map.put("trantype", paramEntity.getTrantype());
        map.put("paytype", paramEntity.getPaytype());
        map.put("devno", paramEntity.getDevno());
        map.put("memno", paramEntity.getMemno());//会员卡号,如果不是会员传1
        map.put("member_status", paramEntity.getMember_status());//会员卡号,如果不是会员传1
        map.put("mchid", paramEntity.getMchid());
        map.put("mername", paramEntity.getMername());
        map.put("goodname", paramEntity.getGoodname());//油品名称
        map.put("goodcode", paramEntity.getGoodcode());//商品代码
        map.put("qty", paramEntity.getQty());//加油升数
        map.put("total_fee", paramEntity.getTotal_fee());//总金额
        map.put("amount", paramEntity.getAmount());//实际扣款
        map.put("trantime", paramEntity.getTrantime());//订单时间
        map.put("saletime", paramEntity.getSaletime());//销售时间
        map.put("class", paramEntity.getClasss());//班次
        map.put("operaterno", paramEntity.getOperaterno());//操作员编号
        map.put("salerno", paramEntity.getSalerno());//销售员编号
        map.put("mchineno", paramEntity.getMchineno());//加油机号
        map.put("gmchineno", paramEntity.getMchineno());//加油枪号
        map.put("rmk", paramEntity.getRmk());//备注
        return map;
    }

    //短码下单参数
    public static Map<String, Object> getRequestShortParams(OrderParamEntity paramEntity) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderid", paramEntity.getOrderid());
        map.put("trantype", paramEntity.getTrantype());
        map.put("paytype", paramEntity.getPaytype());
        map.put("devno", paramEntity.getDevno());
        map.put("mchid", paramEntity.getMchid());
        map.put("mername", paramEntity.getMername());
        map.put("goodname", paramEntity.getGoodname());//油品名称
        map.put("goodcode", paramEntity.getGoodcode());//商品代码
        map.put("qty", paramEntity.getQty());//加油升数
        map.put("total_fee", paramEntity.getTotal_fee());//总金额
        map.put("shortcode", paramEntity.getShortCode());//短码
        map.put("trantime", paramEntity.getTrantime());//订单时间
        map.put("saletime", paramEntity.getSaletime());//销售时间
        map.put("class", paramEntity.getClasss());//班次
        map.put("operaterno", paramEntity.getOperaterno());//操作员编号
        map.put("salerno", paramEntity.getSalerno());//销售员编号
        map.put("mchineno", paramEntity.getMchineno());//加油机号
        map.put("gmchineno", paramEntity.getMchineno());//加油枪号
        map.put("rmk", paramEntity.getRmk());//备注
        return map;
    }


//
//    OrderParamEntity paramEntity = new OrderParamEntity();
//        paramEntity.setOrderid(orderNo);
//        paramEntity.setTrantype(paytype);
//        paramEntity.setPaytype(paytype);
//        paramEntity.setMember_status(status);//0是会员,非会员1
//        paramEntity.setMemno(infoEntity.getMemnerNo());//会员卡号,如果不是会员传1
//        paramEntity.setMchid(App.getPosManager().getMchID());
//        paramEntity.setMername(App.getPosManager().getOrderDec());
//        paramEntity.setGoodname(infoEntity.getOilCode());//油品名称
//        paramEntity.setGoodcode("0092");//商品代码,线上使用infoEntity.getOilCode()
//        paramEntity.setQty(infoEntity.getFuelingUp());//加油升数
//        paramEntity.setTotal_fee("1");//总金额
//        paramEntity.setAmount("1");//实际扣款
//        paramEntity.setTrantime(DateUtil.getCurrentDate());//订单时间
//        paramEntity.setSaletime(DateUtil.getCurrentDate());//销售时间
//        paramEntity.setClasss("0001");//班次
//        paramEntity.setOperaterno(App.getPosManager().getUserNo());//操作员编号
//        paramEntity.setSalerno("0001");//销售员编号
//        paramEntity.setMchineno("000000");//加油机号
//        paramEntity.setDevno("000000");//设备号
//        paramEntity.setGmchineno(infoEntity.getGasNo());//加油枪号
//        paramEntity.setRmk("无");
//        return ParamsUtil.getRequestParams(paramEntity);
}
