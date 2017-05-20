package scanbee.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import scanbee.utils.BasicSetup;
import scanbee.utils.ToastCustom;

public class LoginActivity extends AppCompatActivity {

    Activity activity;
    BasicSetup basicSetup;
    TextView signupBtn, headSubTitle, forgotBtn;
    EditText txtUsername, txtPasswd;
    Button proceedButton;
    VideoView loginBackground;
    RelativeLayout loginLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this;
        basicSetup = new BasicSetup(activity);
        setupView();
        setupVideoBackground();
        softKeyboardAdjustments();
        setProceedButton();
    }

    @Override
    public void onResume(){
        super.onResume();
        setupVideoBackground();

    }

    public void setupView(){

        loginBackground = (VideoView) findViewById(R.id.login_background);
        proceedButton = (Button) findViewById(R.id.proceed_button);
        signupBtn = (TextView) findViewById(R.id.signup);
        headSubTitle = (TextView) findViewById(R.id.head_sub_title);
        forgotBtn = (TextView) findViewById(R.id.forgot);
        txtUsername = (EditText) findViewById(R.id.text_username);
        txtPasswd = (EditText) findViewById(R.id.text_passwd);
        loginLayout = (RelativeLayout) findViewById(R.id.login_layout);

        signupBtn.setTypeface(basicSetup.getNuniR());
        proceedButton.setTypeface(basicSetup.getNuniR());
        forgotBtn.setTypeface(basicSetup.getNuniR());
        headSubTitle.setTypeface(basicSetup.getNuniR());
        txtPasswd.setTypeface(basicSetup.getNuniL());
        txtUsername.setTypeface(basicSetup.getNuniL());
    }

    public void softKeyboardAdjustments(){
        loginLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = loginLayout.getRootView().getHeight() - loginLayout.getHeight();
                if (heightDiff > 500) {
                    loginBackground.setVisibility(View.GONE);
                    proceedButton.setVisibility(View.GONE);
                }else {
                    loginBackground.setVisibility(View.VISIBLE);
                    proceedButton.setVisibility(View.VISIBLE);
                    loginBackground.start();
                }
            }
        });
    }

    public void setupVideoBackground(){
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.img_background);
        loginBackground.setVideoURI(uri);
        loginBackground.start();
        loginBackground.setZOrderOnTop(true);
        loginBackground.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
    }

    public void setProceedButton(){

        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


}
