package com.xuyazhou.mynote.Utils;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

/**
 */
public class MessageUtil {

    public static String message(String code, MessageSource messageSource) {
        return message(code, messageSource, (Object[]) null);
    }

    public static String message(String code, MessageSource messageSource, Object... params) {
        return message(code, messageSource, Locale.CHINA, params);
    }

    public static String message(String code, MessageSource messageSource, Locale locale, Object... params) {
        return message(code, messageSource, "", locale, params);
    }



    public static String message(String code, String message, Locale locale, Object... params) {
        return message(code, message, locale, params);
    }

    public static String message(String code, MessageSource messageSource, String defaultMessage, Locale locale, Object... params) {
        if (messageSource == null) {
            return code;
        }
        try {
            return messageSource.getMessage(code, params, defaultMessage, locale);
        } catch (NoSuchMessageException e) {
            return code;
        }
    }
}
