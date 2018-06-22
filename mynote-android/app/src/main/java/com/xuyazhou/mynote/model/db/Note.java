package com.xuyazhou.mynote.model.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.xuyazhou.mynote.model.bean.RequestAttachment;
import com.xuyazhou.mynote.model.bean.RequestCheck;

import java.util.List;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2016-12-19
 */

@Table(database = AppDatabase.class)
public class Note extends BaseModel {


    @PrimaryKey(autoincrement = true)
    @Column
    long _id;

    @Column
    String userId;

    @Column
    String noteId;


    @Column
    String folderId;

    @Column
    String kind;

    @Column
    String content;

    @Column
    String contentOld;


    @Column
    boolean isAttach;


    @Column
    long createTime;

    @Column
    long modifiedTime;

    @Column
    int status;

    @Column
    boolean deleted;



    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    private RequestAttachment requestAttachment;
    private RequestCheck requestCheck;

    public RequestAttachment getRequestAttachment() {
        return requestAttachment;
    }

    public void setRequestAttachment(RequestAttachment requestAttachment) {
        this.requestAttachment = requestAttachment;
    }


    private List<AttachMent> attachmentList;
    private List<CheckListItem> checkListItemList;

    public List<AttachMent> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<AttachMent> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public List<CheckListItem> getCheckListItemList() {
        return checkListItemList;
    }

    public void setCheckListItemList(List<CheckListItem> checkListItemList) {
        this.checkListItemList = checkListItemList;
    }

    public RequestCheck getRequestCheck() {
        return requestCheck;
    }

    public void setRequestCheck(RequestCheck requestCheck) {
        this.requestCheck = requestCheck;
    }

    public String getSid() {
        return userId;
    }

    public void setUserId(String sid) {
        this.userId = sid;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentOld() {
        return contentOld;
    }

    public void setContentOld(String contentOld) {
        this.contentOld = contentOld;
    }



    public boolean isAttach() {
        return isAttach;
    }

    public void setAttach(boolean attach) {
        isAttach = attach;
    }


    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(long modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
