package tech.six6.servlet;

import tech.six6.myQiNiu.QiniuUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;

@WebServlet(name = "DownloadServlet",urlPatterns = "/download")
public class DownloadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        localDownload(request,response);
        qiniuDownload(request, response);

    }

    private void qiniuDownload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filename = request.getParameter("filename");
        System.out.println("filename：" + filename);
        String downloadFileName =  new QiniuUtil().download(filename);
        System.out.println("downloadFileName:  " + downloadFileName);

        BufferedInputStream dis = null;
        BufferedOutputStream fos = null;
        try {
        URL url = new URL(downloadFileName);
        response.setContentType("application/x-msdownload;");
        response.setHeader("Content-disposition", "attachment; filename=" + new String(filename.getBytes("utf-8"), "UTF-8"));
        response.setHeader("Content-Length", String.valueOf(url.openConnection().getContentLength()));

        dis = new BufferedInputStream(url.openStream());
        fos = new BufferedOutputStream(response.getOutputStream());

        byte[] buff = new byte[2048];
        int bytesRead;
        while (-1 != (bytesRead = dis.read(buff, 0, buff.length))) {
            fos.write(buff, 0, bytesRead);
        }
    } catch(Exception e){
        e.printStackTrace();
    } finally {
        if (dis != null)
            dis.close();
        if (fos != null)
            fos.close();
    }

}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }


    private void localDownload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filename = request.getParameter("filename");
        System.out.println("filename："+filename);
        String sysPath= request.getSession().getServletContext().getRealPath("/uploadFile");
        System.out.println("sysPath： "+sysPath);
//        //构建输入流
        InputStream in = new FileInputStream(sysPath+"/"+filename);
        //下载
        String oldfilename = filename.substring(filename.indexOf("_")+1);
        System.out.println("oldfilename: "+oldfilename);
        //通知客户端以下载的方式打开
        response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(filename, "UTF-8"));

        OutputStream out = response.getOutputStream();

        int len = -1;
        byte b[] = new byte[1024];
        while((len=in.read(b))!=-1){
            out.write(b,0,len);
        }
        in.close();
        out.close();
    }
}
