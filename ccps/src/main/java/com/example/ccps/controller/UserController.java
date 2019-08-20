package com.example.ccps.controller;

import com.example.ccps.model.Response;
import com.example.ccps.model.PayResult;
import com.example.ccps.service.CCPSService;
import com.example.ccps.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    CCPSService ccpsService;
    @Autowired
    OrderService orderService;

    Response response = new Response();

    @RequestMapping(value = "/checkCardId" )
    public Response checkCardId(@RequestParam("cardId")String cardId){
        if (ccpsService.cardIDCheck(cardId)==null) {
            response.code = 0;
            response.msg = "卡号不存在";
        } else {
            response.code = 1;
            response.msg = "卡号存在";
        }
        return response;
    }

    @RequestMapping("/checkBlackList")
    public Response checkBlackList(@RequestParam("cardId")String cardId){
        int check = ccpsService.check(cardId);
        if (check == 0) {
            response.code = 0;
            response.msg = "用户不在黑名单中";
        } else if (check == 1){
            response.code = 1;
            response.msg = "用户在黑名单中";
        } else if (check == 2){
            response.code = 2;
            response.msg = "卡号不存在";
        }
        return response;
    }

    @RequestMapping("/generateOrder")
    public Response generateOrder(@RequestParam("cardId")String cardId, @RequestParam("moneyCost")BigDecimal moneyCost){
        PayResult payResult = orderService.orderFunction(cardId, moneyCost);

        if (payResult.orderStatus == 0) {
            response.code = 0;
            response.msg = "正在支付中...";
            response.result = payResult;
        } else if (payResult.orderStatus == 1){
            response.code = 1;
            response.msg = "支付成功！";
            response.result = payResult;
        } else if (payResult.orderStatus == 2){
            response.code = 2;
            response.msg = "支付失败！请重新输入！";
            response.result = payResult;
        } else {
            response.code = 3;
            response.msg = "出现了神奇的错误！程序员小哥哥正在赶来途中...";
            response.result = payResult;
        }
        System.out.println(response);
        return response;
    }
}
