package cn.yano.samples.elastic.java.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 文件输入服务类
 * Created by xieyangyang0636 on 2020/2/2.
 **/
public class FileInputService extends FileService {

    /**
     * LOGGER
     */
    private final static Logger logger = LoggerFactory.getLogger(FileInputService.class);

    /**
     * 文件输出流
     */
    private FileInputStream fileInputStream;

    /**
     * 输出流Writer
     */
    private InputStreamReader inputStreamReader;

    /**
     * 文件 Reader
     */
    private BufferedReader bufferedReader;

    /**
     * 构造函数
     *
     * @param filePath 文件路径
     * @param encode   编码
     */
    public FileInputService(String filePath, String encode) throws IOException {
        super(filePath);
        this.fileInputStream = new FileInputStream(filePath);
        this.inputStreamReader = new InputStreamReader(this.fileInputStream, encode);
        this.bufferedReader = new BufferedReader(this.inputStreamReader);
    }

    /**
     * 读数据
     *
     * @return 一行数据
     */
    public String read() throws IOException {
        return this.bufferedReader.readLine();
    }

    /**
     * 关闭链接
     */
    public void close() throws IOException {
        this.bufferedReader.close();
        this.inputStreamReader.close();
        this.fileInputStream.close();
    }

}
