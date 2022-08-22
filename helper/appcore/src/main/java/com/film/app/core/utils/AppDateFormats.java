package com.film.app.core.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class AppDateFormats {
    private static final String JSON_DATE_1 = "MMM dd yyyy hh:mma";
    public static final DateFormat DF_JSON_DATE1 =
        new SimpleDateFormat(JSON_DATE_1, Locale.ENGLISH);
    private static final String JSON_DATE_2 = "dd MMM yyyy hh:mm:ss a";
    public static final DateFormat DF_JSON_DATE2 =
        new SimpleDateFormat(JSON_DATE_2, Locale.ENGLISH);

    /**
     * This date format is returned from new Notification Service.
     * From Subham. (24/08/2016)
     */
    private static final String JSON_DATE_3 = "dd MMM yyyy HH:mm";
    public static final DateFormat DF_JSON_DATE3 =
        new SimpleDateFormat(JSON_DATE_3, Locale.ENGLISH);

    private static final String JSON_DATE_4 = "yyyy-MM-dd'T'HH:mm:ss";
    public static final DateFormat DF_JSON_DATE4 =
        new SimpleDateFormat(JSON_DATE_4, Locale.ENGLISH);

    private static final String JSON_DATE_5 = "dd MMM yyyy hh:mma";
    public static final DateFormat DF_JSON_DATE5 =
        new SimpleDateFormat(JSON_DATE_5, Locale.ENGLISH);

    private static final String JSON_DATE = "dd-MM-yyyy HH:mm:ss";
    public static final DateFormat DF_JSON_DATE = new SimpleDateFormat(JSON_DATE, Locale.ENGLISH);

    private static final String MMM = "MMM";
    public static final DateFormat DF_MONTH = new SimpleDateFormat(MMM, Locale.ENGLISH);
    private static final String DD = "dd";
    public static final DateFormat DF_DAY = new SimpleDateFormat(DD, Locale.ENGLISH);
    private static final String YYYY = "yyyy";
    public static final DateFormat DF_YEAR = new SimpleDateFormat(YYYY, Locale.ENGLISH);
    private static final String TIME = "h:mm a";
    public static final DateFormat DF_TIME = new SimpleDateFormat(TIME, Locale.ENGLISH);
    private static final String TIME_HH = "HH:mm a";
    public static final DateFormat DF_TIME_HH = new SimpleDateFormat(TIME_HH, Locale.ENGLISH);
    private static final String TIME_PRECISE = "HH:mm:ss";
    public static final DateFormat DF_TIME_PRECISE =
        new SimpleDateFormat(TIME_PRECISE, Locale.ENGLISH);

    private static final String date1 = "dd MMM yyyy";
    public static final DateFormat df_Date1 = new SimpleDateFormat(date1, Locale.ENGLISH);

    private static final String date4 = "dd/MM/yyyy";
    public static final DateFormat df_Date4 = new SimpleDateFormat(date4, Locale.ENGLISH);
    private static final String date5 = "dd-MMM-yyyy";
    public static final DateFormat df_Date5 = new SimpleDateFormat(date5, Locale.ENGLISH);
    private static final String date6 = "yyyy-MM-dd";
    public static final DateFormat df_Date6 = new SimpleDateFormat(date6, Locale.ENGLISH);
    private static final String date9 = "dd/MM/yyyy HH:mm:ss";
    public static final DateFormat df_Date9 = new SimpleDateFormat(date9, Locale.ENGLISH);
    private static final String date11 = "MMM yy";
    public static final DateFormat df_Date11 = new SimpleDateFormat(date11, Locale.ENGLISH);
    private static final String date12 = "MM/dd/yyyy";
    public static final DateFormat df_Date12 = new SimpleDateFormat(date12, Locale.ENGLISH);
    private static final String date13 = "yyyy-MM-dd HH:mm";
    public static final DateFormat df_Date13 = new SimpleDateFormat(date13, Locale.ENGLISH);

    private static final String date20 = "dd-MM-yyyy  HH:mm a";
    public static final DateFormat df_date20 = new SimpleDateFormat(date20, Locale.ENGLISH);

    private static final String date22 = "dd MMM yyyy,hh:mm a";
    public static final DateFormat df_date_22 = new SimpleDateFormat(date22, Locale.ENGLISH);

    private static final String date25 = "dd-MMM";
    public static final DateFormat df_date_25 = new SimpleDateFormat(date25, Locale.ENGLISH);

    private static final String time1 = "hh:mm";
    public static final DateFormat DF_TIME1 = new SimpleDateFormat(time1, Locale.ENGLISH);

    private static final String time2 = "hh:mm a";
    public static final DateFormat DF_TIME2 = new SimpleDateFormat(time2, Locale.ENGLISH);
}
