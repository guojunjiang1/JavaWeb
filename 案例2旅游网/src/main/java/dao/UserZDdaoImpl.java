package dao;

import domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtil;

//用户注册登录
public class UserZDdaoImpl implements UserZDdao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtil.getDataSource());
    @Override
    //注册账号
    public void upZc(User user) {
        String sql="insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        template.update(sql, user.getUsername(), user.getPassword(), user.getName(), user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail(),user.getStatus(),user.getCode());
    }
    //注册账号查看用户名是否存在
    @Override
    public User findName(String name) {
        String sql="select * from tab_user where username=?";
        User user=null;
        try{
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), name);
        }catch (Exception e){
          return null;
        }
        return user;
    }
    //查看激活码对应的用户是否存在
    @Override
    public User findCodeDao(String code) {
        String sql="select * from tab_user where code=?";
        User user=null;
        try{
            user=template.queryForObject(sql,new BeanPropertyRowMapper<>(User.class),code);
        }catch (Exception e){
            return null;
        }
        return user;
    }
    //设置激活码为激活状态
    @Override
    public void setStatus(User user) {
        String sql="UPDATE tab_user set status='Y' where uid=?";
        template.update(sql,user.getUid());
    }
    //通过账号密码查找用户
    @Override
    public User findUserBypsAndun(String username, String password) {
        String sql="select * from tab_user where username=? and password=?";
        User user=null;
        try{
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username, password);
        }catch (Exception e){
            return null;
        }
        return user;
    }

}
