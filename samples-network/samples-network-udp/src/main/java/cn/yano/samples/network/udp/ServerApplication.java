package cn.yano.samples.network.udp;

import cn.yano.samples.network.udp.bean.InfoBean;
import cn.yano.samples.network.udp.config.AppConfig;
import cn.yano.samples.network.udp.enums.MessageLevelEnum;
import cn.yano.samples.network.udp.service.UDPServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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
     * 启动方法
     *
     * @param args 入参
     */
    public static void main(String[] args) throws IOException {
        // 服务端模拟线程
        logger.info("start udp server");
        UDPServerService serverService = null;
        try {
            serverService = new UDPServerService(AppConfig.UDP_SERVER_PORT);
            while (true) {
                InfoBean infoBean = serverService.receive();
                if (infoBean.getLevel() == MessageLevelEnum.ERROR.getValue()) {
                    logger.warn("client {} occur an error : {}", infoBean.getClient(), infoBean.getMsg());
                }
            }
        } catch (Exception e) {
            logger.error("server process error : ", e);
        } finally {
            if (serverService != null) {
                serverService.close();
            }
        }

    }
}
