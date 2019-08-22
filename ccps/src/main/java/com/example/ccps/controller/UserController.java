package com.example.ccps.controller;

import com.example.ccps.model.Response;
import com.example.ccps.model.PayResult;
import com.example.ccps.service.ClientService;
import com.example.ccps.service.OrderService;
import com.example.ccps.util.CardIdVerifiy;
import com.example.ccps.util.MoneyCostVerify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    ClientService clientService;
    @Autowired
    OrderService orderService;

    Response response = new Response();

    @RequestMapping(value = "/checkCardId" )
    public Response checkCardId(@RequestParam("cardId")String cardId){
        Map<String, Object> map = CardIdVerifiy.verifyCardId(cardId);
        if ((Boolean) map.get("valid")) {
            if (clientService.cardIDCheck(cardId)==null) {
                response.code = 0;
                response.msg = "卡号不存在";
            } else {
                response.code = 1;
                response.msg = "卡号存在";
            }
        } else {
            response.code = 4;
            response.msg = (String) map.get("msg");
        }
        return response;
    }

    @RequestMapping("/checkBlackList")
    public Response checkBlackList(@RequestParam("cardId")String cardId){
        Map<String, Object> map = CardIdVerifiy.verifyCardId(cardId);
        if ((Boolean) map.get("valid")) {
            int check = clientService.check(cardId);
            if (check == 1) {
                response.code = 1;
                response.msg = "用户不在黑名单中";
            } else if (check == 0){
                response.code = 0;
                response.msg = "用户在黑名单中";
            } else if (check == 2){
                response.code = 2;
                response.msg = "卡号不存在";
            }
        } else {
            response.code = 4;
            response.msg = (String) map.get("msg");
        }
        return response;
    }

    @RequestMapping("/generateOrder")
    public Response generateOrder(@RequestParam("cardId")String cardId, @RequestParam("moneyCost")String moneyCost){
        Map<String, Object> map = CardIdVerifiy.verifyCardId(cardId);
        if ((Boolean) map.get("valid")) {
            if (MoneyCostVerify.verifyMoneyCost(moneyCost)) {
                BigDecimal money = new BigDecimal(moneyCost);
                PayResult payResult = orderService.orderFunction(cardId, money);

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
                    response.msg = payResult.failedReason;
                    response.result = payResult;
                } else {
                    response.code = 3;
                    response.msg = "出现了神奇的错误！程序员小哥哥正在赶来途中...";
                    response.result = payResult;
                }
            } else {
                response.code = 4;
                response.msg = "扣款金额非法";
            }
        } else {
            response.code = 5;
            response.msg = (String) map.get("msg");
        }
        return response;
    }
}
