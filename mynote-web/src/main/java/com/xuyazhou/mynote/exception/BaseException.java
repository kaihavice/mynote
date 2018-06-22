package com.xuyazhou.mynote.exception;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017-04-06
 */
public class BaseException extends Exception {

    public static final String SYSTEM_ERROR = "00001";
    public static final String ILLEGAL_OPERATION = "00002";

    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getErrorCode() {
        return super.getMessage();
    }
}
