package com.xuyazhou.mynote.exception;

public class DefineException extends Exception {
    /**
     */
    private static final long serialVersionUID = 4111105820499963520L;
    private String message;

    public String getErrorCode() {
        return "O1000";
    }

    public String getMessage() {
        return message;
    }

    public DefineException(String message) {
        this.message = message;
    }
}
