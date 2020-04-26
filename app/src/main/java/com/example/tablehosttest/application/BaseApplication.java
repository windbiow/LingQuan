package com.example.tablehosttest.application;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getBaseContext();
    }

    public static Context getAppContext(){
        return mContext;
    }
}
