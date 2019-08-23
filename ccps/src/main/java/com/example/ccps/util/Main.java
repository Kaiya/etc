package com.example.ccps.util;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Main {
    public static void main(String[] args) throws Exception {
        //加密部分
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
        enc.encrypt("C:\\Users\\dzx\\Desktop\\dubbo+zookeeper教程\\table.csv","C:\\Users\\dzx\\Desktop\\dubbo+zookeeper教程\\table加密.csv");


        //解密部分
        PrivateKey privateKey = RSA.string2PrivateKey(privateKeyStr);
        //加密后的内容Base64解码
        byte[] base642Byte = RSA.base642Byte(byte2Base64);
        //用私钥解密
        byte[] privateDecrypt = RSA.privateDecrypt(base642Byte, privateKey);
        String key = new String(privateDecrypt);
        //文件的解密
        EncryptUtil encCCPS = new EncryptUtil(key);
        encCCPS.decrypt("C:\\Users\\dzx\\Desktop\\dubbo+zookeeper教程\\table加密.csv","C:\\Users\\dzx\\Desktop\\dubbo+zookeeper教程\\table解密.csv");

    }
}
