package com.scanbee.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.scanbee.scanbee.MainActivity;
import com.scanbee.scanbee.R;

/**
 * Created by kshitij on 5/5/2016.
 */
public class PaymentGetWayFragment extends Fragment {
   View viewMain;
    Button continueToInvoiceBtn;
    TextView amountPaid, amountPaidTxt, storeCredit, storeCreditTxt, itemsBought, itemsBoughtTxt,custName,custInfo;
    Switch saveSwitch;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewMain=inflater.inflate(R.layout.payment_getway_fragment,null,false);
        continueToInvoiceBtn=(Button)viewMain.findViewById(R.id.continueToInvoiceBtn);
        continueToInvoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new InvoiceFragment()).commit();
            }
        });

        amountPaid = (TextView)viewMain.findViewById(R.id.amount_paidTv);
        amountPaidTxt = (TextView)viewMain.findViewById(R.id.amount_paidTxtTv);
        storeCredit = (TextView)viewMain.findViewById(R.id.store_creditTv);
        storeCreditTxt = (TextView)viewMain.findViewById(R.id.store_creditsTxtTv);
        itemsBought = (TextView)viewMain.findViewById(R.id.item_boughtTv);
        itemsBoughtTxt = (TextView)viewMain.findViewById(R.id.item_boughtTxtTv);
        custName = (TextView)viewMain.findViewById(R.id.cust_nameTv);
        custInfo = (TextView)viewMain.findViewById(R.id.typeTv);
        saveSwitch = (Switch)viewMain.findViewById(R.id.save_switch);


        Typeface RobotoMed=Typeface.createFromAsset(getResources().getAssets(),getString(R.string.roboto_med));
        Typeface NotoSans=Typeface.createFromAsset(getResources().getAssets(),getString(R.string.noto_sans));
        Typeface Roboto=Typeface.createFromAsset(getResources().getAssets(),getString(R.string.roboto_font));
        continueToInvoiceBtn.setTypeface(Roboto);
        amountPaid.setTypeface(NotoSans);
        amountPaidTxt.setTypeface(NotoSans);
        storeCredit.setTypeface(NotoSans);
        storeCreditTxt.setTypeface(NotoSans);
        itemsBought.setTypeface(NotoSans);
        itemsBoughtTxt.setTypeface(NotoSans);
        custName.setTypeface(RobotoMed);
        custInfo.setTypeface(NotoSans);
        saveSwitch.setTypeface(RobotoMed);

        amountPaid.setText(getActivity().getString(R.string.Rs)+"54,321");
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

        Activity activity = getActivity();
        if(activity != null){
            Toolbar toolbar = (Toolbar)activity.findViewById(R.id.toolbar);
            TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            ImageView cancelButton = (ImageView) activity.findViewById(R.id.cancelorder);
            ImageView addMoreButton = (ImageView) activity.findViewById(R.id.addmore);

            cancelButton.setVisibility(View.VISIBLE);
            addMoreButton.setVisibility(View.VISIBLE);
            mTitle.setText(R.string.payment);
        }
    }
}
