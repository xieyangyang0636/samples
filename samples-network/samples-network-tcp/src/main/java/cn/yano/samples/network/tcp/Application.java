package cn.yano.samples.network.tcp;

import cn.yano.samples.network.tcp.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * 程序启动类
 * Created by xieyangyang0636 on 2020/1/19.
 */
public class Application {

    /**
     * 日志Logger
     */
    private final static Logger logger = LoggerFactory.getLogger(Application.class);

    /**
     * 启动方法
     *
     * @param args 入参
     */
    public static void main(String[] args) throws IOException {
        // 服务端模拟线程
        logger.info("start tcp server");
        new Thread(() -> {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket();
                serverSocket.setReuseAddress(true);
                // 定义服务端地址端口和最大可处理连接数
                SocketAddress socketAddress = new InetSocketAddress("127.0.0.1", AppConfig.TCP_SERVER_PORT);
                serverSocket.bind(socketAddress, AppConfig.TCP_SERVER_MAX_CONN_SIZE);
                // TODO: 2020/1/19 多client待实现
                // 接收客户端连接
                Socket connectionSocket = serverSocket.accept();
                logger.info("client {} connected server", connectionSocket.toString());
                while (true) {
                    // Socket输入
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                    // Socket输出
                    DataOutputStream dataOutputStream = new DataOutputStream(connectionSocket.getOutputStream());
                    // 获取客户端传入的字符串
                    String line = bufferedReader.readLine();
                    logger.info("server receive : {}", line);
                    // 返回数据至客户端
                    dataOutputStream.writeBytes(line + " World" + "\n");
                    logger.info("server send : {}", line + " World");
                    Thread.sleep(1000L);
                }
            } catch (Exception e) {
                logger.error("server process error : ", e);
            } finally {
                if (serverSocket != null) {
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        logger.error("client socket close error : ", e);
                    }
                }
            }

        }).start();

        // 服务端模拟线程
        logger.info("start tcp client");
        new Thread(() -> {
            Socket clientSocket = null;
            try {
                // 创建客户端 Socket 并指明需要连接的服务器端的主机名及端口号
                clientSocket = new Socket("127.0.0.1", AppConfig.TCP_SERVER_PORT);
                // Socket输出
                DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                // Socket输入
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                while (true) {
                    // 向服务器发送数据
                    dataOutputStream.writeBytes("Hello" + "\n");
                    logger.info("client send : {}", "Hello");
                    // 接收服务器返回数据
                    String response = bufferedReader.readLine();
                    logger.info("client receive : {}", response);
                    Thread.sleep(1000L);
                }
            } catch (Exception e) {
                logger.error("client process error : ", e);
            } finally {
                if (clientSocket != null) {
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        logger.error("client socket close error : ", e);
                    }
                }
            }
        }).start();

        // 阻塞主程序
        System.in.read();
    }
}
