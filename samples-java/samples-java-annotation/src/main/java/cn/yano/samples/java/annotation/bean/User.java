package cn.yano.samples.java.annotation.bean;

import cn.yano.samples.java.annotation.annotations.FixCase;
import cn.yano.samples.java.annotation.annotations.MD5;

import java.io.Serializable;

/**
 * 用户类
 * Created by xieyangyang0636 on 2020/1/19.
 */
public class User implements Serializable {

    private static final long serialVersionUID = 8962607278401673L;
    /**
     * 用户名
     */
    @FixCase(type = 0)
    private String username;

    /**
     * 性别
     */
    @FixCase(type = 1)
    private String sex;

    /**
     * 密码
     */
    @MD5
    private String password;

    /**
     * 构造函数
     *
     * @param username 用户名
     * @param sex      性别
     * @param password 密码
     */
    public User(String username, String sex, String password) {
        this.username = username;
        this.sex = sex;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return super.toString() + "[username=" + this.username + ",sex=" + this.sex + ",password=" + this.password + "]";
    }
}
