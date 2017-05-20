package scanbee.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import scanbee.utils.BasicSetup;
import scanbee.utils.MenuCustom;
import scanbee.utils.ToastCustom;

public class MainActivity extends AppCompatActivity {

    BasicSetup basicSetup;
    TextView headQty;
    EditText textQty;
    TextView emptyCart;
    TextView headProdCode;
    EditText textProdCode;
    TextView headMultipler;
    Button proceedButton;
    ImageView eraseBtn, customMenu, barcodeBtn;
    EditText focusEditText;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        basicSetup = new BasicSetup(activity);
        basicSetup.setupCustomToolbar(getString(R.string.add_product));

        setupView();
        setupKeyboard();
        focusedEditText();
        setProceedButton();
    }

    public void setupView(){
        headQty = (TextView) findViewById(R.id.head_qty);
        textQty = (EditText) findViewById(R.id.text_qty);
        headProdCode = (TextView) findViewById(R.id.head_prod_code);
        textProdCode = (EditText) findViewById(R.id.text_prod_code);
        headMultipler = (TextView) findViewById(R.id.head_multiplier);
        proceedButton = (Button) findViewById(R.id.proceed_button);
        eraseBtn = (ImageView) findViewById(R.id.erase_btn);
        emptyCart = (TextView) findViewById(R.id.empty_cart);
        customMenu = (ImageView) findViewById(R.id.custom_menu_btn);
        barcodeBtn = (ImageView) findViewById(R.id.breader_btn);
        headQty.setTypeface(basicSetup.getNuniR());
        textQty.setTypeface(basicSetup.getNuniL());
        headProdCode.setTypeface(basicSetup.getNuniR());
        textProdCode.setTypeface(basicSetup.getNuniL());
        headMultipler.setTypeface(basicSetup.getNuniEL());
        emptyCart.setTypeface(basicSetup.getNuniR());
        proceedButton.setTypeface(basicSetup.getNuniR());

        textProdCode.requestFocus();
        focusEditText =  textProdCode;

    }

    public void focusedEditText(){

        textQty.setOnTouchListener( new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                focusEditText =  textQty;
                textQty.requestFocus();
                hideSoftKeyboard(textQty);
                return true;
            }
        });

        textProdCode.setOnTouchListener( new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                focusEditText =  textProdCode;
                textProdCode.requestFocus();
                hideSoftKeyboard(textProdCode);
                return true;
            }
        });

        textQty.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if(textQty.getText().length() == 2){
                    focusEditText = textProdCode;
                    textProdCode.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
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

        key0.setTypeface(basicSetup.getNuniEL());
        key1.setTypeface(basicSetup.getNuniEL());
        key2.setTypeface(basicSetup.getNuniEL());
        key3.setTypeface(basicSetup.getNuniEL());
        key4.setTypeface(basicSetup.getNuniEL());
        key5.setTypeface(basicSetup.getNuniEL());
        key6.setTypeface(basicSetup.getNuniEL());
        key7.setTypeface(basicSetup.getNuniEL());
        key8.setTypeface(basicSetup.getNuniEL());
        key9.setTypeface(basicSetup.getNuniEL());

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
        basicSetup.setCustomMenu(customMenu);
        basicSetup.setBarcodeDailog(barcodeBtn);

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
                customToast.show(getString(R.string.product_added));
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

    public void setProceedButton(){

        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


}
