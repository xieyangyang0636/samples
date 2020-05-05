package cn.yano.samples.redis.spring.service;

/**
 * 数据加工 service
 */
public interface DataProcService {
    /**
     * 新增
     */
    void setRedis(String path, String value);

    /**
     * 新增
     */
    void setRedisTTL(String path, String value, Integer ttl);

    /**
     * 获取
     */
    String getRedis(String path);

    /**
     * 删除
     */
    void deleteRedis(String path);

}
