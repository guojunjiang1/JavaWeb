package service;

import dao.RouteZDdao;
import dao.RouteZDdaoImpl;
import domain.Category;
import domain.Route;
import domain.RouteImg;
import domain.Seller;

import java.util.List;

public class RouteZDImpl implements RouteZD{
    private RouteZDdao a=new RouteZDdaoImpl();
    @Override
    //查询具体的旅游信息
    public Route findall(int rid1) {
        List<RouteImg> img=a.findImg(rid1);//查询rid为rid1照片
        Route route=a.findRoute(rid1);//查询rid为rid1的旅游信息
        Seller seller=a.findSeller(route.getSid());//查询sid为sid的商家信息
        Category category=a.findCateGory(route.getCid());//查询cid为cid的分类信息
        route.setCategory(category);//将分类信息添加到route当中
        route.setRouteImgList(img);//将照片信息添加到route当中
        route.setSeller(seller);//将商家信息添加到route当中
        return route;
    }
    //通过用户名查询id
    @Override
    public int findUid(String rong) {
        return a.findUid(rong);
    }
    //查看是否收藏过
    @Override
    public boolean findFavorite(int rid1, int uid) {
        return a.findFavorite(rid1,uid);
    }
    //查看收藏的次数
    @Override
    public int findNum(int rid1) {
        return  a.findNum(rid1);
    }
    //添加收藏
    @Override
    public void insertFavorite(int rid1, int uid) {
        a.insertFavorite(rid1,uid);
    }
}
