package com.scanbee.scanbee;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.scanbee.dialog.ToastCustom;
import com.scanbee.servercommunication.WebRequest;
import com.scanbee.servercommunication.WebServiceUrl;
import com.scanbee.sharedpref.ReadPref;
import com.scanbee.sharedpref.SavePref;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by kshitij on 5/5/2016.
 */
public class LoginActivity extends AppCompatActivity {
    Button loginBtn;
    TextView scanbeeName, scanbeeSubtext, loginHelp;
    ImageView logoImg;
    MaterialEditText userName,userPassword;
    String user_name;
    String user_password;
    ToastCustom customToast;
    ProgressDialog progressDialog;
    SavePref savePref;
    ReadPref readPref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#212121"));
        }

        readPref = new ReadPref(getApplicationContext());
        savePref = new SavePref(getApplicationContext());
        setupUI();
        softKeyboardAdjustments();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_name=userName.getText().toString().trim();
                user_password=userPassword.getText().toString().trim();
                if (user_name.isEmpty() || user_password.isEmpty()){
                    customToast = new ToastCustom(getApplicationContext());
                    customToast.show(getApplicationContext().getString(R.string.unp_error));
                }else {
                    new GetOrderIdAsynctask().execute();
                }
            }
        });
        loginHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpDialog(LoginActivity.this, "Need help? call us at " + getString(R.string.helpline));
            }
        });
    }

    public void setupUI()
    {
        progressDialog=new ProgressDialog(LoginActivity.this);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        scanbeeName = (TextView) findViewById(R.id.scanbeeName);
        scanbeeSubtext = (TextView) findViewById(R.id.scanbeeSubtext);
        loginHelp = (TextView) findViewById(R.id.loginHelp);
        logoImg = (ImageView) findViewById(R.id.logo);
        userName= (MaterialEditText)findViewById(R.id.userNameET);
        userPassword= (MaterialEditText)findViewById(R.id.passwordET);
        Typeface RobotoThin = Typeface.createFromAsset(getResources().getAssets(), getString(R.string.roboto_light));
        Typeface NotoSans = Typeface.createFromAsset(getResources().getAssets(), getString(R.string.noto_sans));
        scanbeeName.setTypeface(RobotoThin);
        scanbeeSubtext.setTypeface(NotoSans);
        loginHelp.setTypeface(NotoSans);


    }

    public void softKeyboardAdjustments(){
        final RelativeLayout activityRootView = (RelativeLayout) findViewById(R.id.activityRoot);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                if (heightDiff > 100) {
                    // if more than 100 pixels, its probably a keyboard...
                    scanbeeSubtext.setVisibility(View.GONE);
                    logoImg.setVisibility(View.GONE);
                    scanbeeName.setVisibility(View.GONE);

                }else {
                    scanbeeSubtext.setVisibility(View.VISIBLE);
                    logoImg.setVisibility(View.VISIBLE);
                    scanbeeName.setVisibility(View.VISIBLE);

                }

            }
        });
    }

    public void helpDialog(Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialog);

        Typeface RobotoMed = Typeface.createFromAsset(activity.getResources().getAssets(), activity.getString(R.string.roboto_med));
        Typeface Roboto = Typeface.createFromAsset(activity.getResources().getAssets(), activity.getString(R.string.roboto_font));
        TextView textTitle = (TextView) dialog.findViewById(R.id.title);
        ImageView iconImage = (ImageView) dialog.findViewById(R.id.alert_icon);
        textTitle.setText(msg);
        Drawable helpIcon = activity.getDrawable(R.drawable.help_2);
        iconImage.setImageDrawable(helpIcon);
        textTitle.setTypeface(RobotoMed);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        Button okTv = (Button) dialog.findViewById(R.id.okTv);
        okTv.setText(getString(R.string.call));
        okTv.setTypeface(Roboto);
        okTv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + getString(R.string.helpline)));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });

        Button auxBtn = (Button) dialog.findViewById(R.id.auxBtn);
        auxBtn.setVisibility(View.VISIBLE);
        auxBtn.setText(getString(R.string.cancel));
        auxBtn.setTypeface(Roboto);
        auxBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });
        dialog.show();

    }
    private class GetOrderIdAsynctask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage(getApplicationContext().getString(R.string.load_logging));
            progressDialog.setCancelable(false);
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            HashMap<String,String> param=new HashMap<>();
            param.put("username", user_name);
            param.put("password", user_password);
            WebRequest webRequest=new WebRequest();
            String response =  webRequest.makeWebServiceCall(WebServiceUrl.BASE_URL+WebServiceUrl.GET_USER_LOGIN,WebRequest.POST,param,readPref.getAuthToken());
            return response ;

        }

        @Override
        protected void onPostExecute(String result) {
                progressDialog.dismiss();
                parseJson(result);
                super.onPostExecute(result);
            }
        }
    public void parseJson(String result){
        try {
            JSONObject newObj=new JSONObject(result);
            int status=newObj.optInt("status");
            JSONObject userData =  newObj.optJSONObject("user_data");
            if (status==200){
                String authToken = userData.optString("auth_token");
                savePref.saveAuthToken(authToken);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }else{
                String message = newObj.optString("message");
                customToast = new ToastCustom(getApplicationContext());
                customToast.show(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
