package com.guide.zelda.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public final class TimeUtils {

    private TimeUtils() {
    }

    public static long getTimeDiff(String timeZone) {
        return TimeZone.getDefault().getRawOffset()
            - TimeZone.getTimeZone(timeZone).getRawOffset();
    }

    public static String getTimeWithTimeZone(String timeZone) {
        long nowTime = new Date().getTime();
        long newTime = nowTime - getTimeDiff(timeZone);
        Date d = new Date(newTime);
        return new SimpleDateFormat("HH:mm").format(d);
    }

    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    public static String formatMill2DateString(long mill, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(mill);
    }
}
