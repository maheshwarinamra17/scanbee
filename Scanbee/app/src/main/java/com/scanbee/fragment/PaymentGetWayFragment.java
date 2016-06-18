package com.scanbee.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.scanbee.scanbee.MainActivity;
import com.scanbee.scanbee.R;
import com.scanbee.servercommunication.WebServicePostCall;
import com.scanbee.servercommunication.WebServiceUrl;
import com.scanbee.sharedpref.ReadPref;
import com.scanbee.sharedpref.SavePref;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kshitij on 5/5/2016.
 */
public class PaymentGetWayFragment extends Fragment implements View.OnClickListener {
   View viewMain;
    Button continueToInvoiceBtn;
    ImageButton tabButton1,tabButton2,tabButton3;
    TextView amountPaid, amountPaidTxt, storeCredit, storeCreditTxt, itemsBought, itemsBoughtTxt,custName,custInfo,payCashTV,payCreditTV;
    Switch saveSwitch;
    Activity activity;
    LinearLayout tabLinearLayout1,tabLinearLayout2,tabLinearLayout3,paymentData;
    ReadPref readPref;
    SavePref savePref;
    String[] customer_info;
    int payMedium;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewMain=inflater.inflate(R.layout.payment_getway_fragment, null, false);
        activity = getActivity();
        continueToInvoiceBtn=(Button)viewMain.findViewById(R.id.continueToInvoiceBtn);
        amountPaid = (TextView)viewMain.findViewById(R.id.amount_paidTv);
        amountPaidTxt = (TextView)viewMain.findViewById(R.id.amount_paidTxtTv);
        storeCredit = (TextView)viewMain.findViewById(R.id.store_creditTv);
        storeCreditTxt = (TextView)viewMain.findViewById(R.id.store_creditsTxtTv);
        itemsBought = (TextView)viewMain.findViewById(R.id.item_boughtTv);
        itemsBoughtTxt = (TextView)viewMain.findViewById(R.id.item_boughtTxtTv);
        custName = (TextView)viewMain.findViewById(R.id.cust_nameTv);
        custInfo = (TextView)viewMain.findViewById(R.id.typeTv);
        saveSwitch = (Switch)viewMain.findViewById(R.id.save_switch);
        tabLinearLayout1 = (LinearLayout) viewMain.findViewById(R.id.tab1_view);
        tabLinearLayout2 = (LinearLayout) viewMain.findViewById(R.id.tab2_view);
        tabLinearLayout3 = (LinearLayout) viewMain.findViewById(R.id.tab3_view);
        paymentData = (LinearLayout) viewMain.findViewById(R.id.paymentData);
        payCashTV = (TextView) viewMain.findViewById(R.id.payCashTV);
        payCreditTV = (TextView) viewMain.findViewById(R.id.payCreditTV);
        payMedium = 1;

