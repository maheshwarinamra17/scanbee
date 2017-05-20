package scanbee.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONObject;

import java.util.ArrayList;

import scanbee.adapters.CartAdapter;
import scanbee.models.CartModel;
import scanbee.utils.BasicSetup;
import scanbee.utils.TestingData;

public class CartActivity extends AppCompatActivity {

    BasicSetup basicSetup;
    TestingData testingData;
    Activity activity;
    Button proceedButton;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<CartModel> cartModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        activity = this;
        basicSetup = new BasicSetup(activity);
        testingData = new TestingData();
        basicSetup.setupCustomToolbar(getString(R.string.order_cart));
        setupView();
        prepareCartModelArrayList();
        setProceedButton();
    }

    public void setupView(){
        proceedButton = (Button) findViewById(R.id.proceed_button);
        proceedButton.setTypeface(basicSetup.getNuniR());
    }

    public void prepareRecyclerView(){
        mRecyclerView = (RecyclerView) findViewById(R.id.cart_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        ViewGroup.LayoutParams params = mRecyclerView.getLayoutParams();
        params.height = basicSetup.getScreenHeight()
                            - (int) getResources().getDimension(R.dimen.toolbar_height)
                            - (int) getResources().getDimension(R.dimen.button_large)
                            - 7 * (int) getResources().getDimension(R.dimen.space_1x);
        mRecyclerView.setLayoutParams(params);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CartAdapter(activity, cartModelArrayList);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void prepareCartModelArrayList(){
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
        prepareRecyclerView();
    }

    public void setProceedButton(){

        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
