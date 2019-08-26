package com.icbc.provider;

import com.icbc.provider.mapper.BlacklistMapper;
import com.icbc.provider.mapper.RegisterMapper;
import com.icbc.provider.model.Register;
import com.icbc.provider.service.AccountService;
import com.icbc.provider.service.BalanceUpdate;
import com.icbc.provider.service.BlacklistService;
import com.icbc.provider.service.RegisterService;
import com.icbc.provider.util.EncryptUtil;
import com.icbc.provider.util.RSA;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProviderApplicationTests {

    @Autowired
    AccountService accountService;
    @Autowired
    BlacklistService blacklistService;
    @Autowired
    RegisterService registerService;

    @Autowired
    BalanceUpdate balanceUpdate;

//    @Test
    public void test() {
        balanceUpdate.balanceUpdate("11111", "1111", new BigDecimal("123"), "123", 1, "12334444", "111");
    }


//    @Test
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

//    @Test
    public void testBlacklist() {


    }

//    @Test
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

    @Autowired
    BlacklistMapper blacklistMapper;
    @Autowired
    RegisterMapper registerMapper;

//    @Test
    public void testIncremental() {


        List<Register> registers = registerMapper.registerLeftJoinBlackList();
//        Register register1 = new Register("432431132",new Date(),"xkvfdsfy",0,"2432143" );
//        Register register2 = new Register("4321432132",new Date(),"xdasky",0,"43214" );
//
//        registers.add(register1);
//        registers.add(register2);
        if (registers.isEmpty()) {
            System.out.println("没有新增数据");
        } else {
            System.out.println("register:" + registers + "size:" + registers.size());
            int result = blacklistMapper.insertIncrementalBlackList(registers);
            System.out.println("incremental: " + result);
        }
    }

//    @Test
    public void testEncryption() throws Exception {
        String originCsv = "";
        String encryptCsv = "";
        /**
         * 1.CCPS使用RSA算法生成公私钥对pk,sk，把公钥pk传给CCIA
         * 2.CCIA生成密钥key，拿着公钥pk加密key，得到密文CT_key
         * 3.CCIA使用密钥通过DES加密csv文件得到密文CT_csv。CCIA把密文CT_key和CT_csv通过SFTP传输给CCPS。
         * 4.CCPS拿到密文CT_key和CT_csv，首先使用自己的私钥sk解密CT_key，得到明文密钥PT_key(aka. key)。然后使用明文密钥key对密文CT_csv文件解密得到csv文件的明文信息。
         */
        //生成密钥key
        EncryptUtil encCCIA = new EncryptUtil("suibiantian");//参数是随机化种子
        //Base64编码成字符串
        String encodedKey = Base64.getEncoder().encodeToString(encCCIA.getKey().getEncoded());
        //解码方式如下
//        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
//        Key originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "DES");

        //获取公钥，从CCPS上传来的文件里获取，假设为pk
        String pk = "";
        //使用公钥加密key
        PublicKey publicKey = RSA.string2PublicKey(pk);//rebuild from string
        byte[] pkEnc = RSA.publicEncrypt(encodedKey.getBytes(), publicKey);//使用公钥加密
        //加密后的内容Base64编码
        String CT_key = RSA.byte2Base64(pkEnc);

        //用DES加密csv文件
        encCCIA.encrypt(originCsv, encryptCsv);
        // CT_key 转为文件存储，和encryptCsv文件，CCPS来取吧。。。
    }


}
