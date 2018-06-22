package com.xuyazhou.mynote.exception;

public class StateException extends Exception {
    /**
     */
    private static final long serialVersionUID = 5150276727985534022L;

    public StateException(String errorCode) {
        super(errorCode);
    }
}
