package com.xuyazhou.mynote.exception;

/**
 * @author Jason
 */
public class ParameterException extends Exception {

    private static final long serialVersionUID = 7838310133095481091L;

    private String message;

    public String getErrorCode() {
        return "PARAMETER ERROR";
    }

    public String getMessage() {
        return message;
    }

    public ParameterException(String message) {
        this.message = message;
    }

}
