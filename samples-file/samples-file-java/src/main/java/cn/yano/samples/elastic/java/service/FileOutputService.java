package cn.yano.samples.elastic.java.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * 文件输出服务类
 * Created by xieyangyang0636 on 2020/2/2.
 **/
public class FileOutputService extends FileService {

    /**
     * LOGGER
     */
    private final static Logger logger = LoggerFactory.getLogger(FileOutputService.class);

    /**
     * 文件输出流
     */
    private FileOutputStream fileOutputStream;

    /**
     * 输出流Writer
     */
    private OutputStreamWriter outputStreamWriter;

    /**
     * 文件 Writer
     */
    private BufferedWriter bufferedWriter;

    /**
     * 构造函数
     *
     * @param filePath 文件路径
     * @param encode   编码
     * @param append   是否续写
     */
    public FileOutputService(String filePath, String encode, boolean append) throws IOException {
        super(filePath, append);
        this.fileOutputStream = new FileOutputStream(filePath, append);
        this.outputStreamWriter = new OutputStreamWriter(this.fileOutputStream, encode);
        this.bufferedWriter = new BufferedWriter(this.outputStreamWriter);
    }

    /**
     * 写数据
     *
     * @param line 一行数据
     */
    public void write(String line) throws IOException {
        this.bufferedWriter.write(line + "\n");
        this.bufferedWriter.flush();
    }

    /**
     * 关闭链接
     */
    public void close() throws IOException {
        this.bufferedWriter.close();
        this.outputStreamWriter.close();
        this.fileOutputStream.close();
    }

}
