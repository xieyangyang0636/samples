package cn.yano.samples.elastic.java.config;

/**
 * 程序配置类
 * Created by xieyangyang0636 on 2020/1/19.
 */
public class AppConfig {

    /**
     * 	elastic 集群hosts and port
     */
    public final static String ES_NODES = "10.20.26.90:9300,10.20.26.91:9300,10.20.26.95:9300";

    /**
     * elastic 集群名称
     */
    public final static String ES_CLUSTER_NAME = "networkcountly";

    /**
     * elastic 最大活跃连接数
     */
    public final static int ES_MIN_IDLE = 4;

    /**
     * elastic 最小空闲连接数
     */
    public final static int ES_MAX_ACTIVE = 10;

    /**
     * elastic 资讯索引别名
     */
    public final static String ES_INDEX_ALIAS = "yano";

    /**
     * elastic 索引模板
     */
    public final static String ES_OLD_INDEX = "yano_index";

}
