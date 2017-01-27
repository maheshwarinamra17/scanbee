package scanbee.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import scanbee.utils.BasicSetup;
import scanbee.utils.TestingData;
import scanbee.utils.ToastCustom;

public class PaymentActivity extends AppCompatActivity {

    Activity activity;
    BasicSetup basicSetup;
    TestingData testingData;
    EditText focusEditText;
    EditText textCustomerMobile;
    Button proceedButton;
    ImageView eraseBtn;
    TextView headCustomerMobile;
    CardView keyboardLayout;
    RelativeLayout paymentDetails;
    TextView headTotalItems, headSubTotal, headDiscount, headTax, payHintText;
    TextView txtTotalItems, txtSubTotal, txtDiscount, txtTax, txtAmtPay, headAmtPay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        activity = this;
        basicSetup = new BasicSetup(activity);
        testingData = new TestingData();
        basicSetup.setupCustomToolbar(getString(R.string.payment_option));

        setupView();
        setupKeyboard();
        focusedEditText();
        preparePaymentDetails();
    }

    public void setupView(){
        keyboardLayout = (CardView) findViewById(R.id.keyboard_layout);
        headCustomerMobile = (TextView) findViewById(R.id.head_cust_mobile);
        textCustomerMobile = (EditText) findViewById(R.id.text_cust_mobile);
        proceedButton = (Button) findViewById(R.id.proceed_button);
        eraseBtn = (ImageView) findViewById(R.id.erase_btn);
        paymentDetails = (RelativeLayout) findViewById(R.id.payment_details);
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
        payHintText = (TextView) findViewById(R.id.pay_hint_text);

        headCustomerMobile.setTypeface(basicSetup.getNuniR());
        textCustomerMobile.setTypeface(basicSetup.getNuniL());
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
        payHintText.setTypeface(basicSetup.getNuniR());

        textCustomerMobile.requestFocus();
        focusEditText =  textCustomerMobile;
    }

    public void preparePaymentDetails(){
        JSONObject dummyPayment = testingData.getDummyPayment();
        txtTotalItems.setText(dummyPayment.optString("cart_number_items"));
        txtSubTotal.setText(dummyPayment.optString("cart_sub_total"));
        txtDiscount.setText("-" + dummyPayment.optString("cart_discount"));
        txtTax.setText("+" + dummyPayment.optString("cart_tax"));
        txtAmtPay.setText(dummyPayment.optString("cart_amt_pay"));
    }

    public void setupKeyboard(){

        final TextView key0 = (TextView) findViewById(R.id.textView0);
        final TextView key1 = (TextView) findViewById(R.id.textView1);
        final TextView key2 = (TextView) findViewById(R.id.textView2);
        final TextView key3 = (TextView) findViewById(R.id.textView3);
        final TextView key4 = (TextView) findViewById(R.id.textView4);
        final TextView key5 = (TextView) findViewById(R.id.textView5);
        final TextView key6 = (TextView) findViewById(R.id.textView6);
        final TextView key7 = (TextView) findViewById(R.id.textView7);
        final TextView key8 = (TextView) findViewById(R.id.textView8);
        final TextView key9 = (TextView) findViewById(R.id.textView9);
        final ImageView keyR = (ImageView) findViewById(R.id.textViewRed);
        final ImageView keyG = (ImageView) findViewById(R.id.textViewGreen);
        final ImageView keyB = (ImageView) findViewById(R.id.textViewBlue);
        final ImageView keyY = (ImageView) findViewById(R.id.textViewYellow);
        final ImageView keyEnter = (ImageView) findViewById(R.id.btn_enter);

        key0.setTypeface(basicSetup.getNuniL());
        key1.setTypeface(basicSetup.getNuniL());
        key2.setTypeface(basicSetup.getNuniL());
        key3.setTypeface(basicSetup.getNuniL());
        key4.setTypeface(basicSetup.getNuniL());
        key5.setTypeface(basicSetup.getNuniL());
        key6.setTypeface(basicSetup.getNuniL());
        key7.setTypeface(basicSetup.getNuniL());
        key8.setTypeface(basicSetup.getNuniL());
        key9.setTypeface(basicSetup.getNuniL());

        setKeyboardActionAdd(key0,"0");
        setKeyboardActionAdd(key1,"1");
        setKeyboardActionAdd(key2,"2");
        setKeyboardActionAdd(key3,"3");
        setKeyboardActionAdd(key4,"4");
        setKeyboardActionAdd(key5,"5");
        setKeyboardActionAdd(key6,"6");
        setKeyboardActionAdd(key7,"7");
        setKeyboardActionAdd(key8,"8");
        setKeyboardActionAdd(key9,"9");
        setKeyboardActionAdd(keyR,"R");
        setKeyboardActionAdd(keyG,"G");
        setKeyboardActionAdd(keyB,"B");
        setKeyboardActionAdd(keyY,"Y");
        setKeyboardActionDelete(eraseBtn);
        setKeyboardActionEnter(keyEnter);

    }

    public void focusedEditText(){

        textCustomerMobile.setOnTouchListener( new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                focusEditText =  textCustomerMobile;
                textCustomerMobile.requestFocus();
                hideSoftKeyboard(textCustomerMobile);
                keyboardLayout.setVisibility(View.VISIBLE);
                paymentDetails.setVisibility(View.GONE);
                proceedButton.setVisibility(View.GONE);
                return true;
            }
        });

    }
    public void hideSoftKeyboard(EditText editText) {
        if(editText!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    public void setKeyboardActionAdd(View v, final String s){
        v.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                focusEditText.append(s);
            }
        });
    }

    public void setKeyboardActionEnter(View v){
        v.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                ToastCustom customToast = new ToastCustom(activity);
                customToast.show("Keyboard close");
                keyboardLayout.setVisibility(View.GONE);
                paymentDetails.setVisibility(View.VISIBLE);
                proceedButton.setVisibility(View.VISIBLE);
            }
        });
    }

    public void setKeyboardActionDelete(View v){
        v.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                BaseInputConnection textFieldInputConnection = new BaseInputConnection(focusEditText, true);
                String text = focusEditText.getText().toString();
                if(text.length() > 0){
                    textFieldInputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                }
            }
        });
    }
}
