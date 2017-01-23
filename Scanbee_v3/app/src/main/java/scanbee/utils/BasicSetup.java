package scanbee.utils;

import android.app.Activity;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import scanbee.activities.R;

/**
 * Created by namra on 13/01/17.
 */

public class BasicSetup {

    Activity mActivity;
    Typeface MontEL;
    Typeface MontL;
    Typeface MontR;
    Typeface MontT;
    Typeface NuniEL;
    Typeface NuniL;
    Typeface NuniR;
    Typeface NuniSb;


    public BasicSetup(Activity activity){
        this.mActivity =  activity;
        this.MontEL = Typeface.createFromAsset(mActivity.getAssets(), Constants.MONT_EL);
        this.MontL = Typeface.createFromAsset(mActivity.getAssets(), Constants.MONT_L);
        this.MontR = Typeface.createFromAsset(mActivity.getAssets(), Constants.MONT_R);
        this.MontT = Typeface.createFromAsset(mActivity.getAssets(), Constants.MONT_T);
        this.NuniEL = Typeface.createFromAsset(mActivity.getAssets(), Constants.NUNI_EL);
        this.NuniL = Typeface.createFromAsset(mActivity.getAssets(), Constants.NUNI_L);
        this.NuniR = Typeface.createFromAsset(mActivity.getAssets(), Constants.NUNI_R);
        this.NuniSb = Typeface.createFromAsset(mActivity.getAssets(), Constants.NUNI_SB);
    }

    public Typeface getMontEL() {
        return MontEL;
    }

    public Typeface getMontL() {
        return MontL;
    }

    public Typeface getMontR() {
        return MontR;
    }

    public Typeface getMontT() {
        return MontT;
    }

    public Typeface getNuniEL() {
        return NuniEL;
    }

    public Typeface getNuniL() {
        return NuniL;
    }

    public Typeface getNuniR() {
        return NuniR;
    }

    public Typeface getNuniSb() {
        return NuniSb;
    }

    public void setupCustomToolbar(String title) {
        View contentView =  mActivity.findViewById(android.R.id.content);
        TextView customTitle = (TextView) contentView.findViewById(R.id.custom_title);
        customTitle.setTypeface(NuniR);
        customTitle.setText(title);
    }

    public int getScreenHeight(){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        return height;
    }
}
