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
import android.widget.ImageView;
import android.widget.TextView;

import com.scanbee.adapter.CartItemAdapter;
import com.scanbee.model.CartItemModelClass;
import com.scanbee.model.HeaderItemModelClass;
import com.scanbee.scanbee.MainActivity;
import com.scanbee.scanbee.R;
import com.scanbee.servercommunication.WebRequest;
import com.scanbee.servercommunication.WebServiceUrl;
import com.scanbee.sharedpref.ReadPref;
import com.scanbee.sharedpref.SavePref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by kshitij on 4/25/2016.
 */
public class CartItemFragment extends Fragment{
    View viewMain;
    Activity activity;
    ReadPref readPref;
    SavePref savePref;
    ArrayList<String>  currentProdArray,currentQuantArray;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    int orderId;
    ArrayList<CartItemModelClass> cartItemList;
    CartItemAdapter cartItemAdapter;
    Double amount_charge;
    Double cart_value;
    Double discount;
    Double tax;
    int ItemsScanned;
    JSONObject cartData;

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
        savePref=new SavePref(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        orderId=readPref.getOrderId();
        setupActionBar();
        setUpUi();
        new FetchData().execute();
        return viewMain;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
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
//            cancelButton.setVisibility(View.VISIBLE);
//            newOrder.setVisibility(View.GONE);
//            addMore.setVisibility(View.VISIBLE);

            mTitle.setText(R.string.cart_item);
        }
    }
    public void setUpUi(){
        cartItemList=new ArrayList<>();
        recyclerView=(RecyclerView)viewMain.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public HeaderItemModelClass getHeaderValues()
    {
        HeaderItemModelClass header = new HeaderItemModelClass(amount_charge,tax,cart_value,discount);
        return header;
    }

    public void setUpData(){
        currentProdArray = new ArrayList<String>(Arrays.asList(readPref.getOrderProds().split("-")));
        currentQuantArray =  new ArrayList<String>( Arrays.asList(readPref.getOrderQuants().split("-")));
        currentProdArray.remove(0);
        currentQuantArray.remove(0);
        cartItemAdapter = new CartItemAdapter(getHeaderValues(),cartItemList,getActivity(),currentProdArray,currentQuantArray,cartData);
        recyclerView.setAdapter(cartItemAdapter);
        cartItemAdapter.getItemCount();
    }

    private class FetchData extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            progressDialog.setMessage(getActivity().getString(R.string.load_prod));
            progressDialog.setCancelable(false);
            progressDialog.show();
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Void... params) {
            String forecastJsonStr = null;
            WebRequest webRequest= new WebRequest();
            HashMap<String,String>param=new HashMap<>();
            param.put("orderid", String.valueOf(orderId));
            forecastJsonStr=webRequest.makeWebServiceCall(WebServiceUrl.BASE_URL+ WebServiceUrl.GET_ORDER_DATA,webRequest.GET,param,readPref.getAuthToken());
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
                 cartData=orderDataObj.optJSONObject("cart_data");
                 amount_charge=cartData.optDouble("amount_charge");
                 cart_value=cartData.optDouble("cart_value");
                 discount=cartData.optDouble("discount");
                 tax=cartData.optDouble("tax");
                JSONArray prodDataArray=orderDataObj.optJSONArray("prod_data");
                ItemsScanned = 0;
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
                    Double mrp=prodDataObj.optDouble("mrp");
                    Double cp = prodDataObj.optDouble("cp");
                    String created_at=prodDataObj.optString("created_at");
                    String updated_at=prodDataObj.optString("updated_at");
                    int quantity=prodDataObj.optInt("quantity");
                    String tax_n_disc =  prodDataObj.optString("tax_n_disc");
                    ItemsScanned = ItemsScanned + quantity;
                    CartItemModelClass cartItemModelClass = new CartItemModelClass(id,brand,title,content,content_unit,cat_name,cat_id,item_id,mrp,cp,created_at,updated_at,quantity,tax_n_disc);
                    cartItemList.add(cartItemModelClass);
                }
                savePref.saveItemsScanned(ItemsScanned);
            }
            setUpData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
