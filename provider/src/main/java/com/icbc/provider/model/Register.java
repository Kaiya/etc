package com.icbc.provider.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Kaiya Xiong
 * @date 2019-08-15
 */
public class Register implements Serializable {

    private String orderId; //订单号
    private Date dateFailed; //订单支付失败的日期
    private String name; //姓名
    private int identityType; //证件类型
    private String identityNumber; //证件号码

    public Register(String orderId, Date dateFailed, String name, int identityType, String identityNumber) {
        this.orderId = orderId;
        this.dateFailed = dateFailed;
        this.name = name;
        this.identityType = identityType;
        this.identityNumber = identityNumber;
    }
    public Register(){

    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getDateFailed() {
        return dateFailed;
    }

    public void setDateFailed(Date dateFailed) {
        this.dateFailed = dateFailed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdentityType() {
        return identityType;
    }

    public void setIdentityType(int identityType) {
        this.identityType = identityType;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }
}
