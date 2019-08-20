package com.example.ccps.imp;

import com.alibaba.dubbo.config.annotation.Reference;
import com.icbc.provider.service.AccountService;
import com.example.ccps.mapper.OrderMapper;
import com.example.ccps.model.PayResult;
import com.example.ccps.service.OrderService;
import com.example.ccps.util.GenerOrderId;
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
    AccountService accountService;

    //实现订单生成功能。获得卡号和消费金额，生成订单编号。获取CCIA余额和订单状态，生成对应的订单完成时间
    @Override
    public PayResult orderFunction(String cardId, BigDecimal moneyCost) {

        Order order=new Order();

        order.setCardId(cardId);     //设置卡号给order
        order.setMoneyCost(moneyCost);    //order设置金额为输入消费金额，之后用于传递给CCIA 不过感觉之后不用传

        GenerOrderId.generateOrderId(order);   //调用静态方法，生成订单编号

//        balanceUpdate.balanceUpdate(cardId,moneyCost)
//        int orderStatus = 1;       //此处接收CCIA传过来的订单状态。前面的moneyCost要先传给CCIA，才能接收状态
//        BigDecimal balance = BigDecimal.valueOf(12.00); //此处接收CCIA传过来的账户余额
        Map<String, Object> map = accountService.updateBalance(cardId, moneyCost);
        int orderStatus = (int) map.get("payRequestStatus");
        BigDecimal balance = (BigDecimal) map.get("balance");

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

        PayResult payResult = new PayResult(order.getOrderId(), cardId, moneyCost, balance, order.getOrderDate(), orderStatus);
//        oI.showOrderStatus(orderStatus); //这个是传给前端
        return payResult;
    }

}