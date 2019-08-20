package com.icbc.provider.impl;

import com.icbc.provider.service.AccountService;
import com.icbc.provider.service.BalanceUpdate;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kaiya Xiong
 * @date 2019-08-19
 */
public class BalanceUpdateImpl implements BalanceUpdate {


    @Autowired
    AccountService accountService;

    /**
     * 更新余额接口
     *
     * @param orderId    订单号
     * @param cardId     卡号
     * @param amount     扣款金额
     * @param clientId   客户编号
     * @param idenType   证件类型
     * @param idenNum    证件号码
     * @param clientName 客户姓名
     * @return 返回值为Map对象，格式为：{"balance":999,"orderID","478123","payRequestStatus",1}
     */
    @Override
    public Map<String, Object> balanceUpdate(String orderId, String cardId, BigDecimal amount, String clientId, int idenType, String idenNum, String clientName) {
        Map<String, Object> resultMap = new HashMap<>();

        Map<String, Object> map = accountService.updateBalance(cardId, amount);

        BigDecimal balance = (BigDecimal) map.get("balance");
        int payRequestStatus = (int) map.get("payRequestStatus");
        // TODO: 2019-08-19 编写业务逻辑
        resultMap.put("balance", balance);
        resultMap.put("orderID", "41241324");
        resultMap.put("payRequestStatus", payRequestStatus);
        return null;
    }
}
