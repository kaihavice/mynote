package com.xuyazhou.mynote.model.bean;

import com.xuyazhou.mynote.model.db.CheckListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017-06-06
 */
public class RequestCheck {

    private List<CheckListItem> add = new ArrayList<>();
    private List<CheckListItem> update = new ArrayList<>();
    private List<CheckListItem> delete = new ArrayList<>();


    public List<CheckListItem> getAdd() {
        return add;
    }

    public void setAdd(List<CheckListItem> add) {
        this.add = add;
    }

    public List<CheckListItem> getUpdate() {
        return update;
    }

    public void setUpdate(List<CheckListItem> update) {
        this.update = update;
    }

    public List<CheckListItem> getDelete() {
        return delete;
    }

    public void setDelete(List<CheckListItem> delete) {
        this.delete = delete;
    }
}
