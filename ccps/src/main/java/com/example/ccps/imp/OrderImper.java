package com.example.ccps.imp;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.ccps.mapper.OrderMapper;
import com.example.ccps.model.PayResult;
import com.example.ccps.model.User;
import com.example.ccps.service.OrderService;
import com.example.ccps.util.GenerOrderId;
import com.icbc.provider.model.Register;
import com.icbc.provider.service.AccountService;
import com.icbc.provider.service.BalanceUpdate;
import org.apache.ibatis.annotations.Mapper;
import com.example.ccps.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


@Mapper
@Service
public class OrderImper implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Reference
    BalanceUpdate balanceUpdate;
    @Reference
    AccountService accountService;
    @Autowired
    ClientImper clientImper;

    //实现订单生成功能。获得卡号和消费金额，生成订单编号。获取CCIA余额和订单状态，生成对应的订单完成时间
    @Override
    public PayResult orderFunction(String cardId, BigDecimal money) {

        Order order=new Order();
        BigDecimal moneyCost = money.multiply(BigDecimal.valueOf(100));

        order.setCardId(cardId);     //设置卡号给order
        order.setMoneyCost(moneyCost);    //order设置金额为输入消费金额，之后用于传递给CCIA 不过感觉之后不用传

        GenerOrderId.generateOrderId(order);   //调用静态方法，生成订单编号

        User user = clientImper.getUser(clientImper.cardIDCheck(cardId));
//        Register register=new Register();
//        register.setName("lmk");
//        register.setIdentityType(1);
//        register.setIdentityNumber("341225199403165116");
//        register.setOrderId(order.getOrderId());
//        register.setDateFailed(new Date());
//        Map<String, Object> map = accountService.updateBalance(cardId, moneyCost, register);
        Map<String, Object> map = balanceUpdate.balanceUpdate(order.getOrderId(),cardId, moneyCost,user.getUserId(),user.getIdentityType(),user.getIdentityNumber(),user.getName());
//        Map<String, Object> map = accountService.updateBalance(cardId, moneyCost,user.getUserId(),user.getIdentityType(),user.getIdentityNumber(),user.getName());

        int orderStatus = (int) map.get("payRequestStatus");
        BigDecimal balance = (BigDecimal) map.get("balance");
        String failedReason = (String) map.get("failedReason");

        //   int orderStatus=2;
        //   int orderStatus=3; //自己加了校验位，如果是其他的输入情况就是default
        order.setOrderStatus(orderStatus);      //设置order的订单状态
        Date orderDate = new Date();    //接收到CCIA返回的状态后，获取系统当前时间
        System.out.println("时间"+orderDate); //此处只是为了测试输出订单完成时间
        order.setOrderDate(orderDate);

        try {
            orderMapper.insert(order);

        } catch (Exception e) {
            e.printStackTrace();
        }

        PayResult payResult = new PayResult(order.getOrderId(), cardId, moneyCost.divide(BigDecimal.valueOf(100)), balance.divide(BigDecimal.valueOf(100)), order.getOrderDate(), orderStatus, failedReason);
//        oI.showOrderStatus(orderStatus); //这个是传给前端
        return payResult;
    }

}