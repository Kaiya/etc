package com.example.ccps;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.example.ccps.util.EncryptUtil;
import com.example.ccps.util.RSA;
import com.example.ccps.util.SFTPClient;
import com.icbc.provider.model.Register;
import com.icbc.provider.service.AccountService;
import com.icbc.provider.service.BatchService;
import com.icbc.provider.service.RegisterService;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
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

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
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

    @Test
    public void SftpTest() {
        SFTPClient sftpClient = new SFTPClient("192.168.103.251", 2223, "test", "qwert123");
        try {
            sftpClient.connect();
            sftpClient.upload("/Users/Kaiya/Desktop/testcase.csv", "./sftptrans_testcase.csv");
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEncryption() throws Exception {

        //生成公私钥对
        String str = "aaa";
        KeyPair keyPair = RSA.getKeyPair();
        String publicKeyStr = RSA.getPublicKey(keyPair);
        String privateKeyStr = RSA.getPrivateKey(keyPair);
        PublicKey publicKey = RSA.string2PublicKey(publicKeyStr);
        //用公钥加密
        byte[] publicEncrypt = RSA.publicEncrypt(str.getBytes(), publicKey);
        //加密后的内容Base64编码
        String byte2Base64 = RSA.byte2Base64(publicEncrypt);
        System.out.println("公钥加密并Base64编码的结果：" + byte2Base64);


        //文件加密部分
        EncryptUtil enc = new EncryptUtil(str);
        enc.encrypt("/Users/Kaiya/Desktop/test.csv", "/Users/Kaiya/Desktop/test_encrypt.csv");


        //解密部分
        PrivateKey privateKey = RSA.string2PrivateKey(privateKeyStr);
        //加密后的内容Base64解码
        byte[] base642Byte = RSA.base642Byte(byte2Base64);
        //用私钥解密
        byte[] privateDecrypt = RSA.privateDecrypt(base642Byte, privateKey);
        String key = new String(privateDecrypt);
        System.out.println("plain text: " + key);
        //文件的解密
        EncryptUtil encCCPS = new EncryptUtil(key);
        encCCPS.decrypt("/Users/Kaiya/Desktop/test_encrypt.csv", "/Users/Kaiya/Desktop/test_decrypt.csv");

    }

    @Test
    public void testBatch() throws Exception {

        String encryptCsv = "";
        String decryptCsv = "";
        /**
         * 1.CCPS使用RSA算法生成公私钥对pk,sk，把公钥pk传给CCIA
         * 2.CCIA生成密钥key，拿着公钥pk加密key，得到密文CT_key
         * 3.CCIA使用密钥通过DES加密csv文件得到密文CT_csv。CCIA把密文CT_key和CT_csv通过SFTP传输给CCPS。
         * 4.CCPS拿到密文CT_key和CT_csv，首先使用自己的私钥sk解密CT_key，得到明文密钥PT_key(aka. key)。然后使用明文密钥key对密文CT_csv文件解密得到csv文件的明文信息。
         */
        //CCPS生成公私钥对，通过SFTPClient把公钥put给CCIA
        KeyPair keyPair = RSA.getKeyPair();
        SFTPClient client = new SFTPClient("127.0.0.1", 2333, "test", "qwert123");
        client.connect();
        client.upload("", ""); //todo 公钥字符串生成文件
        //等CCIA生成好密文。。可以睡一会儿。。。
        Thread.sleep(3000);
        //取密文CT_key和CT_csv文件，CT_key读成String。。。假设为CT_key字符串
        client.download("", "");//取CT_key文件
        client.download("", "");//取CT_csv文件
        //使用私钥解密CT_key
        String CT_key = "";//假设为读出的CT_key字符串
        String privateKeyStr = RSA.getPrivateKey(keyPair);
        PrivateKey privateKey = RSA.string2PrivateKey(privateKeyStr);
        //加密后的内容Base64解码
        byte[] base642Byte = RSA.base642Byte(CT_key);
        //用私钥解密
        byte[] privateDecrypt = RSA.privateDecrypt(base642Byte, privateKey);

        //因为CCIA那边对key进行了Base64编码，这边还需要解码
        String encodedKey = new String(privateDecrypt);
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        Key originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "DES");//即为原始的key

        //使用PT_key对加密后的文件进行解密
        EncryptUtil encCCPS = new EncryptUtil(originalKey);
        encCCPS.decrypt(encryptCsv, decryptCsv);//得到解密的文件

    }



}

