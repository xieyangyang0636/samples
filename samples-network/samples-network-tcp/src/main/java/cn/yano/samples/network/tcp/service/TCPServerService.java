package cn.yano.samples.network.tcp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * TCP 服务端服务类
 * Created by xieyangyang0636 on 2020/2/2.
 */
public class TCPServerService implements Runnable {

    /**
     * 日志Logger
     */
    private final static Logger logger = LoggerFactory.getLogger(TCPServerService.class);

    /**
     * Socket
     */
    private Socket socket;

    /**
     * 构造函数
     *
     * @param socket socket
     */
    public TCPServerService(Socket socket) {
        this.socket = socket;
        logger.info("create task {} process socket {}", Thread.currentThread(), socket.toString());
    }

    /**
     * 开始接受数据
     */
    @Override
    public void run() {
        BufferedReader bufferedReader = null;
        DataOutputStream dataOutputStream = null;
        try {
            logger.info("client {} connected server", socket.toString());
            // Socket输入
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Socket输出
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            // 获取客户端传入的字符串
            String line = bufferedReader.readLine();
            if (line != null) {
                logger.info("server receive : {}", line);
                // 返回数据至客户端
                dataOutputStream.writeBytes(line + " World" + "\n");
                logger.info("server send : {}", line + " World");
                Thread.sleep(5000L);
            }
        } catch (Exception e) {
            logger.error("server process error : ", e);
        } finally {
            if (dataOutputStream != null) {
                try {
                    dataOutputStream.close();
                } catch (IOException e) {
                    logger.error("server close dataOutputStream error : ", e);
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    logger.error("server close bufferedReader error : ", e);
                }
            }
        }
    }
}
