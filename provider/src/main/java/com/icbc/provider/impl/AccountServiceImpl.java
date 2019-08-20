package com.icbc.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.icbc.provider.mapper.AccountMapper;
import com.icbc.provider.mapper.RegisterMapper;
import com.icbc.provider.model.Account;
import com.icbc.provider.model.Register;
import com.icbc.provider.service.AccountService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kaiya Xiong
 * @date 2019-08-14
 */
@Service
@Component
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountMapper accountMapper;

    @Autowired
    RegisterMapper registerMapper;

    private Log log = LogFactory.getLog(AccountServiceImpl.class);


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
            } else {
                System.out.println("卡号不存在，未查询到余额");
            }
        }
        return balance;
    }

    /**
     * 扣款操作
     *
     * @param cardId 卡号
     * @param amount 待扣款数量
     * @return 格式为{"balance":666,"payRequestStatus":1}
     */
    @Override
    public Map<String, Object> updateBalance(String cardId, BigDecimal amount, Register register) {
        Map<String, Object> resultMap = new HashMap<>();
        BigDecimal postBalance = null;
        int payRequestStatus = 0;
        // card id and amount is not null. !!important!! amount must greater then 0.
        if (cardId != null && amount != null && amount.compareTo(BigDecimal.ZERO) >= 0) {

            BigDecimal balance = queryBalance(cardId);
            if (balance.compareTo(BigDecimal.ZERO) > 0) { // 查询余额是否大于0
                if (balance.compareTo(amount) >= 0) { //再次检查余额是否足够扣款
                    BigDecimal moneyRemain = balance.subtract(amount);
                    Account account = new Account(cardId, moneyRemain);
                    if (accountMapper.updateMoney(account) > 0) {//更新余额成功，返回的是更新成功的记录数
                        postBalance = queryBalance(cardId);
                        payRequestStatus = 1;
                    } else {
                        payRequestStatus = 2;
                        log.info("数据库更新失败... 太健壮了吧");
                    }
                } else {
                    payRequestStatus = 2;
                    log.info("余额不够扣款");
                }
            } else {
                payRequestStatus = 2;
                log.info("余额小于0");
            }

        } else {
            payRequestStatus = 2;
            log.info("卡号或金额为空或者金额小于0");
        }


        if (payRequestStatus == 2) { //支付失败，记录到登记簿
            postBalance = queryBalance(cardId); //就算失败也要去数据库查一下当前余额
            if (registerMapper.addPayFailed(register) > 0) {
                resultMap.put("balance", postBalance);
                resultMap.put("payRequestStatus", payRequestStatus);
            } else {
                log.info("支付失败，记录到登记簿也失败。。。");
            }
        }
        resultMap.put("balance", postBalance);
        resultMap.put("payRequestStatus", payRequestStatus);

        return resultMap;
    }

}
