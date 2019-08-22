package com.icbc.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.icbc.provider.model.Register;
import com.icbc.provider.service.AccountService;
import com.icbc.provider.service.BatchService;
import com.icbc.provider.service.BlacklistService;
import com.icbc.provider.service.RegisterService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


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
    @Reference
    BatchService batchService;


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


    public String HttpClientTest(String url, String params) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        //创建Get请求
        HttpGet httpGet = new HttpGet(url + "?" + params);
        // 响应模型
        CloseableHttpResponse response;

        // 由客户端执行(发送)Get请求
        response = client.execute(httpGet);
        // 从响应模型中获取响应实体
        HttpEntity responseEntity = response.getEntity();

        String returnResult = null;
        System.out.println("响应状态为:" + response.getStatusLine());
        if (responseEntity != null) {
            //响应内容
            returnResult = EntityUtils.toString(responseEntity);
        }
        // 释放资源
        if (client != null) {
            client.close();
        }
        if (response != null) {
            response.close();
        }

        return returnResult;
    }

    public List<String> readCsv(String path) {
        List<String> params = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));//换成你的文件名
            reader.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
            String line = null;
            while ((line = reader.readLine()) != null) {
//                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
//                String last = item[item.length - 1];//这就是你要的数据了
//                //int value = Integer.parseInt(last);//如果是数值，可以转化为数值
                params.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return params;
    }

    @Test
    public void batchTest() {
        List<String> params = readCsv("/Users/Kaiya/Desktop/export.csv");
        for (String param : params) {
            //拼接
            String paramUrl = null;
            String item[] = param.split(",");

            paramUrl = "cardId=" + item[0] + "&dateFailed=" + item[1] + "&name=" + item[2] + "&idenType=" + item[3] + "&idenNum=" + item[4];

            log.info("paramUrl" + paramUrl);
            try {
                String responseResult = HttpClientTest("", param);
                log.info("responseResult:" + responseResult);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void incrementalTest() {
        if (batchService.incrementalUpdateBlackList()) {
            log.info("增量更新成功");
        } else {
            log.info("增量更新失败");
        }
    }


}
