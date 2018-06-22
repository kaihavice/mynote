package com.xuyazhou.mynote.model.bean;

import com.xuyazhou.mynote.model.Attachment;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017-06-06
 */
public class RequestAttachment {

    private List<Attachment> add = new ArrayList<>();
    private List<Attachment> delete  = new ArrayList<>();


    public List<Attachment> getAdd() {
        return add;
    }

    public void setAdd(List<Attachment> add) {
        this.add = add;
    }

    public List<Attachment> getDelete() {
        return delete;
    }

    public void setDelete(List<Attachment> delete) {
        this.delete = delete;
    }
}
