package com.xuyazhou.mynote.model.bean;

import com.xuyazhou.mynote.common.config.ErrorCode;
import com.xuyazhou.mynote.common.utils.StringUtil;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * <p>
 * Date: 2016-05-30
 */
public class Response<T> {
    public boolean success;
    public String errCode;
    public String message;
    public T data;

    public boolean isTokenExpired() {
        return StringUtil.equals(errCode, ErrorCode.TOKENEXPIRED2) ;
    }


}
