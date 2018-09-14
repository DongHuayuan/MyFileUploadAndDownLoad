package tech.six6.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

@MultipartConfig
@WebServlet(name = "UpLoadServlet",urlPatterns = {"/upload"})
public class UpLoadServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println("action = "+action);
        if ("listFile".equals(action)){
            System.out.println("进入listFile");
            listFile(request, response);
        }else if ("uploadFile".equals(action)){
            System.out.println("进入uploadFile");
            uploadFile(request, response);
        }
    }


    private void listFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<String> files = new ArrayList<String>();
        String path = request.getSession().getServletContext().getRealPath("/uploadFile/");
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                System.out.println("文     件：" + tempList[i]);
                System.out.println("======================");
                String suffix = tempList[i].toString().substring(tempList[i].toString().lastIndexOf("\\")+1, tempList[i].toString().length());
                System.out.println("suffix="+suffix);
                files.add(suffix);
            }
            if (tempList[i].isDirectory()) {
                System.out.println("文件夹：" + tempList[i]);
            }
        }
        System.out.println("打印files：");
        files.forEach(x-> System.out.println(x));


        request.setAttribute("files",files);
        request.getRequestDispatcher("/index.jsp").forward(request,response);
    }




    public void uploadFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            // 如果用户上传了这里代码是不会出现异常 了
            // 如果没有上传这里出现异常
            Part part = request.getPart("file");

//            // 保存到项目的路径中去
            String sysPath= request.getSession().getServletContext().getRealPath("/uploadFile/");
            System.out.println("sysPath="+sysPath);
            File file = new File(sysPath);
            if (!file.exists()){
                file.mkdirs();
            }

            // 上传文件的内容性质
            String contentDispostion = part.getHeader("content-disposition");
            System.out.println("contentDispostion: "+contentDispostion);
            //  提取图片的类型
            String suffix = contentDispostion.substring(contentDispostion.lastIndexOf("."), contentDispostion.length() - 1);
            //定义一个新的图片名称
            String fileName = getRandomFile()+suffix;
            System.out.println("sysPath+fileName = "+sysPath+fileName);

            part.write(sysPath+fileName);

            request.setAttribute("tip","上传文件成功");
            request.setAttribute("fileName",fileName);

        }catch (Exception e){
            request.setAttribute("tip","上传文件失败！！！");
            e.printStackTrace();
        }
        request.getRequestDispatcher("/upload?action=listFile").forward(request,response);
    }


    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random random = new Random();
    /**
     * 生成随机文件名，当前年月日小时分钟秒+五为随机数
     * @return
     */
    private static String getRandomFile() {
        int num = random.nextInt(89999)+10000;
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr+num;
    }
}
