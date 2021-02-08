package com.scanbee.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.provider.Settings.Secure;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
    public Double doublePrecision(Double number, int pres){
        Double truncatedDouble = new BigDecimal(number)
                .setScale(pres, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
        return truncatedDouble;
    };

    public void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = ((Activity) mContext).getCurrentFocus();
        if (v == null)
            return;
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
    public static String getCurrentTimeStamp(){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTimeStamp = dateFormat.format(new Date()); // Find todays date
            return currentTimeStamp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void activitySetLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = mContext.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }
}
