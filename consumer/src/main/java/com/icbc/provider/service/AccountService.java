package com.icbc.provider.service;

import com.icbc.provider.model.Register;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.Future;

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
     * @return 格式为{"balance":999,"orderID","478123","payRequestStatus":1, "failedReason","xxx"}
     */
    Map<String, Object> updateBalance(String cardId, BigDecimal amount, Register register);
    Future<String> updateBalance(String cardId);

}
