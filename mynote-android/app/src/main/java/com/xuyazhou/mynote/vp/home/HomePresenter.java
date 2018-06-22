package com.xuyazhou.mynote.vp.home;


import android.Manifest;
import android.app.Activity;

import com.google.gson.Gson;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xuyazhou.mynote.R;
import com.xuyazhou.mynote.common.config.Constant;
import com.xuyazhou.mynote.common.utils.ActivityUtil;
import com.xuyazhou.mynote.common.utils.DataUtils;
import com.xuyazhou.mynote.common.utils.FileUtil;
import com.xuyazhou.mynote.model.bean.NoteListData;
import com.xuyazhou.mynote.model.bean.RequestNote;
import com.xuyazhou.mynote.model.bean.SyncNoteData;
import com.xuyazhou.mynote.model.db.Note;
import com.xuyazhou.mynote.model.db.User;
import com.xuyazhou.mynote.model.db.UserSetting;
import com.xuyazhou.mynote.model.event.UserAuthSuccessEvent;
import com.xuyazhou.mynote.vp.base.BasePresenter;
import com.xuyazhou.mynote.vp.home.member.LoginIndexActivity;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class HomePresenter extends BasePresenter<HomeContract.View>
        implements HomeContract.Presenter {


    @Inject
    Activity activity;
    private ArrayList<Note> noteList;
    private boolean isloadMore;
    private int page = 0;
    private UserSetting userSetting;
    @Inject
    DataUtils dataUtils;


    public UserSetting getUserSetting() {
        return dataManager.getUserSetting();
    }


    @Inject
    public HomePresenter() {
        noteList = new ArrayList<>();
    }


    @Override
    public void initPresmission() {

        new RxPermissions(activity)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.CAMERA)
                .subscribe(granted -> {
                    if (granted) {
                        FileUtil.CreateDir(Constant.localPath);
                        FileUtil.CreateDir(Constant.photoPath);
                    }
                });


    }

    @Override
    public void getNoteList() {


        view.showData(dataUtils.SetListData(context, isloadMore, dataManager.getNoteList(page)));


    }

    @Override
    public void addSub() {
        subscription.add(rxBus.subscribe(event -> {
                    User user = getUser();
                    user.setAccessToken(event.getToken());
                    user.update();
                    view.closeDrawer();
                    syncNoteData();

                },
                UserAuthSuccessEvent.class));
    }

    @Override
    public void refresh() {
        isloadMore = false;
        page = 0;
        getNoteList();
    }

    @Override
    public void loadMore() {
        isloadMore = true;
        page++;
        getNoteList();
    }

    public User getUser() {
        return dataManager.getUser();
    }

    public String initListType() {
        userSetting = dataManager.getUserSetting();
        return userSetting.getListType();
    }

    public void SetListType(String listType) {
        userSetting.setListType(listType);
        userSetting.update();
    }

    @Override
    public void Unsubscribe() {
        subscription.dispose();
    }

    public void syncNoteData() {
        if (dataManager.getUserSetting().isLogin()) {

            final RequestNote[] requestNote = {null};

            Flowable.create((FlowableOnSubscribe<String>) e -> {
                requestNote[0] = dataManager.getRuestNoteString();
                e.onNext(new Gson().toJson(requestNote[0]));
            }, BackpressureStrategy.BUFFER)
                    .subscribeOn(Schedulers.io())
                    .flatMap((Function<String, Publisher<?>>) data ->
                            apiWrapper.syncJson(context, RequestBody.create(MediaType
                                            .parse("application/json; charset=utf-8"), data),
                                    dataManager.getUser().getAccessToken(),
                                    dataManager.getUser().getLastSyncNoteTime() + ""))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnTerminate(() -> showDialogView(R.string.doing, false))
                    .doFinally(this::dismissDialog)
                    .subscribe(newSubscriber((Consumer<SyncNoteData>) syncJson -> {
                        User user = getUser();
                        user.setLastSyncNoteTime(syncJson.getCheckpoint());
                        user.update();

                        UpdateStatus(requestNote[0]);

                        if (syncJson.getSyncNoteList() != null && syncJson.getSyncNoteList().size() > 0) {
                            updateLocalData(syncJson.getSyncNoteList());
                            if (syncJson.getSyncNoteList().size() == 10) {
                                getNextPage();
                            }
                        }
                    }), throwableSubscriber());

        } else {
            ActivityUtil.moveToActivity(context, LoginIndexActivity.class);
        }

    }

    private void getNextPage() {


        int pageNum = 1;
        pageNum++;
        apiWrapper.getNoteListBypage(context, pageNum, dataManager.getUser().getAccessToken())

                .subscribe(newSubscriber((Consumer<NoteListData>) noteListData -> {
                            updateLocalData(noteListData.getNoteList());
                            if (noteListData.isIshaveNext()) {
                                getNextPage();
                            }
                        }
                ), throwableSubscriber());


    }

    private void UpdateStatus(RequestNote requestNote) {


        for (Note note : requestNote.getAdd()) {

            dataManager.fastSaveAttachment(note.getRequestAttachment().getAdd());
            dataManager.fastSaveCheckList(note.getRequestCheck().getAdd());
        }


        for (Note note : requestNote.getUpdate()) {
            updateAttachStatus(note);
        }


        for (Note note : requestNote.getDelete()) {
            updateAttachStatus(note);
        }

        dataManager.fastSaveNote(requestNote.getAdd());
        dataManager.fastSaveNote(requestNote.getUpdate());
        dataManager.fastSaveNote(requestNote.getDelete());
    }

    private void updateAttachStatus(Note note) {

        dataManager.fastSaveAttachment(note.getRequestAttachment().getAdd());
        dataManager.fastSaveAttachment(note.getRequestAttachment().getDelete());


        dataManager.fastSaveCheckList(note.getRequestCheck().getAdd());
        dataManager.fastSaveCheckList(note.getRequestCheck().getUpdate());
        dataManager.fastSaveCheckList(note.getRequestCheck().getDelete());


    }

    private void updateLocalData(List<Note> syncNoteList) {

        dataManager.fastSaveNote(syncNoteList);

        for (Note update : syncNoteList) {

            dataManager.fastSaveAttachment(update.getAttachmentList());
            dataManager.fastSaveCheckList(update.getCheckListItemList());
        }


        refresh();
    }
}