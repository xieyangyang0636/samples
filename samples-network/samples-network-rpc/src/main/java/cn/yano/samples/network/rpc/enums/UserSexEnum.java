package cn.yano.samples.network.rpc.enums;

/**
 * 用户性别枚举
 * Created by xieyangyang0636 on 2020/1/19.
 */
public enum UserSexEnum {
    /**
     * 男
     */
    MAN("男", 0),
    /**
     * 女
     */
    WOMEN("女", 1);

    /**
     * 描述
     */
    private String desc;

    /**
     * 代码
     */
    private Integer code;

    /**
     * 构造函数
     *
     * @param desc 描述
     * @param code 代码
     */
    UserSexEnum(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }
}
