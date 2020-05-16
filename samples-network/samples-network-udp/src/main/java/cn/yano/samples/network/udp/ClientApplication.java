package cn.yano.samples.network.udp;

import cn.yano.samples.network.udp.bean.InfoBean;
import cn.yano.samples.network.udp.config.AppConfig;
import cn.yano.samples.network.udp.enums.MessageLevelEnum;
import cn.yano.samples.network.udp.service.UDPClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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
        // 模拟客户端线程
        logger.info("start udp client");
        UDPClientService clientService = null;
        try {
            clientService = new UDPClientService(AppConfig.UDP_CLIENT_PORT);
            for(;;) {
                clientService.send(new InfoBean("clientA", "mem is full", System.currentTimeMillis(), MessageLevelEnum.ERROR.getValue()),
                        AppConfig.UDP_SERVER_PORT);
                clientService.send(new InfoBean("clientB", "mem is high", System.currentTimeMillis(), MessageLevelEnum.WARN.getValue()),
                        AppConfig.UDP_SERVER_PORT);
                clientService.send(new InfoBean("clientC", "mem is low", System.currentTimeMillis(), MessageLevelEnum.INFO.getValue()),
                        AppConfig.UDP_SERVER_PORT);
                clientService.send(new InfoBean("clientD", "mem is full", System.currentTimeMillis(), MessageLevelEnum.ERROR.getValue()),
                        AppConfig.UDP_SERVER_PORT);
                Thread.sleep(1000L);
            }
        } catch (Exception e) {
            logger.error("server process error : ", e);
        } finally {
            if (clientService != null) {
                clientService.close();
            }
        }

    }
}
