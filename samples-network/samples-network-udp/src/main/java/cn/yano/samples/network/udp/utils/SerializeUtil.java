package cn.yano.samples.network.udp.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 序列化工具类
 * Created by xieyangyang0636 on 2020/1/19.
 */
public class SerializeUtil {

    /**
     * 序列化
     *
     * @param srBean 原始Bean
     * @param <T>    原始Bean类型
     * @return JSONString序列化
     */
    public static <T> byte[] serialize(T srBean) {
        return JSONObject.toJSONString(srBean).getBytes();
    }

    /**
     * 反序列化
     *
     * @param serializedBytes JSONString序列化
     * @param tClass          原始Bean类
     * @param <T>             原始Bean类型
     * @return 原始Bean
     */
    public static <T> T deserialize(byte[] serializedBytes, Class tClass) {
        return (T) JSONObject.toJavaObject(JSON.parseObject(new String(serializedBytes)), tClass);
    }


}
