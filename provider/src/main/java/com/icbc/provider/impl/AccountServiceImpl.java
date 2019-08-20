package com.icbc.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.icbc.provider.mapper.AccountMapper;
import com.icbc.provider.model.Account;
import com.icbc.provider.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author Kaiya Xiong
 * @date 2019-08-14
 */
@Service
@Component
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountMapper accountMapper;

    /**
     * 查询指定卡号的余额
     *
     * @param cardId 卡号
     * @return balance 指定卡号的余额
     */
    @Override
    public BigDecimal queryBalance(String cardId) {
        BigDecimal balance = null;
        if (cardId != null) {
            Account account = accountMapper.selectByCardId(cardId);
            if (account != null) {
                balance = account.getMoneyRemain();
            }else {
                System.out.println("卡号不存在，未查询到余额");
            }
        }
        return balance;
    }

    /**
     * 扣款操作
     * @param cardId 卡号
     * @param amount 待扣款数量
     * @return postBalance 更新后的余额
     */
    @Override
    public BigDecimal updateBalance(String cardId, BigDecimal amount) {
        BigDecimal postBalance = null;
        // card id and amount is not null. !!important!! amount must greater then 0.
        if (cardId != null && amount != null && amount.compareTo(BigDecimal.ZERO) >= 0) {

            BigDecimal balance = queryBalance(cardId);
            if (balance.compareTo(amount) >= 0) { //再次检查余额是否足够
                BigDecimal moneyRemain = balance.subtract(amount);
                Account account = new Account(cardId, moneyRemain);
                if (accountMapper.updateMoney(account) > 0) {//更新余额成功，返回的是更新成功的记录数
                    postBalance = queryBalance(cardId);
                }
            } else {
                System.out.println("更新余额失败");
            }
        } else {
            System.out.println("卡号、金额为空或金额小于0");
        }
        return postBalance;
    }

}
