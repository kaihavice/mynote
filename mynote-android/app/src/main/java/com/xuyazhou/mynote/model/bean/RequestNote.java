package com.xuyazhou.mynote.model.bean;

import com.xuyazhou.mynote.model.db.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017/5/16
 */
public class RequestNote {
    List<Note> add = new ArrayList<>();
    List<Note> update = new ArrayList<>();
    List<Note> delete =  new ArrayList<>();


    public List<Note> getAdd() {
        return add;
    }

    public void setAdd(List<Note> add) {
        this.add = add;
    }

    public List<Note> getUpdate() {
        return update;
    }

    public void setUpdate(List<Note> update) {
        this.update = update;
    }

    public List<Note> getDelete() {
        return delete;
    }

    public void setDelete(List<Note> delete) {
        this.delete = delete;
    }
}
