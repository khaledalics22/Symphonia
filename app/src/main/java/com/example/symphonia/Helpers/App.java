package com.example.symphonia.Helpers;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * this class is only used to get a static reference for the context
 *
 * @author islamahmed1092
 * @version 1.0
 */
public class App extends Application{

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}
