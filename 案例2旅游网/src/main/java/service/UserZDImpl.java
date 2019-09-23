package service;

import dao.UserZDdaoImpl;
import domain.User;
import util.MailUtils;
import util.UuidUtil;

//用户注册登录
public class UserZDImpl implements UserZD {
    UserZDdaoImpl a=new UserZDdaoImpl();
    //注册账号
    public boolean upZc(User user) {
        User name = a.findName(user.getUsername());
        if(name!=null){
            return false;
        }else {
            String uuid = UuidUtil.getUuid();//产生一个该用户的激活码
            user.setCode(uuid);
            user.setStatus("N");
            a.upZc(user);
            String value="<a href='http://192.168.30.1:8080/maven_LYW/user/jiHuoServlet?code="+user.getCode()+"'>点击激活【旅游网】</a>";
            MailUtils.sendMail(user.getEmail(),value,"激活旅游网");
            return true;
        }
    }
    //通过激活码激活用户
    @Override
    public boolean findCode(String code) {
        User b=a.findCodeDao(code);
        if(b==null){
            return false;
        }else {
            a.setStatus(b);
            return true;
        }
    }
    //登录账号(查询)
    @Override
    public int UserLogin(User user) {
        User user1=a.findUserBypsAndun(user.getUsername(),user.getPassword());
        if(user1!=null){
            if(user1.getStatus().equals("Y")){
                return 1;
            }else {
                return 2;
            }
        }else {
            return 3;
        }
    }
}
