package cn.yano.samples.elastic.java;

import cn.yano.samples.elastic.java.config.AppConfig;
import cn.yano.samples.elastic.java.service.FileInputService;
import cn.yano.samples.elastic.java.service.FileOutputService;
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
        logger.info("file test start");
        FileOutputService fileOutputService = null;
        try {
            fileOutputService = new FileOutputService(AppConfig.FILE_PATH, AppConfig.FILE_ENCODE, true);
            fileOutputService.write("Hello World");
        } catch (Exception e) {
            logger.error("program process error : ", e);
        } finally {
            if (fileOutputService != null) {
                fileOutputService.close();
            }
        }

        FileInputService fileInputService = null;
        try {
            fileInputService = new FileInputService(AppConfig.FILE_PATH, AppConfig.FILE_ENCODE);
            System.out.println(fileInputService.read());
        } catch (Exception e) {
            logger.error("program process error : ", e);
        } finally {
            if (fileInputService != null) {
                fileInputService.close();
            }
        }
        logger.info("file test end");
    }

}
