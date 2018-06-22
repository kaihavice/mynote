package com.xuyazhou.mynote.vp.home.member;


import com.xuyazhou.mynote.vp.base.IBaseView;

public interface LoginContract {
    interface View extends IBaseView {
        void initUI();

        void forgetPasswordClick();


        void loginClick();

        void loginSuccuess();
    }

    interface Presenter {
        void LoginDo(String mail, String password);
    }
}
