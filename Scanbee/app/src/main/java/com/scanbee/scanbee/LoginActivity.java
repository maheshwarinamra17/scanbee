package com.scanbee.scanbee;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by kshitij on 5/5/2016.
 */
public class LoginActivity extends AppCompatActivity {
       Button loginBtn;
       TextView scanbeeName,scanbeeSubtext;
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
        Typeface RobotoThin=Typeface.createFromAsset(getResources().getAssets(),getString(R.string.roboto_light));
        Typeface NotoSans=Typeface.createFromAsset(getResources().getAssets(),getString(R.string.noto_sans));
        scanbeeName.setTypeface(RobotoThin);
        scanbeeSubtext.setTypeface(NotoSans);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
