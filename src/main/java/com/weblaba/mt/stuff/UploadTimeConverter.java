package com.weblaba.mt.stuff;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public final class UploadTimeConverter {
    public static final long ONE_SEC_MS = 1000L;
    public static final long ONE_MINUTE_MS = ONE_SEC_MS * 60;
    public static final long ONE_HOUR_MS = ONE_MINUTE_MS * 60;
    public static final long ONE_DAY_MS = ONE_HOUR_MS * 24;
    public static final long ONE_WEEK_MS = ONE_DAY_MS * 7;

    private UploadTimeConverter() { }

    public static String convert(Timestamp time) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        long diff = now.getTime() - time.getTime();
        long lDiff = 0;
        String date = "";

        if(diff < ONE_MINUTE_MS) {
            lDiff = diff / ONE_SEC_MS;
            date += lDiff + " second";
        } else if(diff >= ONE_MINUTE_MS && diff < ONE_HOUR_MS) {
            lDiff = diff / ONE_MINUTE_MS;
            date += lDiff + " minute";
        } else if (diff >= ONE_HOUR_MS && diff < ONE_DAY_MS) {
            lDiff = diff / ONE_HOUR_MS;
            date += lDiff + " hour";
        } else if (diff >= ONE_DAY_MS && diff < ONE_WEEK_MS) {
            lDiff = diff / ONE_DAY_MS;
            date += lDiff + " day";
        } else {
            date = new SimpleDateFormat("d MMMM yyyy").format(time);
            return date;
        }

        if(lDiff > 1) {
            date += "s";
        }

        return date + " ago";
    }
}