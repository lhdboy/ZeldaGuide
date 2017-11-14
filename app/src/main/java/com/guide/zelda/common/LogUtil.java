package com.guide.zelda.common;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public final class LogUtil {

    private LogUtil() {

    }

    public static void init() {
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static void v(String tag, String msg) {
        Logger.v(tag, msg);
    }

    public static void i(String tag, String msg) {
        Logger.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        Logger.d(tag, msg);
    }

    public static void w(String tag, String msg) {
        Logger.w(tag, msg);
    }

    public static void w(String tag, String msg, Throwable tr) {
        Logger.w(tag, msg, tr);
    }

    public static void e(String tag, String msg) {
        Logger.e(tag, msg);
    }

    public static void e(String tag, Throwable tr) {
       Logger.e(tag,tr.getMessage(), tr);
    }

    public static void e(String tag, String msg, Throwable tr) {
        Logger.e(tag,msg, tr);
    }

    public static void json(String tag, String json) {
        Logger.json(json);
    }

    public static void xml(String tag, String xml) {
        Logger.xml(xml);
    }
}
