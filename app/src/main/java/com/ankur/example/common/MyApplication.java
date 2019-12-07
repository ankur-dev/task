package com.ankur.example.common;

import android.app.Application;



/**
 * Created by ankur.khandelwal on 26-07-2017.
 */

public class MyApplication extends Application {

    private static MyApplication mApplication;

    /**
     * Get Application Context
     *
     * @return mApplication
     */
    public static MyApplication getApplicationInstance() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // initialized application class variable
        mApplication = this;

    }
}
