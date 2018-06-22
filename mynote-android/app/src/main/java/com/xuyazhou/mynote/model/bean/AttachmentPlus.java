package com.xuyazhou.mynote.model.bean;

import com.xuyazhou.mynote.model.db.AttachMent;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017/6/26
 */
public class AttachmentPlus {
    AttachMent attachMent;
    String base64;

    public AttachmentPlus(AttachMent attachMent, String base64) {
        this.attachMent = attachMent;
        this.base64 = base64;
    }

    public AttachMent getAttachMent() {
        return attachMent;
    }

    public void setAttachMent(AttachMent attachMent) {
        this.attachMent = attachMent;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }
}
