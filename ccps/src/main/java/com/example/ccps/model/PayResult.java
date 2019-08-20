package com.example.ccps.model;

import java.math.BigDecimal;
import java.util.Date;

public class PayResult {
    public String orderId; //订单编号
    public String cardId;  //信用卡卡号
    public BigDecimal moneyCost; //消费金额，定点2位小数
    public BigDecimal balance;  //账户余额，定点2位小数
    public Date orderDate;  //订单完成时间
    public int orderStatus; //订单状态（0：订单未完成，显示正在支付；1：订单支付成功；2：订单支付失败）

    public PayResult() {
    }

    public PayResult(String orderId, String cardId, BigDecimal moneyCost, BigDecimal balance, Date orderDate, int orderStatus) {
        this.orderId = orderId;
        this.cardId = cardId;
        this.moneyCost = moneyCost;
        this.balance = balance;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
    }
}
