package com.scanbee.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.scanbee.dialog.ToastCustom;
import com.scanbee.scanbee.MainActivity;
import com.scanbee.scanbee.R;
import com.scanbee.sharedpref.ReadPref;
import com.scanbee.sharedpref.SavePref;

import java.util.Locale;

/**
 * Created by kshitij on 5/6/2016.
 */
public class SettingFragment extends Fragment {
    View viewMain;
    Button saveBtn;
    MaterialEditText ipAddressET,orderType;
    SavePref savePref;
    ReadPref readPref;
    TextView languageHi,languageEn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewMain=inflater.inflate(R.layout.setting_fragment,null,false);
        setUpUi();
        setupActionBar();
        return viewMain;
    }
    public void setUpUi(){
        savePref = new SavePref(getActivity());
        readPref = new ReadPref(getActivity());
        ipAddressET = (MaterialEditText) viewMain.findViewById(R.id.ipAddressET);
        orderType = (MaterialEditText) viewMain.findViewById(R.id.order_type);
        languageHi = (TextView) viewMain.findViewById(R.id.HindiBtnBtn);
        languageEn = (TextView) viewMain.findViewById(R.id.EnglishBtnBtn);
        setupUI();
        ipAddressET.setText(readPref.getIpAddress());
        orderType.setText(readPref.getOrderType());
        saveBtn = (Button)viewMain.findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastCustom customToast = new ToastCustom(getActivity());
                if (ipAddressET.getText().toString().trim().isEmpty()) {
                    customToast.show("Please fill IP address.");
                } else {
                    savePref.saveIpAddress(ipAddressET.getText().toString().trim());
                    System.out.println(readPref.getIpAddress());
                    customToast.show("Successfully saved.");
                }

                if (orderType.getText().toString().trim().isEmpty()) {
                    customToast.show("Please fill Order Type");
                } else {
                    savePref.saveOrderType(orderType.getText().toString().trim());
                    customToast.show("Successfully saved.");
                }
            }
        });

        languageHi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePref.saveLangPref("hi");
                setLocale("hi");
            }
        });
        languageEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePref.saveLangPref("en");
                setLocale("en");
            }
        });
    }

    public void setupUI(){
        String lang = readPref.getLangPref();
        if (lang == "hi"){
            int pL = languageHi.getPaddingLeft();
            int pT = languageHi.getPaddingTop();
            int pR = languageHi.getPaddingRight();
            int pB = languageHi.getPaddingBottom();
            languageHi.setBackground(getResources().getDrawable(R.drawable.button_blue_color));
            languageHi.setPadding(pL, pT, pR, pB);
        } else{
            int pL = languageEn.getPaddingLeft();
            int pT = languageEn.getPaddingTop();
            int pR = languageEn.getPaddingRight();
            int pB = languageEn.getPaddingBottom();
            languageEn.setBackground(getResources().getDrawable(R.drawable.button_blue_color));
            languageEn.setPadding(pL, pT, pR, pB);
        }

    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent intent = getActivity().getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        getActivity().overridePendingTransition(0, 0);
        getActivity().finish();
        getActivity().overridePendingTransition(0, 0);
        startActivity(intent);
        ToastCustom customToast = new ToastCustom(getActivity());
        customToast.show(getString(R.string.lang_change) +" " + myLocale.getDisplayName());
    }

    private void setupActionBar() {
        ActionBar actionBar = ((MainActivity) getActivity())
                .getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(false);

        Activity activity = getActivity();
        if(activity != null){
            Toolbar toolbar = (Toolbar)activity.findViewById(R.id.toolbar);
            TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            ImageView cancelButton = (ImageView) activity.findViewById(R.id.cancelorder);
            ImageView addMoreButton = (ImageView) activity.findViewById(R.id.addmore);
            ImageView newOrder = (ImageView) activity.findViewById(R.id.neworder);
            cancelButton.setVisibility(View.GONE);
            addMoreButton.setVisibility(View.GONE);
            newOrder.setVisibility(View.GONE);

            mTitle.setText(R.string.settings);
        }
    }
}
