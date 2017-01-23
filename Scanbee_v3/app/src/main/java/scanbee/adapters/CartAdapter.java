package scanbee.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import scanbee.activities.R;
import scanbee.models.CartModel;
import scanbee.utils.BasicSetup;


public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<CartModel> mCartDataList;
    Context mContext;
    BasicSetup basicSetup;

    public CartAdapter(Context ctx, ArrayList<CartModel> cartDataList){
        this.mContext = ctx;
        this.mCartDataList = cartDataList;
        basicSetup = new BasicSetup((Activity) ctx);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View cartView = inflater.inflate(R.layout.adapter_cart, parent, false);
        return new CartItem(cartView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        final CartModel cartModel = mCartDataList.get(position);
        final CartItem cartItem = (CartItem) viewHolder;
        cartItem.prodName.setText(cartModel.getmProductName());
        cartItem.prodCode.setText(cartModel.getmProductCode());
        cartItem.prodPrice.setText(mContext.getString(R.string.rupee_symbol) + cartModel.getmProductPrice());
        cartItem.prodInfo.setText(cartModel.getmProductQty() + " x " +cartModel.getmProductContent());
        setCardStyle(cartModel.getmProductCode().substring(0, 1),cartItem);
        extendCartItemInfo(cartItem);
    }

    @Override
    public int getItemCount() {
        return mCartDataList.size();
    }

    public class CartItem extends RecyclerView.ViewHolder{
        TextView prodName;
        TextView prodCode;
        TextView prodPrice;
        TextView prodInfo;
        RelativeLayout innerCartItemView;
        RelativeLayout cartItemView;
        RelativeLayout extendProdInfo;
        TextView hideExtendProdInfo;
        public CartItem(View itemView) {
            super(itemView);
            innerCartItemView = (RelativeLayout) itemView.findViewById(R.id.inner_cart_view);
            extendProdInfo = (RelativeLayout) itemView.findViewById(R.id.extend_prod_info);
            cartItemView = (RelativeLayout) itemView.findViewById(R.id.cart_item_view);
            hideExtendProdInfo = (TextView) itemView.findViewById(R.id.hide_extend_info);
            prodName =  (TextView) itemView.findViewById(R.id.prod_name);
            prodCode =  (TextView) itemView.findViewById(R.id.prod_code);
            prodInfo =  (TextView) itemView.findViewById(R.id.prod_info);
            prodPrice =  (TextView) itemView.findViewById(R.id.prod_price);
            prodName.setTypeface(basicSetup.getNuniSb());
            prodInfo.setTypeface(basicSetup.getNuniSb());
            prodPrice.setTypeface(basicSetup.getNuniSb());
            prodCode.setTypeface(basicSetup.getMontR());
        }
    }

    public void extendCartItemInfo(final CartItem cartItem){
        cartItem.cartItemView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                ViewGroup.LayoutParams layoutParams = cartItem.cartItemView.getLayoutParams();
                layoutParams.height = (int) mContext.getResources().getDimension(R.dimen.card_height);
                cartItem.cartItemView.setLayoutParams(layoutParams);
                cartItem.extendProdInfo.setVisibility(View.VISIBLE);
            }
        });

        cartItem.hideExtendProdInfo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                ViewGroup.LayoutParams layoutParams = cartItem.cartItemView.getLayoutParams();
                layoutParams.height = (int) mContext.getResources().getDimension(R.dimen.card_height_small);
                cartItem.cartItemView.setLayoutParams(layoutParams);
                cartItem.extendProdInfo.setVisibility(View.GONE);
            }
        });
    }

    public void setCardStyle(String color, CartItem cartItem){
        switch (color) {
            case "R":
                cartItem.innerCartItemView.setBackground(mContext.getDrawable(R.drawable.sb_ripple_red_gradient));
                break;
            case "G":
                cartItem.innerCartItemView.setBackground(mContext.getDrawable(R.drawable.sb_ripple_green_gradient));
                break;
            case "B":
                cartItem.innerCartItemView.setBackground(mContext.getDrawable(R.drawable.sb_ripple_blue_gradient));
                break;
            case "Y":
                cartItem.innerCartItemView.setBackground(mContext.getDrawable(R.drawable.sb_ripple_yellow_gradient));
                break;
        }
    }

}


