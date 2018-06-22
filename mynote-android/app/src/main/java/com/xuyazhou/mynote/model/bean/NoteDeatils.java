package com.xuyazhou.mynote.model.bean;

import com.xuyazhou.mynote.model.db.AttachMent;
import com.xuyazhou.mynote.model.db.CheckListItem;
import com.xuyazhou.mynote.model.db.Note;

import java.util.ArrayList;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2016/12/30
 */
public class NoteDeatils {
    private ArrayList<AttachMent> attachmentList = new ArrayList<>();
    private ArrayList<AttachMent> imageList = new ArrayList<>();
    private ArrayList<CheckListItem> checkList = new ArrayList<>();
    private ArrayList<CheckListItem> checkListDone = new ArrayList<>();

    private Note note;

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public NoteDeatils() {
        super();
    }

    public ArrayList<AttachMent> getAttachmentList() {
        return attachmentList;
    }

    public ArrayList<AttachMent> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<AttachMent> imageList) {
        this.imageList = imageList;
    }

    public void setSingleAttach(AttachMent attach) {
        attachmentList.add(attach);
    }

    public ArrayList<CheckListItem> getCheckListDone() {
        return checkListDone;
    }

    public void setCheckListDone(ArrayList<CheckListItem> checkListDone) {
        this.checkListDone = checkListDone;
    }

    public void setSingleImage(AttachMent attach) {
        imageList.add(0, attach);
    }

    public void setSingleAttch(AttachMent attach) {
        attachmentList.add(attach);
    }

    public void setSingCheckList(CheckListItem check) {
        checkList.add(check);
    }

    public void setAttachmentList(ArrayList<AttachMent> attachmentList) {
        this.attachmentList = attachmentList;
    }


    public ArrayList<CheckListItem> getCheckList() {
        return checkList;
    }

    public void setCheckList(ArrayList<CheckListItem> checkList) {
        this.checkList = checkList;
    }


    public void updateCheckItem(CheckListItem checkListItem, int position) {
        checkList.set(position, checkListItem);

    }


}
