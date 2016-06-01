package com.scanbee.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.scanbee.dialog.DialogCustom;
import com.scanbee.scanbee.MainActivity;
import com.scanbee.scanbee.R;
import com.scanbee.servercommunication.NetworkAvailablity;
import com.scanbee.servercommunication.WebRequest;
import com.scanbee.servercommunication.WebServicePostCall;
import com.scanbee.servercommunication.WebServiceUrl;
import com.scanbee.sharedpref.ReadPref;
import com.scanbee.sharedpref.SavePref;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.util.HashMap;

/**
 * Created by kshitij on 4/23/2016.
 */
public class GenerateOrderidFragment extends Fragment implements View.OnClickListener {
    private View viewMain;
    Toolbar toolbar;
    SavePref savePref;
    ReadPref readPref;
    ProgressDialog progressDialog;
    TextView idTv, totalIitemSelectedTv, handlerNameTv, storeName, circleImgTv,orderId,itemScan;
    Button continue_btn;
    Activity activity;
    String raspberryData;
    AVLoadingIndicatorView loader;
    ImageView shopIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewMain = inflater.inflate(R.layout.generate_orderid_fragment,
                container, false);
        activity=getActivity();
        savePref=new SavePref(getActivity());
        readPref=new ReadPref(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        setupActionBar();
        setUpUi();
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
        if(activity != null){
            Toolbar toolbar = (Toolbar)activity.findViewById(R.id.toolbar);
            ImageView cancelButton = (ImageView) activity.findViewById(R.id.cancelorder);
            cancelButton.setVisibility(View.VISIBLE);
            /*cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DialogCustom(activity,getString(R.string.cancel_order),activity.getDrawable(R.drawable.cancel_order),getString(R.string.ok),getString(R.string.cancel)).show();
                    return;

                }
            });*/
            TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            mTitle.setText(R.string.scanning);
        }
    }

    public void setUpUi(){

            idTv = (TextView)viewMain.findViewById(R.id.orderIdTv);
            totalIitemSelectedTv = (TextView)viewMain.findViewById(R.id.itemScannedTv);
            handlerNameTv = (TextView) viewMain.findViewById(R.id.custNameTv);
            storeName = (TextView) viewMain.findViewById(R.id.storeTv);
            circleImgTv = (TextView) viewMain.findViewById(R.id.circleImgTv);
            orderId = (TextView)viewMain.findViewById(R.id.orderId);
            itemScan = (TextView)viewMain.findViewById(R.id.itemScannedTxtTv);
            continue_btn = (Button) viewMain.findViewById(R.id.continue_btn);
            continue_btn.setEnabled(true);
           // continue_btn.setBackgroundResource(R.drawable.button_gray_color);
            continue_btn.setText(R.string.scanning_data);
            continue_btn.setOnClickListener(this);

            Typeface Roboto=Typeface.createFromAsset(getResources().getAssets(),getString(R.string.roboto_font));
            Typeface RobotoThin=Typeface.createFromAsset(getResources().getAssets(),getString(R.string.roboto_thin));
            Typeface RobotoMed=Typeface.createFromAsset(getResources().getAssets(),getString(R.string.roboto_med));
            Typeface NotoSans=Typeface.createFromAsset(getResources().getAssets(),getString(R.string.noto_sans));

            idTv.setTypeface(NotoSans);
            totalIitemSelectedTv.setTypeface(NotoSans);
            continue_btn.setTypeface(Roboto);
            circleImgTv.setTypeface(RobotoMed);
            handlerNameTv.setTypeface(RobotoMed);
            orderId.setTypeface(NotoSans);
            itemScan.setTypeface(Roboto);
            storeName.setTypeface(Roboto);

        }


