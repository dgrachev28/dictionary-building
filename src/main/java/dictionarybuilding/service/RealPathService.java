package dictionarybuilding.service;



public class RealPathService {
    public static String resourcesFolder;
    public static String mystemPath;

    public static void setPaths(String realPath) {
        resourcesFolder = realPath + "/WEB-INF/classes/file/";
        mystemPath = realPath + "/WEB-INF/classes/lib/mystem/mystem.exe";
    }

}
