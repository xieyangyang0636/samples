package cn.yano.samples.network.tcp;

import cn.yano.samples.network.tcp.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 程序启动类
 * Created by xieyangyang0636 on 2020/1/19.
 */
public class ClientApplication {

    /**
     * 日志Logger
     */
    private final static Logger logger = LoggerFactory.getLogger(ClientApplication.class);

    /**
     * 启动方法
     *
     * @param args 入参
     */
    public static void main(String[] args) throws IOException {
        // 客户端模拟线程
        for (int clientNo = 1; clientNo <= 100; clientNo++) {
            logger.info("start tcp client{}", clientNo);
            final int finalClientNo = clientNo;
            new Thread(() -> {
                Socket clientSocket = null;
                DataOutputStream dataOutputStream = null;
                BufferedReader bufferedReader = null;
                try {
                    // 创建客户端 Socket 并指明需要连接的服务器端的主机名及端口号
                    clientSocket = new Socket("127.0.0.1", AppConfig.TCP_SERVER_PORT);
                    // Socket输出
                    dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                    // Socket输入
                    bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    // 向服务器发送数据
                    dataOutputStream.writeBytes("Hello" + finalClientNo + "\n");
                    logger.info("client{} send : {}", finalClientNo, "Hello");
                    // 接收服务器返回数据
                    String response = bufferedReader.readLine();
                    logger.info("client{} receive : {}", finalClientNo, response);
                } catch (Exception e) {
                    logger.error("client{} process error : ", finalClientNo, e);
                } finally {
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            logger.error("client{} bufferedReader close error : ", finalClientNo, e);
                        }
                    }
                    if (dataOutputStream != null) {
                        try {
                            dataOutputStream.close();
                        } catch (IOException e) {
                            logger.error("client{} dataOutputStream close error : ", finalClientNo, e);
                        }
                    }
                    if (clientSocket != null) {
                        try {
                            clientSocket.close();
                        } catch (IOException e) {
                            logger.error("client{} socket close error : ", finalClientNo, e);
                        }
                    }
                }
            }).run();
        }
    }
}
