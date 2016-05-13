package com.scanbee.scanbee;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by kshitij on 5/5/2016.
 */
public class LoginActivity extends AppCompatActivity {
       Button loginBtn;
       TextView scanbeeName,scanbeeSubtext,loginHelp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#212121"));
        }
        loginBtn = (Button) findViewById(R.id.loginBtn);
        scanbeeName = (TextView) findViewById(R.id.scanbeeName);
        scanbeeSubtext = (TextView) findViewById(R.id.scanbeeSubtext);
        loginHelp = (TextView) findViewById(R.id.loginHelp);
        Typeface RobotoThin=Typeface.createFromAsset(getResources().getAssets(),getString(R.string.roboto_light));
        Typeface NotoSans=Typeface.createFromAsset(getResources().getAssets(),getString(R.string.noto_sans));
        scanbeeName.setTypeface(RobotoThin);
        scanbeeSubtext.setTypeface(NotoSans);
        loginHelp.setTypeface(NotoSans);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        loginHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpDialog(LoginActivity.this,"Need help? call us at "+getString(R.string.helpline));
            }
        });

    }

    public void helpDialog(Activity activity, String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialog);

        Typeface RobotoMed=Typeface.createFromAsset(activity.getResources().getAssets(),activity.getString(R.string.roboto_med));
        Typeface Roboto=Typeface.createFromAsset(activity.getResources().getAssets(),activity.getString(R.string.roboto_font));
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
                callIntent.setData(Uri.parse("tel:"+getString(R.string.helpline)));
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

}
