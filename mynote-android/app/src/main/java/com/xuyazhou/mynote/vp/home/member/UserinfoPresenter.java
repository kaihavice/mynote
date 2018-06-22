package com.xuyazhou.mynote.vp.home.member;

import com.xuyazhou.mynote.R;
import com.xuyazhou.mynote.common.config.Constant;
import com.xuyazhou.mynote.common.utils.ShowToast;
import com.xuyazhou.mynote.model.db.User;
import com.xuyazhou.mynote.model.db.UserSetting;
import com.xuyazhou.mynote.vp.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class UserinfoPresenter extends BasePresenter<UserinfoContract.View>
        implements UserinfoContract.Presenter {


    public User getUser() {
        return dataManager.getUser();
    }

    @Inject
    public UserinfoPresenter() {

    }


    public void logout() {
        UserSetting userSetting = dataManager.getUserSetting();
        userSetting.setLogin(false);
        userSetting.update();

        User user = dataManager.getUser();
        user.setActivity(Constant.DISABLE);
        user.setLastSyncNoteTime(0);
        user.update();

        apiWrapper.logout(user.getAccessToken(), context).doOnTerminate(() ->
                showDialogView(R.string.doing, true))
                .doFinally(this::dismissDialog).subscribe(
                newSubscriber((Consumer<String>) s ->
                        ShowToast.Short(context, "退出登录成功"))
                , throwableSubscriber());
    }
}