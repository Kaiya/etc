package com.example.ccps.mapper;

import com.example.ccps.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Mapper
@Service
public interface UserMapper {
    //根据卡号查询客户编号
    String selectByCardId(String cardId);
    //根据客户编号查询三要素
    User selectByUserId(String userId);
    //根据三要素查询黑名单 0：不在黑名单中
    int selectBlackList(User user);
}
