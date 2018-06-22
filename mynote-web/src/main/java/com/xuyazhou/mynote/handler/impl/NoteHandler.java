package com.xuyazhou.mynote.handler.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.xuyazhou.mynote.Utils.TimeUtils;
import com.xuyazhou.mynote.handler.INoteHander;
import com.xuyazhou.mynote.mapper.AttachmentMapper;
import com.xuyazhou.mynote.mapper.CheckListItemMapper;
import com.xuyazhou.mynote.mapper.NoteMapper;
import com.xuyazhou.mynote.model.*;
import com.xuyazhou.mynote.model.bean.NoteListData;
import com.xuyazhou.mynote.model.bean.RequestNote;
import com.xuyazhou.mynote.model.bean.SyncNoteData;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017-05-15
 */
@Service
public class NoteHandler implements INoteHander {

    @Autowired
    private NoteMapper noteMapper;
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Autowired
    private CheckListItemMapper checkListItemMapper;

    @Transactional
    @Override
    public SyncNoteData checkNoteAndUpdate(String noteData, String checkPiont, String userId, boolean isNew) {

        SyncNoteData syncNoteData = new SyncNoteData();
        syncNoteData.setSyncNoteList(new ArrayList<>());

        GsonBuilder builder = new GsonBuilder();


        Gson gson = builder.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) ->
                new Date(json.getAsJsonPrimitive().getAsLong())).create();

        RequestNote requestNote = gson.fromJson(noteData, RequestNote.class);


        for (int i = 0; i < requestNote.getAdd().size(); i++) {
            Note noteBean = new Note();
            Note addNote = requestNote.getAdd().get(i);

            saveAttachment(addNote);


            BeanUtils.copyProperties(addNote, noteBean);
            noteBean.setUserId(userId);
            noteMapper.insertSelective(noteBean);

        }

        for (int i = 0; i < requestNote.getUpdate().size(); i++) {
            Note clientBean = requestNote.getUpdate().get(i);
            Note noteBean = noteMapper.selectByPrimaryKey(clientBean.getNoteId());

            saveAttachment(clientBean);
            updateAttachment(clientBean);
            detelteAttachment(clientBean);

            if (noteBean.getModifiedTime().getTime() < clientBean.getModifiedTime().getTime()) {
//                clientBean.setModifiedTime(new Date());
                noteMapper.updateByPrimaryKey(clientBean);
            } else if (noteBean.getModifiedTime().getTime() > clientBean.getModifiedTime().getTime()) {
                syncNoteData.getSyncNoteList().add(noteBean);
            }


        }

        for (int i = 0; i < requestNote.getDelete().size(); i++) {
            Note clientBean = requestNote.getDelete().get(i);
            Note noteBean = noteMapper.selectByPrimaryKey(clientBean.getNoteId());
            if (noteBean != null) {
                noteBean.setDeleted(true);
                noteMapper.updateByPrimaryKey(noteBean);
            } else {
                Note note = new Note();
                BeanUtils.copyProperties(clientBean, note);
                note.setUserId(userId);
                noteMapper.insertSelective(note);
            }

        }


        if (!isNew) {

            NoteExample noteExample = new NoteExample();
            NoteExample.Criteria criteria = noteExample.createCriteria();
            try {
                criteria.andModifiedTimeGreaterThan(TimeUtils.StringToDate(checkPiont))
                        .andUserIdEqualTo(userId);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            PageHelper.startPage(1, 10);
            List<Note> list = noteMapper.selectByExample(noteExample);


            NoteRespose(syncNoteData.getSyncNoteList(), list);


        }

        return syncNoteData;
    }

    private void NoteRespose(List<Note> noteList, List<Note> list) {
        if (CollectionUtils.isNotEmpty(list)) {

            for (int i = 0; i < list.size(); i++) {

                Note note = list.get(i);

                AttachmentExample attachmentExample = new AttachmentExample();
                AttachmentExample.Criteria attachCriteria = attachmentExample.createCriteria();

                attachCriteria.andNoteIdEqualTo(note.getNoteId());

                List<Attachment> attachmentList = attachmentMapper.selectByExample(attachmentExample);

                if (CollectionUtils.isNotEmpty(attachmentList)) {
                    list.get(i).getAttachmentList().addAll(attachmentList);
                }

                CheckListItemExample checkListItemExample = new CheckListItemExample();
                CheckListItemExample.Criteria checkCriteria = checkListItemExample.createCriteria();

                checkCriteria.andNoteIdEqualTo(note.getNoteId());

                List<CheckListItem> checkList = checkListItemMapper.selectByExample(checkListItemExample);

                if (CollectionUtils.isNotEmpty(checkList)) {
                    list.get(i).getCheckListItemList().addAll(checkList);
                }


            }

            noteList.addAll(list);
        }
    }

    @Override
    public NoteListData getNoteListByPage(String usrId, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<Note> list = noteMapper.selectByExample(new NoteExample());
        PageInfo page = new PageInfo(list);

        NoteListData noteListData = new NoteListData();
        NoteRespose(noteListData.getNoteList(), list);

        noteListData.setIshaveNext(page.isHasNextPage());

        return noteListData;
    }

    private void updateAttachment(Note note) {

        for (int i = 0; i < note.getRequestCheck().getUpdate().size(); i++) {

            CheckListItem clientBean = note.getRequestCheck().getUpdate().get(i);

            CheckListItem checkListItem = checkListItemMapper.selectByPrimaryKey(clientBean.getSid());

            if (clientBean.getModifiedTime().getTime() > checkListItem.getModifiedTime().getTime()) {


                checkListItemMapper.updateByPrimaryKey(clientBean);


            } else if (clientBean.getModifiedTime().getTime() < checkListItem.getModifiedTime().getTime()) {
                note.getRequestCheck().getUpdate().set(i, checkListItem);
            }
        }


    }

    private void saveAttachment(Note note) {

        for (int j = 0; j < note.getRequestAttachment().getAdd().size(); j++) {
            Attachment attachment = new Attachment();

            BeanUtils.copyProperties(note.getRequestAttachment().getAdd().get(j), attachment);
            attachmentMapper.insert(attachment);

        }

        for (int j = 0; j < note.getRequestCheck().getAdd().size(); j++) {
            CheckListItem checkListItem = new CheckListItem();
            BeanUtils.copyProperties(note.getRequestCheck().getAdd().get(j), checkListItem);
            checkListItemMapper.insert(checkListItem);
        }
    }

    private void detelteAttachment(Note note) {


        for (int i = 0; i < note.getRequestAttachment().getDelete().size(); i++) {

            Attachment clientBean = note.getRequestAttachment().getDelete().get(i);
            Attachment attachment = attachmentMapper.selectByPrimaryKey(clientBean.getSid());

            if (attachment != null) {
                attachment.setDeleted(true);
                attachmentMapper.updateByPrimaryKey(attachment);
            } else {
                Attachment attachmentBean = new Attachment();
                BeanUtils.copyProperties(clientBean, attachmentBean);
                attachmentMapper.insertSelective(attachmentBean);
            }

        }

        for (int j = 0; j < note.getRequestCheck().getDelete().size(); j++) {
            CheckListItem clientBean = note.getRequestCheck().getDelete().get(j);

            CheckListItem checkListItem = checkListItemMapper.selectByPrimaryKey(clientBean.getSid());

            if (checkListItem != null) {
                checkListItem.setDeleted(true);
                checkListItemMapper.updateByPrimaryKey(checkListItem);
            } else {
                CheckListItem checkListItemBean = new CheckListItem();
                BeanUtils.copyProperties(clientBean, checkListItemBean);
                checkListItemMapper.insertSelective(checkListItemBean);

            }


        }
    }

}
