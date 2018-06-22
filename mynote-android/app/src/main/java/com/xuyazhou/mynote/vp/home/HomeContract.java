package com.xuyazhou.mynote.vp.home;


import com.xuyazhou.mynote.model.db.Note;
import com.xuyazhou.mynote.vp.base.IBaseView;
import com.xuyazhou.mynote.vp.base.IRefreshPresenter;

import java.util.ArrayList;

public interface HomeContract {
    interface View extends IBaseView {
        void initUI();

        void showData(ArrayList<Note> noteList);

        void closeDrawer();
    }

    interface Presenter extends IRefreshPresenter {
        void initPresmission();

        void getNoteList();

        void Unsubscribe();

        void addSub();
    }
}