        readPref = new ReadPref(getActivity());
        savePref = new SavePref(getActivity());
        int itemsScanned = getArguments().getInt("items_scanned");
        customer_info = readPref.getCustInfo().split(";");
        custName.setText(customer_info[0]);
        custInfo.setText(customer_info[1]);
        continueToInvoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GeneratePaymentAsynctask().execute();
            }
        });
        tabButton1 = (ImageButton)viewMain.findViewById(R.id.tabButton1);
        tabButton2 = (ImageButton)viewMain.findViewById(R.id.tabButton2);
        tabButton3 = (ImageButton)viewMain.findViewById(R.id.tabButton3);
        tabButton1.setOnClickListener(this);
        tabButton2.setOnClickListener(this);
        tabButton3.setOnClickListener(this);

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
        payCashTV.setTypeface(RobotoMed);
        custInfo.setTypeface(NotoSans);
        saveSwitch.setTypeface(RobotoMed);
        payCreditTV.setTypeface(RobotoMed);
        amountPaid.setText(getActivity().getString(R.string.Rs) + readPref.getAmountPaid());
        itemsBought.setText(String.valueOf(itemsScanned));
        softKeyboardAdjustments();
        setupActionBar();
        return viewMain;
    }

    public void softKeyboardAdjustments(){
        final LinearLayout activityRootView = (LinearLayout) viewMain.findViewById(R.id.payactivityRoot);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                if (heightDiff > 500) {
                    paymentData.setVisibility(View.GONE);
                }else {
                    paymentData.setVisibility(View.VISIBLE);
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
            ImageView newOrder = (ImageView) activity.findViewById(R.id.neworder);
            ImageView addMore = (ImageView) activity.findViewById(R.id.addmore);
            cancelButton.setVisibility(View.VISIBLE);
            newOrder.setVisibility(View.VISIBLE);
            addMore.setVisibility(View.VISIBLE);

            mTitle.setText(R.string.payment);
        }
    }

    public String createPaymentJsonData() throws JSONException {
        JSONObject mainJsonObject = new JSONObject();
        JSONObject childJsonObject = new JSONObject();
        mainJsonObject.put("payment_type",payMedium);
        mainJsonObject.put("cust_id",readPref.getCustInfo());
        mainJsonObject.put("order_id", readPref.getOrderId());
        mainJsonObject.put("amount_paid",readPref.getAmountPaid());
        mainJsonObject.put("gateway_data",childJsonObject);
            childJsonObject.put("id", "dummy_pay_"+readPref.getOrderId());
            childJsonObject.put("amount", readPref.getAmountPaid());
            childJsonObject.put("status", 200);
            childJsonObject.put("message", "captured");
        return mainJsonObject.toString();
    }
    private class GeneratePaymentAsynctask extends AsyncTask<Void, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(getString(R.string.pay_prod));
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            String paymentParams = null;
            try {
                paymentParams = createPaymentJsonData();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            WebServicePostCall webServicePostCal=new WebServicePostCall();
            String response =  webServicePostCal.excutePost(WebServiceUrl.BASE_URL+WebServiceUrl.GENERATE_PAYMENT_DATA, paymentParams,readPref.getAuthToken());
            return response ;

        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            if(isAdded()) {
                super.onPostExecute(result);
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new InvoiceFragment()).commit();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tabButton1 :
                tabButton1.setBackground(getActivity().getDrawable(R.drawable.circle));
                tabButton2.setBackground(getActivity().getDrawable(R.drawable.solid_circle_app_color));
                tabButton3.setBackground(getActivity().getDrawable(R.drawable.solid_circle_app_color));
                tabButton1.setImageDrawable(getActivity().getDrawable(R.drawable.funds_w));
                tabButton2.setImageDrawable(getActivity().getDrawable(R.drawable.credit));
                tabButton3.setImageDrawable(getActivity().getDrawable(R.drawable.wallet));
                tabLinearLayout1.setVisibility(View.VISIBLE);
                tabLinearLayout2.setVisibility(View.GONE);
                tabLinearLayout3.setVisibility(View.GONE);
                payMedium = 1;
                break;
            case R.id.tabButton2 :
                tabButton1.setBackground(getActivity().getDrawable(R.drawable.solid_circle_app_color));
                tabButton2.setBackground(getActivity().getDrawable(R.drawable.circle));
                tabButton3.setBackground(getActivity().getDrawable(R.drawable.solid_circle_app_color));
                tabButton1.setImageDrawable(getActivity().getDrawable(R.drawable.funds));
                tabButton2.setImageDrawable(getActivity().getDrawable(R.drawable.credit_w));
                tabButton3.setImageDrawable(getActivity().getDrawable(R.drawable.wallet));
                tabLinearLayout2.setVisibility(View.VISIBLE);
                tabLinearLayout1.setVisibility(View.GONE);
                tabLinearLayout3.setVisibility(View.GONE);
                payMedium = 2;
                break;
            case R.id.tabButton3 :
                tabButton1.setBackground(getActivity().getDrawable(R.drawable.solid_circle_app_color));
                tabButton2.setBackground(getActivity().getDrawable(R.drawable.solid_circle_app_color));
                tabButton3.setBackground(getActivity().getDrawable(R.drawable.circle));
                tabButton1.setImageDrawable(getActivity().getDrawable(R.drawable.funds));
                tabButton2.setImageDrawable(getActivity().getDrawable(R.drawable.credit));
                tabButton3.setImageDrawable(getActivity().getDrawable(R.drawable.wallet_w));
                tabLinearLayout3.setVisibility(View.VISIBLE);
                tabLinearLayout2.setVisibility(View.GONE);
                tabLinearLayout1.setVisibility(View.GONE);
                payMedium = 3;
                break;
            default:
                break;
        }
    }
}
