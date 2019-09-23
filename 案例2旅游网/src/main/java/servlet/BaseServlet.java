package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

//抽取多个Servlet，统一管理
public class BaseServlet extends HttpServlet {
    @Override
    //当用户访问BaseServlet的子类时，都会执行BaseServlet中的service方法
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取访问的路径 eg:maven_LYW/user/方法名
        String requestURI = req.getRequestURI();
        String methodName=requestURI.substring(requestURI.lastIndexOf('/')+1);//获取到访问的方法名
        Class Clazz = this.getClass();//这里的this指的就是调用该service的具体对象 eg:UserServlet
        try {
            Method method = Clazz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);//获取到具体的方法
            method.invoke(this,req,resp);//调用该方法
        } catch (Exception e) {
        }
    }
}
