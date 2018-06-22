package com.xuyazhou.mynote.model.bean;

import com.xuyazhou.mynote.model.Folder;
import com.xuyazhou.mynote.model.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017-05-11
 */
public class SyncNoteData {
    private Long checkpoint;
    private List<Note> syncNoteList = new ArrayList<>();
    private List<Folder> folderList = new ArrayList<>();


    public Long getCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(Long checkpoint) {
        this.checkpoint = checkpoint;
    }

    public List<Note> getSyncNoteList() {
        return syncNoteList;
    }

    public void setSyncNoteList(List<Note> syncNoteList) {
        this.syncNoteList = syncNoteList;
    }

    public List<Folder> getFolderList() {
        return folderList;
    }

    public void setFolderList(List<Folder> folderList) {
        this.folderList = folderList;
    }
}
