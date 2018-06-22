package com.xuyazhou.mynote.vp.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Window;

import com.xuyazhou.mynote.common.config.Constant;
import com.xuyazhou.mynote.common.utils.ShowToast;
import com.xuyazhou.mynote.model.api.ApiWrapper;
import com.xuyazhou.mynote.model.api.RetrofitUtil;
import com.xuyazhou.mynote.model.data.DataManager;
import com.xuyazhou.mynote.model.db.User;
import com.xuyazhou.mynote.model.db.UserSetting;
import com.xuyazhou.mynote.model.event.RxBus;
import com.xuyazhou.mynote.vp.home.HomeActivity;
import com.xuyazhou.mynote.vp.home.member.LoginActivity;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;


/**
 * 控制器的基类
 * <p>
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * <p>
 * Date: 2016-03-09
 */
public class BasePresenter<V extends IBaseView> {

    protected V view;
    @Inject
    protected ApiWrapper apiWrapper;//网络请求封装类
    @Inject
    protected Context context;
    protected ProgressDialog spinner;
    @Inject
    protected RxBus rxBus;//事件总线
    @Inject
    protected CompositeDisposable subscription;

    @Inject
    protected DataManager dataManager;

    protected Map<String, Object> params;//网络请求的params


    public BasePresenter() {

        this.params = new HashMap<>();


    }


    public void setView(V view) {
        this.view = view;
    }

    public void onDestroy() {
        this.view = null;
    }


    /**
     * 创建网络回调数据的观察者
     *
     * @param onNext
     * @param <T>
     * @return
     */
    protected <T> Consumer newSubscriber(final Consumer<? super T> onNext) {
        return (Consumer<T>) onNext::accept;
    }


    protected Consumer throwableSubscriber() {
        return (Consumer<Throwable>) t -> {
            if (t instanceof RetrofitUtil.TokenException) {
                ShowToast.Long(context, "token过期请重新登录!");
                TokenExpired();
            } else {
                ShowToast.Long(context, t.getMessage());
            }
        };
    }


    /**
     * 用户界面交互提示对话框
     *
     * @param msg
     * @param cancelable
     */
    public void showDialogView(int msg, boolean cancelable) {

        spinner = new ProgressDialog(context);

        spinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
        spinner.setMessage(context.getString(msg));
        spinner.show();
        spinner.setCancelable(cancelable);
    }

    /**
     * 取消当前用户界面交互提示对话框
     */
    public void dismissDialog() {
        if (spinner != null) {
            spinner.dismiss();
        }
    }


    //  Token过期的处理内容
    private void TokenExpired() {
        UserSetting userSetting = dataManager.getUserSetting();
        userSetting.setLogin(false);
        userSetting.update();

        User user = dataManager.getUser();
        user.setActivity(Constant.DISABLE);
        user.setLastSyncNoteTime(0);
        user.update();

        context.startActivity(new Intent(context, LoginActivity.class));
        if (!(context instanceof HomeActivity)) {
            ((Activity) context).finish();
        }


    }
}
