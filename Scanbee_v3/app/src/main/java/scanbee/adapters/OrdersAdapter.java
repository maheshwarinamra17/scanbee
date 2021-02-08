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
import scanbee.utils.TestingData;


public class OrdersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    TestingData testingData;
    ArrayList<CartModel> mCartDataList;
    Context mContext;
    BasicSetup basicSetup;

    public OrdersAdapter(Context ctx, ArrayList<CartModel> cartDataList){
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
        cartItem.prodPrice.setText(cartModel.getmProductPrice());
        cartItem.prodInfo.setText(cartModel.getmProductQty() + " x " +cartModel.getmProductContent());
        cartItem.circleSymbolText.setText(cartModel.getmProductCode().substring(0, 1));
    }

    @Override
    public int getItemCount() {
        return mCartDataList.size();
    }

    public class CartItem extends RecyclerView.ViewHolder{
        TextView prodName;
        TextView prodPrice;
        TextView prodInfo;
        TextView circleSymbolText;
        RelativeLayout circleSymbol;
        public CartItem(View itemView) {
            super(itemView);
            prodName =  (TextView) itemView.findViewById(R.id.prod_name);
            prodInfo =  (TextView) itemView.findViewById(R.id.prod_info);
            prodPrice =  (TextView) itemView.findViewById(R.id.prod_price);
            circleSymbolText =  (TextView) itemView.findViewById(R.id.circle_symbol_text);
            circleSymbol=  (RelativeLayout) itemView.findViewById(R.id.circle_symbol);

            prodName.setTypeface(basicSetup.getNuniR());
            prodInfo.setTypeface(basicSetup.getNuniSb());
            prodPrice.setTypeface(basicSetup.getNuniSb());
            circleSymbolText.setTypeface(basicSetup.getMontEL());
        }
    }



}


