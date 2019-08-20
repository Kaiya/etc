package com.example.ccps.util;

import com.example.ccps.model.Order;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GenerOrderId {

    //收到ETC数据时获取系统时间，为了生成订单编号，系统日期+卡号后三位
    public static Order generateOrderId(Order order) {
        Date currentd = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentdStr = df.format(currentd);  //格式化后的系统日期
        System.out.println("格式化"+currentdStr);
        String cardId = order.getCardId();
        String cardIdCheck = cardId.substring(cardId.length() - 3); //获得卡号的后三位校验位

        String orderId = currentdStr + cardIdCheck; //生成的订单编号是系统日期+卡号后三位
        System.out.println("订单号"+orderId);

        order.setOrderId(orderId); //设置订单号，这个之后就用于传给CCIA啦
        return order;
    }
}
