package cn.yano.samples.redis.spring.service.impl;

import cn.yano.samples.redis.spring.service.DataProcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;

/**
 * 资讯基础数据加工 service impl
 */
@Service
public class DataProcServiceImpl implements DataProcService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataProcServiceImpl.class);

    @Resource
    private JedisCluster redisTemplate;

    /**
     * 新增
     *
     * @param path  路径
     * @param value 值
     */
    @Override
    public void setRedis(String path, String value) {
        redisTemplate.set(path, value);
    }

    /**
     * 新增
     *
     * @param path  路径
     * @param value 值
     * @param ttl   ttl
     */
    @Override
    public void setRedisTTL(String path, String value, Integer ttl) {
        redisTemplate.set(path, value);
        redisTemplate.expire(path, ttl);
    }

    /**
     * 获取
     *
     * @param path 路径
     */
    @Override
    public String getRedis(String path) {
        return redisTemplate.get(path);
    }

    /**
     * 删除
     */
    @Override
    public void deleteRedis(String path) {
        redisTemplate.del(path);
    }
}
