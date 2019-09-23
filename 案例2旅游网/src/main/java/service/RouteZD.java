package service;

import domain.Route;

public interface RouteZD {
    //查询详细信息
    Route findall(int rid1);
    //通过用户名查询id
    int findUid(String rong);
    //添加收藏
    void insertFavorite(int rid1, int uid);
    //查看是否收藏过
    boolean findFavorite(int rid1, int uid);
    //查看收藏的次数
    int findNum(int rid1);
}
