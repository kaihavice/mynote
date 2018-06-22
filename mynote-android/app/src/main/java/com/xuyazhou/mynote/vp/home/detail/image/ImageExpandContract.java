package com.xuyazhou.mynote.vp.home.detail.image;


import com.xuyazhou.mynote.model.db.AttachMent;
import com.xuyazhou.mynote.vp.base.IBaseView;

import java.util.ArrayList;

public interface ImageExpandContract {
    interface View extends IBaseView {
        void initUI();

        void showImage(ArrayList<AttachMent> imagelist);
    }

    interface Presenter {
        void getImagList();
    }
}
