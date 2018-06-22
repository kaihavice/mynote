package com.xuyazhou.mynote.vp.home.detail;


import com.xuyazhou.mynote.model.bean.NoteDeatils;
import com.xuyazhou.mynote.vp.base.IBaseView;

public interface NoteDetailContract {
    interface View extends IBaseView {
        void initUI();

        void ShowData(NoteDeatils deatils);
        void insertCheckData(NoteDeatils deatils);
        void insertPhotoData(NoteDeatils deatils);
        void insertAttachData(NoteDeatils deatils);
        void delteItemTolist(NoteDeatils deatils, int position);
        void showNoteDialog(NoteDeatils deatils);

    }

    interface Presenter {
        void initNote();

        void setPhotoData(String path,String fileName) throws Exception;

    }
}
