package com.example.ccps;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.icbc.provider.model.Register;
import com.icbc.provider.service.AccountService;
import com.icbc.provider.service.BatchService;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author Kaiya Xiong
 * @date 2019-08-23
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UnitTest {

    private Log log = LogFactory.getLog(UnitTest.class);

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

        // test terrible tx
        map = accountService.updateBalance("6212261311002888", BigDecimal.valueOf(-2.0), register);
        balance = (BigDecimal) map.get("balance");
        log.info("balance " + balance);


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


    }


    public String HttpClientTest(String url) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        //创建Get请求
        HttpGet httpGet = new HttpGet(url);
        // 响应模型
        CloseableHttpResponse response;

        // 由客户端执行(发送)Get请求
        response = client.execute(httpGet);
        // 从响应模型中获取响应实体
        HttpEntity responseEntity = response.getEntity();

        String returnResult = null;
//        System.out.println("响应状态为:" + response.getStatusLine());
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
                params.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return params;
    }

    @Test
    public void batchTestCheckCardId() throws Exception {
        List<String> params = readCsv("/Users/Kaiya/Desktop/testcase.csv");
        for (int i = 0; i < 4; i++) {
            //拼接
            String item[] = params.get(i).split(",");

            String paramUrl = item[0] + "?cardId=" + item[1];

//            log.info("paramUrl:" + paramUrl);
            //获取响应
            String responseResult = HttpClientTest(paramUrl);
//            log.info("responseResult:" + responseResult);

            JSONObject json = JSONObject.parseObject(responseResult);

            String expectResult = item[3];
//            log.info("expect result:" + expectResult);
            log.info("预期输出：" + expectResult + " 实际输出：" + json.get("msg"));
            assert json.get("msg").equals(expectResult);
        }

    }

    @Test
    public void batchTestGenerateOrder() throws Exception {
        List<String> params = readCsv("/Users/Kaiya/Desktop/testcase.csv");
        for (int i = 4; i < params.size(); i++) {
            //拼接
            String item[] = params.get(i).split(",");

            String paramUrl = item[0] + "?cardId=" + item[1] + "&moneyCost=" + item[2];

//            log.info("paramUrl:" + paramUrl);
            String responseResult = HttpClientTest(paramUrl);
//            log.info("responseResult:" + responseResult);

            JSONObject json = JSONObject.parseObject(responseResult);

            String expectResult = item[3];
            log.info("预期输出：" + expectResult + " 实际输出：" + json.get("msg"));
            assert json.get("msg").equals(expectResult);
        }
    }


    @Test
    public void incrementalTest() {
        if (batchService.incrementalUpdateBlackList() == 1) {
            log.info("增量更新成功");
        } else {
            log.info("增量更新失败");
        }
    }


}

