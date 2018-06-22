package com.xuyazhou.mynote.vp.home.member;

import com.xuyazhou.mynote.R;
import com.xuyazhou.mynote.common.config.Constant;
import com.xuyazhou.mynote.common.utils.ShowToast;
import com.xuyazhou.mynote.model.bean.UserBeanBack;
import com.xuyazhou.mynote.model.db.User;
import com.xuyazhou.mynote.model.db.UserSetting;
import com.xuyazhou.mynote.model.event.UserAuthSuccessEvent;
import com.xuyazhou.mynote.vp.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class RegistPresenter extends BasePresenter<RegistContract.View>
        implements RegistContract.Presenter {

    @Inject
    public RegistPresenter() {

    }

    @Override
    public void regist(String email, String password) {

        params.put("email", email);
        params.put("passWord", password);


        apiWrapper.register(context, params)
                .doOnTerminate(() -> showDialogView(R.string.doing, false))
                .doFinally(this::dismissDialog)
                .subscribe(newSubscriber((Consumer<UserBeanBack>) userBeanBack -> {

                    User user = userBeanBack.getUserBean();


                    user.setActivity(Constant.LIVE);
                    user.save();

                    UserSetting userSetting = dataManager.getUserSetting();
                    userSetting.setLogin(true);
                    userSetting.setModifiedTime(System.currentTimeMillis() / 1000);
                    userSetting.update();
                    if (rxBus.hasSubscribers()) {
                        rxBus.send(new UserAuthSuccessEvent(user.getAccessToken()));
                    }

                    ShowToast.Short(context, "注册成功");
                    view.registerSuccuess();
                }), throwableSubscriber());

    }
}