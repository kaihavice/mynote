package com.xuyazhou.mynote.model.bean;

import com.xuyazhou.mynote.model.db.AttachMent;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017-06-06
 */
public class RequestAttachment {

    private List<AttachMent> add = new ArrayList<>();
    private List<AttachMent> delete = new ArrayList<>();


    public List<AttachMent> getAdd() {
        return add;
    }

    public void setAdd(List<AttachMent> add) {
        this.add = add;
    }

    public List<AttachMent> getDelete() {
        return delete;
    }

    public void setDelete(List<AttachMent> delete) {
        this.delete = delete;
    }
}
