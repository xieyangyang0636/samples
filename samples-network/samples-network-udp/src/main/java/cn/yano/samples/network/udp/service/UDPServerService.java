package cn.yano.samples.network.udp.service;

import cn.yano.samples.network.udp.bean.InfoBean;
import cn.yano.samples.network.udp.utils.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;

/**
 * UDP 服务端服务类
 * Created by xieyangyang0636 on 2020/1/19.
 */
public class UDPServerService extends UDPService {

    /**
     * 日志Logger
     */
    private final static Logger logger = LoggerFactory.getLogger(UDPServerService.class);

    /**
     * UDPService构造方法
     *
     * @param port 端口
     */
    public UDPServerService(int port) throws SocketException {
        super(port);
    }

    /**
     * 接收信息
     *
     * @return 信息
     * @throws IOException
     */
    public InfoBean receive() throws IOException {
        // 创建发送数据包，并标注源地址#，目的地址#
        byte[] receiveData = new byte[1024];
        // 定义接收数据
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        // 接收数据
        super.receive(receivePacket);
        return SerializeUtil.deserialize(receivePacket.getData(), InfoBean.class);
    }

}
