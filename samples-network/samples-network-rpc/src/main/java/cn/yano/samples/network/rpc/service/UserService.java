package cn.yano.samples.network.rpc.service;

import cn.yano.samples.network.rpc.bean.User;

import java.util.List;

/**
 * 用户服务接口类
 * Created by xieyangyang0636 on 2020/1/19.
 */
public interface UserService {

    /**
     * 获取用户列表
     *
     * @param code 性别代码
     * @return 用户列表
     */
    List<User> getUserList(Integer code);

    /**
     * 按名称获取用户列表
     *
     * @param name 用户名称
     * @return 用户列表
     */
    List<User> getUserByName(String name);
}
