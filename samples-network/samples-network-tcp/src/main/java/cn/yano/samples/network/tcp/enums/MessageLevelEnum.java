package cn.yano.samples.network.tcp.enums;

/**
 * 消息等级枚举
 * Created by xieyangyang0636 on 2020/1/19.
 */
public enum MessageLevelEnum {
    /**
     * INFO
     */
    INFO(0),

    /**
     * WARN
     */
    WARN(1),

    /**
     * ERROR
     */
    ERROR(2);

    private int value;

    private MessageLevelEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
