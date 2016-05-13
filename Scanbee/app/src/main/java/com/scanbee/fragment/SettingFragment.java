package com.scanbee.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
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

/**
 * Created by kshitij on 5/6/2016.
 */
public class SettingFragment extends Fragment {
    View viewMain;
    Button saveBtn;
    MaterialEditText ipAddressET,orderType;
    SavePref savePref;
    ReadPref readPref;

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
