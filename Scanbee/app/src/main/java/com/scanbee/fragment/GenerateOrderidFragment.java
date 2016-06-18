package com.scanbee.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.media.MediaPlayer;
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
import java.util.ArrayList;
import java.util.Arrays;
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
    Boolean addMoreFlag;
    int newDataLength;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewMain = inflater.inflate(R.layout.generate_orderid_fragment,
                container, false);
        activity=getActivity();
        savePref=new SavePref(getActivity());
        readPref=new ReadPref(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        addMoreFlag = false;
        if(getArguments()!=null) {
            addMoreFlag = getArguments().getBoolean("add_more");
        }
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
            ImageView newOrder = (ImageView) activity.findViewById(R.id.neworder);
            ImageView addMore = (ImageView) activity.findViewById(R.id.addmore);
            cancelButton.setVisibility(View.GONE);
            newOrder.setVisibility(View.GONE);
            addMore.setVisibility(View.GONE);

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
            loader = (AVLoadingIndicatorView) viewMain.findViewById(R.id.orderLoader);
            shopIcon = (ImageView) viewMain.findViewById(R.id.shopIcon);
            loader.setVisibility(View.GONE);
            shopIcon.setVisibility(View.VISIBLE);
            continue_btn = (Button) viewMain.findViewById(R.id.continue_btn);
            continue_btn.setEnabled(true);
            continue_btn.setBackgroundResource(R.drawable.button_blue_color);
            continue_btn.setText(R.string.start_scanning);
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
            itemScan.setTypeface(NotoSans);
            storeName.setTypeface(Roboto);

            if(addMoreFlag){
                idTv.setText("SBO"+readPref.getOrderId());
                totalIitemSelectedTv.setText(readPref.getItemsScanned()+" + X");
            }

        }


    @Override
    public void onClick(View view) {
        if(!NetworkAvailablity.chkStatus(activity)){
            new DialogCustom(activity,getString(R.string.no_internet_connection_play),activity.getDrawable(R.drawable.router),getString(R.string.try_again)).show();
            return;
        }
        if (NetworkAvailablity.chkStatus(getActivity())) {
            loader.setVisibility(View.VISIBLE);
            shopIcon.setVisibility(View.GONE);
            continue_btn.setBackgroundResource(R.drawable.button_gray_color);
            continue_btn.setText(R.string.scanning_data);
            continue_btn.setEnabled(false);
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
                forecastJsonStr=webRequest.makeWebServiceCall("http://" + readPref.getIpAddress() + WebServiceUrl.GET_RASPBERRY_DATA, webRequest.GET, readPref.getAuthToken());
            }else{
                forecastJsonStr=webRequest.makeWebServiceCall(WebServiceUrl.BASE_URL + WebServiceUrl.GENERATE_DUMMY_ORDER, webRequest.GET, readPref.getAuthToken());
            }
            return forecastJsonStr;

        }

        @Override
        protected void onPostExecute(String result) {
            if(isAdded()) {

                if (result.length() == 0) {
                    new DialogCustom(activity, getString(R.string.no_rasp_hub), activity.getDrawable(R.drawable.raspberry), getString(R.string.ok)).show();
                }else {
                    raspberryData = result;
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
            String dashedProducts = "";
            String dashedQuantity = "";

            for(HashMap.Entry<String, Integer> entry : prodHash.entrySet())
            {   newBarcodeData.put(entry.getKey());
                quantArray.put(entry.getValue());
                dashedProducts = dashedProducts + "-" + entry.getKey();
                dashedQuantity = dashedQuantity + "-" + entry.getValue();
            }
            savePref.saveOrderProducts(dashedProducts);
            savePref.saveOrderQuants(dashedQuantity);
            newData.put("timestamp",timeStamp);
            newData.put("barcode_data", newBarcodeData);
            newData.put("quant_array",quantArray);
            newData.put("machine_id", machineId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newData.toString();
    }

    public String UpdatedProductsData(){
        JSONObject updatedData = new JSONObject();
        ArrayList<String> currentProdArray,currentQuantArray;
        try {
            JSONObject newOrderData= new JSONObject(raspberryData);
            JSONObject updatedOrderData = new JSONObject();
            updatedOrderData.put("timestamp",newOrderData.optString("timestamp"));
            updatedOrderData.put("machine_id",newOrderData.optInt("machine_id"));
                currentProdArray = new ArrayList<String>(Arrays.asList(readPref.getOrderProds().split("-")));
                currentQuantArray =  new ArrayList<String>( Arrays.asList(readPref.getOrderQuants().split("-")));
                currentProdArray.remove(0);
                currentQuantArray.remove(0);
            JSONArray newBarcodeData = newOrderData.optJSONArray("barcode_data");
            newDataLength =  newBarcodeData.length();
            for(int i=0; i < newDataLength; i++){
               if(currentProdArray.contains(newBarcodeData.getString(i))) {
                   int index = currentProdArray.indexOf(newBarcodeData.getString(i));
                   currentQuantArray.set(index,String.valueOf(Integer.valueOf(currentQuantArray.get(index))+1));
               } else{
                   currentProdArray.add(newBarcodeData.getString(i));
                   currentQuantArray.add("1");
               }
            }
            updateSharedPrefProd(currentProdArray);
            updateSharedPrefQuant(currentQuantArray);
            updatedOrderData.put("barcode_data",new JSONArray(currentProdArray));
            updatedOrderData.put("quant_array",new JSONArray(currentQuantArray));
            updatedData.put("orderid",readPref.getOrderId());
            updatedData.put("order_data",updatedOrderData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return updatedData.toString();
    }

    public void updateSharedPrefProd(ArrayList<String> currentProdArray){
        String dspProd ="";
        for(String p: currentProdArray){
            dspProd += "-" + p;
        }
        savePref.saveOrderProducts(dspProd);
    }
    public void updateSharedPrefQuant(ArrayList<String> currentQuantArray){
        String dspQuant ="";
        for(String q: currentQuantArray){
            dspQuant += "-" + q;
        }
        savePref.saveOrderQuants(dspQuant);
    }

    private class GetOrderIdAsynctask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            String param, response =  null;
            if(addMoreFlag){
                param = UpdatedProductsData();
                WebServicePostCall webServicePostCal = new WebServicePostCall();
                response = webServicePostCal.excutePost(WebServiceUrl.BASE_URL + WebServiceUrl.UPDATE_ORDER_DATA, param, readPref.getAuthToken());
            } else {
                param = RaspToOrderIDData(raspberryData);
                WebServicePostCall webServicePostCal = new WebServicePostCall();
                response = webServicePostCal.excutePost(WebServiceUrl.BASE_URL + WebServiceUrl.GENERATE_ORDER_ID, param, readPref.getAuthToken());
            }
            return response ;

        }

        @Override
        protected void onPostExecute(String result) {
            if(isAdded()) {
                if(addMoreFlag) {
                    parseUpdatedJson(result);
                }else{
                    parseJson(result);
                }
                loader.setVisibility(View.GONE);
                shopIcon.setVisibility(View.VISIBLE);
                continue_btn.setEnabled(true);
                continue_btn.setBackgroundResource(R.drawable.button_app_color);
                continue_btn.setText(R.string.continue_btn);
                MediaPlayer mp = MediaPlayer.create(activity, R.raw.order);
                mp.start();
                continue_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment fragment = null;
                        fragment = new CartItemFragment();
                        if (fragment != null) {
                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.content_frame, fragment)
                                    .commit();
                        }
                    }
                });
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
              JSONObject orderdata  = newObj.optJSONObject("order_data");
              int order_id=orderdata.optInt("orderid");
              int quantity=orderdata.optInt("quantity");
              savePref.saveOrderId(order_id);
              if(order_id!=0){
                idTv.setText("SBO"+String.valueOf(order_id));
                totalIitemSelectedTv.setText(String.valueOf(quantity));
              }

          } catch (JSONException e) {
              e.printStackTrace();
          }
      }

    public void parseUpdatedJson(String result){
        try {
            JSONObject newObj=new JSONObject(result);
            int status=newObj.optInt("status");
            if (status==200) {
                continue_btn.setEnabled(true);
                continue_btn.setText(R.string.continue_btn);
                continue_btn.setBackgroundResource(R.drawable.button_app_color);

                JSONObject orderdata = newObj.optJSONObject("order_data");
                JSONArray prodData = orderdata.optJSONArray("prod_data");
                int order_id = readPref.getOrderId();
                int quantity = readPref.getItemsScanned();
                if (order_id != 0) {
                    idTv.setText("SBO" + String.valueOf(order_id));
                    totalIitemSelectedTv.setText(quantity + " + " +String.valueOf(newDataLength));
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}