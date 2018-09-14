package tech.six6.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class FileUtil {

    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random random = new Random();

    public static String getNewFileName(String UpFileName) {
        String fileName = getRandomFile()+getFileExtension(UpFileName);
        System.out.println("上传到七牛云的文件名："+fileName);
        File dirPath = new File(fileName);
        if (!dirPath.exists()){
            dirPath.mkdirs();
        }
        return fileName;
    }

    /**
     * 获取输入文件流的扩展名
     * @return
     */
    private static String getFileExtension(String uploadName) {
        return uploadName.substring(uploadName.lastIndexOf("."), uploadName.length() - 1);
    }

    /**
     * 获取文件原名称
     */
    public static String getOldFileName(String uploadName){
        return uploadName.substring(uploadName.lastIndexOf("\""), uploadName.length() - 1);
    }

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
