package com.xuyazhou.mynote.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017-05-24
 */
public class TimeUtils {


    public static Date StringToDate(String stringTime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long time;

        if (stringTime.startsWith("Optional")) {
            time = new Long(stringTime.substring("Optional(".length(), stringTime.length() - 1));
        } else {
            time = new Long(stringTime);
        }


        String d = format.format(time);

        return format.parse(d);
    }


}
