package com.scanbee.sharedpref;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SavePref {

    Context context;
    SharedPreferences preferences;
    String myprefs="scanbee";
    int mode = Activity.MODE_PRIVATE;

    public SavePref(Context context){
        this.context = context;
        preferences = context.getSharedPreferences(myprefs, mode);
    }
    public void saveOrderId(int orderId){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("order_id",orderId);
        editor.commit();
    }
    public void saveIpAddress(String ipAddress){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ip_address",ipAddress);
        editor.commit();
    }

}
