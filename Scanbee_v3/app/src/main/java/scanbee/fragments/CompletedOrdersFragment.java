package scanbee.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;

import java.util.ArrayList;

import scanbee.activities.R;
import scanbee.adapters.OrdersAdapter;
import scanbee.models.CartModel;
import scanbee.utils.BasicSetup;
import scanbee.utils.TestingData;

public class CompletedOrdersFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<CartModel> cartModelArrayList;
    BasicSetup basicSetup;
    TestingData testingData;
    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = getActivity();
        testingData = new TestingData();
        View layout  = inflater.inflate(R.layout.fragment_completed_orders, container, false);;
        prepareCartModelArrayList(layout);
        return layout;
    }


    public void prepareRecyclerView(View parentLayout){
        mRecyclerView = (RecyclerView) parentLayout.findViewById(R.id.completed_orders_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new OrdersAdapter(activity, cartModelArrayList);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void prepareCartModelArrayList( View parentLayout){
        cartModelArrayList = new ArrayList<>();
        ArrayList<JSONObject> dummyCart = testingData.getDummyCart(5);
        for (int i=0 ;i< dummyCart.size() ;i++){
            JSONObject cartItem = dummyCart.get(i);
            CartModel cartModel = new CartModel(
                    cartItem.optString("prod_name"),
                    cartItem.optString("prod_qty"),
                    cartItem.optString("prod_price"),
                    cartItem.optString("prod_code"),
                    cartItem.optString("prod_content")
            );
            cartModelArrayList.add(cartModel);
        }
        prepareRecyclerView(parentLayout);
    }
}
