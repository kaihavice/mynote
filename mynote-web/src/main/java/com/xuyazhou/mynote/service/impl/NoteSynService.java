package com.xuyazhou.mynote.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.xuyazhou.mynote.handler.impl.MemberHandler;
import com.xuyazhou.mynote.handler.impl.NoteHandler;
import com.xuyazhou.mynote.model.User;
import com.xuyazhou.mynote.model.bean.NoteListData;
import com.xuyazhou.mynote.model.bean.SyncNoteData;
import com.xuyazhou.mynote.service.INoteSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017-05-18
 */
@Service
public class NoteSynService implements INoteSyncService {

    @Autowired
    NoteHandler noteHandler;

    @Autowired
    MemberHandler memberHandler;

    @Override
    public SyncNoteData saveNoteAndBack(String noteData, String checkPiont, String userId) {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) ->
                new Date(json.getAsJsonPrimitive().getAsLong())).create();

        User user = memberHandler.getUersByuserId(userId);


        SyncNoteData syncNoteData = noteHandler.checkNoteAndUpdate(noteData, checkPiont,
                user.getSid(), (user.getSyncnotetime().getTime() + "").equals(checkPiont));

        Date sysncTime = new Date();
        user.setSyncnotetime(sysncTime);

        memberHandler.updateCheckPoint(user);


        User newUser = memberHandler.getUersByuserId(userId);

        syncNoteData.setCheckpoint(Long.valueOf(gson.toJson(newUser.getSyncnotetime().getTime())));

        return syncNoteData;


    }

    @Override
    public NoteListData getNoteMoreByPage(int pageSize, int pageNum, String usrId) {
        return noteHandler.getNoteListByPage(usrId, pageNum, pageSize);
    }
}
