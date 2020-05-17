package cn.yano.samples.java.annotation;

import cn.yano.modules.utils.encrypt.MD5Utils;
import cn.yano.samples.java.annotation.annotations.FixCase;
import cn.yano.samples.java.annotation.annotations.MD5;
import cn.yano.samples.java.annotation.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

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
    public static void main(String[] args) throws IOException, NoSuchFieldException, IllegalAccessException {
        User user = new User("ADMIN", "man", "123456");
        System.out.println(user);

        Field[] fields = User.class.getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof FixCase) {
                    field.setAccessible(true);
                    if (((FixCase) annotation).type() == 0) {
                        field.set(user, field.get(user).toString().toLowerCase());
                    } else {
                        field.set(user, field.get(user).toString().toUpperCase());
                    }
                } else if (annotation instanceof MD5) {
                    field.setAccessible(true);
                    field.set(user, MD5Utils.getMd5(field.get(user).toString()));
                }
            }
        }

        System.out.println(user);
    }

}
