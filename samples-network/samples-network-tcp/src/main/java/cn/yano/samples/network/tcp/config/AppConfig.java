package cn.yano.samples.network.tcp.config;

/**
 * 程序配置类
 * Created by xieyangyang0636 on 2020/1/19.
 */
public class AppConfig {

    /**
     * TCP客户端端口
     */
    public final static int TCP_CLIENT_PORT = 28083;

    /**
     * TCP服务端端口
     */
    public final static int TCP_SERVER_PORT = 28084;

    /**
     * TCP服务端最大连接数
     */
    public final static int TCP_SERVER_MAX_CONN_SIZE = 10;

    /**
     * TCP服务端最大连接队列数
     */
    public final static int TCP_SERVER_MAX_QUEUE_SIZE = 5;

}
