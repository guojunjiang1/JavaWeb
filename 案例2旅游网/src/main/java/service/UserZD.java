package service;

import domain.User;

//用户注册登录
public interface UserZD {
    //注册账号
    boolean upZc(User user);
    //通过激活码激活用户
    boolean findCode(String code);
    //登录账号
    int UserLogin(User user);
}
