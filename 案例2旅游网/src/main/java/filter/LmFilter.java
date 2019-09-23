package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//对所有的servlet进行post提交乱码和response乱码问题进行解决
@WebFilter(urlPatterns = "/*")
public class LmFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response=(HttpServletResponse) resp;
        String method = request.getMethod();
        if(method.equalsIgnoreCase("post")){
            request.setCharacterEncoding("utf-8");//对post乱码处理
        }
        response.setContentType("text/html;charset=utf-8");//对发出数据乱码处理
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
