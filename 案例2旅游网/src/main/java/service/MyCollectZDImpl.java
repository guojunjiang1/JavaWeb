package service;

import dao.MyCollectZDdao;
import dao.MyCollectZDdaoImpl;
import domain.*;

import java.util.ArrayList;
import java.util.List;

public class MyCollectZDImpl implements MyCollectZD{
    private MyCollectZDdao a=new MyCollectZDdaoImpl();
    //根据uid查找用户的收藏(分页查询)
    @Override
    public PageBean<Route> findAll(int uid1,int currentPage,int row) {
        PageBean<Route> pageBean=new PageBean<>();
        int start=0;
        start=(currentPage-1)*row;//查询的开始位置
        List<MyFavorite> list=a.findall(uid1,start,row);//分页查询到用户的收藏id
        List<Route> list1=new ArrayList<>();
        for (MyFavorite xx:list){
            Route route=a.findRoute(xx.getRid());//根据用户的收藏id查询具体的商品
            list1.add(route);
        }
        pageBean.setList(list1);//展示信息
        pageBean.setRows(row);//每页显示的记录数
        pageBean.setCurrentPage(currentPage);//当前页
        int total = a.findcount(uid1);
        pageBean.setTotalCount(total);//总记录数
        int i = total % row;
        if(i==0){
            pageBean.setTotalPage(total/row);//总页数
        }else {
            pageBean.setTotalPage(total/row+1);//总页数
        }
        return pageBean;
    }
}
