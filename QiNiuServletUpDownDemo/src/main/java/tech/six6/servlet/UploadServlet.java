package tech.six6.servlet;

import com.qiniu.storage.model.FileInfo;
import tech.six6.myQiNiu.PropertiesUtil;
import tech.six6.myQiNiu.QiniuUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static tech.six6.util.FileUtil.getNewFileName;
import static tech.six6.util.FileUtil.getOldFileName;
import static tech.six6.util.PathUtil.getImgBasePath;
import static tech.six6.util.PathUtil.getShopImagePath;


@MultipartConfig
@WebServlet(name = "UploadServlet",urlPatterns = {"/upload"})
public class UploadServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");

        String action = request.getParameter("action");
        if (action.equals("uploadFile")){
            uploadFile(request,response);
        }else if (action.equals("listFile")){
            qiniuListFile(request,response);
        }

    }

    private void qiniuListFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入qiniuListFile。。。。。。。");
        ArrayList<String> files = new ArrayList<>();
        FileInfo[] fileInfos = QiniuUtil.list();
        for (FileInfo fileInfo : fileInfos) {
            System.out.println(fileInfo.key);
            files.add(fileInfo.key);
        }
        System.out.println("打印files：  ");
        files.forEach(x-> System.out.println(x));
        request.setAttribute("files",files);
        request.setAttribute("url",PropertiesUtil.getProperty("qiniu.URL"));
        System.out.println("url="+PropertiesUtil.getProperty("qiniu.URL"));
        request.getRequestDispatcher("/upload.jsp").forward(request,response);
    }

    public void uploadFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            // 如果用户上传了这里代码是不会出现异常 了
            // 如果没有上传这里出现异常
            Part part = request.getPart("file");

            // 保存到项目的路径中去
            String sysPath = getImgBasePath() + getShopImagePath(1);
            System.out.println("sysPath="+sysPath);
            File file = new File(sysPath);
            if (!file.exists()){
                file.mkdirs();
            }
            System.out.println("上传文件的   上传文件的内容性质");
            // 上传文件的内容性质
            String contentDispostion = part.getHeader("content-disposition");
            System.out.println("contentDispostion: "+contentDispostion);
            // 定义一个新的图片名称
            String fileName = getNewFileName(contentDispostion);
            System.out.println("文件名 fileName="+fileName);

            //获取上传的文件名称
            String oldFileName = getOldFileName(contentDispostion);
            System.out.println("获取上传的文件名称 oldFileName= "+oldFileName);

            // 把图片保存到路径中去
            System.out.println("sysPath+fileName="+sysPath+fileName);
            part.write(sysPath+fileName);

            boolean flag = false;
            String msg = "";
            File deleteFile = new File(sysPath+fileName);
            if (deleteFile.exists()){
                QiniuUtil.upload(sysPath+fileName,fileName);
                flag = deleteFile.delete();
                if (flag){
                    msg="删除本地文件成功";
                }else{
                    msg="删除本地文件失败";
                }
            }else{
                msg = "上传到本地异常。。。";
            }

            System.out.println("msg="+msg);
            request.setAttribute("tip","上传文件成功");
            request.setAttribute("fileName",fileName);

//            listFile(request,response);
//            showUpdate();
        }catch (Exception e){
            request.setAttribute("tip","上传文件失败！！！");
            e.printStackTrace();
        }
        request.getRequestDispatcher("/upload?action=listFile").forward(request,response);
    }
}
