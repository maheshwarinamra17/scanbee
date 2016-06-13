package com.scanbee.util;

import android.content.Context;
import android.provider.Settings.Secure;

/**
 * Created by kshitij on 5/27/2016.
 */
public class Utils {
    Context mContext;

    // constructor
    public Utils(Context context){
        this.mContext = context;
    }
    public String getDeviceId(){
        String android_id = Secure.getString(mContext.getContentResolver(),
                Secure.ANDROID_ID);
        return android_id;
    }

    public void addTwoNumber(int a,int b){
        int c=a+b;
    }

}
