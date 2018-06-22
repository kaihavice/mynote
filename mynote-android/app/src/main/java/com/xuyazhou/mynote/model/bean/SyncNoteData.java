package com.xuyazhou.mynote.model.bean;

import com.xuyazhou.mynote.model.db.Folder;
import com.xuyazhou.mynote.model.db.Note;

import java.util.List;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017-05-11
 */
public class SyncNoteData {
    Long checkpoint;
    List<Note> syncNoteList;
    List<Folder> folderList;


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
