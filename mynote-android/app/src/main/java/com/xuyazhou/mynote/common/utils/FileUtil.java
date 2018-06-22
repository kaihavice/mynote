package com.xuyazhou.mynote.common.utils;

import java.io.File;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * <p/>
 * Date: 2015-08-10
 */
public class FileUtil {

    //创建文件夹
    public static void CreateDir(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                System.out.println("mkdirs success.");
            }
        }
    }

    public static String getFileName(String path) {
        return path.substring(path.lastIndexOf("/") + 1, path.length());
    }
}
