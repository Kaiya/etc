package com.icbc.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.icbc.provider.model.Register;
import com.icbc.provider.service.AccountService;
import com.icbc.provider.service.BalanceUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @author Kaiya Xiong
 * @date 2019-08-19
 */
@Service
@Component
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
     * @return 返回值为Map对象，格式为：{"balance":999,"orderID","478123","payRequestStatus":1, "failedReason","xxx"}
     */
    @Override
    public Map<String, Object> balanceUpdate(String orderId, String cardId, BigDecimal amount, String clientId, int idenType, String idenNum, String clientName) {
//        Map<String, Object> resultMap = new HashMap<>();

        Register register = new Register();
        register.setName(clientName);
        register.setIdentityNumber(idenNum);
        register.setIdentityType(idenType);
        register.setOrderId(orderId);
        register.setDateFailed(new Date());
        Map<String, Object> map = accountService.updateBalance(cardId, amount, register);

        map.put("orderID", orderId);
        return map;

//        BigDecimal balance = (BigDecimal) map.get("balance");
//        int payRequestStatus = (int) map.get("payRequestStatus");
//        String failedReason = (String) map.get("failedReason");
//        resultMap.put("balance", balance);
//        resultMap.put("orderID", orderId);
//        resultMap.put("payRequestStatus", payRequestStatus);
//        resultMap.put("failedReason", failedReason);
//        return resultMap;
    }
}
