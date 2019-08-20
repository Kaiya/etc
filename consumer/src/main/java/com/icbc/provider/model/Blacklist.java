package com.icbc.provider.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Kaiya Xiong
 * @date 2019-08-15
 */
public class Blacklist implements Serializable {
    private String name; //姓名
    private int identityType; //证件类型
    private String identityNumber; //证件号码
    private Date dateBanned; //加入黑名单的时间
    private int reasonBanned; //加入黑名单的原因

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

    public Date getDateBanned() {
        return dateBanned;
    }

    public void setDateBanned(Date dateBanned) {
        this.dateBanned = dateBanned;
    }

    public int getReasonBanned() {
        return reasonBanned;
    }

    public void setReasonBanned(int reasonBanned) {
        this.reasonBanned = reasonBanned;
    }
}
