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
public class NoteSyncedJson extends BaseModel {

    @PrimaryKey(autoincrement = true)
    @Column
    long _id;

    @Column
    long userId;

    @Column
    long noteSid;

    @Column
    String json;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getNoteSid() {
        return noteSid;
    }

    public void setNoteSid(long noteSid) {
        this.noteSid = noteSid;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
