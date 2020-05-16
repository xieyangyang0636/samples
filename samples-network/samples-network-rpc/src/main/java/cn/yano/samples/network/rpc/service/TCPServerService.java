package cn.yano.samples.network.rpc.service;

import cn.yano.samples.network.rpc.ServerApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Method;
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
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            logger.info("client {} connected server", socket.toString());
            // Socket输入
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            // Socket输出
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            // 获取客户端传入的类对应实例
            String interfaceName = objectInputStream.readUTF();
            Object serviceImpl = ServerApplication.serviceImplMap.get(interfaceName);
            if (serviceImpl != null) {
                logger.info("find service : {}", serviceImpl.getClass().getName());
                // 返回数据至客户端
                String methodName = objectInputStream.readUTF();
                Class<?>[] parameterTypes = (Class<?>[]) objectInputStream.readObject();
                Object[] arguments = (Object[]) objectInputStream.readObject();
                Method method = serviceImpl.getClass().getMethod(methodName, parameterTypes);
                Object result = method.invoke(serviceImpl, arguments);
                objectOutputStream.writeObject(result);
                Thread.sleep(5000L);
            } else {
                objectOutputStream.writeObject(new RuntimeException("service not found"));
            }
        } catch (Exception e) {
            logger.error("server process error : ", e);
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    logger.error("server close objectOutputStream error : ", e);
                }
            }
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    logger.error("server close objectInputStream error : ", e);
                }
            }
        }
    }
}
