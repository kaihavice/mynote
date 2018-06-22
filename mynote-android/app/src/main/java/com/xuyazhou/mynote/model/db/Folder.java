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
public class Folder extends BaseModel {

    @PrimaryKey
    @Column
    String _id;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Column
    String noteId;

    @Column
    String name;

    @Column
    String Color;

    @Column
    boolean defaultFolder;

    @Column
    boolean isLock;

    @Column
    long displayMode;

    @Column
    Long order;

    @Column
    long createTime;

    @Column
    long modifiedTime;

    @Column
    int status;

    @Column
    boolean deleted;

    @Column
    long sortType;


    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public boolean isDefaultFolder() {
        return defaultFolder;
    }

    public void setDefaultFolder(boolean defaultFolder) {
        this.defaultFolder = defaultFolder;
    }

    public boolean isLock() {
        return isLock;
    }

    public void setLock(boolean lock) {
        isLock = lock;
    }

    public long getDisplayMode() {
        return displayMode;
    }

    public void setDisplayMode(long displayMode) {
        this.displayMode = displayMode;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
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

    public long getSortType() {
        return sortType;
    }

    public void setSortType(long sortType) {
        this.sortType = sortType;
    }
}
