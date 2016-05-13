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
import android.view.Menu;
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
    Menu menu;

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

        if (NetworkAvailablity.chkStatus(getActivity())) {
            new FetchData().execute();
        } else {
            new DialogCustom(activity,getString(R.string.no_internet_connection_play),activity.getDrawable(R.drawable.router),getString(R.string.try_again)).show();
        }

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
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DialogCustom(activity,getString(R.string.cancel_order),activity.getDrawable(R.drawable.cancel_order),getString(R.string.ok),getString(R.string.cancel)).show();
                    return;

                }
            });
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
            continue_btn.setEnabled(false);
            continue_btn.setBackgroundResource(R.drawable.button_gray_color);
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
            Fragment fragment = null;
            fragment = new CartItemFragment();
            if (fragment != null) {
                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();
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
//          forecastJsonStr=webRequest.makeWebServiceCall(WebServiceUrl.BASE_URL+ WebServiceUrl.GENERATE_DUMMY_ORDER,webRequest.GET);

            readPref = new ReadPref(getActivity());
            forecastJsonStr=webRequest.makeWebServiceCall("http://"+readPref.getIpAddress()+ WebServiceUrl.GET_RASPBERRY_DATA,webRequest.GET);
            return forecastJsonStr;

        }

        @Override
        protected void onPostExecute(String result) {
            if(isAdded()) {
                AVLoadingIndicatorView loader = (AVLoadingIndicatorView) viewMain.findViewById(R.id.orderLoader);
                ImageView shopIcon = (ImageView) viewMain.findViewById(R.id.shopIcon);
                loader.setVisibility(View.GONE);
                shopIcon.setVisibility(View.VISIBLE);
                if (result.length() == 0) {
                    new DialogCustom(activity, getString(R.string.no_rasp_hub), activity.getDrawable(R.drawable.raspberry), getString(R.string.ok)).show();
                }
//              parseJson(result);
                raspberryData = result;
                new GetOrderIdAsynctask().execute();
                super.onPostExecute(result);
                loader.setVisibility(View.GONE);
            }
        }
    }
//    public void parseJson(String json) {
//
//        JSONObject mainObj = null;
//        try {
//            mainObj = new JSONObject(json);
//
//           JSONObject orderDataObj= mainObj.optJSONObject("order_data");
//            String timeStamp= orderDataObj.optString("timestamp","2016-04-12 23:09:11");
//            System.out.println(timeStamp);
//            JSONArray barcodeDataArray=orderDataObj.optJSONArray("barcode_data");
//            for (int i=0;i<barcodeDataArray.length();i++){
//                ArrayList<Integer> idList=new ArrayList<Integer>();
//                idList.add(i);
//            }
//            JSONArray quantArray=orderDataObj.optJSONArray("quant_array");
//            for (int i=0;i<barcodeDataArray.length();i++){
//                ArrayList<Integer> quantList=new ArrayList<Integer>();
//                quantList.add(i);
//            }
//                    int machineId=orderDataObj.optInt("machine_id");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
////    public void parseRaspberryJSON(String json) {
////
////        JSONObject mainObj = null;
////        try {
////            mainObj = new JSONObject(json);
////            String timeStamp= mainObj.optString("timestamp","2016-04-12 23:09:11");
////            System.out.println(timeStamp);
////            JSONArray barcodeDataArray=mainObj.optJSONArray("barcode_data");
////            for (int i=0;i<barcodeDataArray.length();i++){
////                ArrayList<Integer> idList=new ArrayList<Integer>();
////                idList.add(i);
////            }
////            JSONArray quantArray=mainObj.optJSONArray("quant_array");
////            for (int i=0;i<barcodeDataArray.length();i++){
////                ArrayList<Integer> quantList=new ArrayList<Integer>();
////                quantList.add(i);
////            }
////            int machineId=mainObj.optInt("machine_id");
////        } catch (JSONException e) {
////            e.printStackTrace();
////        }
////    }

    private String createUserDataJson() {

        JSONObject obj = new JSONObject();
        JSONArray barcodeArray=new JSONArray();
        JSONArray quantArray=new JSONArray();
        try {
            obj.put("timestamp","2016-04-12 23:09:11");
            barcodeArray.put("13095949");
            barcodeArray.put("57840055");
            barcodeArray.put("91749284");
            barcodeArray.put("84865601");
            obj.put("barcode_data", barcodeArray);

            quantArray.put(2);
            quantArray.put(4);
            quantArray.put(3);
            quantArray.put(4);
            obj.put("quant_array",quantArray);
            obj.put("machine_id","21");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj.toString();
    }

    private class GetOrderIdAsynctask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
          String param;

            if(readPref.getOrderType().equals("1")){
                param = raspberryData;
            }else{
                param = createUserDataJson();
            }

            WebServicePostCall webServicePostCal=new WebServicePostCall();
            String response =  webServicePostCal.excutePost(WebServiceUrl.BASE_URL+WebServiceUrl.GENERATE_ORDER_ID, param);
            return response ;

        }

        @Override
        protected void onPostExecute(String result) {
            if(isAdded()) {
                parseJson(result);
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
              String message=newObj.optString("Success");
              int order_id=newObj.optInt("order_id");
              savePref.saveOrderId(order_id);
              if(order_id!=0){
                idTv.setText("SBO"+String.valueOf(order_id));
              }
          } catch (JSONException e) {
              e.printStackTrace();
          }
      }
}