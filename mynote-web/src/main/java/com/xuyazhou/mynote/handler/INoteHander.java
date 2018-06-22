package com.xuyazhou.mynote.handler;

import com.xuyazhou.mynote.model.bean.NoteListData;
import com.xuyazhou.mynote.model.bean.SyncNoteData;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017-05-15
 */
public interface INoteHander {

    SyncNoteData checkNoteAndUpdate(String noteData, String checkPiont,
                                    String userid, boolean isNew);

    NoteListData getNoteListByPage(String usrId, int pageNum, int pageSize);
}
