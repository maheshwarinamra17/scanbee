package com.scanbee.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scanbee.model.CartItemModelClass;
import com.scanbee.scanbee.R;

import java.util.ArrayList;

/**
 * Created by kshitij on 4/28/2016.
 */
public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {
    ArrayList<CartItemModelClass> cartItemDataList;
    CartItemModelClass cartItemModelClass;
    Context mCtx;

    public CartItemAdapter(ArrayList<CartItemModelClass> cartItemDataList, Context mCtx) {
        this.cartItemDataList=cartItemDataList;
        this.mCtx = mCtx;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
       public TextView title,mrp,circleTxt,itemInfo;
        public ViewHolder(View itemView) {
            super(itemView);
            title=(TextView) itemView.findViewById(R.id.titleTv);
            mrp=(TextView) itemView.findViewById(R.id.prizeTv);
            circleTxt=(TextView) itemView.findViewById(R.id.circleTv);
            itemInfo = (TextView) itemView.findViewById(R.id.itemQuantityTv);
            Typeface Roboto=Typeface.createFromAsset(mCtx.getResources().getAssets(), mCtx.getString(R.string.roboto_font));
            Typeface RobotoThin=Typeface.createFromAsset(mCtx.getResources().getAssets(),mCtx.getString(R.string.roboto_thin));
            Typeface RobotoMed=Typeface.createFromAsset(mCtx.getResources().getAssets(), mCtx.getString(R.string.roboto_med));
            Typeface NotoSans=Typeface.createFromAsset(mCtx.getResources().getAssets(),mCtx.getString(R.string.noto_sans));
            circleTxt.setTypeface(Roboto);
            mrp.setTypeface(NotoSans);
            title.setTypeface(RobotoMed);
            itemInfo.setTypeface(NotoSans);

        }
    }
    @Override
    public CartItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row_cart_item,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartItemAdapter.ViewHolder holder, int position) {
         CartItemModelClass modelClass=cartItemDataList.get(position);
        holder.title.setText(modelClass.getTitle());
        holder.mrp.setText(mCtx.getString(R.string.Rs)+String.valueOf(modelClass.getMrp() * modelClass.getQuantity()));
        holder.circleTxt.setText(String.valueOf(modelClass.getTitle().charAt(0)));
        String itemInfoText = String.valueOf(modelClass.getQuantity()) + " x "+ mCtx.getString(R.string.Rs) + modelClass.getMrp()+ ", "+
            modelClass.getContent()+ " " + modelClass.getContentItem();
        holder.itemInfo.setText(itemInfoText);

    }

    @Override
    public int getItemCount() {
        return cartItemDataList.size();
    }
}
