package com.xuyazhou.mynote.service;

import com.xuyazhou.mynote.model.bean.NoteListData;
import com.xuyazhou.mynote.model.bean.SyncNoteData;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017-05-11
 */
public interface INoteSyncService {

    SyncNoteData saveNoteAndBack(String noteData, String checkPiont, String userId);

    NoteListData getNoteMoreByPage(int pageSize, int pageNum, String usrId);

}
