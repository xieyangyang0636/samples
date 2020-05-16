package cn.yano.samples.network.rpc.service.impl;

import cn.yano.samples.network.rpc.bean.User;
import cn.yano.samples.network.rpc.enums.UserSexEnum;
import cn.yano.samples.network.rpc.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户服务类
 * Created by xieyangyang0636 on 2020/1/19.
 */
public class UserServiceImpl implements UserService {

    /**
     * 获取用户列表
     *
     * @param code 性别代码
     * @return 用户列表
     */
    public List<User> getUserList(Integer code) {
        List<User> userList = new ArrayList<>();
        if (code == null) {
            return userList;
        }
        if (UserSexEnum.MAN.getCode().equals(code)) {
            userList.add(new User("小明", UserSexEnum.MAN.getDesc(), "apple"));
        } else if (UserSexEnum.WOMEN.getCode().equals(code)) {
            userList.add(new User("小红", UserSexEnum.WOMEN.getDesc(), "orange"));
        }
        return userList;
    }

    /**
     * 按名称获取用户列表
     *
     * @param name 用户名称
     * @return 用户列表
     */
    @Override
    public List<User> getUserByName(String name) {
        return new ArrayList<>();
    }
}
