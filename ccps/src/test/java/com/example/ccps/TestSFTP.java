package com.example.ccps;

import com.example.ccps.util.RSA;
import com.example.ccps.util.SFTPClient;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Kaiya Xiong
 * @date 2019-08-29
 */
public class TestSFTP {

    public static void main(String[] args) {
        TestSFTP testSFTP = new TestSFTP();
//        testSFTP.testKey();
        testSFTP.testTrans();
    }
    public void testTrans(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        SFTPClient client = new SFTPClient("127.0.0.1", 2223, "test", "qwert123");

        String ccia_csvPath = "provider/target/classes/csv/ccia_blacklist_" + df.format(new Date()) + ".csv";
        String ccps_csvPath = "ccps/target/classes/csv/ccia_blacklist_" + df.format(new Date()) + ".csv";
        try {
            client.connect();
//            client.download(ccia_csvPath, ccps_csvPath);
            client.download(ccia_csvPath,ccps_csvPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void transPK(){
        SFTPClient client = new SFTPClient("127.0.0.1", 2223, "test", "qwert123");

        //读公钥文件
        String pkPath = this.getClass().getResource("/key/id_rsa.pub").getPath();
        //把公钥传给CCIA
        try {
            client.connect();
            client.upload(pkPath,"./provider/target/classes/key/");
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

    public void testKey() {
        try {
            KeyPair keyPair = RSA.getKeyPair();
            String filePath = "/Users/Kaiya/Desktop/sql/id_rsa.pub";
            FileUtils.writeStringToFile(new File(filePath), RSA.getPublicKey(keyPair));
            String pk = FileUtils.readFileToString(new File(filePath), "UTF-8");
            System.out.println("pk: " + pk);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testSFTP() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

        SFTPClient sftpClient = new SFTPClient("127.0.0.1", 2223, "test", "qwert123");
//        String pathPrefix = this.getClass().getResource("/csv/").getPath();
        //运行时resource里面的资源都会被放到target/clssses中
        String pathPrefix = "./provider/target/classes/csv/";
        String cciaCsvPath = pathPrefix + "ccia_blacklist_" + df.format(new Date()) + ".csv";

        try {
            sftpClient.connect();
            sftpClient.download(cciaCsvPath, "/Users/Kaiya/Desktop/sql/");
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }
}
