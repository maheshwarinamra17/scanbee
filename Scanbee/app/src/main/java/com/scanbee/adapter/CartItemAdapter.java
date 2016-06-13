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
import com.scanbee.servercommunication.WebServicePostCall;
import com.scanbee.servercommunication.WebServiceUrl;
import com.scanbee.sharedpref.ReadPref;
import com.scanbee.sharedpref.SavePref;
import com.scanbee.util.NumberPicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

    public CartItemAdapter(HeaderItemModelClass header, ArrayList<CartItemModelClass> cartItemDataList, Context mCtx, ArrayList<String> currentProdArray,ArrayList<String>  currentQuantArray) {
        this.cartItemDataList=cartItemDataList;
        this.currentProdArray = currentProdArray;
        this.currentQuantArray = currentQuantArray;
        this.header = header;
        this.mCtx = mCtx;
        this.amount_charge = header.getAmount();
        this.readPref=new ReadPref(mCtx);
        this.savePref=new SavePref(mCtx);
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType == TYPE_HEADER)
            {
                View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_header_item,parent,false);
                return new VHHeader(headerView);
            }
            else if(viewType == TYPE_ITEM)
            {
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
            VHheader.chargRsBtn.setText("Charge  "+ mCtx.getString(R.string.Rs) + String.valueOf(header.getAmount()));
            VHheader.chargRsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customerDialog();
                }
            });

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
                                                       modelClass.setQuantity(numberPicker.getCount());
                                                       savePref.saveItemsScanned(readPref.getItemsScanned() + numberPicker.getCount() - oldQuant);
                                                       currentQuantArray.add(currentProdArray.indexOf(modelClass.getItemId()), String.valueOf(numberPicker.getCount()));
                                                       VHItem.mrp.setText(mCtx.getString(R.string.Rs) + String.valueOf(modelClass.getMrp() * modelClass.getQuantity()));
                                                       String itemInfoText = String.valueOf(modelClass.getQuantity()) + " x " + mCtx.getString(R.string.Rs) + modelClass.getMrp() + ", " +
                                                               modelClass.getContent() + " " + modelClass.getContentItem();
                                                       VHItem.itemInfo.setText(itemInfoText);
                                                   }
                                               }
            );

            VHItem.delete_prod.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          VHItem.cardView.setVisibility(View.GONE);
                                                          int index = currentProdArray.indexOf(modelClass.getItemId());
                                                          int oldQuant = modelClass.getQuantity();
                                                          savePref.saveItemsScanned(readPref.getItemsScanned() - oldQuant);
                                                          currentQuantArray.remove(index);
                                                          currentProdArray.remove(index);
                                                          if (currentProdArray.size() == 0) {
                                                              new DialogCustom(mCtx, mCtx.getString(R.string.empty_cart), mCtx.getDrawable(R.drawable.fishy),
                                                                      mCtx.getString(R.string.ok), mCtx.getString(R.string.new_order)).show();

                                                          }
                                                          ToastCustom customToast = new ToastCustom(mCtx);
                                                          customToast.show(VHItem.title.getText() + " " + mCtx.getString(R.string.item_removed));
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

    public void customerDialog(){
        final Dialog dialog = new Dialog(mCtx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.customer_dialog);
        dialog.show();
        MaterialEditText phoneNoEt = (MaterialEditText)dialog.findViewById(R.id.phoneNoET);
        Button skipBtn =(Button)dialog.findViewById(R.id.skipButton);
        Button okBtn =(Button)dialog.findViewById(R.id.okButton);
        savePref.saveAmountPaid(amount_charge.toString());
        final Fragment fragment = new PaymentGetWayFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("items_scanned", readPref.getItemsScanned());
        fragment.setArguments(bundle);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GeneratePaymentAsynctask().execute();
                dialog.dismiss();
                if (fragment != null) {
                    FragmentManager fragmentManager = ((Activity) mCtx).getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .commit();
                }            }
        });
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GeneratePaymentAsynctask().execute();
                dialog.dismiss();
                if (fragment != null) {
                    FragmentManager fragmentManager = ((Activity) mCtx).getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .commit();
                }            }
        });
    }

    public String createJsonData() throws JSONException {
        JSONObject mainJsonObject = new JSONObject();
        JSONObject childJsonObject = new JSONObject();

        mainJsonObject.put("payment_type",1);
        mainJsonObject.put("cust_id",1);
        mainJsonObject.put("order_id", readPref.getOrderId());
        mainJsonObject.put("amount_paid",amount_charge);
        mainJsonObject.put("gateway_data",childJsonObject);
        childJsonObject.put("id", "pay_29QQoUBi66xm2f");
        childJsonObject.put("amount", 5000);
        childJsonObject.put("status", 200);
        childJsonObject.put("message", "captured");
        return mainJsonObject.toString();
    }
    private class GeneratePaymentAsynctask extends AsyncTask<Void, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(mCtx);
            progressDialog.setMessage(mCtx.getString(R.string.pay_prod));
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            String parm = null;
            try {
                parm = createJsonData();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            WebServicePostCall webServicePostCal=new WebServicePostCall();
            String response =  webServicePostCal.excutePost(WebServiceUrl.BASE_URL+WebServiceUrl.GENERATE_PAYMENT_DATA, parm,readPref.getAuthToken());
            return response ;

        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            Fragment pFragment = new PaymentGetWayFragment();
            if(pFragment.isAdded()) {
                super.onPostExecute(result);
            }
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
