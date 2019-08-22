package com.example.ccps;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.ccps.imp.Auth;
import com.example.ccps.imp.ClientImper;
import com.example.ccps.util.CardIdVerifiy;
import com.example.ccps.util.MoneyCostVerify;
import com.icbc.provider.service.AccountService;
import com.icbc.provider.service.BalanceUpdate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CcpsApplicationTests {
    @Autowired
    ClientImper ccps;
    @Reference
    AccountService accountService;
    @Reference
    BalanceUpdate update;
    @Test
    public void contextLoads() {
//        ccps.check();
//        accountService.queryBalance("6212260012987431");
//        update.balanceUpdate("2213123","6212261311002888",new BigDecimal("2.00"),"000000001",0,"1231231231231","大撒大撒");

//        boolean b = MoneyCostVerify.verifyMoneyCost("2");
//        System.out.println(b);
        Map<String, Object> m = CardIdVerifiy.verifyCardId("qwer123456723456");

    }
    @Test
    public void testAuth(){
        try {
            System.out.println(Auth.createJWT("xxx", 360000000));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
