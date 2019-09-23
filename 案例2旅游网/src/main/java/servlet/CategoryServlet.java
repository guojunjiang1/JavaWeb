package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Category;
import domain.PageBean;
import domain.Route;
import service.CategoryZDImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
//旅游分类以及内容展示
@WebServlet(urlPatterns = "/category/*")
public class CategoryServlet extends BaseServlet {
    private CategoryZDImpl a=new CategoryZDImpl();
    //查询分类信息
    public void findAllServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> list = a.findAll();
        ObjectMapper om=new ObjectMapper();
        String value = om.writeValueAsString(list);
        response.getWriter().write(value);
    }
    //分页查询每个cid中的数据
    public void findByPageServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        String currentPage = request.getParameter("currentPage");
        String rows = request.getParameter("rows");
        String name = request.getParameter("name");
        if(currentPage==null||currentPage.equals("")){
            currentPage="1";
        }
        if(rows==null||rows.equals("")){
            rows="5";
        }
        PageBean<Route> pageBean=a.findByPage(cid,currentPage,rows,name);
        ObjectMapper om=new ObjectMapper();
        String value = om.writeValueAsString(pageBean);
        response.getWriter().write(value);
    }
}
