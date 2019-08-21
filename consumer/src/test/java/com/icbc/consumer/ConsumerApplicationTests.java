package com.icbc.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.icbc.provider.model.Register;
import com.icbc.provider.service.AccountService;
import com.icbc.provider.service.BlacklistService;
import com.icbc.provider.service.RegisterService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsumerApplicationTests {

    private Log log = LogFactory.getLog(ConsumerApplicationTests.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Reference
    BlacklistService blacklistService;
    @Reference
    RegisterService registerService;
    @Reference
    AccountService accountService;

    @Test
    public void AccountServiceTest() {

        Register register = new Register();
        register.setName("xiong");
        register.setDateFailed(new Date());
        register.setOrderId("43215321532");
        register.setIdentityType(1);
        register.setIdentityNumber("34215321532143214");
        BigDecimal balance = accountService.queryBalance("6212263025530243");
        log.info("balance:" + balance);

        // test normal tx
        Map<String, Object> map = accountService.updateBalance("6212261311002888", BigDecimal.valueOf(2.0), register);
        balance = (BigDecimal) map.get("balance");
        log.info("balance " + balance);

//        assert balance == null;

//        assert  balance.compareTo(BigDecimal.ZERO) >= 0;

        // test terrible tx
        map = accountService.updateBalance("6212261311002888", BigDecimal.valueOf(-2.0), register);
        balance = (BigDecimal) map.get("balance");


        // terrible tx two....
//        map = accountService.updateBalance("6212261311002888", BigDecimal.valueOf(0), register);
//        balance = (BigDecimal) map.get("balance");
//
//        log.info("terrible balance" + balance);
////        assert  balance == null;
//
//        // test equal balance
//        map = accountService.updateBalance("6212269847532475", BigDecimal.valueOf(1.0), register);
//        balance = (BigDecimal) map.get("balance");
//        log.info("equal case: balance:"+balance);


    }

    @Test
    public void BlacklistServiceTest() {
//        blacklistService.batchAddBlacklist(); //no need to test
    }

    @Test
    public void RegisterServiceTest() {
        Register register = new Register();
        register.setName("xky");
        register.setIdentityType(1);
        register.setDateFailed(new Date());
        register.setOrderId("47389246738214");
        register.setIdentityNumber("341225199403165116");

        //test go to darkroom
        assert registerService.gotoDarkroom(register) == Boolean.TRUE;

//        registerService.batchExportToFile("/Users/Kaiya/Desktop/example.csv");//no need to test

    }

    @Test
    public void DecimalTest() {
        BigDecimal a = BigDecimal.valueOf(4.0);
        BigDecimal b = BigDecimal.valueOf(4.0);
        System.out.println(a.compareTo(b));


    }

}
