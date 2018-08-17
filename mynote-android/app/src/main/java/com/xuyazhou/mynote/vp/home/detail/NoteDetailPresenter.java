package com.xuyazhou.mynote.vp.home.detail;


import android.app.Activity;

import com.xuyazhou.mynote.common.config.Constant;
import com.xuyazhou.mynote.common.config.SyncStatus;
import com.xuyazhou.mynote.common.utils.FileUtil;
import com.xuyazhou.mynote.common.utils.FileUtils;
import com.xuyazhou.mynote.common.utils.ListUtils;
import com.xuyazhou.mynote.common.utils.UUIDUtils;
import com.xuyazhou.mynote.model.bean.AttachmentPlus;
import com.xuyazhou.mynote.model.bean.NoteDeatils;
import com.xuyazhou.mynote.model.bean.QiniuRepose;
import com.xuyazhou.mynote.model.db.AttachMent;
import com.xuyazhou.mynote.model.db.CheckListItem;
import com.xuyazhou.mynote.model.db.Note;
import com.xuyazhou.mynote.model.db.UserSetting;
import com.xuyazhou.mynote.vp.base.BaseActivity;
import com.xuyazhou.mynote.vp.base.BasePresenter;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class NoteDetailPresenter extends BasePresenter<NoteDetailContract.View>
        implements NoteDetailContract.Presenter {

    private String noteId;
    private NoteDeatils deatils;
    private Note note;
    private CheckListItem checkListItem;
    @Inject
    Activity activity;


    public void setCurrentNoteContent(String currentNoteContent) {
        this.currentNoteContent = currentNoteContent;
    }

    private String currentNoteContent = "";

    public String getCurrentNoteContent() {
        return currentNoteContent;
    }

    @Inject
    public NoteDetailPresenter() {
        deatils = new NoteDeatils();
    }

    public void saveNote(String content) {


        deatils.setNote(dataManager.updateNote(noteId, content));

    }

    public NoteDeatils getDeatils() {
        return deatils;
    }

    @Override
    public void initNote() {
        if (activity.getIntent().hasExtra("noteId")) {
            noteId = activity.getIntent().getStringExtra("noteId");

            note = dataManager.selectNote(noteId);

            deatils.setNote(note);
            deatils.setImageList(dataManager.selectPhoto(noteId));
            deatils.setAttachmentList(dataManager.selectAttach(noteId));
            deatils.setCheckList(ListUtils.getCheckList(dataManager.selectCheckList(noteId),
                    dataManager.selectCheckListDone(noteId)));
            deatils.setCheckListDone(dataManager.selectCheckListDone(noteId));


            view.ShowData(deatils);
        } else {
            if (note == null) {
                note = dataManager.createNote();
            }
            noteId = note.getNoteId();

            deatils.setNote(note);
        }


        if (deatils.getImageList().size() == 0
                && deatils.getAttachmentList().size() == 0) {
            note.setAttach(false);
            note.update();
        }

        if (deatils.getImageList().size() == 0) {
            note.setKind(Constant.TEXT);
            note.update();
        }


        List<AttachMent> attachMentList = dataManager.getUnUpLoadAttatmentList(noteId);

        Flowable.fromIterable(attachMentList).

                map(attachMent -> new AttachmentPlus(attachMent,
                        FileUtils.encodeBase64File(attachMent.getLocalPath())))
                .compose(((BaseActivity) context).bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(attachmentPlus ->
                        updateRequest(attachmentPlus.getBase64(), attachmentPlus.getAttachMent(),
                                attachmentPlus.getAttachMent().getLocalPath().substring(
                                        attachmentPlus.getAttachMent().getLocalPath().lastIndexOf(".") + 1)));


    }

    @Override
    public void setPhotoData(String path, String fileName) {

        long currentTime = System.currentTimeMillis() / 1000;

        AttachMent photo = new AttachMent();
        photo.setSid(UUIDUtils.getUUID());
        photo.setNoteId(deatils.getNote().getNoteId());
        photo.setLocalPath(path);
        photo.setFileName(FileUtil.getFileName(fileName));
        photo.setStatus(SyncStatus.LocalNew);
        photo.setFileType(Constant.PHOTO);
        photo.setModifiedTime(currentTime);
        photo.setCrateTime(currentTime);


        deatils.setSingleImage(photo);

        if (dataManager.getUserSetting().isLogin()) {
            uploadFile(path, photo);
        }
        photo.setStatus(SyncStatus.LocalNew);
        photo.insert();

        deatils.getNote().setContent(currentNoteContent);
        deatils.getNote().setAttach(true);
        deatils.getNote().setModifiedTime(currentTime);
        deatils.getNote().setKind(Constant.PHOTO);

        view.insertPhotoData(deatils);
        if (deatils.getNote().getStatus() == SyncStatus.LocalNew) {
            deatils.getNote().setStatus(SyncStatus.LocalNew);

        } else {
            deatils.getNote().setStatus(SyncStatus.LocalUpdate);
        }
        deatils.getNote().update();
    }

    private void uploadFile(String path, AttachMent photo) {


        Luban.with(context)
                .load(path)                                   // 传人要压缩的图片列表
                .ignoreBy(100)                                  // 忽略不压缩图片的大小
                .setTargetDir(Constant.photoPath)                        // 设置压缩后文件存储位置
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {


                        Observable.just(file.getAbsolutePath())
                                .map(FileUtils::encodeBase64File)
                                .compose(((BaseActivity) context).bindToLifecycle())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(base64 ->
                                        updateRequest(base64, photo, path.substring(path.lastIndexOf(".") + 1)));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }).launch();    //启动压缩


    }

    private void updateRequest(String base64, AttachMent photo, String fileprefix) {
        try {
            params.put("fileBytes", base64);
        } catch (Exception e) {
            e.printStackTrace();
        }
        params.put("fileprefix", fileprefix);
        params.put("noteId", photo.getNoteId());

        if (dataManager.getUser() == null) {
            return;
        }

        apiWrapper.uploadFile(context, params, dataManager.getUser().getAccessToken())
                .subscribe(newSubscriber((QiniuRepose data) -> {
                    photo.setSpath(data.getFileUrl());
                    photo.setSize(data.getFsize());
                    photo.update();
                }), throwableSubscriber());
    }


    public void cearEmpty() {
        if (deatils.getNote().getContent().equals("") && deatils.getImageList().size() == 0
                && deatils.getAttachmentList().size() == 0 && deatils.getCheckList().size() == 0
                ) {
            deatils.getNote().delete();
        }

//        if (deatils.getImageList().size() == 0 && deatils.getAttachmentList().size() == 0) {
//            deatils.getNote().setAttach(false);
//            deatils.getNote().update();
//        }
    }

    public void addCheckItem() {
        long currentTime = System.currentTimeMillis() / 1000;
        deatils.getNote().setModifiedTime(currentTime);

        CheckListItem checkListItem = new CheckListItem();
        checkListItem.setSid(UUIDUtils.getUUID());
        checkListItem.setNoteId(deatils.getNote().getNoteId());
        checkListItem.setTitle("");
        checkListItem.setStatus(SyncStatus.LocalNew);
        checkListItem.setChecked(false);
        checkListItem.setSortOder(deatils.getCheckList().size() + 1);
        checkListItem.setCreateTime(currentTime);
        checkListItem.setModifiedTime(currentTime);
        checkListItem.insert();

        if (deatils.getImageList().size() > 0) {
            deatils.getNote().setKind(Constant.CHECKLISTIMAGE);
        } else {
            deatils.getNote().setKind(Constant.CHECKLIST);
        }
        if (deatils.getNote().getStatus() == SyncStatus.LocalNew) {
            deatils.getNote().setStatus(SyncStatus.LocalNew);
        } else {
            deatils.getNote().setStatus(SyncStatus.LocalUpdate);
        }
        deatils.getNote().setContent(currentNoteContent);

        deatils.getNote().update();
        deatils.setSingCheckList(checkListItem);

//        view.ShowData(deatils);
        view.insertCheckData(deatils);

    }


    public void updateCheck(String id, String content, int position) {
        long currentTime = System.currentTimeMillis() / 1000;
        checkListItem = dataManager.updateCheck(id, content);
        checkListItem.setModifiedTime(currentTime);
        if (deatils.getNote().getStatus() == SyncStatus.LocalNew) {
            deatils.getNote().setStatus(SyncStatus.LocalNew);
        } else {
            deatils.getNote().setStatus(SyncStatus.LocalUpdate);
        }
        deatils.getNote().setModifiedTime(currentTime);
        deatils.getNote().update();
        deatils.updateCheckItem(checkListItem, position);

    }

    public void deleteNote() {


        if (note.getStatus() == SyncStatus.HasSync) {
            note.setStatus(SyncStatus.Delete);
            note.setModifiedTime(System.currentTimeMillis() / 1000);
            note.setDeleted(true);
            note.update();
        } else {
            if (deatils.getCheckList().size() > 0) {
                dataManager.deleteCheckList(noteId);

            }
            if (deatils.getImageList().size() > 0) {
                dataManager.deleteImageList(noteId);
            }

            if (deatils.getAttachmentList().size() > 0) {
                dataManager.deleteAttachList(noteId);
            }

            note.delete();
        }


    }

    public void delteCheck(int position) {
        deatils.getNote().setStatus(SyncStatus.LocalUpdate);
        deatils.getNote().setModifiedTime(System.currentTimeMillis() / 1000);
        deatils.getNote().update();


        CheckListItem checkListItem = deatils.getCheckList().get(position);
        checkListItem.setStatus(SyncStatus.Delete);
        checkListItem.setDeleted(true);
        checkListItem.update();

        deatils.getCheckList().remove(position);

        view.delteItemTolist(deatils, position);
    }

    public void showDialogData() {


        note = dataManager.selectNote(noteId);

        deatils.setNote(note);
        deatils.setImageList(dataManager.selectPhoto(noteId));
        deatils.setAttachmentList(dataManager.selectAttach(noteId));
        deatils.setCheckList(ListUtils.getCheckList(dataManager.selectCheckList(noteId),
                dataManager.selectCheckListDone(noteId)));

        deatils.setCheckListDone(dataManager.selectCheckListDone(noteId));
        view.showNoteDialog(deatils);

    }

    public void addAttachment(String fileUrl, String fileName) {
        long currentTime = System.currentTimeMillis() / 1000;


        AttachMent attachMent = new AttachMent();
        attachMent.setSid(UUIDUtils.getUUID());
        attachMent.setNoteId(deatils.getNote().getNoteId());
        attachMent.setLocalPath(fileUrl);
        attachMent.setFileName(fileName);
        attachMent.setFileType(Constant.ATTACH);
        attachMent.setModifiedTime(currentTime);
        attachMent.setCrateTime(currentTime);


        attachMent.insert();
        deatils.setSingleAttch(attachMent);

        if (dataManager.getUserSetting().isLogin()) {
            uploadFile(fileUrl, attachMent);
        }

        attachMent.setStatus(SyncStatus.LocalNew);

        deatils.getNote().setAttach(true);
        deatils.getNote().setModifiedTime(currentTime);
        if (deatils.getImageList().size() > 0) {
            deatils.getNote().setKind(Constant.ATTACHIMAGE);
        } else {
            deatils.getNote().setKind(Constant.ATTACH);
        }


        if (deatils.getNote().getStatus() == SyncStatus.LocalNew) {
            deatils.getNote().setStatus(SyncStatus.LocalNew);
        } else {
            deatils.getNote().setStatus(SyncStatus.LocalUpdate);
        }
        deatils.getNote().update();


//        view.ShowData(deatils);
        view.insertPhotoData(deatils);
    }

    public void delteImage(int position) {

        AttachMent photo = deatils.getImageList().get(position);
        photo.setStatus(SyncStatus.Delete);
        photo.setDeleted(true);
        photo.update();


        deatils.getImageList().remove(position);
        if (deatils.getImageList().size() == 0) {
            deatils.getNote().setKind(Constant.TEXT);

        }
        deatils.getNote().setStatus(SyncStatus.LocalUpdate);
        deatils.getNote().setModifiedTime(System.currentTimeMillis() / 1000);
        deatils.getNote().update();

        view.delteItemTolist(deatils, position);
    }

    public void delteAttach(int position) {
        AttachMent attachMent = deatils.getAttachmentList().get(position);
        attachMent.setStatus(SyncStatus.Delete);
        attachMent.setDeleted(true);
        attachMent.update();

        deatils.getNote().setStatus(SyncStatus.LocalUpdate);
        deatils.getNote().setModifiedTime(System.currentTimeMillis() / 1000);
        deatils.getNote().update();

        deatils.getAttachmentList().remove(position);

        view.delteItemTolist(deatils, position);
    }

    public NoteDeatils moveCheck() {

        note = dataManager.selectNote(noteId);

        deatils.setNote(note);
        deatils.setImageList(dataManager.selectPhoto(noteId));
        deatils.setAttachmentList(dataManager.selectAttach(noteId));
        deatils.setCheckList(ListUtils.getCheckList(dataManager.selectCheckList(noteId),
                dataManager.selectCheckListDone(noteId)));

        deatils.setCheckListDone(dataManager.selectCheckListDone(noteId));
        if (deatils.getCheckList().indexOf(checkListItem) >= 0) {
            deatils.getCheckList().set(deatils.getCheckList().indexOf(checkListItem), checkListItem);
        }
        return deatils;
    }

    public void setdeleteDialog(boolean isChecked) {
        UserSetting usersetting = dataManager.getUserSetting();
        usersetting.set_deleted(isChecked);
        usersetting.update();
    }

    public UserSetting getUserseting() {
        return dataManager.getUserSetting();
    }

    public void updateNoteStatus() {
        long currentTime = System.currentTimeMillis() / 1000;

        if (deatils.getNote().getStatus() == SyncStatus.LocalNew) {
            deatils.getNote().setStatus(SyncStatus.LocalNew);
        } else {
            deatils.getNote().setStatus(SyncStatus.LocalUpdate);
        }
        deatils.getNote().setModifiedTime(currentTime);
        deatils.getNote().update();

    }
}