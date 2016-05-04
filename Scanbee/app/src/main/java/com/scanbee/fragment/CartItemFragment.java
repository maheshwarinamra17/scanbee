package com.scanbee.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.scanbee.adapter.CartItemAdapter;
import com.scanbee.model.CartItemModelClass;
import com.scanbee.scanbee.MainActivity;
import com.scanbee.scanbee.R;
import com.scanbee.servercommunication.WebRequest;
import com.scanbee.servercommunication.WebServiceUrl;
import com.scanbee.sharedpref.ReadPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

//import android.app.Fragment;

/**
 * Created by kshitij on 4/25/2016.
 */
public class CartItemFragment extends Fragment{
    View viewMain;
    Activity activity;
    ReadPref readPref;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    int orderId;
    TextView discountTv,taxTv,cartValueTv;
    Button chargRsBtn;
    ArrayList<CartItemModelClass> cartItemList;
    CartItemAdapter cartItemAdapter;
    int amount_charge;
    int cart_value;
    int discount;
    int tax;
    final String public_key = "rzp_test_1DP5mmOlF5G5ag";

    public CartItemFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewMain = inflater.inflate(R.layout.cart_item_fragment,
                container, false);
        activity=getActivity();
        setHasOptionsMenu(true);
        readPref=new ReadPref(getActivity());
        progressDialog=new ProgressDialog(getActivity());
        orderId=readPref.getOrderId();
        setupActionBar();
        setUpUi();
        new FetchData().execute();
        return viewMain;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main,menu);
       // menu.setGroupVisible(R.id.group2,true);

        if (menu != null) {

            menu.findItem(R.id.cart).setVisible(true);
            menu.findItem(R.id.delete).setVisible(true);
        }
        super.onCreateOptionsMenu(menu, inflater);
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
            Toolbar toolbar = (Toolbar)activity. findViewById(R.id.toolbar);
            TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            mTitle.setText(R.string.cart_item);
        }
    }
    public void setUpUi(){
        final CheckoutFragment co = new CheckoutFragment();
        co.setPublicKey(public_key);
        cartItemList=new ArrayList<>();
        discountTv=(TextView)viewMain.findViewById(R.id.discountTv);
        taxTv=(TextView)viewMain.findViewById(R.id.taxTv);
        cartValueTv=(TextView)viewMain.findViewById(R.id.cartValueTv);
        chargRsBtn=(Button)viewMain.findViewById(R.id.chargBtn);
        recyclerView=(RecyclerView)viewMain.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        chargRsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    JSONObject options = new JSONObject("{" +
                            "description: 'Demoing Charges'," +
                            "image: 'https://rzp-mobile.s3.amazonaws.com/images/rzp.png'," +
                            "currency: 'INR'}"
                    );

                    options.put("amount", "500");
                    options.put("name", "Razorpay Corp");
                    options.put("prefill", new JSONObject("{email: 'sm@razorpay.com', contact: '9876543210'}"));

                    co.open(activity, options);

                } catch(Exception e){
                    Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }
    public void setUpData(){
        discountTv.setText(getActivity().getString(R.string.Rs) + String.valueOf(discount));
        taxTv.setText(getActivity().getString(R.string.Rs) + String.valueOf(tax));
        cartValueTv.setText(getActivity().getString(R.string.Rs) + String.valueOf(cart_value));
        chargRsBtn.setText("Charge "+ getActivity().getString(R.string.Rs) + String.valueOf(amount_charge));
        cartItemAdapter=new CartItemAdapter(cartItemList,getActivity());
        recyclerView.setAdapter(cartItemAdapter);
    }
    private class FetchData extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading...");
            //show dialog
            progressDialog.show();
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Void... params) {
            String forecastJsonStr = null;
            WebRequest webRequest= new WebRequest();
            HashMap<String,String>param=new HashMap<>();
            param.put("orderid", String.valueOf(orderId));
            forecastJsonStr=webRequest.makeWebServiceCall(WebServiceUrl.BASE_URL+ WebServiceUrl.GET_ORDER_DATA,webRequest.GET,param);
            System.out.println("response===>>>>"+forecastJsonStr);
            return forecastJsonStr;
        }
        @Override
        protected void onPostExecute(String result) {
            parseJson(result);
            progressDialog.dismiss();
            super.onPostExecute(result);
            Log.i("json", result);
        }
    }
    public void parseJson(String json){
        try {
            JSONObject parentObj=new JSONObject(json);
            int status=parentObj.optInt("status");
            if (status==200){
                String message=parentObj.optString("Success");
                JSONObject orderDataObj=parentObj.optJSONObject("order_data");
                JSONObject cartData=orderDataObj.optJSONObject("cart_data");
                 amount_charge=cartData.optInt("amount_charge");
                 cart_value=cartData.optInt("cart_value");
                 discount=cartData.optInt("discount");
                 tax=cartData.optInt("tax");
                JSONArray prodDataArray=orderDataObj.optJSONArray("prod_data");

                for (int i=0;i<prodDataArray.length();i++){
                    JSONObject prodDataObj= prodDataArray.getJSONObject(i);
                    int id=prodDataObj.optInt("id");
                    String brand=prodDataObj.optString("brand");
                    String title=prodDataObj.optString("title");
                    int content=prodDataObj.optInt("content");
                    String content_unit=prodDataObj.optString("content_unit");
                    String cat_name=prodDataObj.optString("cat_name");
                    int cat_id=prodDataObj.optInt("cat_id");
                    String item_id=prodDataObj.optString("item_id");
                    int mrp=prodDataObj.optInt("mrp");
                    int cp=prodDataObj.optInt("cp");
                    String created_at=prodDataObj.optString("created_at");
                    String updated_at=prodDataObj.optString("updated_at");
                    CartItemModelClass cartItemModelClass = new CartItemModelClass(id,brand,title,content,content_unit,cat_name,cat_id,item_id,mrp,cp,created_at,updated_at);
                    cartItemList.add(cartItemModelClass);
                }
            }
            setUpData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
