package com.xuyazhou.mynote.model.bean;

import com.xuyazhou.mynote.model.db.AttachMent;
import com.xuyazhou.mynote.model.db.CheckListItem;

import java.util.ArrayList;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017/3/27
 */
public class SyncJson {
    private String content;
    private ArrayList<AttachMent> attachMentList;
    private ArrayList<CheckListItem> checkList;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<AttachMent> getAttachMentList() {
        return attachMentList;
    }

    public void setAttachMentList(ArrayList<AttachMent> attachMentList) {
        this.attachMentList = attachMentList;
    }

    public ArrayList<CheckListItem> getCheckList() {
        return checkList;
    }

    public void setCheckList(ArrayList<CheckListItem> checkList) {
        this.checkList = checkList;
    }
}
