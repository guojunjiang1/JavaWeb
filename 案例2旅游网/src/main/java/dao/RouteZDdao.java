package dao;

import domain.Category;
import domain.Route;
import domain.RouteImg;
import domain.Seller;

import java.util.List;

public interface RouteZDdao {
    //查询照片
    List<RouteImg> findImg(int rid1);
    //查询旅游信息
    Route findRoute(int rid1);
    //查询商家信息
    Seller findSeller(int sid);
    //查询分类信息
    Category findCateGory(int cid);
    //通过用户名查询id
    int findUid(String rong);
    //添加收藏
    void insertFavorite(int rid1, int uid);
    //查看是否收藏过
    boolean findFavorite(int rid1, int uid);
    //查看收藏的次数
    int findNum(int rid1);
}
