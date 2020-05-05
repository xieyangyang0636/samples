package cn.yano.samples.redis.spring.config;

import cn.yano.modules.utils.encrypt.AESUtils;
import cn.yano.modules.utils.redis.JedisUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisCluster;

@Configuration
@ConfigurationProperties("redis")
public class RedisConfig {
    private String password;
    private String master;
    private String clusterNodes;
    private int maxTotal;
    private int maxIdle;
    private int minIdle;
    private int maxWaitMillis;
    private int connectionTimeout;
    private int soTimeout;
    private int maxAttempts;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws Exception {
        if (StringUtils.isNotEmpty(password)) {
            AESUtils aesUtil = new AESUtils();
            this.password = aesUtil.decrypt(password);
        }
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getClusterNodes() {
        return clusterNodes;
    }

    public void setClusterNodes(String clusterNodes) {
        this.clusterNodes = clusterNodes;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(int maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    @Bean(name = "redisTemplate")
    public JedisCluster redis() throws Exception {
        if (StringUtils.isEmpty(this.password)) {
            return JedisUtils.getJedisCluster(this.clusterNodes, this.maxTotal,
                    this.maxIdle, this.minIdle, this.maxWaitMillis, this.connectionTimeout,
                    this.soTimeout, this.maxAttempts);
        } else {
            return JedisUtils.getJedisCluster(this.clusterNodes, this.password, this.maxTotal,
                    this.maxIdle, this.minIdle, this.maxWaitMillis, this.connectionTimeout,
                    this.soTimeout, this.maxAttempts);
        }
    }
}
