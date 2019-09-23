package dao;

import domain.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtil;

import java.util.Date;
import java.util.List;

public class RouteZDdaoImpl implements RouteZDdao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtil.getDataSource());
    //查询照片
    @Override
    public List<RouteImg> findImg(int rid1) {
        String sql="select * from tab_route_img where rid=?";
        List<RouteImg> query = template.query(sql, new BeanPropertyRowMapper<>(RouteImg.class),rid1);
        return query;
    }
    //查询旅游信息
    @Override
    public Route findRoute(int rid1) {
        String sql="select * from tab_route where rid=?";
        Route route = template.queryForObject(sql, new BeanPropertyRowMapper<>(Route.class), rid1);
        return route;
    }
    //查询商家信息
    @Override
    public Seller findSeller(int sid) {
        String sql="select * from tab_seller where sid=?";
        Seller seller = template.queryForObject(sql, new BeanPropertyRowMapper<>(Seller.class), sid);
        return seller;
    }
    //查询分类信息
    @Override
    public Category findCateGory(int cid) {
        String sql="select * from tab_category where cid=?";
        Category category = template.queryForObject(sql, new BeanPropertyRowMapper<>(Category.class), cid);
        return category;
    }
    //通过用户名查询id
    @Override
    public int findUid(String rong) {
       String sql="select * from tab_user where username=?";
        User user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), rong);
        return user.getUid();
    }

    //查看是否收藏过
    @Override
    public boolean findFavorite(int rid1, int uid) {
        String sql="select * from tab_favorite where rid=? and uid=?";
        MyFavorite favorite=null;
        try {
            favorite= template.queryForObject(sql, new BeanPropertyRowMapper<>(MyFavorite.class), rid1, uid);
        }catch (Exception e){
            return false;
        }
        if(favorite==null){
            return false;
        }else {
            return true;
        }
    }
    //查看收藏的次数
    @Override
    public int findNum(int rid1) {
        String sql="select count(rid) from tab_favorite where rid=?";
        Integer integer = template.queryForObject(sql, Integer.class, rid1);
        return integer;
    }

    //添加收藏
    @Override
    public void insertFavorite(int rid1, int uid) {
        String sql="insert into tab_favorite values(?,?,?)";
        template.update(sql,rid1,new Date(),uid);
    }
}
