package com.xuyazhou.mynote.model.Download;



import java.io.File;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Url;

/**
 *
 * 下载声明注解接口
 *
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * <p>
 * Date: 2016-07-02
 */

public interface DownloadFileService {

    @GET
    Call<File> download(@Url String fileUrl, @Header(FileConverter.SAVE_PATH) String path);
}
