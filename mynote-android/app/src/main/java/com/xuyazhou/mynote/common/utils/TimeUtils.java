package com.xuyazhou.mynote.common.utils;

import android.text.format.Time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 时间相关辅助工具
 */
public class TimeUtils {
    public static final long MILLIS_IN_A_DAY = 24 * 60 * 60 * 1000; // 一天的毫秒数

    public static final String PATTERN_Y_SLASH_M_SLASH_D_H_COMMA_M = "yyyy/MM/dd HH:mm";
    public static final String PATTERN_Y_SLASH_M_SLASH_D = "yyyy/MM/dd";
    public static final String PATTERN_Y_DASH_M_DASH_D = "yyyy-MM-dd";
    public static final String PATTERN_D_SLASH_M_SLASH_Y = "dd/MM/yyyy";

    /**
     * 获取格式化时间
     */
    public static String getFormatTime(String milliseconds, String pattern) {
        return getFormatTime(Long.valueOf(milliseconds), pattern);
    }

    /**
     * 获取格式化时间
     */
    public static String getFormatTime(Long milliseconds, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern,
                Locale.getDefault());

        return sdf.format(new Date(milliseconds));
    }

    /**
     * 获取格式化时间
     *
     * @param milliseconds
     * @param strategy     格式化策略
     * @return
     */
    public static String getFormatTime(long milliseconds,
                                       IDateFormatStrategy strategy) {
        if (strategy == null)
            return null;

        return strategy.formatTime(milliseconds);
    }

    /**
     * 1h=3600s
     */
    private final static int SECOND_IN_HOUR = 3600;
    /**
     * 1m=60s
     */
    private final static int SECOND_IN_MINUTE = 60;
    /**
     * 1h=60m
     */
    private final static int MINUTE_IN_HOUR = 60;

    /**
     * 格式化视频录制时间（时间格式：hh:mm:ss'）
     *
     * @param seconds 视频录制时间（单位秒）
     * @return
     */
    public static String getFormatRecordTime(long seconds) {
        long hh = seconds / SECOND_IN_HOUR;
        long mm = seconds % SECOND_IN_HOUR;
        long ss = mm % SECOND_IN_MINUTE;
        mm = mm / SECOND_IN_MINUTE;

        return (mm == 0 ? "" : (mm < 10 ? "0" + mm : mm) + ":")
                + (ss == 0 ? "" : (ss < 10 ? "0" + ss : ss) + "'");
    }

    /**
     * 计算两个日期之间相差多少天
     */
    public static int getDateDifference(long millis1, long millis2) {
        long millisFormer;
        long millisLatter;

        // 保证millisFormer保存大时间，millisLatter保存小时间
        if (millis1 >= millis2) {
            millisFormer = millis1;
            millisLatter = millis2;
        } else {
            millisFormer = millis2;
            millisLatter = millis1;
        }

		/*
         * 因为昨天与今天两个日期之间可能相差几秒，可能相差几十小时 比如：2014/12/25 23:59:50 和 2014/12/26
		 * 00:00:01 比如：2014/12/25 00:00:01 和 2014/12/26 23:59:50
		 * 因此，由日期生成的Calendar对象必须将时、分、秒参数清0，否则将影响最终计算结果
		 */
        Calendar calFormer = Calendar.getInstance();
        calFormer.setTime(new Date(millisFormer));
        calFormer.set(Calendar.HOUR_OF_DAY, 0);
        calFormer.set(Calendar.MINUTE, 0);
        calFormer.set(Calendar.SECOND, 0);

        Calendar calLatter = Calendar.getInstance();
        calLatter.setTime(new Date(millisLatter));
        calLatter.set(Calendar.HOUR_OF_DAY, 0);
        calLatter.set(Calendar.MINUTE, 0);
        calLatter.set(Calendar.SECOND, 0);

        // 计算两个日期相差天数
        return (int) ((calFormer.getTimeInMillis() - calLatter
                .getTimeInMillis()) / TimeUtils.MILLIS_IN_A_DAY);
    }


    public static String getRelativeTimeSpanString(long time) {

        Date startDate = Calendar.getInstance().getTime();

        // if (startDate == null || endDate == null) {
        // return null;
        // }

        // String str = context.getResources().getQuantityString(resId,
        // (int) conunt);

        long timeLong = System.currentTimeMillis() / 1000 - time;
        if (timeLong < 60 * 1000)

            return timeLong / 1000 + " s ago";
        else if (timeLong < 60 * 60 * 1000) {
            timeLong = timeLong / 1000 / 60;
            return timeLong + " 分钟前";
        } else if (timeLong < 60 * 60 * 24 * 1000) {
            timeLong = timeLong / 60 / 60 / 1000;
            return timeLong + " 小时前";
        } else if (timeLong < 60 * 60 * 24 * 1000 * 7) {
            timeLong = timeLong / 1000 / 60 / 60 / 24;
            return timeLong + " 天前";
        } else if (timeLong < 60 * 60 * 24 * 1000 * 7 * 4) {
            timeLong = timeLong / 1000 / 60 / 60 / 24 / 7;
            return timeLong + " 周前";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("MM.dd");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            return sdf.format(time);
        }
    }


    public static boolean isToday(long when) {
        Time time = new Time();
        time.set(when);

        int thenYear = time.year;
        int thenMonth = time.month;
        int thenMonthDay = time.monthDay;

        time.set(System.currentTimeMillis() / 1000);
        return (thenYear == time.year)
                && (thenMonth == time.month)
                && (thenMonthDay == time.monthDay);
    }

    public static boolean isThisYear(long when) {
        Time time = new Time();
        time.set(when);

        int thenYear = time.year;


        time.set(System.currentTimeMillis() / 1000);
        return thenYear == time.year;
    }
}
