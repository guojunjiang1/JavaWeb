package service;

import domain.PageBean;
import domain.Route;

public interface MyCollectZD {
    //根据uid查找用户的收藏
    PageBean<Route> findAll(int uid1,int currentPage,int row);
}
