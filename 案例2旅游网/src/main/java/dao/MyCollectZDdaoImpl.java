package dao;

import domain.MyFavorite;
import domain.Route;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtil;

import java.util.List;

public class MyCollectZDdaoImpl implements MyCollectZDdao{
    JdbcTemplate template=new JdbcTemplate(JDBCUtil.getDataSource());
    @Override
    //根据uid查找用户的收藏
    public List<MyFavorite> findall(int uid1,int start,int row) {
        String sql="select rid FROM tab_favorite WHERE uid=? LIMIT ? , ? ";
        List<MyFavorite> query = template.query(sql, new BeanPropertyRowMapper<>(MyFavorite.class), uid1,start,row);
        return query;
    }
    //根据rid查找对应的旅游信息
    @Override
    public Route findRoute(int rid) {
        String sql="select * from tab_route where rid=?";
        Route route = template.queryForObject(sql, new BeanPropertyRowMapper<>(Route.class), rid);
        return route;
    }

    @Override
    public int findcount(int uid1) {
        String sql="select count(*) from tab_favorite where uid=?";
        Integer integer = template.queryForObject(sql, Integer.class, uid1);
        return integer;
    }

}
