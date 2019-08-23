package com.example.ccps.util;

import com.jcraft.jsch.*;

/**
 * @author Kaiya Xiong
 * @date 2019-08-23
 */
public class SFTPClient {

    private Session session = null;
    private String host;
    private int port;
    private String username;
    private String password;

    public SFTPClient(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;

    }


    public void connect() throws JSchException {
        JSch jsch = new JSch();
        // Uncomment the two lines below if the FTP server requires password
        session = jsch.getSession(username, host, port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
    }

    public void upload(String source, String destination) throws JSchException, SftpException {
        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftpChannel = (ChannelSftp) channel;
        sftpChannel.put(source, destination);
        sftpChannel.exit();
    }

    public void download(String source, String destination) throws JSchException, SftpException {
        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftpChannel = (ChannelSftp) channel;
        sftpChannel.get(source, destination);
        sftpChannel.exit();
    }

    public void disconnect() {
        if (session != null) {
            session.disconnect();
        }
    }
}
