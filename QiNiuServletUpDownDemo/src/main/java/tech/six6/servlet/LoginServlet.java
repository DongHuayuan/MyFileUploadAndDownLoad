package tech.six6.servlet;

import org.apache.commons.lang.StringUtils;
import tech.six6.entity.User;
import tech.six6.service.UserService;
import tech.six6.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet",urlPatterns = {"/login.do"})
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserService userService = new UserServiceImpl();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            response.sendRedirect("/index.jsp?flag=0");
        }else{
            User user = userService.login(username,password);
            if ( user != null ){
                HttpSession session = request.getSession();
                session.setAttribute("loginUser",user.getUsername());
                response.sendRedirect("/main.jsp");
            } else{
                response.sendRedirect("/index.jsp?flag=0");
            }
        }





    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
