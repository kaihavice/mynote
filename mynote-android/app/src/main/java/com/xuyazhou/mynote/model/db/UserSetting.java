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
public class UserSetting extends BaseModel {

    @PrimaryKey(autoincrement = true)
    @Column
    long _id;


    @Column
    long cratedTime;

    @Column
    long modifiedTime;

    @Column
    String lockType;

    @Column
    boolean _deleted;

    @Column
    boolean isLogin;


    @Column
    String listType;


    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public long getCratedTime() {
        return cratedTime;
    }

    public void setCratedTime(long cratedTime) {
        this.cratedTime = cratedTime;
    }

    public long getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(long modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }

    public boolean is_deleted() {
        return _deleted;
    }

    public void set_deleted(boolean _deleted) {
        this._deleted = _deleted;
    }
}
