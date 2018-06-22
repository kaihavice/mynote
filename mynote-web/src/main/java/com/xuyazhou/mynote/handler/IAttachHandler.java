package com.xuyazhou.mynote.handler;


import com.xuyazhou.mynote.model.Attachment;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017-05-15
 */
public interface IAttachHandler {

    Attachment getAttachById(String id);

    int UpdateAttachment(Attachment attachmentBean);

    int SaveAttachment(Attachment attachmentBean);
}
