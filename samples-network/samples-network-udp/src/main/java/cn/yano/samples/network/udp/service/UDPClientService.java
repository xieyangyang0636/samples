package cn.yano.samples.network.udp.service;

import cn.yano.samples.network.udp.bean.InfoBean;
import cn.yano.samples.network.udp.config.AppConfig;
import cn.yano.samples.network.udp.utils.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * UDP 客户端服务类
 * Created by xieyangyang0636 on 2020/1/19.
 */
public class UDPClientService extends UDPService {

    /**
     * 日志Logger
     */
    private final static Logger logger = LoggerFactory.getLogger(UDPClientService.class);

    /**
     * UDPService构造方法
     *
     * @param port 端口
     */
    public UDPClientService(int port) throws SocketException {
        super(port);
    }

    /**
     * 发送消息
     *
     * @param infoBean 消息
     * @throws IOException
     */
    public void send(InfoBean infoBean) throws IOException {
        // 定义发送数据
        byte[] sendData = SerializeUtil.serialize(infoBean);
        // 获取本地 IP 地址
        InetAddress IPAddress = InetAddress.getLocalHost();
        // 创建发送数据包，并标注源地址#，目的地址#
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, AppConfig.UDP_SERVER_PORT);
        // 发送数据
        super.send(sendPacket);
    }

}
