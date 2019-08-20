package com.icbc.provider.service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Kaiya Xiong
 * @date 2019-08-19
 */
public interface BalanceUpdate {
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
    Map<String, Object> balanceUpdate(String orderId, String cardId, BigDecimal amount, String clientId, int idenType, String idenNum, String clientName);
}
