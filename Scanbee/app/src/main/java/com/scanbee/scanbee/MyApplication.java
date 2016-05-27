package com.scanbee.scanbee;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by kshitij on 5/27/2016.
 */
public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getSimpleName();
    @Override
    public void onCreate() {
        super.onCreate();
        try{
            Fabric.with(this, new Crashlytics());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

