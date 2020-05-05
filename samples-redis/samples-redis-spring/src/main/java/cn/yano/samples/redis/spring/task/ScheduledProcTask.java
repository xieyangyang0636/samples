package cn.yano.samples.redis.spring.task;

import cn.yano.samples.redis.spring.service.DataProcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 资讯基础数据加工 task
 *
 * @author loucx12727
 */
@Component
public class ScheduledProcTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledProcTask.class);

    @Resource
    private DataProcService dataProcService;

    @Scheduled(initialDelay = 1000, fixedDelay = 5000)
    public void redisOperate() {
        LOGGER.info("ScheduledProcTask redisOperate start -----------------------------");

        long start = System.currentTimeMillis();
        dataProcService.setRedis("/yano/test","hello");
        dataProcService.getRedis("/yano/test");
        dataProcService.setRedisTTL("/yano/test","hello", 5);
        dataProcService.deleteRedis("/yano/test");
        long end = System.currentTimeMillis();

        LOGGER.info("ScheduledProcTask redisOperate end. process time: " + (end - start) + "(ms)----------\n\n");
    }

}
