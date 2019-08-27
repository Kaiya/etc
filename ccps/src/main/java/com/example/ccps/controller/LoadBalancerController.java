package com.example.ccps.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.icbc.provider.service.AccountService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kaiya Xiong
 * @date 2019-08-26
 */
@RestController
@RequestMapping("lb")
public class LoadBalancerController {
    @Reference(loadbalance = "roundrobin")
    AccountService accountService;
    @RequestMapping(value = "/test")
    public String testLb (){
        return accountService.testLoadbalance();
    }
}
