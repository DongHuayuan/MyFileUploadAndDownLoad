package tech.six6.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "DoFilter",urlPatterns = "/main.jsp")
public class DoFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        String loginUser = (String)httpServletRequest.getSession().getAttribute("loginUser");
        System.out.println("doFilter.....");
        if (loginUser == null){
            httpServletResponse.sendRedirect("/index.jsp?flag=1");
            return;
        }else{
            filterChain.doFilter(request,response);
            return;
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
