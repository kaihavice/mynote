package com.xuyazhou.mynote.vp.home.member;


import com.xuyazhou.mynote.vp.base.IBaseView;

public interface LoginIndexContract {
    interface View extends IBaseView {
        void initUI();

    }

    interface Presenter {
        void addSub();

        void Unsubscribe();
    }
}
