package com.xuyazhou.mynote.common.config;

import android.os.Environment;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017/1/3
 */
public class Constant {

    public static final String localPath = Environment
            .getExternalStorageDirectory().getPath()
            + "/Android/data/com.xuyazhou.mynote/";

    public static final String baseCDNUrl = "http://opnz8d5s2.bkt.clouddn.com/";

    public static final String photoPath = Environment
            .getExternalStorageDirectory().getPath()
            + "/Android/data/com.xuyazhou.mynote/photo";
    public static final String DownLoadPath = Environment
            .getExternalStorageDirectory().getPath()
            + "/Android/data/com.xuyazhou.mynote/download/";

    public static final long LIVE = 1;
    public static final long DISABLE = 2;
    public static final String TEXT = "TEXT";
    public static final String CHECKLIST = "CHECKLIST";
    public static final String CHECKLISTIMAGE = "CHECKLISTIMAGE";
    public static final String PHOTO = "PHOTO";
    public static final String ATTACH = "ATTACH";
    public static final String ATTACHIMAGE = "ATTACHIMAGE";
    public static final String LIST = "list";
    public static final String LISTONLYTITLE = "listonlytitle";
    public static final String GRID = "grid";
    public static final int FILEBROWERBACK = 0x14;

}
