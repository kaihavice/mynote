package com.xuyazhou.mynote.model.api;

import com.xuyazhou.mynote.model.bean.LoginBean;
import com.xuyazhou.mynote.model.bean.NoteListData;
import com.xuyazhou.mynote.model.bean.QiniuRepose;
import com.xuyazhou.mynote.model.bean.Response;
import com.xuyazhou.mynote.model.bean.SyncNoteData;
import com.xuyazhou.mynote.model.bean.UserBeanBack;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * 所有app api的注入声明
 * *
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2016-08-31
 */
public interface ApiService {

    @POST("mcenter/link")
    Flowable<Response<LoginBean>> getNonce();

    @FormUrlEncoded
    @POST("mcenter/regist")
    Flowable<Response<UserBeanBack>> regist(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("mcenter/login")
    Flowable<Response<UserBeanBack>> login(@Header("randomToken") String token, @FieldMap Map<String, Object> map);

    @POST("mcenter/logout")
    Flowable<Response<String>> logout(@Header("accessToken") String token);

    @FormUrlEncoded
    @POST("qiniu/upload")
    Flowable<Response<QiniuRepose>> uploadFile(@Header("accessToken") String token, @FieldMap Map<String, Object> map);

    @POST("sync/syncnote")
    Flowable<Response<SyncNoteData>> syncnote(@Header("accessToken") String token, @Body RequestBody body,
                                              @Header("checkPiont") String checkPoint);


    @FormUrlEncoded
    @POST("sync/note/usernotelist")
    Flowable<Response<NoteListData>> getNoteListBypage(@Header("accessToken") String token, @FieldMap Map<String, Object> map);
}
