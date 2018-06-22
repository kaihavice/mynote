package com.xuyazhou.mynote.exception;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017-04-06
 */
public class MemberException extends BaseException {

    public MemberException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public MemberException(String errorCode) {
        super(errorCode);
    }

    // 账户被禁用
    public static final String ACCOUNT_IS_DISABLED = "M00013";

    // 用户名与密码不匹配
    public static final String LOGIN_NAME_OR_PWD_ERROR = "M00036";

    //密码为空
    public static final String ISNOTBLANK_ERROR = "M00003";

    //token错误
    public static final String TOKEN_ERROR = "M00009";
    //token错误
    public static final String ACCOUT_IS_REGIST = "M00010";
    public static final String ACCOUT_ISNOT_ESIST = "M00011";


    // accesstoken失效，请重新登录
    public static final String ACCESSTOKEN_ERROR = "M00014";

    public static final String PASSWORD_ERROR = "M00002";

    public MemberException() {
        super();
    }

    public String getErrorCode() {
        return super.getMessage();
    }
}
