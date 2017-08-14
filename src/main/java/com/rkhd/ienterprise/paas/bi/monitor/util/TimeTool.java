package com.rkhd.ienterprise.paas.bi.monitor.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("all")
/**
 * Created by Administrator on 2017/7/10.
 */
public class TimeTool {

    private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";
    @SuppressWarnings("rawtypes")
    private static ThreadLocal threadLocal = new ThreadLocal() {
        protected synchronized Object initialValue() {
            return new SimpleDateFormat(dateFormat);
        }
    };

    public static DateFormat getDateFormat() {
        return (DateFormat) threadLocal.get();
    }


    public static String getLongDate() {
        return getDateFormat().format(new Date());
    }


    public static String getCurDate() {
        return getDateFormat().format(new Date()).substring(0, 10);
    }

}