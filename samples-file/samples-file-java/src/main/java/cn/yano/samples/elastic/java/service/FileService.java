package cn.yano.samples.elastic.java.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * 文件服务类
 * Created by xieyangyang0636 on 2020/2/2.
 **/
public abstract class FileService {

    /**
     * LOGGER
     */
    private final static Logger logger = LoggerFactory.getLogger(FileService.class);

    /**
     * File
     */
    protected File file;

    /**
     * 构造函数
     *
     * @param filePath 文件路径
     */
    public FileService(String filePath) {
        this.file = new File(filePath);
    }

    /**
     * 构造函数
     *
     * @param filePath 文件路径
     * @param append   是否续写
     */
    public FileService(String filePath, boolean append) throws IOException {
        this.file = new File(filePath);
        if (this.file.exists()) {
            if (!append) {
                if (this.file.delete()) {
                    if (!this.file.createNewFile()) {
                        logger.error("{} create new file failed.", filePath);
                    }
                } else {
                    logger.error("{} delete failed.", filePath);
                }
            }
        }
    }


}