    @Override
    public void onClick(View view) {
        if(!NetworkAvailablity.chkStatus(activity)){
            new DialogCustom(activity,getString(R.string.no_internet_connection_play),activity.getDrawable(R.drawable.router),getString(R.string.try_again)).show();
            return;
        }
        if (NetworkAvailablity.chkStatus(getActivity())) {
            new FetchData().execute();
        } else {
            new DialogCustom(activity,getString(R.string.some_thing_went_wrong),activity.getDrawable(R.drawable.router),getString(R.string.try_again)).show();
        }
    }
    private class FetchData extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;
            WebRequest webRequest= new WebRequest();
            readPref = new ReadPref(getActivity());
            if(readPref.getOrderType().equals("1")){
                forecastJsonStr=webRequest.makeWebServiceCall("http://"+readPref.getIpAddress()+ WebServiceUrl.GET_RASPBERRY_DATA,webRequest.GET,readPref.getAuthToken());
            }else{
                forecastJsonStr=webRequest.makeWebServiceCall(WebServiceUrl.BASE_URL + WebServiceUrl.GENERATE_DUMMY_ORDER ,webRequest.GET,readPref.getAuthToken());
            }
            return forecastJsonStr;

        }

        @Override
        protected void onPostExecute(String result) {
            if(isAdded()) {
                loader = (AVLoadingIndicatorView) viewMain.findViewById(R.id.orderLoader);
                shopIcon = (ImageView) viewMain.findViewById(R.id.shopIcon);
                if (result.length() == 0) {
                    new DialogCustom(activity, getString(R.string.no_rasp_hub), activity.getDrawable(R.drawable.raspberry), getString(R.string.ok)).show();
                }else {
                    raspberryData = RaspToOrderIDData(result);
                    if(raspberryData.equals(getString(R.string.duplicacy))){
                        new DialogCustom(activity, getString(R.string.dup_order), activity.getDrawable(R.drawable.fishy), getString(R.string.ok),getString(R.string.try_again)).show();
                        return;
                    }else {
                        new GetOrderIdAsynctask().execute();
                        super.onPostExecute(result);
                    }
                }
            }
        }
    }

    public String RaspToOrderIDData(String json) {

        JSONObject raspData = null;
        JSONObject newData = new JSONObject();
        readPref = new ReadPref(getActivity());
        savePref = new SavePref(getActivity());
        try {
            raspData = new JSONObject(json);
            String timeStamp= raspData.optString("timestamp", "2000-01-1 22:11:00");
            if(readPref.getOrderTimeStamp().equals(timeStamp)) {
                return getString(R.string.duplicacy);
            }else{
                savePref.saveOrderTimeStamp(timeStamp);
            }
            JSONArray barcodeDataArray=raspData.optJSONArray("barcode_data");
            int machineId=raspData.optInt("machine_id");

            HashMap<String,Integer> prodHash = new HashMap<String,Integer>();
            for (int i=0;i<barcodeDataArray.length();i++){
                String barcode = barcodeDataArray.getString(i);
                if(!prodHash.containsKey(barcode)){
                    prodHash.put(barcode,1);
                }else {
                    prodHash.put(barcode,prodHash.get(barcode).intValue()+1);
                }
            }
            JSONArray newBarcodeData = new JSONArray();
            JSONArray quantArray = new JSONArray();
            for(HashMap.Entry<String, Integer> entry : prodHash.entrySet())
            {   newBarcodeData.put(entry.getKey());
                quantArray.put(entry.getValue());
            }
            newData.put("timestamp",timeStamp);
            newData.put("barcode_data", newBarcodeData);
            newData.put("quant_array",quantArray);
            newData.put("machine_id", machineId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newData.toString();
    }



    private class GetOrderIdAsynctask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            String param;
            param = raspberryData;
            WebServicePostCall webServicePostCal=new WebServicePostCall();
            String response =  webServicePostCal.excutePost(WebServiceUrl.BASE_URL+WebServiceUrl.GENERATE_ORDER_ID, param,readPref.getAuthToken());
            return response ;

        }

        @Override
        protected void onPostExecute(String result) {
            if(isAdded()) {
                parseJson(result);
                loader.setVisibility(View.GONE);
                shopIcon.setVisibility(View.VISIBLE);
                super.onPostExecute(result);
            }
        }
    }
      public void parseJson(String result){
          try {
              JSONObject newObj=new JSONObject(result);
              int status=newObj.optInt("status");
              if (status==200){
                  continue_btn.setEnabled(true);
                  continue_btn.setText(R.string.continue_btn);
                  continue_btn.setBackgroundResource(R.drawable.button_app_color);
              }
              String message=newObj.optString("message");
              JSONObject orderdata  = newObj.optJSONObject("order_data");
              int order_id=orderdata.optInt("orderid");
              int quantity=orderdata.optInt("quantity");
              savePref.saveOrderId(order_id);
              if(order_id!=0){
                idTv.setText("SBO"+String.valueOf(order_id));
                totalIitemSelectedTv.setText(String.valueOf(quantity));
              }
              Fragment fragment = null;
              fragment = new CartItemFragment();
              if (fragment != null) {
                  // Insert the fragment by replacing any existing fragment
                  FragmentManager fragmentManager = getFragmentManager();
                  fragmentManager.beginTransaction()
                          .replace(R.id.content_frame, fragment)
                          .commit();
              }
          } catch (JSONException e) {
              e.printStackTrace();
          }
      }
}