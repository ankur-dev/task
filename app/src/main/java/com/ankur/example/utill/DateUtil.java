package com.ankur.example.utill;

import com.ankur.example.constant.AppConstant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static Calendar getPreviousDate(Calendar currentDate) {
        currentDate.add(Calendar.DATE, -1);
        return currentDate;
    }

    public static Calendar getNextDate(Calendar currentDate) {
        currentDate.add(Calendar.DATE, +1);
        return currentDate;

    }

    public static String getStringFormatFor(int typeOfStringNeeded, Calendar calendar) {
        SimpleDateFormat dateFormatForPortrait = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        SimpleDateFormat dateFormatForLandscape = new SimpleDateFormat("EEEE, dd-MM-yyyy", Locale.getDefault());
        SimpleDateFormat dateFormatForApi = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String dateFormat = null;

        switch (typeOfStringNeeded) {
            case AppConstant.ORIENTATION_LANDSCAPE:
                dateFormat = dateFormatForLandscape.format(calendar.getTime());
                break;
            case AppConstant.ORIENTATION_PORTRAIT:
                dateFormat = dateFormatForPortrait.format(calendar.getTime());
                break;
            case AppConstant.API:
                dateFormat = dateFormatForApi.format(calendar.getTime());
                break;
        }
        return dateFormat;
    }

}
