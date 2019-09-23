package dao;

import domain.Category;
import domain.Route;

import java.util.List;

public interface CategoryZDdao {
    //查找分类信息
    List<Category> findAll();
    //分页查询信息
    List<Route> findByPage(String cid, int start, String rows,String name);
    //查询总记录数
    int findByTotal(String cid,String name);
}
