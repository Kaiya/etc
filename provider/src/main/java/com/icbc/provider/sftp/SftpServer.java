package com.icbc.provider.sftp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.sshd.common.file.FileSystemFactory;
import org.apache.sshd.common.session.Session;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.config.keys.AuthorizedKeysAuthenticator;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.subsystem.sftp.SftpSubsystemFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.util.Collections;

/**
 * @author Kaiya Xiong
 * @date 2019-08-19
 * 在本地构建一个SFTP服务，ccps需要实现ftp客户端通过ip端口，账户密码下载同步登记簿
 * SFTP底层是通过SSH协议通讯，所以需要构建一个SSH server
 */
@Service
public class SftpServer {
    private Log log = LogFactory.getLog(SftpServer.class);

    @PostConstruct
    public void startServer() throws IOException {
        start();
    }

    private void start() throws IOException {
        SshServer sshd = SshServer.setUpDefaultServer();
        sshd.setHost("0.0.0.0");//server host ip
        sshd.setPort(2223);//server port
        sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(new File("host.ser")));
        sshd.setSubsystemFactories(Collections.singletonList(new SftpSubsystemFactory()));
        //sftp登陆使用的用户名和密码
        sshd.setPasswordAuthenticator((username, password, session) -> username.equals("test") && password.equals("qwert123"));
        //authorized keys for public private key auth. 存储ftp客户端的公钥文件
        sshd.setPublickeyAuthenticator(new AuthorizedKeysAuthenticator(new File("./authorized_keys")));

        sshd.start();
        log.info("SFTP server started");
    }
}
