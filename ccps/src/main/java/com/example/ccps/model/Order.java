package com.example.ccps.model;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

//说必须实现Serializable接口，不然dubbo的服务无法发送实体类
public class Order implements Serializable {
    private String orderId; //订单编号
    private String cardId;  //信用卡卡号
    private BigDecimal moneyCost; //消费金额，定点2位小数
    private Date orderDate;  //订单完成时间
    private int orderStatus; //订单状态（0：订单未完成，显示正在支付；1：订单支付成功；2：订单支付失败）

    public Order(){

    }


    public String getOrderId() {
        return orderId;
    }

    public String getCardId() {
        return cardId;
    }

    public BigDecimal getMoneyCost() {
        return moneyCost;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public void setMoneyCost(BigDecimal moneyCost) {
        this.moneyCost = moneyCost;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }
}
