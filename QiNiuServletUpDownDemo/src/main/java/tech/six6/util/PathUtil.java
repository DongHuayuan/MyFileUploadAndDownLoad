package tech.six6.util;

public class PathUtil {


    private static String seperator = System.getProperty("file.separator");
    public static String getImgBasePath(){
        String os = System.getProperty("os.name");
        String basePath = "";
        if(os.toLowerCase().startsWith("win")){
            basePath = "D:/projectdev/image/";
        }else {
            basePath = "/home/root/image/";
        }
        basePath = basePath.replace("/",seperator);
        return basePath;
    }

    public static String getShopImagePath(int shopId){
        String imagePath = "upload/item/"+shopId+"/";
        return imagePath.replace("/",seperator);
    }


}
