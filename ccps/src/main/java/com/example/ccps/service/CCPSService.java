package com.example.ccps.service;

import com.example.ccps.model.User;

public interface CCPSService {
    //查询卡号是否存在，存在则返回客户编号
    String cardIDCheck(String cardID);

    //根据客户编号查询三要素
    User getUser(String userID);

    //卡号存在的情况下检查用户是否是黑名单
    //返回结果为0则表示不是黑名单
    int blacklistCheck(User user);

    //查询用户是否是黑名单的流程
    int check(String cardId);
}
