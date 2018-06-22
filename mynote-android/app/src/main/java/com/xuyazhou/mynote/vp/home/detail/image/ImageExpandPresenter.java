package com.xuyazhou.mynote.vp.home.detail.image;


import android.app.Activity;

import com.xuyazhou.mynote.common.config.SyncStatus;
import com.xuyazhou.mynote.model.db.AttachMent;
import com.xuyazhou.mynote.vp.base.BasePresenter;

import java.util.ArrayList;

import javax.inject.Inject;

public class ImageExpandPresenter extends BasePresenter<ImageExpandContract.View>
        implements ImageExpandContract.Presenter {

    private ArrayList<AttachMent> imagelist;
    private String noteId;

    @Inject
    Activity activity;

    @Inject
    public ImageExpandPresenter() {

    }

    public void getImagList() {

        noteId = activity.getIntent().getStringExtra("noteId");
        imagelist = dataManager.selectPhoto(noteId);
        view.showImage(imagelist);
    }

    public void deleteAttach(int currentPosition) {
        AttachMent photo = imagelist.get(currentPosition);
        photo.setDeleted(true);
        photo.setStatus(SyncStatus.Delete);
        photo.update();


        imagelist.remove(currentPosition);
        view.showImage(imagelist);
    }
}