package com.example.dogfinder;

import android.app.Application;

public class MyApplication extends Application {
    private static DatabaseHelper dbAdapter;

    public static DatabaseHelper getDbAdapter() {
        return dbAdapter;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        dbAdapter.getInstance(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        //terminate something else here if you want
    }
}