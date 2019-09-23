package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.ResultInfo;
import domain.User;
import org.apache.commons.beanutils.BeanUtils;
import service.UserZDImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
//用户操作相关的Servlet

//用户访问user/*的时候只会执行BaseServlet中的方法，在BaseServlet中通过反射来访问具体*的方法
//访问方法：user/方法名，访问具体的方法
@WebServlet(urlPatterns = "/user/*")
public class UserServlet extends BaseServlet {

    //注册Servlet
    public void zCServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //检验验证码
            String check = request.getParameter("check");
            HttpSession session = request.getSession();
            String yzm = (String) session.getAttribute("yzm");
            session.removeAttribute("guo");
            //如果验证码输入正确
            if(yzm!=null&&yzm.equalsIgnoreCase(check)){
                Map<String, String[]> parameterMap = request.getParameterMap();
                User user=new User();
                BeanUtils.populate(user,parameterMap);
                UserZDImpl a=new UserZDImpl();
                boolean b=a.upZc(user);
                ResultInfo c=new ResultInfo();
                if(b){
                    c.setFlag(true);
                }else {
                    c.setFlag(false);
                    c.setErrorMsg("用户名已存在");
                }
                ObjectMapper om=new ObjectMapper();
                String json=om.writeValueAsString(c);
                response.getWriter().write(json);
            }else {
                ResultInfo c=new ResultInfo();
                c.setFlag(false);
                c.setErrorMsg("验证码输入错误");
                ObjectMapper om=new ObjectMapper();
                String json=om.writeValueAsString(c);
                response.getWriter().write(json);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    //激活账户的Servlet
    public void jiHuoServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        UserZDImpl a=new UserZDImpl();
        boolean b=a.findCode(code);
        if(b){
            response.getWriter().write("恭喜你!激活成功可以登录了");
        }else {
            response.getWriter().write("激活失败请联系郭峻江");
        }
    }

    //登录的Servlet
    public void loginServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");
        ResultInfo a=new ResultInfo();
        ObjectMapper b=new ObjectMapper();
        String yzm = (String) request.getSession().getAttribute("yzm");
        request.getSession().removeAttribute("yzm");
        if(check.equalsIgnoreCase(yzm)&&yzm!=null){
            UserZDImpl c=new UserZDImpl();
            Map<String, String[]> parameterMap = request.getParameterMap();
            User user=new User();
            try {
                BeanUtils.populate(user,parameterMap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //调用service层，如果返回1则说明登录成功并且激活成功，如果返回2说明未激活，如果返回3说明账号密码不正确
            int d=c.UserLogin(user);
            if(d==1){
                request.getSession().setAttribute("rong",user.getUsername());//登录成功后将用户名存到session中去
                a.setFlag(true);
            }else if(d==2){
                a.setFlag(false);
                a.setErrorMsg("该账户尚未激活请去邮箱激活");
            }else if(d==3){
                a.setFlag(false);
                a.setErrorMsg("账号或密码输入错误");
            }
        }else{
            a.setFlag(false);
            a.setErrorMsg("验证码输入错误");
        }
        String value = b.writeValueAsString(a);
        response.getWriter().write(value);
    }

    //在页面上用户登录后显示用户名的Servlet
    public void findUserNameServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String  username = (String)request.getSession().getAttribute("rong");
        if(username!=null){
            Map<String,String> map=new HashMap<>();
            ObjectMapper om=new ObjectMapper();
            map.put("username",username);
            om.writeValue(response.getWriter(),map);
        }
    }

    //退出登录的Servlet
    public void exitServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //将登录信息删除掉
        request.getSession().removeAttribute("rong");
    }
}
