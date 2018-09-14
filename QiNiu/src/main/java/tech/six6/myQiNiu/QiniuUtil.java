package tech.six6.myQiNiu;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.model.FileListing;
import com.qiniu.util.Auth;

import java.io.IOException;

public class QiniuUtil {

    //设置好账号的ACCESS_KEY和SECRET_KEY
    private static String ACCESS_KEY = PropertiesUtil.getProperty("qiniu.ACCESS_KEY");
    private static String SECRET_KEY = PropertiesUtil.getProperty("qiniu.SECRET_KEY");
    //要上传的空间
    private static String bucketname = PropertiesUtil.getProperty("qiniu.bucketname");

    private static String URL = PropertiesUtil.getProperty("qiniu.URL");
    //密钥配置
    private static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    //第二种方式: 自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。
    private static Zone z = Zone.autoZone();
    private static Configuration c = new Configuration(z);

    private static UploadManager initQiniu() {
        //创建上传对象
        UploadManager uploadManager = new UploadManager(c);
        return uploadManager;
    }

    private static BucketManager getbucketManager(){
        //实例化一个BucketManager对象
        BucketManager bucketManager = new BucketManager(auth, c);
        return bucketManager;
    }



    /**
     * 一、简单方式上传到七牛云
     * 简单上传，使用默认策略，只需要设置上传的空间名就可以了
     * @param FilePath 上传文件的路径
     * @param key  上传到七牛后保存的文件名
     * @throws IOException
     */
    public static void upload(String FilePath, String key) throws IOException {
        String uploadToken = auth.uploadToken(bucketname);
        try {
            //调用put方法上传
            Response res = initQiniu().put(FilePath, key, uploadToken);
            //打印返回的信息
            System.out.println("打印返回的信息"+res.bodyString());
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println("请求失败时打印的异常的信息:"+r.toString());
            try {
                //响应的文本信息
                System.out.println("响应的文本信息:"+r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
        }
    }


    /**
     * 从七牛云下载文件
     * @param key  需要下载的名称
     */
    public static String download(String key) {
        //构造私有空间的需要生成的下载的链接
        URL += key;
        //调用privateDownloadUrl方法生成下载链接,第二个参数可以设置Token的过期时间
        String downloadRUL = auth.privateDownloadUrl(URL, 3600);
        System.out.println(downloadRUL);
        return downloadRUL;
    }


    public static Boolean delete(String key){
        try {
            //调用delete方法移动文件
            getbucketManager().delete(bucketname, key);
            return true;
        } catch (QiniuException e) {
            //捕获异常信息
            Response r = e.response;
            System.out.println(r.toString());
        }
        return false;
    }


    /**
     * 显示七牛云上的文件
     *
     * @return
     */
    public static FileInfo[] list(){
        FileInfo[] items;
        try {
            //调用listFiles方法列举指定空间的指定文件
            //参数一：bucket    空间名
            //参数二：prefix    文件名前缀
            //参数三：marker    上一次获取文件列表时返回的 marker
            //参数四：limit     每次迭代的长度限制，最大1000，推荐值 100
            //参数五：delimiter 指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
            FileListing fileListing = getbucketManager().listFiles(bucketname, null, null, 100, null);
           items = fileListing.items;
            for (FileInfo fileInfo : items) {
                System.out.println("fileInfo.hash: "+fileInfo.hash);
                System.out.println("fileInfo.key:  "+fileInfo.key);
                System.out.println("fileInfo.type:  "+fileInfo.type);
            }
        } catch (QiniuException e) {
            //捕获异常信息
            Response r = e.response;
            System.out.println(r.toString());
            return null;
        }
        return items;
    }


    public static void main(String[] args) {
        QiniuUtil.list();
    }

}
