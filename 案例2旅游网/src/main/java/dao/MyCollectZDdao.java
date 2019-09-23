package dao;

import domain.MyFavorite;
import domain.Route;

import java.util.List;

public interface MyCollectZDdao {
    //根据uid查找用户的收藏
    List<MyFavorite> findall(int uid1,int start,int row);
    //根据rid查找对应的旅游信息
    Route findRoute(int rid);
    //总记录数
    int findcount(int uid1);
}
