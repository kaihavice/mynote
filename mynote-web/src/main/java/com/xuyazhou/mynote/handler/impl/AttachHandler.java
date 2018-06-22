package com.xuyazhou.mynote.handler.impl;

import com.xuyazhou.mynote.handler.IAttachHandler;
import com.xuyazhou.mynote.mapper.AttachmentMapper;
import com.xuyazhou.mynote.model.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017-05-15
 */
@Service
public class AttachHandler implements IAttachHandler {

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Override
    public Attachment getAttachById(String id) {
        return null;
    }

    @Override
    public int UpdateAttachment(Attachment attachmentBean) {
        return 0;
    }

    @Override
    public int SaveAttachment(Attachment attachmentBean) {
        return 0;
    }
}
