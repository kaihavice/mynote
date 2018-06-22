package com.xuyazhou.mynote.model.api;


import android.content.Context;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.xuyazhou.mynote.model.bean.LoginBean;
import com.xuyazhou.mynote.model.bean.NoteListData;
import com.xuyazhou.mynote.model.bean.QiniuRepose;
import com.xuyazhou.mynote.model.bean.Response;
import com.xuyazhou.mynote.model.bean.SyncNoteData;
import com.xuyazhou.mynote.model.bean.UserBeanBack;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.RequestBody;

/**
 * 网络访问封装类
 * *
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2016-8-31
 */
public class ApiWrapper extends RetrofitUtil {


    private ApiService apiService;


    public ApiWrapper(ApiService apiService) {
        this.apiService = apiService;
    }


    public Flowable<Response<UserBeanBack>> register(Context context, Map<String, Object> map) {
        return apiService.regist(map)
                .compose(applySchedulersLifecycle((RxAppCompatActivity) context));
    }

    public Flowable<Response<UserBeanBack>> login(Context context, Map<String, Object> map, String token) {

        return apiService.login(token, map)
                .compose(applySchedulersLifecycle((RxAppCompatActivity) context));
    }


    public Flowable<Response<LoginBean>> getNonce() {

        return apiService.getNonce();
    }

    public Flowable<Response<String>> logout(String token, Context context) {

        return apiService.logout(token).compose(applySchedulersLifecycle((RxAppCompatActivity) context));
    }

    public Flowable<Response<QiniuRepose>> uploadFile(Context context, Map<String, Object> map, String token) {

        return apiService.uploadFile(token, map)
                .compose(applySchedulersLifecycle((RxAppCompatActivity) context));
    }

    public Flowable<Response<NoteListData>> getNoteListBypage(Context context, long pageNum, String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("pageSize", 10);
        map.put("pageNum", pageNum);

        return apiService.getNoteListBypage(token, map)
                .compose(applySchedulersLifecycle((RxAppCompatActivity) context));
    }

    public Flowable<Response<SyncNoteData>> syncJson(Context context, RequestBody body, String token, String checkPoint) {

        return apiService.syncnote(token, body, checkPoint)
                .compose(applySchedulersLifecycle((RxAppCompatActivity) context));
    }
}
