package tech.six6.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

@WebServlet(name = "DownLoadServlet",urlPatterns = {"/download"})
public class DownLoadServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入DownLoadServlet==============");
        String fileName = "new.jpg";    //点击下载时显示下载到本地的名称
        response.setHeader("content-disposition","attachment;filename="+URLEncoder.encode(fileName,"UTF-8"));
        OutputStream os = response.getOutputStream();
        ServletContext context = this.getServletContext();
        String realPath = context.getRealPath("/uploadFile/");
        System.out.println("realPath:"+realPath);

        String downFileName = request.getParameter("downFileName"); //需要下载的文件名
        System.out.println("downFileName="+downFileName);

        FileInputStream fis = new FileInputStream(realPath+downFileName);
        byte[] b = new byte[1024];
        int i;
        while( (i=fis.read(b,0,b.length))!=-1 ){
            os.write(b,0,b.length);
            b=new byte[1024];
        }

        fis.close();
        os.flush();
        os.close();
    }
}
