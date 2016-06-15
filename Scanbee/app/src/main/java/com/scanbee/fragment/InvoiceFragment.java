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
import android.widget.ImageView;
import android.widget.TextView;

import com.scanbee.scanbee.MainActivity;
import com.scanbee.scanbee.R;
import com.scanbee.sharedpref.ReadPref;
import com.scanbee.util.Utils;

/**
 * Created by kshitij on 5/5/2016.
 */
public class InvoiceFragment extends Fragment {
    View viewMain;
    TextView amountPaid,amountPaidTxt,orderId,orderIdTxt,custName,custInfo,circleImgTv;
    ReadPref readPref;
    Utils utilLib;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewMain=inflater.inflate(R.layout.invoice_frgment,null,false);
        utilLib = new Utils(getActivity());
        utilLib.hideKeyboard();
        amountPaid = (TextView)viewMain.findViewById(R.id.amount_paidin);
        amountPaidTxt = (TextView)viewMain.findViewById(R.id.amountPaidInTxt);
        orderId = (TextView)viewMain.findViewById(R.id.orderIdIn);
        orderIdTxt = (TextView)viewMain.findViewById(R.id.OrderIdInTxt);
        custName = (TextView)viewMain.findViewById(R.id.cust_nameTv);
        custInfo = (TextView)viewMain.findViewById(R.id.typeTv);
        circleImgTv = (TextView)viewMain.findViewById(R.id.circleImgTv);

        readPref = new ReadPref(getActivity());

        Typeface RobotoMed=Typeface.createFromAsset(getResources().getAssets(),getString(R.string.roboto_med));
        Typeface NotoSans=Typeface.createFromAsset(getResources().getAssets(),getString(R.string.noto_sans));
        Typeface Roboto=Typeface.createFromAsset(getResources().getAssets(),getString(R.string.roboto_font));
        Typeface RobotoThin=Typeface.createFromAsset(getResources().getAssets(), getString(R.string.roboto_thin));
        amountPaid.setTypeface(NotoSans);
        amountPaidTxt.setTypeface(NotoSans);
        orderId.setTypeface(NotoSans);
        orderIdTxt.setTypeface(NotoSans);
        custName.setTypeface(RobotoMed);
        custInfo.setTypeface(NotoSans);
        String[] customer_info = readPref.getCustInfo().split(";");
        custName.setText(customer_info[0]);
        custInfo.setText(customer_info[1]);
        circleImgTv.setText(String.valueOf(customer_info[0].toString().charAt(0)));
        circleImgTv.setTypeface(RobotoThin);
        amountPaid.setText(getActivity().getString(R.string.Rs) + readPref.getAmountPaid());
        orderId.setText("SBO"+readPref.getOrderId());
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
            ImageView newOrder = (ImageView) activity.findViewById(R.id.neworder);
            ImageView addMore = (ImageView) activity.findViewById(R.id.addmore);
            cancelButton.setVisibility(View.GONE);
            newOrder.setVisibility(View.VISIBLE);
            addMore.setVisibility(View.GONE);
            mTitle.setText(R.string.invoice);
        }
    }
}
