package com.xuyazhou.mynote.common.inject.models;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xuyazhou.mynote.BuildConfig;
import com.xuyazhou.mynote.model.api.ApiService;
import com.xuyazhou.mynote.model.api.ApiWrapper;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * api网络请求的一些注入模块
 * <p>
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * <p>
 * Date: 2016-03-21
 */
@Module
public class ApiServiceModule {

    private static final String DEBUGENDPOINT = "http://ss.xuyazhou.com:8080/mynote/";
    // private static final String DEBUGENDPOINT = "http://192.168.0.34:8002/mobile-front/";
    private static final String RLEASEENDPOINT = "http://ss.xuyazhou.com:8080/mynote/";

    /**
     * retrofit 的创建
     *
     * @return retrofit的实例
     */
    @Provides
    @Singleton
    protected ApiService provideApiService() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.DEBUG ? DEBUGENDPOINT : RLEASEENDPOINT)//根据编译类型选择测试或者正式服务器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 添加Rx适配器
                .addConverterFactory(GsonConverterFactory.create(gson))//添加GSON自动解析
                .client(okHttpClient()).build()
                .create(ApiService.class);
    }

    /**
     * okHttpClient 的创建
     *
     * @return
     */
    @Provides
    @Singleton
    protected OkHttpClient okHttpClient() {

        //自动在header上添加token,如果值为空,则header上不添加token

        Interceptor interceptor = chain -> chain.proceed(chain.request().newBuilder()
//                .addHeader("accessToken",
//                        config.isLogin() && userinfo.getAccessToken() != null ?
//                                userinfo.getAccessToken() : "")
                .method(chain.request().method(), chain.request().body()).build());

        return new OkHttpClient.Builder()
                .connectTimeout(60 * 60 * 1000, TimeUnit.MILLISECONDS)//添加链接超时60S
                .readTimeout(60 * 60 * 1000, TimeUnit.MILLISECONDS)//添加读取超时60S
                .addInterceptor(new HttpLoggingInterceptor().setLevel(
                        BuildConfig.DEBUG ? Level.BODY : Level.BODY))//网络请求的打印
                .addNetworkInterceptor(interceptor)//header上的一些截取处理
                .build();

    }

    //声明自己封装的一些网络api请求类
    @Provides
    protected ApiWrapper provideApiWrapper() {
        return new ApiWrapper(provideApiService());
    }
}
