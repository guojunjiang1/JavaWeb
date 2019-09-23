package service;

import domain.Category;
import domain.PageBean;
import domain.Route;

import java.util.List;

public interface CategoryZD {
    //查询分类信息
    List<Category> findAll();
    //分页查询信息
    PageBean<Route> findByPage(String cid, String currentPage, String rows,String name);
}
