package cn.yano.samples.redis.java;

import cn.yano.samples.redis.java.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 程序启动类
 * Created by xieyangyang0636 on 2020/1/19.
 */
public class Application {

    /**
     * 日志Logger
     */
    private final static Logger logger = LoggerFactory.getLogger(Application.class);

    /**
     * 启动方法
     *
     * @param args 入参
     */
    public static void main(String[] args) throws IOException {
        logger.info("redis test start");
        RedisService redisService = null;
        try {
            redisService = RedisService.getInstance();
            String path = "/yano/test";
            String value = "hello";
            redisService.set(path, value);
            redisService.expire(path, 5);
            redisService.get(path);
            redisService.del(path);
        } catch (Exception e) {
            logger.error("program process error : ", e);
        } finally {
            if (redisService != null) {
                redisService.close();
            }
        }
        logger.info("redis test end");
    }

}
