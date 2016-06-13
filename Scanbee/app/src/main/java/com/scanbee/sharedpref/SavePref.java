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
    //dsrods - dash-seperated-products-string
    public void saveOrderProducts(String dsprods){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("dsprods", dsprods);
        editor.commit();
    }
    public void saveOrderQuants(String dsquants){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("dsquants", dsquants);
        editor.commit();
    }
    public void saveIpAddress(String ipAddress){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ip_address", ipAddress);
        editor.commit();
    }
    // Dummy - 2, live - 1
    public void saveOrderType(String ordertype){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("order_type", ordertype);
        editor.commit();
    }

    public void saveOrderTimeStamp(String timestamp){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("timestamp", timestamp);
        editor.commit();
    }
    public void saveAuthToken(String token){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("auth_token", token);
        editor.commit();
    }

    public void saveAmountPaid(String amount){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("amount_paid", amount);
        editor.commit();
    }

    public void saveUserInfo(String token){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_info", token);
        editor.commit();
    }
    public void saveItemsScanned(int items){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("items_scanned", items);
        editor.commit();
    }


}

