package com.scanbee.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.scanbee.dialog.CustomNumpad;
import com.scanbee.scanbee.MainActivity;
import com.scanbee.scanbee.R;
import com.scanbee.sharedpref.ReadPref;
import com.scanbee.sharedpref.SavePref;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * Created by namra on 4/23/2016.
 */
public class GenerateOrderidFragment extends Fragment {
    private View viewMain;
    SavePref savePref;
    ReadPref readPref;
    ProgressDialog progressDialog;
    TextView idTv, totalIitemSelectedTv, handlerNameTv, storeName, circleImgTv, orderId, itemScan;
    Button proceed_btn;
    Activity activity;

    Boolean addMoreFlag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewMain = inflater.inflate(R.layout.sb_add_product_fragment,
                container, false);
        activity = getActivity();
        savePref = new SavePref(getActivity());
        readPref = new ReadPref(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        addMoreFlag = false;
        CustomNumpad customNumpad =  new CustomNumpad(activity);
        customNumpad.show();
        setupActionBar();
        return viewMain;
    }

    private void setupActionBar() {
        ActionBar actionBar = ((MainActivity) getActivity())
                .getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(false);
        final Activity activity = getActivity();
        if (activity != null) {
            Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
            TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            mTitle.setText(R.string.scanning);
        }
    }

    public void setUpUi() {

//        idTv = (TextView) viewMain.findViewById(R.id.orderIdTv);
//        totalIitemSelectedTv = (TextView) viewMain.findViewById(R.id.itemScannedTv);
//        handlerNameTv = (TextView) viewMain.findViewById(R.id.custNameTv);
//        storeName = (TextView) viewMain.findViewById(R.id.storeTv);
//        circleImgTv = (TextView) viewMain.findViewById(R.id.circleImgTv);
//        orderId = (TextView) viewMain.findViewById(R.id.orderId);
//        itemScan = (TextView) viewMain.findViewById(R.id.itemScannedTxtTv);
//        loader = (AVLoadingIndicatorView) viewMain.findViewById(R.id.orderLoader);
//        shopIcon = (ImageView) viewMain.findViewById(R.id.shopIcon);
//        loader.setVisibility(View.GONE);
//        shopIcon.setVisibility(View.VISIBLE);
//        continue_btn = (Button) viewMain.findViewById(R.id.continue_btn);
//        continue_btn.setEnabled(true);
//        continue_btn.setBackgroundResource(R.drawable.button_blue_color);
//        continue_btn.setText(R.string.start_scanning);
//
//        Typeface Roboto = Typeface.createFromAsset(getResources().getAssets(), getString(R.string.roboto_font));
//        Typeface RobotoThin = Typeface.createFromAsset(getResources().getAssets(), getString(R.string.roboto_thin));
//        Typeface RobotoMed = Typeface.createFromAsset(getResources().getAssets(), getString(R.string.roboto_med));
//        Typeface NotoSans = Typeface.createFromAsset(getResources().getAssets(), getString(R.string.noto_sans));
//
//        idTv.setTypeface(NotoSans);
//        totalIitemSelectedTv.setTypeface(NotoSans);
//        continue_btn.setTypeface(Roboto);
//        circleImgTv.setTypeface(RobotoMed);
//        handlerNameTv.setTypeface(RobotoMed);
//        orderId.setTypeface(NotoSans);
//        itemScan.setTypeface(NotoSans);
//        storeName.setTypeface(Roboto);
//
//        if (addMoreFlag) {
//            idTv.setText("SBO" + readPref.getOrderId());
//            totalIitemSelectedTv.setText(readPref.getItemsScanned() + " + X");
//        }


        proceed_btn = (Button) viewMain.findViewById(R.id.proceed_button);



    }


}