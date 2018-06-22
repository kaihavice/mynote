package com.xuyazhou.mynote.exception;

/**
 */
public class LoginException extends BaseException {

    private static final long serialVersionUID = 3921446124615701922L;

    public static final String NO_PERMISSION = "L01001";

    public LoginException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public LoginException(String errorCode) {
        super(errorCode);
    }

    public LoginException() {
        super();
    }

    public String getErrorCode() {
        return super.getMessage();
    }
}
