package com.example.ccps.bean;

import java.sql.Timestamp;

public class Blacklist {
    private String name;
    private Integer identity_type;
    private String identity_number;
    private Timestamp date_banned;
    private Integer reason_banned;

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    private Long createTime;

    public Blacklist(String name, Integer identity_type, String identity_number, Timestamp date_banned, Integer reason_banned) {
        this.name = name;
        this.identity_type = identity_type;
        this.identity_number = identity_number;
        this.date_banned = date_banned;
        this.reason_banned = reason_banned;
    }

    public Blacklist() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdentity_type() {
        return identity_type;
    }

    public void setIdentity_type(Integer identity_type) {
        this.identity_type = identity_type;
    }

    public String getIdentity_number() {
        return identity_number;
    }

    public void setIdentity_number(String identity_number) {
        this.identity_number = identity_number;
    }

    public Timestamp getDate_banned() {
        return date_banned;
    }

    public void setDate_banned(Timestamp date_banned) {
        this.date_banned = date_banned;
    }

    public Integer getReason_banned() {
        return reason_banned;
    }

    public void setReason_banned(Integer reason_banned) {
        this.reason_banned = reason_banned;
    }
}
