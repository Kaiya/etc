package com.icbc.provider;

import com.icbc.provider.model.Register;
import com.icbc.provider.service.AccountService;
import com.icbc.provider.service.BlacklistService;
import com.icbc.provider.service.RegisterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProviderApplicationTests {

    @Autowired
    AccountService accountService;
    @Autowired
    BlacklistService blacklistService;
    @Autowired
    RegisterService registerService;


    @Test
    public void testAccount() {
//        String cardId = "6212261311002888";
//        Boolean updateResult = false;
//        Boolean result = accountService.balanceIsSufficient(cardId, new BigDecimal(0));
//        if (result) {
//            updateResult = accountService.updateBalance(cardId, new BigDecimal(1.00));
//        } else {
//            System.out.println("balance is not sufficient!");
//        }
//        System.out.println(updateResult);
    }

    @Test
    public void testBlacklist() {


    }

    @Test
    public void testRegister() {
        Register register = new Register();
        register.setName("kaiya");
        register.setIdentityType(1);
        register.setIdentityNumber("341225199403165116");
        register.setOrderId("7432784972314");
        register.setDateFailed(new Date(System.currentTimeMillis()));
        Boolean result = registerService.gotoDarkroom(register);
        assert result == true;

    }


}
