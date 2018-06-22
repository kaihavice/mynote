package com.xuyazhou.mynote.common.utils;


public interface IDateFormatStrategy {
    /**
     * 格式化日期
     *
     * @param millisecond 日期
     * @return
     */
	String formatTime(long millisecond);
}
