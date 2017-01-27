package scanbee.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import scanbee.utils.BasicSetup;
import scanbee.utils.TestingData;

public class InvoiceActivity extends AppCompatActivity {

    Activity activity;
    BasicSetup basicSetup;
    TestingData testingData;
    Button proceedButton;
    TextView headTotalItems, headSubTotal, headDiscount, headTax, headOrderId ;
    TextView txtTotalItems, txtSubTotal, txtDiscount, txtTax, txtAmtPay, headAmtPay, orderStatusText, txtOrderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        activity = this;
        basicSetup = new BasicSetup(activity);
        testingData = new TestingData();
        basicSetup.setupCustomToolbar(getString(R.string.invoice));

        setupView();
    }


    public void setupView(){
        proceedButton = (Button) findViewById(R.id.proceed_button);
        headTotalItems = (TextView) findViewById(R.id.head_total_items);
        headSubTotal = (TextView) findViewById(R.id.head_sub_total);
        headDiscount = (TextView) findViewById(R.id.head_discount);
        headTax = (TextView) findViewById(R.id.head_tax);
        txtTotalItems = (TextView) findViewById(R.id.txt_total_items);
        txtSubTotal = (TextView) findViewById(R.id.txt_sub_total);
        txtDiscount = (TextView) findViewById(R.id.txt_discount);
        txtTax = (TextView) findViewById(R.id.txt_tax);
        txtAmtPay = (TextView) findViewById(R.id.txt_amt_pay);
        headAmtPay = (TextView) findViewById(R.id.head_amt_pay);
        orderStatusText = (TextView) findViewById(R.id.orde_status_text);
        headOrderId = (TextView) findViewById(R.id.head_order_id);
        txtOrderId = (TextView) findViewById(R.id.txt_order_id);

        proceedButton.setTypeface(basicSetup.getNuniR());
        headTotalItems.setTypeface(basicSetup.getNuniR());
        headSubTotal.setTypeface(basicSetup.getNuniR());
        headDiscount.setTypeface(basicSetup.getNuniR());
        headTax.setTypeface(basicSetup.getNuniR());
        txtTotalItems.setTypeface(basicSetup.getNuniR());
        txtSubTotal.setTypeface(basicSetup.getNuniR());
        txtDiscount.setTypeface(basicSetup.getNuniR());
        txtTax.setTypeface(basicSetup.getNuniR());
        txtAmtPay.setTypeface(basicSetup.getNuniL());
        headAmtPay.setTypeface(basicSetup.getNuniR());
        txtOrderId.setTypeface(basicSetup.getNuniL());
        headOrderId.setTypeface(basicSetup.getNuniR());
        orderStatusText.setTypeface(basicSetup.getNuniR());

    }
}
