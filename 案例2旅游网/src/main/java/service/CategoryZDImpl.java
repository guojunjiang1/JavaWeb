package service;

import dao.CategoryZDdaoImpl;
import domain.Category;
import domain.PageBean;
import domain.Route;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;
import util.JedisUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryZDImpl implements CategoryZD{
    CategoryZDdaoImpl a=new CategoryZDdaoImpl();
    //查询分类信息
    //用redis缓存来提高效率
    public List<Category> findAll() {
        Jedis jedis= JedisUtil.getJedis();
        Set<Tuple> set = jedis.zrangeWithScores("category", 0, -1);
        if(set==null||set.size()==0){
            //说明Jedis中没有数据，需要放数据
            List<Category> list1 = a.findAll();//从数据库中查询数据
            for(Category name:list1){
                jedis.zadd("category",name.getCid(),name.getCname());
            }
            JedisUtil.close(jedis);
            return list1;
        }else {
            //说明Jedis中有数据
            List<Category> list2=new ArrayList<>();
            for(Tuple name:set){
                Category category=new Category();
                category.setCname(name.getElement());
                category.setCid((int)name.getScore());
                list2.add(category);
            }
            JedisUtil.close(jedis);
            return list2;
        }
    }
    //分页查询信息
    @Override
    public PageBean<Route> findByPage(String cid, String currentPage, String rows,String name) {
        int start=0;
        start=(Integer.parseInt(currentPage)-1)*Integer.parseInt(rows);
        List<Route> list = a.findByPage(cid, start, rows,name);
        int total=a.findByTotal(cid,name);
        PageBean<Route> pageBean=new PageBean<>();
        pageBean.setRows(Integer.parseInt(rows));//每页查询几条记录
        pageBean.setCurrentPage(Integer.parseInt(currentPage));//当前的页数
        pageBean.setTotalCount(total);//总记录数
        pageBean.setList(list);//数据
        int i = total % Integer.parseInt(rows);
        if(i==0){
            pageBean.setTotalPage(total/Integer.parseInt(rows));//总页数
        }else {
            pageBean.setTotalPage(total/Integer.parseInt(rows)+1);//总页数
        }
        return pageBean;
    }
}
