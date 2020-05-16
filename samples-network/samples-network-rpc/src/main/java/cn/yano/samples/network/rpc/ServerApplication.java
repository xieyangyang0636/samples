package cn.yano.samples.network.rpc;

import cn.yano.samples.network.rpc.config.AppConfig;
import cn.yano.samples.network.rpc.service.TCPServerService;
import cn.yano.samples.network.rpc.service.UserService;
import cn.yano.samples.network.rpc.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 程序启动类
 * Created by xieyangyang0636 on 2020/1/19.
 */
public class ServerApplication {
    /**
     * 日志Logger
     */
    private final static Logger logger = LoggerFactory.getLogger(ServerApplication.class);

    /**
     * 服务实现类
     */
    public static Map<String, Object> serviceImplMap = new HashMap<>();

    /**
     * 启动方法
     *
     * @param args 入参
     */
    public static void main(String[] args) throws IOException {
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        serviceImplMap.put(UserService.class.getName(), userServiceImpl);
        // 服务端模拟线程
        logger.info("start tcp server");
        ServerSocket serverSocket = null;
        ExecutorService executorService = null;
        try {
            serverSocket = new ServerSocket();
            // 端口是否可重用
            serverSocket.setReuseAddress(true);
            // 定义服务端地址端口和最大可处理连接数
            SocketAddress socketAddress = new InetSocketAddress("127.0.0.1", AppConfig.TCP_SERVER_PORT);
            serverSocket.bind(socketAddress, AppConfig.TCP_SERVER_MAX_QUEUE_SIZE);
            // 定义线程池
            executorService = Executors.newFixedThreadPool(AppConfig.TCP_SERVER_MAX_CONN_SIZE);
            // 接收客户端连接
            logger.info("server start accept data");
            for (; ; ) {
                Socket connectionSocket = serverSocket.accept();
                TCPServerService serverService = new TCPServerService(connectionSocket);
                executorService.execute(serverService);
            }
        } catch (Exception e) {
            logger.error("server process error : ", e);
        } finally {
            if (executorService != null) {
                executorService.shutdown();
            }
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    logger.error("client socket close error : ", e);
                }
            }
        }
    }
}
