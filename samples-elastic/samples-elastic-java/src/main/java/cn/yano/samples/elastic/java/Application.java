package cn.yano.samples.elastic.java;

import cn.yano.samples.elastic.java.config.AppConfig;
import cn.yano.samples.elastic.java.service.ElasticService;
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
        logger.info("elastic test start");
        ElasticService elasticService = null;
        try {
            elasticService = new ElasticService(AppConfig.ES_NODES, AppConfig.ES_CLUSTER_NAME, AppConfig.ES_MAX_ACTIVE, AppConfig.ES_MIN_IDLE);
            // elasticService.saveOrUpdate();
        } catch (Exception e) {
            logger.error("program process error : ", e);
        } finally {
            if (elasticService != null) {
                elasticService.close();
            }
        }
        logger.info("elastic test end");
    }

}
