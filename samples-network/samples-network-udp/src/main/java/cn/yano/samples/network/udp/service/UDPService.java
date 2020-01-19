package cn.yano.samples.network.udp.service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * UDP 服务类
 * Created by xieyangyang0636 on 2020/1/19.
 */
public class UDPService {

    /**
     * DatagramSocket 对象
     */
    private DatagramSocket datagramSocket;

    /**
     * UDPService构造方法
     *
     * @param port 端口
     */
    public UDPService(int port) throws SocketException {
        this.datagramSocket = new DatagramSocket(port);
    }


    /**
     * 发送数据
     *
     * @param sendPacket 数据
     */
    public void send(DatagramPacket sendPacket) throws IOException {
        datagramSocket.send(sendPacket);
    }

    /**
     * 接收数据
     *
     * @param receivePacket 数据
     */
    public void receive(DatagramPacket receivePacket) throws IOException {
        datagramSocket.receive(receivePacket);
    }

    /**
     * 关闭连接
     *
     * @throws IOException
     */
    public void close() {
        datagramSocket.close();
    }

}
