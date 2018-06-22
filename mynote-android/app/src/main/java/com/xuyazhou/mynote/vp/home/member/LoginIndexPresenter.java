package com.xuyazhou.mynote.vp.home.member;

import android.app.Activity;

import com.xuyazhou.mynote.model.event.UserAuthSuccessEvent;
import com.xuyazhou.mynote.vp.base.BasePresenter;

import javax.inject.Inject;

public class LoginIndexPresenter extends BasePresenter<LoginIndexContract.View>
        implements LoginIndexContract.Presenter {


    @Inject
    Activity activity;

    @Inject
    public LoginIndexPresenter() {

    }

    @Override
    public void addSub() {
        subscription.add(rxBus.subscribe(event -> activity.finish(),
                UserAuthSuccessEvent.class));
    }

    @Override
    public void Unsubscribe() {
        subscription.dispose();
    }
}