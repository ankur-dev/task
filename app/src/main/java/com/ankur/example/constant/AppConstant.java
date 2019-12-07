package com.ankur.example.constant;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AppConstant {
    public static final int ORIENTATION_PORTRAIT = 1;
    public static final int ORIENTATION_LANDSCAPE = 2;
    public static final int API = 3;

    public interface BundleKeys {
        String IS_DATE_SELECTED = "IS_DATE_SELECTED";
        String IS_START_TIME_SELECTED = "IS_START_TIME_SELECTED";
        String IS_END_TIME_SELECTED = "IS_END_TIME_SELECTED";
        String DESCRIPTION = "DESCRIPTION";
        String CURRENT_DATE = "CURRENT_DATE";
        String START_TIME = "START_TIME";
        String END_TIME = "END_DATE";

        String MEETING_LIST = "MEETING_LIST";
        String IS_MEETING_LIST_LOADED = "IS_MEETING_LIST_LOADED";
    }


}
