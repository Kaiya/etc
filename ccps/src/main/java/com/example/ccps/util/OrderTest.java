package com.example.ccps.util;

import com.example.ccps.model.Order;

public class OrderTest {
    public void show(Order order) { //弄了半天，感觉还是要个show函数，因为想的是支付正在进行中时不需要输出订单编号啥的吧
        System.out.println("您的订单编号是：" + order.getOrderId());
        System.out.println("您的信用卡卡号是：" + order.getCardId());
        System.out.println("您的消费金额是：" + order.getMoneyCost());
//        System.out.println(order.getOrderStatus());        //内部人知道订单状态编码就好
        System.out.println("您的订单时间是：" + order.getOrderDate());
    }


    public void showOrderStatus(Order order) {   //根据订单状态编号，显示不同内容
        int orderStatus=order.getOrderStatus();
        switch (orderStatus) {
            case 0:
                System.out.println("正在支付中...");
                break;
            case 1:
                System.out.println("支付成功！");
                show(order);
                break;
            case 2:
                System.out.println("支付失败！请重新输入！");
                show(order);
                break;

            default:       //此处的default！
                System.out.println("出现了神奇的错误！程序员小哥哥正在赶来途中...");

        }
    }


}
