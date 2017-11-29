package com.example.phanv.virus.Control;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by phanv on 26-Oct-17.
 */

public class ProcessData {

    public String getCurrentTimeByFormat(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        return dateFormat.format(date).toString();
    }
    public String getTimeByFormat(String format,Long date) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date).toString();
    }
}
