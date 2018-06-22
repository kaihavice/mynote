package com.xuyazhou.mynote.model.data;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.rx2.language.RXSQLite;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;
import com.xuyazhou.mynote.common.config.Constant;
import com.xuyazhou.mynote.common.config.SyncStatus;
import com.xuyazhou.mynote.common.utils.UUIDUtils;
import com.xuyazhou.mynote.model.bean.RequestAttachment;
import com.xuyazhou.mynote.model.bean.RequestCheck;
import com.xuyazhou.mynote.model.bean.RequestNote;
import com.xuyazhou.mynote.model.db.AppDatabase;
import com.xuyazhou.mynote.model.db.AttachMent;
import com.xuyazhou.mynote.model.db.AttachMent_Table;
import com.xuyazhou.mynote.model.db.CheckListItem;
import com.xuyazhou.mynote.model.db.CheckListItem_Table;
import com.xuyazhou.mynote.model.db.Folder;
import com.xuyazhou.mynote.model.db.Folder_Table;
import com.xuyazhou.mynote.model.db.Note;
import com.xuyazhou.mynote.model.db.Note_Table;
import com.xuyazhou.mynote.model.db.User;
import com.xuyazhou.mynote.model.db.UserSetting;
import com.xuyazhou.mynote.model.db.User_Table;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;


/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017/1/3
 */
public class DataManager {


    public List<User> getUserList() {

        return SQLite.select().from(User.class).queryList();
    }

    public User getUser() {
        return SQLite.select().from(User.class).where(User_Table.activity.
                eq(Constant.LIVE))
                .querySingle();
    }


    public String getFolderId() {

        Folder folder = SQLite.select().from(Folder.class).where(Folder_Table.defaultFolder.eq(true)).querySingle();

        if (folder != null) {
            return folder.get_id();
        } else {
            return "0";
        }
    }

    public UserSetting getUserSetting() {
        return SQLite.select().from(UserSetting.class).querySingle();
    }


