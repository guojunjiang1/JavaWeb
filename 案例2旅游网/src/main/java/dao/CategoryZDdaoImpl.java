package dao;

import domain.Category;
import domain.Route;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtil;

import java.util.ArrayList;
import java.util.List;

public class CategoryZDdaoImpl implements CategoryZDdao{
    private JdbcTemplate a=new JdbcTemplate(JDBCUtil.getDataSource());
    //查找分类信息
    public List<Category> findAll() {
        String sql="select * from tab_category";
        List<Category> query = a.query(sql, new BeanPropertyRowMapper<>(Category.class));
        return query;
    }
    //分页查询信息
    @Override
    public List<Route> findByPage(String cid, int start, String rows,String name) {
        String sql="select * from tab_route where 1=1 ";
        StringBuilder sb=new StringBuilder(sql);//添加查询信息
        List<Object> list=new ArrayList();//添加条件
        if(cid!=null&&cid.length()>0&&!cid.equals("null")){
            int cid1 = Integer.parseInt(cid);
            sb.append(" and cid=? ");
            list.add(cid1);
        }
        if(name!=null&&name.length()>0&&!name.equals("null")){
            sb.append(" and rname like ? ");
            list.add("%"+name+"%");//模糊查询
        }
        sb.append(" LIMIT ? , ? ");
        sql=sb.toString();
        list.add(start);
        list.add(Integer.parseInt(rows));
        List<Route> query = a.query(sql, new BeanPropertyRowMapper<>(Route.class),list.toArray());
        return query;

    }
    //总记录数
    @Override
    public int findByTotal(String cid,String name) {
        String sql="select count(rid) from tab_route where 1=1 ";
        StringBuilder sb=new StringBuilder(sql);
        List<Object> list=new ArrayList<>();
        if(cid!=null&&cid.length()>0&&!cid.equals("null")){
            int cid1 = Integer.parseInt(cid);
            sb.append(" and cid=? ");
            list.add(cid1);
        }
        if(name!=null&&name.length()>0&&!name.equals("null")){
            sb.append(" and rname like ? ");
            list.add("%"+name+"%");//模糊查询
        }
        sql=sb.toString();
        Integer integer = a.queryForObject(sql, Integer.class,list.toArray());
        return integer;
    }
}
