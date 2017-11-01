package com.szxb.utils.ftp;

import android.util.Log;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 作者: Tangren on 2017/7/27
 * 包名：com.szxb.ftp
 * 邮箱：996489865@qq.com
 * TODO:FTP工具类
 */

public class FTP {

    private String url;
    private int port = 21;
    private String username;
    private String password;
    private String path;
    private String ftpPath;
    private String sinfileName;

    public FTP builder(String url) {
        this.url = url;
        return this;
    }

    public FTP setPort(int port) {
        this.port = port;
        return this;
    }

    public FTP setLogin(String username, String psw) {
        this.username = username;
        this.password = psw;
        return this;
    }

    public FTP setPath(String path) {
        this.path = path;
        return this;
    }

    public FTP setFTPPath(String ftpPath) {
        this.ftpPath = ftpPath;
        return this;
    }

    public FTP setFileName(String sinfileName) {
        this.sinfileName = sinfileName;
        return this;
    }

    public boolean download() {
        boolean success = false;
        FTPClient ftp = new FTPClient();
        BufferedOutputStream buffOut = null;
        int reply;
        ftp.setConnectTimeout(12000);
        try {
            ftp.connect(url, port);// 连接FTP服务器
            ftp.login(username, password);// 登录
            //连接的状态码
            reply = ftp.getReplyCode();
            ftp.setDataTimeout(12000);
            //判断是否连接上ftp
            if (!FTPReply.isPositiveCompletion(reply)) {
                Log.d("FTP",
                        "call(FTP.java:159)FTP连接失败");
                ftp.disconnect();
                return false;
            }

            Log.d("FTP",
                    "call(FTP.java:165)FTP连接成功");
            File file = new File(path + sinfileName);
            if (file.exists()) {
                Log.d("FTP",
                        "call(FTP.java:86)存在,先删除");
                file.delete();
            }

            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.setBufferSize(8 * 1024);
            ftp.setControlEncoding("UTF-8");
            ftp.enterLocalPassiveMode();
            buffOut = new BufferedOutputStream(new FileOutputStream(path + sinfileName), 8 * 1024);
            success = ftp.retrieveFile(ftpPath, buffOut);
            Log.d("FTP",
                    "call(FTP.java:98)检索文件是否成功=" + success);
            buffOut.flush();
            buffOut.close();

            ftp.logout();
            ftp.disconnect();
            //判断是否退出成功，不成功就再断开连接。
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    Log.d("FTP",
                            "call(FTP.java:109)" + ioe.toString());
                    throw new RuntimeException("FTP disconnect fail!", ioe);
                }
            }
        } catch (IOException e) {
            success = false;
            Log.d("FTP",
                    "call(FTP.java:192)FTP异常" + e.toString());
            e.printStackTrace();
        }
        return success;
    }

}
