package com.xuyazhou.mynote.vp.home.member;


import com.xuyazhou.mynote.vp.base.IBaseView;

public interface RegistContract {
    interface View extends IBaseView {
        void initUI();


        void registerClick();

        void registerSuccuess();
    }

    interface Presenter {
        void regist(String email, String password);
    }
}
