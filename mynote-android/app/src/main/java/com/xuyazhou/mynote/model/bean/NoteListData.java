package com.xuyazhou.mynote.model.bean;

import com.xuyazhou.mynote.model.db.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017/6/15
 */
public class NoteListData {

    List<Note> noteList = new ArrayList<>();
    boolean ishaveNext;

    public List<Note> getNoteList() {
        return noteList;
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }

    public boolean isIshaveNext() {
        return ishaveNext;
    }

    public void setIshaveNext(boolean ishaveNext) {
        this.ishaveNext = ishaveNext;
    }
}
