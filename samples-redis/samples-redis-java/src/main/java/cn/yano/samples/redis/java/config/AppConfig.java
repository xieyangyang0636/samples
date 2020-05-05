package cn.yano.samples.redis.java.config;

/**
 * 程序配置类
 * Created by xieyangyang0636 on 2020/1/19.
 */
public class AppConfig {

    /**
     * 	redis 连接超时时间
     */
    public final static int REDIS_CONNECT_TIMEOUT = 1000;

    /**
     * 	redis 读超时时间
     */
    public final static int REDIS_SO_TIMEOUT = 2000;

    /**
     * redis 最多重试次数
     */
    public final static int REDIS_MAX_ATTEMPTS = 3;

    /**
     * 	redis 最大连接数
     */
    public final static int REDIS_MAX_TOTAL = 200;

    /**
     * 	redis 最大空闲连接数
     */
    public final static int REDIS_MAX_IDLE = 100;

    /**
     * 	redis 最小空闲连接数
     */
    public final static int REDIS_MIN_IDLE = 10;

    /**
     * 	redis 最大等待时间
     */
    public final static int REDIS_MAX_WAIT_MILLIS = 3000;

    /**
     * 	redis 密码
     */
    public final static String REDIS_PASSWORD = "7ae75ac562a836ab6f88425a332f6764";

    /**
     * 	redis 集群hosts and port
     */
    public final static String REDIS_HOST_AND_PORT = "10.20.26.90:7000,10.20.26.91:7003,10.20.26.95:7006";

}
