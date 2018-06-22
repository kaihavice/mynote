package com.xuyazhou.mynote.Utils;

public interface ResultEnum {

    /** 是否成功 **/
    Boolean getSuccess();

    /** 获取错误码 **/
    String getErrCode();

    /** 获取错信息 **/
    String getMessage();

}
