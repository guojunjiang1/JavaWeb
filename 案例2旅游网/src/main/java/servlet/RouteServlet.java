package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.ResultInfo;
import domain.Route;
import service.RouteZDImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//详细信息的展示
@WebServlet(urlPatterns = "/route/*")
public class RouteServlet extends BaseServlet {
    private RouteZDImpl a=new RouteZDImpl();
    //显示旅游信息为rid的数据
    public void findallServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String rid = request.getParameter("rid");
        int rid1=0;
        if(rid!=null&&rid.length()>0&&!rid.equals("null")){
            rid1 = Integer.parseInt(rid);
        }
        Route route=a.findall(rid1);
        ObjectMapper om=new ObjectMapper();
        String value = om.writeValueAsString(route);
        response.getWriter().write(value);
    }
    //查看用户是否收藏过
    public void lookServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String rid = request.getParameter("rid");
        int rid1=0;
        int uid=0;
        if(rid!=null&&rid.length()>0&&!rid.equals("null")){
            rid1 = Integer.parseInt(rid);
        }
        String rong = (String)request.getSession().getAttribute("rong");
        if(rong==null){
            //用户尚未登录，设置uid为0，到时候查数据也查不出来
            ResultInfo resultInfo=new ResultInfo();
            resultInfo.setFlag(false);
            ObjectMapper om=new ObjectMapper();
            String value = om.writeValueAsString(resultInfo);
            response.getWriter().write(value);
            return;
        }else {
            //用户登陆过，获取该用户的uid
            //通过用户名查询用户的id
            uid=a.findUid(rong);
        }
        //查找是否收藏过
        boolean b=a.findFavorite(rid1,uid);
        ResultInfo resultInfo=new ResultInfo();
        resultInfo.setFlag(b);
        ObjectMapper om=new ObjectMapper();
        String value = om.writeValueAsString(resultInfo);
        response.getWriter().write(value);
    }
    //查看收藏的次数
    public void numServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String rid = request.getParameter("rid");
        int rid1=0;
        if(rid!=null&&rid.length()>0&&!rid.equals("null")){
            rid1 = Integer.parseInt(rid);
        }
        int num=a.findNum(rid1);
        ObjectMapper om=new ObjectMapper();
        String value = om.writeValueAsString(num);
        response.getWriter().write(value);
    }
    //添加用户的收藏
    public void addServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String rid = request.getParameter("rid");
        int rid1=0;
        if(rid!=null&&rid.length()>0&&!rid.equals("null")){
            rid1 = Integer.parseInt(rid);
        }
        String rong =(String)request.getSession().getAttribute("rong");
        if(rong==null) {
            //用户尚未登录，设置uid为0，到时候查数据也查不出来
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("未登录");
            ObjectMapper om = new ObjectMapper();
            String value = om.writeValueAsString(resultInfo);
            response.getWriter().write(value);
            return;
        }else {
            int uid = a.findUid(rong);//查找用户的uid
            a.insertFavorite(rid1,uid);
            ObjectMapper om=new ObjectMapper();
            String value = om.writeValueAsString("true");
            response.getWriter().write(value);
        }
    }
}
