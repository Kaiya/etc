package com.example.ccps;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.ccps.imp.CCPS;
import com.icbc.provider.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CcpsApplicationTests {
    @Autowired
    CCPS ccps;
    @Reference
    AccountService accountService;
    @Test
    public void contextLoads() {
//        ccps.check();
        accountService.queryBalance("6212260012987431");
    }
}
