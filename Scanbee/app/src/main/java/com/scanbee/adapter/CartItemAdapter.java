package com.scanbee.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.scanbee.dialog.DialogCustom;
import com.scanbee.dialog.ToastCustom;
import com.scanbee.fragment.PaymentGetWayFragment;
import com.scanbee.model.CartItemModelClass;
import com.scanbee.model.HeaderItemModelClass;
import com.scanbee.scanbee.R;
import com.scanbee.servercommunication.WebRequest;
import com.scanbee.servercommunication.WebServicePostCall;
import com.scanbee.servercommunication.WebServiceUrl;
import com.scanbee.sharedpref.ReadPref;
import com.scanbee.sharedpref.SavePref;
import com.scanbee.util.NumberPicker;
import com.scanbee.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by kshitij on 4/28/2016.
 */
public class CartItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    ArrayList<CartItemModelClass> cartItemDataList;
    HeaderItemModelClass header;
    Context mCtx;
    ArrayList<String> currentProdArray,currentQuantArray;
    ReadPref readPref;
    SavePref savePref;
    Double amount_charge;
    JSONObject cartData;
    Utils utilLib;
    MaterialEditText phoneNoEt;
    Fragment fragment;

    public CartItemAdapter(HeaderItemModelClass header, ArrayList<CartItemModelClass> cartItemDataList, Context mCtx, ArrayList<String> currentProdArray,ArrayList<String>  currentQuantArray, JSONObject cartData) {
        this.cartItemDataList=cartItemDataList;
        this.currentProdArray = currentProdArray;
        this.currentQuantArray = currentQuantArray;
        this.header = header;
        this.mCtx = mCtx;
        this.cartData = cartData;
        this.amount_charge = header.getAmount();
        this.readPref=new ReadPref(mCtx);
        this.savePref=new SavePref(mCtx);
        this.utilLib = new Utils(mCtx);
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType == TYPE_HEADER) {
                View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_header_item,parent,false);
                return new VHHeader(headerView);
            }
            else if(viewType == TYPE_ITEM) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row_cart_item,parent,false);
                return new VHItem(itemView);
            }
            throw new RuntimeException("There is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof VHHeader)
        {
            VHHeader VHheader = (VHHeader) holder;
            VHheader.discountTv.setText(mCtx.getString(R.string.Rs) + String.valueOf(header.getDiscount()));
            VHheader.taxTv.setText(mCtx.getString(R.string.Rs) + String.valueOf(header.getTax()));
            VHheader.cartValueTv.setText(mCtx.getString(R.string.Rs) + String.valueOf(header.getCartValue()));
            VHheader.chargRsBtn.setText(mCtx.getString(R.string.charge)+ " " + mCtx.getString(R.string.Rs) + String.valueOf(header.getAmount()));
            VHheader.chargRsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customerDialog();
                }
            });
            if (currentProdArray.size() == 0) {
                VHheader.chargRsBtn.setEnabled(false);
                VHheader.chargRsBtn.setBackgroundResource(R.drawable.button_gray_color);
            }

        }
        else if (holder instanceof VHItem) {
            final CartItemModelClass modelClass = cartItemDataList.get(position-1);
            final VHItem VHItem = (VHItem)holder;
            VHItem.title.setText(modelClass.getTitle());
            VHItem.mrp.setText(mCtx.getString(R.string.Rs) + String.valueOf(modelClass.getMrp() * modelClass.getQuantity()));
            VHItem.circleTxt.setText(String.valueOf(modelClass.getTitle().charAt(0)));
            VHItem.prodNameTv.setText(modelClass.getTitle());
            String itemInfoText = String.valueOf(modelClass.getQuantity()) + " x " + mCtx.getString(R.string.Rs) + modelClass.getMrp() + ", " +
                    modelClass.getContent() + " " + modelClass.getContentItem();
            VHItem.itemInfo.setText(itemInfoText);
            final NumberPicker numberPicker = new NumberPicker(mCtx, VHItem.numbPickerView, modelClass.getQuantity());
            numberPicker.show();

            VHItem.circleTxt.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        VHItem.basicView.setVisibility(View.GONE);
                                                        VHItem.clickView.setVisibility(View.VISIBLE);
                                                        VHItem.cardView.setBackgroundColor(mCtx.getResources().getColor(R.color.app_color));
                                                    }
                                                }
            );
            VHItem.crossBtn.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       VHItem.basicView.setVisibility(View.VISIBLE);
                                                       VHItem.clickView.setVisibility(View.GONE);
                                                       VHItem.cardView.setBackgroundColor(mCtx.getResources().getColor(R.color.white));
                                                       int oldQuant = modelClass.getQuantity();
                                                       adjustHeaderValues(modelClass.getMrp(),modelClass.getTandD(),oldQuant,numberPicker.getCount());
                                                       modelClass.setQuantity(numberPicker.getCount());
                                                       savePref.saveItemsScanned(readPref.getItemsScanned() + numberPicker.getCount() - oldQuant);
                                                       currentQuantArray.set(currentProdArray.indexOf(modelClass.getItemId()), String.valueOf(numberPicker.getCount()));
                                                       updateSharedPrefQuant();
                                                       VHItem.mrp.setText(mCtx.getString(R.string.Rs) + String.valueOf(modelClass.getMrp() * modelClass.getQuantity()));
                                                       String itemInfoText = String.valueOf(modelClass.getQuantity()) + " x " + mCtx.getString(R.string.Rs) + modelClass.getMrp() + ", " +
                                                               modelClass.getContent() + " " + modelClass.getContentItem();
                                                       VHItem.itemInfo.setText(itemInfoText);
                                                       notifyItemChanged(0);
                                                   }
                                               }
            );

            VHItem.delete_prod.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          VHItem.cardView.setVisibility(View.GONE);
                                                          int index = currentProdArray.indexOf(modelClass.getItemId());
                                                          int oldQuant = modelClass.getQuantity();
                                                          adjustHeaderValues(modelClass.getMrp(),modelClass.getTandD(),oldQuant,0);
                                                          savePref.saveItemsScanned(readPref.getItemsScanned() - oldQuant);
                                                          currentQuantArray.remove(index);
                                                          currentProdArray.remove(index);
                                                          updateSharedPrefProd();
                                                          updateSharedPrefQuant();
                                                          if (currentProdArray.size() == 0) {
                                                              new DialogCustom(mCtx, mCtx.getString(R.string.empty_cart), mCtx.getDrawable(R.drawable.fishy),
                                                                      mCtx.getString(R.string.ok), mCtx.getString(R.string.new_order)).show();
                                                          }
                                                          ToastCustom customToast = new ToastCustom(mCtx);
                                                          customToast.show(VHItem.title.getText() + " " + mCtx.getString(R.string.item_removed));
                                                          notifyItemChanged(0);
                                                      }
                                                  }
            );

        }

    }

    public int getItemViewType(int position) {
        if(isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }


    public int getItemCount() {
        return cartItemDataList.size()+1;
    }

    public class VHHeader extends RecyclerView.ViewHolder{
        public TextView discountTv,taxTv,cartValueTv,discountTvTxt,taxTvTxt,cartValueTvTxt;
        public Button chargRsBtn;
        public VHHeader(View headerView) {
            super(headerView);
            discountTv=(TextView)headerView.findViewById(R.id.discountTv);
            taxTv=(TextView)headerView.findViewById(R.id.taxTv);
            cartValueTv=(TextView)headerView.findViewById(R.id.cartValueTv);
            discountTvTxt=(TextView)headerView.findViewById(R.id.discountTxtTv);
            taxTvTxt=(TextView)headerView.findViewById(R.id.taxTxtTv);
            cartValueTvTxt=(TextView)headerView.findViewById(R.id.cartValueTxtTv);
            chargRsBtn=(Button)headerView.findViewById(R.id.chargBtn);
            Typeface Roboto=Typeface.createFromAsset(mCtx.getResources().getAssets(),mCtx.getString(R.string.roboto_font));
            Typeface NotoSans=Typeface.createFromAsset(mCtx.getResources().getAssets(),mCtx.getString(R.string.noto_sans));
            chargRsBtn.setTypeface(Roboto);
            discountTv.setTypeface(NotoSans);
            taxTv.setTypeface(NotoSans);
            cartValueTv.setTypeface(NotoSans);
            discountTvTxt.setTypeface(NotoSans);
            cartValueTvTxt.setTypeface(NotoSans);
            discountTvTxt.setTypeface(NotoSans);
        }
    }

    public void updateSharedPrefProd(){
        String dspProd ="";
        for(String p: currentProdArray){
            dspProd += "-" + p;
        }
        savePref.saveOrderProducts(dspProd);
    }
    public void updateSharedPrefQuant(){
        String dspQuant ="";
        for(String q: currentQuantArray){
            dspQuant += "-" + q;
        }
        savePref.saveOrderQuants(dspQuant);
    }

    public void adjustHeaderValues(Double mrp, String TnD, int oldQuant, int newQuant){

        Double cartValue = header.getCartValue();
        Double tax = header.getTax();
        Double discount  =  header.getDiscount();
        String[] TandDArray =  TnD.split(";");
        int factor =  newQuant - oldQuant;
        Double taxPerProd = 0.0, discPerProd= 0.0;
        try {
            JSONObject taxData = cartData.optJSONObject("tax_array");
            for (int i = 0; i < TandDArray.length; i++) {
                if (taxData.getJSONObject(TandDArray[i]).optInt("value_type")==1){
                    taxPerProd = taxPerProd + (taxData.getJSONObject(TandDArray[i]).optDouble("percent")* mrp / 100.0);
                }else if(taxData.getJSONObject(TandDArray[i]).optInt("value_type")==2){
                    discPerProd = discPerProd + (taxData.getJSONObject(TandDArray[i]).optDouble("percent")* mrp / 100.0);
                }
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        cartValue =  cartValue + factor * mrp;
        tax =  tax +  factor *  taxPerProd;
        discount =  discount + factor * discPerProd;
        amount_charge = utilLib.doublePrecision(cartValue + tax - discount,2);
        if(cartValue<1.0){
            amount_charge = 0.0;
            cartValue = 0.0;
            tax = 0.0;
            discount = 0.0;
        }
        header.setCartValue(utilLib.doublePrecision(cartValue, 1));
        header.setDiscount(utilLib.doublePrecision(discount, 1));
        header.setTax(utilLib.doublePrecision(tax, 1));
        header.setAmount(amount_charge);
    }
    public void customerDialog(){
        final Dialog dialog = new Dialog(mCtx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.customer_dialog);
        dialog.show();
        savePref.saveAmountPaid(amount_charge.toString());
        phoneNoEt = (MaterialEditText)dialog.findViewById(R.id.phoneNoET);
        Button skipBtn =(Button)dialog.findViewById(R.id.skipButton);
        Button okBtn =(Button)dialog.findViewById(R.id.okButton);
        fragment = new PaymentGetWayFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("items_scanned", readPref.getItemsScanned());
        fragment.setArguments(bundle);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) mCtx.getSystemService(Context.
                        INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                //Put phone number check
                if (phoneNoEt.getText().toString().trim().length() > 0) {
                    if(isOrderDataChange()){
                        new UpdateOrderData(true).execute();
                    } else{
                        new GetCustomerData().execute();
                    }
                    dialog.dismiss();
                } else {
                    phoneNoEt.setHelperText(mCtx.getString(R.string.valid_ph));
                }

            }
        });
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) mCtx.getSystemService(Context.
                        INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                if(isOrderDataChange()){
                    new UpdateOrderData(false).execute();
                } else {
                    if (fragment != null) {
                        FragmentManager fragmentManager = ((Activity) mCtx).getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.content_frame, fragment)
                                .commit();
                    }
                }
                dialog.dismiss();
                savePref.saveCustInfo(mCtx.getString(R.string.no_cust)+";XXXXXXXXXX");
            }
        });
    }

    public Boolean isOrderDataChange(){

        Boolean result = false;
        ArrayList<String> oldQuantArray =  new ArrayList<String>(Arrays.asList(readPref.getOrderQuants().split("-")));
        oldQuantArray.remove(0);
        if(oldQuantArray.size()!=currentQuantArray.size()){
            result = true;
        }else if(!Arrays.equals(oldQuantArray.toArray(),currentQuantArray.toArray())){
            result = true;
        };
        return result;
    };

    private class GetCustomerData extends AsyncTask<Void, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(mCtx);
            progressDialog.setMessage(mCtx.getString(R.string.cust_info));
            progressDialog.setCancelable(false);
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            HashMap<String,String> param = new HashMap<>();
            param.put("custph", phoneNoEt.getText().toString());
            WebRequest webRequest= new WebRequest();
            String response =  webRequest.makeWebServiceCall(WebServiceUrl.BASE_URL + WebServiceUrl.GET_CUSTOMER_DATA, webRequest.GET, param, readPref.getAuthToken());
            return response ;

        }

        @Override
        protected void onPostExecute(String result) {
            parseJson(result);
            progressDialog.dismiss();
            super.onPostExecute(result);
            if (fragment != null) {
                FragmentManager fragmentManager = ((Activity) mCtx).getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();
            }

        }
    }
    private class UpdateOrderData extends AsyncTask<Void, Void, String> {
        ProgressDialog progressDialog;
        boolean custFlag = false;

        public UpdateOrderData(boolean custFlag) {
            super();
            this.custFlag = custFlag;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(mCtx);
            progressDialog.setMessage(mCtx.getString(R.string.update_cart));
            progressDialog.setCancelable(false);
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            String updatedParams = updatedData();
            WebServicePostCall webServicePostCal=new WebServicePostCall();
            String response =  webServicePostCal.excutePost(WebServiceUrl.BASE_URL + WebServiceUrl.UPDATE_ORDER_DATA, updatedParams, readPref.getAuthToken());
            return response ;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            parseUpdatedOrderJson(result);
            if(custFlag) {
                new GetCustomerData().execute();
            }else {
                if (fragment != null) {
                    FragmentManager fragmentManager = ((Activity) mCtx).getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .commit();
                }
            }
            super.onPostExecute(result);
        }
    }

    public String updatedData() {
        // We should also save prod and quant data in shared pref as done in generateOrderid fragment
        JSONObject newData = new JSONObject();
        JSONObject orderData =  new JSONObject();
        JSONArray mProdJSONArray = new JSONArray(currentProdArray);
        JSONArray mQuantJSONArray = new JSONArray(currentQuantArray);
        try {
            orderData.put("timestamp",utilLib.getCurrentTimeStamp());
            orderData.put("barcode_data", mProdJSONArray);
            orderData.put("quant_array",mQuantJSONArray);
            orderData.put("machine_id", 999);
            newData.put("orderid",readPref.getOrderId());
            newData.put("order_data",orderData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newData.toString();
    }

    public void parseJson(String result){
        try {
            JSONObject newObj=new JSONObject(result);
            int status=newObj.optInt("status");
            String cust_name = "";
            String cust_ph = "";
            if (status==200){
               JSONArray customerDataArray =  newObj.optJSONArray("customer_data");
                if(customerDataArray.length() > 0) {
                    JSONObject customerData = customerDataArray.getJSONObject(0);
                    cust_name = customerData.optString("name");
                    cust_ph = customerData.optString("phone");
                    savePref.saveCustInfo(cust_name + ";CUSTOMER, " + cust_ph);
                }else {
                    cust_name = "New Customer";
                    cust_ph = phoneNoEt.getText().toString();
                    savePref.saveCustInfo(cust_name + ";CUSTOMER, " + cust_ph);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void parseUpdatedOrderJson(String json){
        try {
            JSONObject parentObj = new JSONObject(json);
            JSONObject updateCartData;
            int status=parentObj.optInt("status");
            if (status==200){
                updateCartData = parentObj.optJSONObject("order_data").optJSONObject("cart_data");
                Double update_amount = updateCartData.optDouble("amount_charge");
                savePref.saveAmountPaid(update_amount.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public class VHItem extends RecyclerView.ViewHolder{
        public TextView title,mrp,circleTxt,itemInfo,prodNameTv;
        public RelativeLayout basicView, clickView;
        public LinearLayout numbPickerView;
        public CardView cardView;
        public ImageView crossBtn,delete_prod;
        public VHItem(View itemView) {
            super(itemView);
            title=(TextView) itemView.findViewById(R.id.titleTv);
            mrp=(TextView) itemView.findViewById(R.id.prizeTv);
            circleTxt=(TextView) itemView.findViewById(R.id.circleTv);
            itemInfo = (TextView) itemView.findViewById(R.id.itemQuantityTv);
            basicView = (RelativeLayout) itemView.findViewById(R.id.basic_view);
            clickView = (RelativeLayout) itemView.findViewById(R.id.click_view);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            crossBtn = (ImageView) itemView.findViewById(R.id.crossBtn);
            numbPickerView = (LinearLayout) itemView.findViewById(R.id.numbPickerView);
            delete_prod = (ImageView) itemView.findViewById(R.id.delete_prod);
            prodNameTv = (TextView) itemView.findViewById(R.id.prodNameTv);
            Typeface RobotoThin=Typeface.createFromAsset(mCtx.getResources().getAssets(),mCtx.getString(R.string.roboto_thin));
            Typeface RobotoMed=Typeface.createFromAsset(mCtx.getResources().getAssets(), mCtx.getString(R.string.roboto_med));
            Typeface NotoSans=Typeface.createFromAsset(mCtx.getResources().getAssets(),mCtx.getString(R.string.noto_sans));
            circleTxt.setTypeface(RobotoThin);
            mrp.setTypeface(NotoSans);
            title.setTypeface(RobotoMed);
            prodNameTv.setTypeface(RobotoMed);
            itemInfo.setTypeface(NotoSans);

        }
    }
}