    public List<Note> getNoteList(int offset) {

        User user = getUser();
        UserSetting setting = getUserSetting();
        List<Note> noteList = new ArrayList<>();

        RXSQLite.rx(SQLite.select().from(Note.class)
                .where(Note_Table.deleted.eq(false))
                .and(Note_Table.userId.eq(setting.isLogin()
                        ? user.getSid() : user != null ? user.getSid() : "0"))
                .orderBy(Note_Table.modifiedTime, false)
                .limit(10).offset(offset * 10)).queryList()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Consumer<List<Note>>) noteList::addAll);

        return noteList;

    }

    public void getVistorNoteList() {

        RXSQLite.rx(SQLite.select().from(Note.class)
                .where(Note_Table.deleted.eq(false))
                .and(Note_Table.userId.eq("0"))
                .orderBy(Note_Table.modifiedTime, false))
                .queryList()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::fastUpdateNote);


    }

    public AttachMent getFirstPhotoByNoteid(String noteId) {

        return SQLite.select().from(AttachMent.class).where(AttachMent_Table.noteId.eq(noteId))
                .and(AttachMent_Table.fileType.eq(Constant.PHOTO))
                .and(Note_Table.deleted.eq(false))
                .orderBy(Note_Table.modifiedTime, false).querySingle();

    }


    public RequestNote getRuestNoteString() {

        User user = getUser();
        UserSetting setting = getUserSetting();

        RequestNote requestNote = new RequestNote();

        RXSQLite.rx(SQLite.select().from(Note.class)
                .where(Note_Table.status.eq(SyncStatus.LocalNew))
                .and(Note_Table.userId.eq(setting.isLogin()
                        ? user.getSid() : user != null ? user.getSid() : "0")))
                .queryList()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((list) ->
                        requestNote.setAdd(setlectAttachment(list)));

        RXSQLite.rx(SQLite.select().from(Note.class)
                .where(Note_Table.status.eq(SyncStatus.LocalUpdate))
                .and(Note_Table.userId.eq(setting.isLogin()
                        ? user.getSid() : user != null ? user.getSid() : "0")))
                .queryList()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((list) ->
                        requestNote.setUpdate(setlectAttachment(list)));

        RXSQLite.rx(SQLite.select().from(Note.class)
                .where(Note_Table.status.eq(SyncStatus.Delete))
                .and(Note_Table.userId.eq(setting.isLogin()
                        ? user.getSid() : user != null ? user.getSid() : "0")))
                .queryList()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((list) ->
                        requestNote.setDelete(setlectAttachment(list)));


        return requestNote;
    }

    private List<Note> setlectAttachment(List<Note> noteList) {


        for (Note note : noteList) {

            RequestAttachment attachment = new RequestAttachment();
            attachment.getAdd().addAll(selectAddAttach(note.getNoteId()));
            attachment.getDelete().addAll(selectdetelteAttach(note.getNoteId()));


            RequestCheck requestCheck = new RequestCheck();
            requestCheck.getAdd().addAll(selectaddCheckList(note.getNoteId()));
            requestCheck.getUpdate().addAll(selectaUpdateCheckList(note.getNoteId()));
            requestCheck.getDelete().addAll(selectdeteleCheckList(note.getNoteId()));


            note.setRequestAttachment(attachment);
            note.setRequestCheck(requestCheck);

        }

        return noteList;
    }


    public Note createNote() {
        Note note = new Note();

        String id = UUIDUtils.getUUID();
        note.setNoteId(id);
        note.setKind(Constant.TEXT);
        Long currentTime = System.currentTimeMillis() / 1000;
        note.setCreateTime(currentTime);
        note.setModifiedTime(currentTime);
        note.setDeleted(false);
        note.setStatus(SyncStatus.LocalNew);
        note.setContent("");
        note.setFolderId(getFolderId());

        User user = getUser();
        if (user == null) {
            note.setUserId("0");
        } else {
            note.setUserId(user.getSid());
        }

        note.insert();

        return note;
    }


    public Note updateNote(String id, String content) {
        Note note = selectNote(id);
        Long currentTime = System.currentTimeMillis() / 1000;
        note.setModifiedTime(currentTime);
        note.setContentOld(note.getContent());
        if (note.getStatus() == SyncStatus.HasSync) {
            note.setStatus(SyncStatus.LocalUpdate);
        }
        note.setContent(content);
        note.save();

        return note;

    }

    public CheckListItem updateCheck(String id, String content) {
        CheckListItem checkItem = selectCheckItem(id);
        Long currentTime = System.currentTimeMillis() / 1000;
        checkItem.setModifiedTime(currentTime);
        if (checkItem.getStatus() == SyncStatus.HasSync) {
            checkItem.setStatus(SyncStatus.LocalUpdate);
        }
        checkItem.setTitle(content);
        checkItem.update();

        return checkItem;

    }


    public Note selectNote(String id) {
        return SQLite.select().from(Note.class).where(Note_Table.noteId.is(id)).querySingle();
    }

    public CheckListItem selectCheckItem(String id) {
        return SQLite.select().from(CheckListItem.class).where(CheckListItem_Table.sid.is(id)).querySingle();
    }


    public ArrayList<AttachMent> selectAddAttach(String id) {
        return (ArrayList<AttachMent>) SQLite.select().from(AttachMent.class).where(AttachMent_Table.noteId.is(id))
                .and(AttachMent_Table.status.eq(SyncStatus.LocalNew))
                .orderBy(Note_Table.modifiedTime, false).queryList();
    }


    public ArrayList<AttachMent> selectdetelteAttach(String id) {
        return (ArrayList<AttachMent>) SQLite.select().from(AttachMent.class).where(AttachMent_Table.noteId.is(id))
                .and(AttachMent_Table.status.eq(SyncStatus.Delete))
                .orderBy(Note_Table.modifiedTime, false).queryList();
    }

    public ArrayList<AttachMent> selectAttach(String id) {
        return (ArrayList<AttachMent>) SQLite.select().from(AttachMent.class).where(AttachMent_Table.noteId.is(id))
                .and(AttachMent_Table.fileType.eq(Constant.ATTACH))
                .and(AttachMent_Table.deleted.eq(false))
                .orderBy(Note_Table.modifiedTime, false).queryList();
    }

    public AttachMent selectAttachMent(String id) {
        return SQLite.select().from(AttachMent.class)
                .where(AttachMent_Table.sid.is(id))
                .querySingle();
    }

    public CheckListItem selectCheckListItem(String id) {
        return SQLite.select().from(CheckListItem.class)
                .where(CheckListItem_Table.sid.is(id))
                .querySingle();
    }

    public ArrayList<AttachMent> selectPhoto(String id) {
        return (ArrayList<AttachMent>) SQLite.select().from(AttachMent.class).where(AttachMent_Table.noteId.eq(id))
                .and(AttachMent_Table.fileType.eq(Constant.PHOTO))
                .and(AttachMent_Table.deleted.eq(false))
                .orderBy(Note_Table.modifiedTime, false).queryList();
    }

    public ArrayList<CheckListItem> selectCheckList(String id) {
        return (ArrayList<CheckListItem>) SQLite.select().from(CheckListItem.class).where(CheckListItem_Table.noteId.eq(id))
                .and(CheckListItem_Table.checked.eq(false))
                .and(Note_Table.deleted.eq(false))
                .orderBy(CheckListItem_Table.sortOder, true)
                .queryList();
    }

    public ArrayList<CheckListItem> selectaUpdateCheckList(String id) {
        return (ArrayList<CheckListItem>) SQLite.select().from(CheckListItem.class).where(CheckListItem_Table.noteId.eq(id))
                .and(CheckListItem_Table.status.eq(SyncStatus.LocalUpdate))
                .orderBy(CheckListItem_Table.sortOder, true)
                .queryList();
    }

    public ArrayList<CheckListItem> selectaddCheckList(String id) {
        return (ArrayList<CheckListItem>) SQLite.select().from(CheckListItem.class).where(CheckListItem_Table.noteId.eq(id))
                .and(CheckListItem_Table.status.eq(SyncStatus.LocalNew))
                .orderBy(CheckListItem_Table.sortOder, true)
                .queryList();
    }

    public ArrayList<CheckListItem> selectdeteleCheckList(String id) {
        return (ArrayList<CheckListItem>) SQLite.select().from(CheckListItem.class).where(CheckListItem_Table.noteId.eq(id))
                .and(CheckListItem_Table.status.eq(SyncStatus.Delete))
                .orderBy(CheckListItem_Table.sortOder, true)
                .queryList();
    }

    public ArrayList<CheckListItem> selectCheckListDone(String id) {
        return (ArrayList<CheckListItem>) SQLite.select().from(CheckListItem.class).where(CheckListItem_Table.noteId.eq(id))
                .and(Note_Table.deleted.eq(false))
                .and(CheckListItem_Table.checked.eq(true))
                .orderBy(Note_Table.modifiedTime, false)
                .queryList();
    }


    public void deleteCheckList(String noteId) {
        SQLite.delete(CheckListItem.class)
                .where(CheckListItem_Table.noteId.is(noteId))
                .async().execute();
    }

    public void deleteImageList(String noteId) {
        SQLite.delete(AttachMent.class)
                .where(AttachMent_Table.noteId.is(noteId))
                .and(AttachMent_Table.fileType.is(Constant.PHOTO))
                .async().execute();
    }

    public void deleteAttachList(String noteId) {
        SQLite.delete(AttachMent.class)
                .where(AttachMent_Table.noteId.is(noteId))
                .and(AttachMent_Table.fileType.is(Constant.ATTACH))
                .async().execute();

    }


    public void fastUpdateNote(List<Note> noteList) {

        DatabaseDefinition database = FlowManager.getDatabase(AppDatabase.class);

        ProcessModelTransaction<Note> processModelTransaction =
                new ProcessModelTransaction.Builder<>((ProcessModelTransaction.ProcessModel<Note>)
                        (note, databaseWrapper) -> {

                            note.setUserId(getUser().getSid());
                            note.update();

                        }).processListener((current, total, modifiedModel) -> {
                }).addAll(noteList).build();

        Transaction transaction = database.beginTransactionAsync(processModelTransaction).build();
        transaction.execute();
    }

    public void fastSaveNote(List<Note> noteList) {

        DatabaseDefinition database = FlowManager.getDatabase(AppDatabase.class);

        ProcessModelTransaction<Note> processModelTransaction =
                new ProcessModelTransaction.Builder<>((ProcessModelTransaction.ProcessModel<Note>)
                        (note, databaseWrapper) -> {
                            Note localNote = selectNote(note.getNoteId());
                            if (localNote != null) {
                                localNote.delete();
                            }

                            if (note.isDeleted()) {
                                return;
                            }

                            note.setStatus(SyncStatus.HasSync);
                            note.setUserId(getUser().getSid());
                            note.save();


                        }).processListener((current, total, modifiedModel) -> {
                }).addAll(noteList).build();

        Transaction transaction = database.beginTransactionAsync(processModelTransaction).build();
        transaction.execute();
    }


    public void fastSaveAttachment(List<AttachMent> attachMentList) {


        DatabaseDefinition database = FlowManager.getDatabase(AppDatabase.class);

        ProcessModelTransaction<AttachMent> processModelTransaction =
                new ProcessModelTransaction.Builder<>((ProcessModelTransaction.ProcessModel<AttachMent>)
                        (attachMent, databaseWrapper) -> {
                            AttachMent localAttach = selectAttachMent(attachMent.getNoteId());
                            if (localAttach != null) {
                                localAttach.delete();
                            }

                            if (attachMent.isDeleted()) {
                                return;
                            }
                            attachMent.setStatus(SyncStatus.HasSync);
                            attachMent.save();


                        }).processListener((current, total, modifiedModel) -> {
                }).addAll(attachMentList).build();

        Transaction transaction = database.beginTransactionAsync(processModelTransaction).build();
        transaction.execute();
    }


    public void fastSaveCheckList(List<CheckListItem> checkListItemList) {

        DatabaseDefinition database = FlowManager.getDatabase(AppDatabase.class);

        ProcessModelTransaction<CheckListItem> processModelTransaction =
                new ProcessModelTransaction.Builder<>((ProcessModelTransaction.ProcessModel<CheckListItem>)
                        (checkListItem, databaseWrapper) -> {
                            CheckListItem localcheck = selectCheckItem(checkListItem.getNoteId());
                            if (localcheck != null) {
                                localcheck.delete();
                            }

                            if (checkListItem.isDeleted()) {
                                return;
                            }

                            checkListItem.setStatus(SyncStatus.HasSync);
                            checkListItem.save();

                        }).processListener((current, total, modifiedModel) -> {
                }).addAll(checkListItemList).build();

        Transaction transaction = database.beginTransactionAsync(processModelTransaction).build();
        transaction.execute();
    }

    public List<AttachMent> getUnUpLoadAttatmentList(String id) {

        return SQLite.select().from(AttachMent.class).where(AttachMent_Table.noteId.eq(id))
                .and(AttachMent_Table.spath.isNull()).queryList();
    }
}
