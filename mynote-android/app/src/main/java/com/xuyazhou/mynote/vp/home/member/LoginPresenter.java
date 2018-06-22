package com.xuyazhou.mynote.vp.home.member;

import android.annotation.SuppressLint;

import com.xuyazhou.mynote.R;
import com.xuyazhou.mynote.common.config.Constant;
import com.xuyazhou.mynote.common.utils.MD5Utils;
import com.xuyazhou.mynote.common.utils.ShowToast;
import com.xuyazhou.mynote.model.bean.LoginBean;
import com.xuyazhou.mynote.model.bean.Response;
import com.xuyazhou.mynote.model.bean.UserBeanBack;
import com.xuyazhou.mynote.model.db.User;
import com.xuyazhou.mynote.model.db.UserSetting;
import com.xuyazhou.mynote.model.event.UserAuthSuccessEvent;
import com.xuyazhou.mynote.vp.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginContract.View>
        implements LoginContract.Presenter {

    @Inject
    public LoginPresenter() {

    }

    @SuppressLint("CheckResult")
    @Override
    public void LoginDo(String mail, String password) {


        UserSetting config = dataManager.getUserSetting();

        apiWrapper.getNonce().
                flatMap((Function<Response<LoginBean>, Flowable<?>>) token -> {
                    params.put("email", mail);
                    params.put("deviceType", "1");
                    params.put("password",
                            MD5Utils.MD5(mail + ":" + MD5Utils.MD5(password) +
                                    ":" + token.data.getNonce()));
                    return apiWrapper.login(context, params,
                            token.data.getRandomToken());
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(() -> showDialogView(R.string.doing, false))
                .doFinally(this::dismissDialog)
                .subscribe(newSubscriber((UserBeanBack user) -> {

                    User userinfo = user.getUserBean();


                    userinfo.setActivity(Constant.LIVE);
                    userinfo.save();


                    dataManager.getVistorNoteList();


                    config.setLogin(true);
                    config.update();

                    if (rxBus.hasSubscribers()) {
                        rxBus.send(new UserAuthSuccessEvent(userinfo.getAccessToken()));
                    }

                    view.loginSuccuess();
                    ShowToast.Short(context, "登陆成功");

                }), throwableSubscriber());
    }


}