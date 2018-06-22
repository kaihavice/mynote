package com.xuyazhou.mynote.Utils;

public enum ResultEnumImp implements ResultEnum {

    OK(true, "00000", "成功");

    /** 状态 **/
    private Boolean success;

    /** 错误码 **/
    private String errCode;

    /** 消息 **/
    private String message;

    /** 构造函数 **/
    ResultEnumImp(Boolean success, String errCode, String message) {
        this.success = success;
        this.errCode = errCode;
        this.message = message;
    }



    public String getErrCode() {
        return errCode;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getSuccess() {
        return success;
    }
}
