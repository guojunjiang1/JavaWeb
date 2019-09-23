package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.RouteZDdao;
import dao.RouteZDdaoImpl;
import domain.PageBean;
import domain.ResultInfo;
import domain.Route;
import service.MyCollectZD;
import service.MyCollectZDImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//我的收藏Servlet
@WebServlet(urlPatterns = "/collect/*")
public class MyCollectServlet extends BaseServlet {
    private MyCollectZD a=new MyCollectZDImpl();
    //查找登录用户的id
    public void findUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rong =(String)request.getSession().getAttribute("rong");
        ObjectMapper om=new ObjectMapper();
        ResultInfo resultInfo=new ResultInfo();
        if(rong==null){
            resultInfo.setFlag(false);
        }else {
            RouteZDdao a=new RouteZDdaoImpl();
            int uid = a.findUid(rong);//查找该用户的Uid
            resultInfo.setFlag(true);
            resultInfo.setData(uid);
        }
        String value = om.writeValueAsString(resultInfo);
        response.getWriter().write(value);
    }
    //根据用户的id查找他的收藏
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uid = request.getParameter("uid");
        String currentPage = request.getParameter("currentPage");
        int currentPage1=1;
        if(currentPage!=null&&currentPage.length()>0&&!currentPage.equals("null")){
            currentPage1 = Integer.parseInt(currentPage);
        }
        int uid1=0;
        if(uid!=null&&uid.length()>0&&!uid.equals("null")){
            uid1 = Integer.parseInt(uid);
        }
        int row=12;
        PageBean<Route> all = a.findAll(uid1,currentPage1,row);//分页查询用户的收藏
        ObjectMapper om=new ObjectMapper();
        String value = om.writeValueAsString(all);
        response.getWriter().write(value);
    }
}
