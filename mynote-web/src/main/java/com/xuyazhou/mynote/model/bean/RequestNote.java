package com.xuyazhou.mynote.model.bean;

import com.xuyazhou.mynote.model.Note;

import java.util.List;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017-05-17
 */
public class RequestNote {
    private List<Note> add;
    private List<Note> update;
    private List<Note> delete;


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
