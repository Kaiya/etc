package com.icbc.provider.service;

import java.math.BigDecimal;

/**
 * @author Kaiya Xiong
 * @date 2019-08-14
 */
public interface AccountService {
    /**
     * 查询指定卡号的余额
     * @param cardId 卡号
     * @return balance 卡号余额
     */
    BigDecimal queryBalance(String cardId);

    /**
     *  更新指定卡号的余额
     * @param cardId 卡号
     * @param amount 待扣款数量
     * @return balance 扣款后余额
     */
    BigDecimal updateBalance(String cardId, BigDecimal amount);
}
