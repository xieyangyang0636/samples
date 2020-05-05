package cn.yano.samples.redis.java.service;

import cn.yano.modules.utils.encrypt.AESUtils;
import cn.yano.modules.utils.redis.JedisUtils;
import cn.yano.samples.redis.java.config.AppConfig;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Redis服务类
 * Created by xieyangyang0636 on 2020/2/2.
 */
public class RedisService extends JedisCluster {

    /**
     * 日志Logger
     */
    private final static Logger logger = LoggerFactory.getLogger(RedisService.class);

    /**
     * 单例对象
     */
    private static RedisService redisService;

    /**
     * 构造函数
     */
    private RedisService() {
        super(new HashSet<>());
    }

    /**
     * 构造函数
     *
     * @param nodeSet    nodeSet
     * @param poolConfig poolConfig
     * @param passwd     passwd
     */
    private RedisService(Set<HostAndPort> nodeSet, JedisPoolConfig poolConfig, String passwd) {
        super(nodeSet, AppConfig.REDIS_CONNECT_TIMEOUT, AppConfig.REDIS_SO_TIMEOUT, AppConfig.REDIS_MAX_ATTEMPTS,
                passwd, poolConfig);
    }

    /**
     * 构造函数
     *
     * @param nodeSet    nodeSet
     * @param poolConfig poolConfig
     */
    private RedisService(Set<HostAndPort> nodeSet, JedisPoolConfig poolConfig) {
        super(nodeSet, AppConfig.REDIS_CONNECT_TIMEOUT, AppConfig.REDIS_SO_TIMEOUT, AppConfig.REDIS_MAX_ATTEMPTS, poolConfig);
    }

    /**
     * 实例化一个Redis服务
     *
     * @return Redis服务
     */
    public synchronized static RedisService getInstance() {
        if (redisService == null) {
            Set<HostAndPort> nodeSet = JedisUtils.getHostAndPortSet(AppConfig.REDIS_HOST_AND_PORT);
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(AppConfig.REDIS_MAX_TOTAL);
            poolConfig.setMaxIdle(AppConfig.REDIS_MAX_IDLE);
            poolConfig.setMinIdle(AppConfig.REDIS_MIN_IDLE);
            poolConfig.setMaxWaitMillis(AppConfig.REDIS_MAX_WAIT_MILLIS);
            String password = AppConfig.REDIS_PASSWORD;
            if (StringUtils.isEmpty(password)) {
                redisService = new RedisService(nodeSet, poolConfig);
            } else {
                String passwd = null;
                try {
                    AESUtils aesUtil = new AESUtils();
                    passwd = aesUtil.decrypt(password);
                } catch (Exception e) {
                    logger.error("##### 密码解密失败", e);
                }
                redisService = new RedisService(nodeSet, poolConfig, passwd);
            }
        }
        return redisService;
    }

    @Override
    public void close() {
        try {
            super.close();
        } catch (IOException e) {
            logger.error("##### 关闭Redis资源链接失败", e);
        }
    }
}
