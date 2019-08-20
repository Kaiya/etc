package com.example.ccps.service;

import com.example.ccps.model.PayResult;

import java.math.BigDecimal;

public interface OrderService {


    //实现订单生成功能。获得卡号和消费金额，生成订单编号。获取CCIA余额和订单状态，生成对应的订单完成时间
    public PayResult orderFunction(String cardId, BigDecimal moneyCost);
}
