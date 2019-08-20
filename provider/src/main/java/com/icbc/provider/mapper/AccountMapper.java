package com.icbc.provider.mapper;

import com.icbc.provider.model.Account;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

/**
 * @author Kaiya Xiong
 * @date 2019-08-14
 */
@Mapper
@Service
public interface AccountMapper {
    Account selectByCardId(String cardId);

    int updateMoney(Account account);
}
