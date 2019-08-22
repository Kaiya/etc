package com.example.ccps.imp;

import com.example.ccps.mapper.UserMapper;
import com.example.ccps.model.User;
import com.example.ccps.service.ClientService;
import com.example.ccps.util.CardIdVerifiy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientImper implements ClientService {
    @Autowired
    private UserMapper userMapper;

    //查询卡号是否存在，存在则返回客户编号
    @Override
    public String cardIDCheck(String cardID) {
        String userId = userMapper.selectByCardId(cardID);
        return userId;
    }

    //根据客户编号查询三要素
    @Override
    public User getUser(String userID) {
        User user = userMapper.selectByUserId(userID);
        return user;
    }

    //卡号存在的情况下检查用户是否是黑名单
    //返回结果为0则表示不是黑名单
    @Override
    public int blacklistCheck(User user){
        int i = userMapper.selectBlackList(user);
        return i;
    }

    //查询用户是否是黑名单的流程
    @Override
    public int check(String cardId) {
//        String cardId = "6212261311002531";     //卡号不存在
//        String cardId = "6212262121234457";     //卡号存在但不在黑名单中
//        String cardId = "6212261311002888";   //卡号存在且在黑名单中

        String userId = cardIDCheck(cardId);
        if (userId == null) {
            System.out.println("卡号不存在！");
            return 2;
//            return "卡号不存在！";
        } else {
            System.out.println("卡号：" + cardId);
            System.out.println("客户编号：" + userId);

            //根据客户编号得到user对象
            User user = getUser(userId);
            System.out.println("姓名：" + user.getName());
            System.out.println("证件类型：" + user.getIdentityType());
            System.out.println("证件号：" + user.getIdentityNumber());

            //根据user的三要素查询是否是黑名单
            int blacklist = blacklistCheck(user);
            if (blacklist == 1) {
                System.out.println("用户在黑名单中！");
//                return "用户在黑名单中！";
                return 0;
            } else {
                System.out.println("用户不在黑名单中，正在生成订单……");
//                return "用户不在黑名单中，正在生成订单……";
                return 1;
            }
        }
    }

}
