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
public class User extends BaseModel {

    @PrimaryKey(autoincrement = true)
    @Column
    long _id;

    @Column
    String userName;

    @Column
    String password;

    @Column
    String accessToken;

    @Column
    long activity;

    @Column
    long modifyTime;

    @Column
    long createdTime;

    @Column
    int _status;


    @Column
    boolean _deleted;

    @Column
    long lastSyncNoteTime;

    @Column
    String sid;

    @Column
    String avatar;

    @Column
    String email;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getActivity() {
        return activity;
    }

    public void setActivity(long activity) {
        this.activity = activity;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public int get_status() {
        return _status;
    }

    public void set_status(int _status) {
        this._status = _status;
    }

    public boolean is_deleted() {
        return _deleted;
    }

    public void set_deleted(boolean _deleted) {
        this._deleted = _deleted;
    }

    public long getLastSyncNoteTime() {
        return lastSyncNoteTime;
    }

    public void setLastSyncNoteTime(long lastSyncNoteTime) {
        this.lastSyncNoteTime = lastSyncNoteTime;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }




    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
