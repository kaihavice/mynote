package com.xuyazhou.mynote.model.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2016-12-19
 */

@Table(database = AppDatabase.class)
public class CheckListItem extends BaseModel {



    @Column
    String noteId;

    @Column
    long userId;

    @Column
    String title;

    @Column
    boolean checked;

    @Column
    long sortOder;

    @PrimaryKey
    @Column
    String sid;

    @Column
    long createTime;

    @Column
    long modifiedTime;

    @Column
    int status;

    @Column
    boolean deleted;

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public long getSortOder() {
        return sortOder;
    }

    public void setSortOder(long sortOder) {
        this.sortOder = sortOder;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


}
