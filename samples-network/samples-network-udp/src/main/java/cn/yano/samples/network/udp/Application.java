package cn.yano.samples.network.udp;

import cn.yano.samples.network.udp.bean.InfoBean;
import cn.yano.samples.network.udp.config.AppConfig;
import cn.yano.samples.network.udp.enums.MessageLevelEnum;
import cn.yano.samples.network.udp.service.UDPClientService;
import cn.yano.samples.network.udp.service.UDPServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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
        logger.info("start udp server");
        new Thread(() -> {
            UDPServerService serverService = null;
            try {
                serverService = new UDPServerService(AppConfig.UDP_SERVER_PORT);
                while (true) {
                    InfoBean infoBean = serverService.receive();
                    if (infoBean.getLevel() == MessageLevelEnum.ERROR.getValue()) {
                        logger.warn("client {} occur an error : {}", infoBean.getClient(), infoBean.getMsg());
                    } else {
                        logger.info("all client is ok");
                    }
                    Thread.sleep(1000L);
                }
            } catch (Exception e) {
                logger.error("server process error : ", e);
            } finally {
                if (serverService != null) {
                    serverService.close();
                }
            }
        }).start();

        // 模拟客户端线程
        logger.info("start udp client");
        new Thread(() -> {
            UDPClientService clientService = null;
            try {
                clientService = new UDPClientService(AppConfig.UDP_CLIENT_PORT);
                while (true) {
                    clientService.send(new InfoBean("clientA", "mem is full", System.currentTimeMillis(), MessageLevelEnum.ERROR.getValue()));
                    clientService.send(new InfoBean("clientB", "mem is high", System.currentTimeMillis(), MessageLevelEnum.WARN.getValue()));
                    clientService.send(new InfoBean("clientC", "mem is low", System.currentTimeMillis(), MessageLevelEnum.INFO.getValue()));
                    clientService.send(new InfoBean("clientD", "mem is full", System.currentTimeMillis(), MessageLevelEnum.ERROR.getValue()));
                    Thread.sleep(1000L);
                }
            } catch (Exception e) {
                logger.error("server process error : ", e);
            } finally {
                if (clientService != null) {
                    clientService.close();
                }
            }
        }).start();

        // 阻塞主程序
        System.in.read();
    }
}
