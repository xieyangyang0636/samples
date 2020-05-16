package cn.yano.samples.network.rpc;

import cn.yano.samples.network.rpc.bean.User;
import cn.yano.samples.network.rpc.config.AppConfig;
import cn.yano.samples.network.rpc.enums.UserSexEnum;
import cn.yano.samples.network.rpc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;
import java.util.List;

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
        InvocationHandler userServiceHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket clientSocket = null;
                ObjectOutputStream objectOutputStream = null;
                ObjectInputStream objectInputStream = null;
                try {
                    // 创建客户端 Socket 并指明需要连接的服务器端的主机名及端口号
                    clientSocket = new Socket("127.0.0.1", AppConfig.TCP_SERVER_PORT);
                    // Socket输出
                    objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                    // Socket输入
                    objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                    // 向服务器发送数据
                    objectOutputStream.writeUTF(proxy.getClass().getInterfaces()[0].getName());
                    objectOutputStream.writeUTF(method.getName());
                    objectOutputStream.writeObject(method.getParameterTypes());
                    objectOutputStream.writeObject(args);
                    objectOutputStream.flush();
                    // 接收服务器返回数据
                    Object response = objectInputStream.readObject();
                    if (response instanceof Throwable) {
                        throw (Throwable) response;
                    }
                    return response;
                } catch (Exception e) {
                    logger.error("client process error : ", e);
                } finally {
                    if (objectInputStream != null) {
                        try {
                            objectInputStream.close();
                        } catch (IOException e) {
                            logger.error("client bufferedReader close error : ", e);
                        }
                    }
                    if (objectOutputStream != null) {
                        try {
                            objectOutputStream.close();
                        } catch (IOException e) {
                            logger.error("client dataOutputStream close error : ", e);
                        }
                    }
                    if (clientSocket != null) {
                        try {
                            clientSocket.close();
                        } catch (IOException e) {
                            logger.error("client socket close error : ", e);
                        }
                    }
                }
                return null;
            }
        };
        UserService userService = (UserService) Proxy.newProxyInstance(UserService.class.getClassLoader(), new Class[]{UserService.class}, userServiceHandler);
        List<User> userList = userService.getUserList(UserSexEnum.WOMEN.getCode());
        if (userList != null) {
            for (User user : userList) {
                System.out.println("find user : " + user.getUsername());
            }
        }
    }
}
