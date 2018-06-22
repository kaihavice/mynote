package com.xuyazhou.mynote.model.Download;


import com.xuyazhou.mynote.model.Download.body.HttpClientHelper;
import com.xuyazhou.mynote.model.Download.body.ProgressRequestListener;
import com.xuyazhou.mynote.model.Download.body.ProgressResponseListener;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * 根据编译环境的不同切换url的前缀地址
 *
 * Author: lampard_xu(xuyazhou18@gmail.com)
 *
 * Date: 2016-07-02
 */

public class ServiceGenerator {


    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("http://opnz8d5s2.bkt.clouddn.com/")
            .addConverterFactory(FileConverterFactory.create());


    public static <T> T createService(Class<T> tClass) {
        return builder.build().create(tClass);
    }


    /**
     * 创建带响应进度(下载进度)回调的service
     */
    public static <T> T createResponseService(Class<T> tClass, ProgressResponseListener listener) {
        OkHttpClient client = HttpClientHelper.addProgressResponseListener(new OkHttpClient.Builder(), listener).build();
        return builder
                .client(client)
                .build()
                .create(tClass);
    }


    /**
     * 创建带请求体进度(上传进度)回调的service
     */
    public static <T> T createReqeustService(Class<T> tClass, ProgressRequestListener listener) {
        OkHttpClient client = HttpClientHelper.addProgressRequestListener(new OkHttpClient.Builder(), listener).build();
        return builder
                .client(client)
                .build()
                .create(tClass);
    }
}
