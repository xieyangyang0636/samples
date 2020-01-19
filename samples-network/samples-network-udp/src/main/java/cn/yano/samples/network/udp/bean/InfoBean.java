package cn.yano.samples.network.udp.bean;

/**
 * 消息类
 * Created by xieyangyang0636 on 2020/1/19.
 */
public class InfoBean {

    /**
     * Client
     */
    private String client;

    /**
     * 消息
     */
    private String msg;

    /**
     * 时间戳
     */
    private long time;

    /**
     * 消息等级（0：info，1：warn，2：error）
     */
    private int level;

    public InfoBean() {
    }

    public InfoBean(String client, String msg, long time, int level) {
        this.client = client;
        this.msg = msg;
        this.time = time;
        this.level = level;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
}
