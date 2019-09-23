package dao;

import domain.User;

//用户注册登录
public interface UserZDdao {
    //注册账号
    void upZc(User user);
    //注册账号查看用户名是否存在
    User findName(String name);
    //查看激活码是否存在
    User findCodeDao(String code);
    //设置激活状态为激活
    void setStatus(User user);
    //通过账号密码查找用户
    User findUserBypsAndun(String username, String password);
}
