package tech.six6.servlet;

import tech.six6.myQiNiu.QiniuUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteFileServlet",urlPatterns = {"/delete"})
public class DeleteFileServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入了删除操作，。。");
        String fileName = request.getParameter("fileName");
        System.out.println("======fileName="+fileName);
        if (QiniuUtil.delete(fileName)){
            System.out.println("删除成功！");
        }else{
            System.out.println("删除失败");
        }
        response.sendRedirect("/upload?action=uploadFile");
    }
}
