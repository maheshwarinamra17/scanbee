package com.scanbee.scanbee;

/**
 * Created by kshitij on 4/25/2016.
 */

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scanbee.dialog.ToastCustom;
import com.scanbee.fragment.AnalyticsFragment;
import com.scanbee.fragment.GenerateOrderidFragment;
import com.scanbee.fragment.PaymentGetWayFragment;
import com.scanbee.fragment.SettingFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener  {
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().replace(R.id.content_frame, new GenerateOrderidFragment()).commit();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#115292"));
        }
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerUI();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Typeface NotoSans=Typeface.createFromAsset(getResources().getAssets(),getString(R.string.noto_sans));
        Menu navMenu = navigationView.getMenu();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        ToastCustom customToast = new ToastCustom(getApplicationContext());
        customToast.show(getString(R.string.not_allowed));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        // int id = item.getItemId();
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.all_press_news:
                //mtoolbatTitle.setText("Item1");
             //   fragment = new GenerateOrderidFragment();
                break;
            case R.id.art_entertainment:
              //  mtoolbatTitle.setText("Item2");
              //  fragment = new CartItemFragment();
                break;
        }
       /* if (fragment !=null) {
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        }*/


        return true;
    }

    public void drawerUI(){
        TextView nav_row1 = (TextView) drawer.findViewById(R.id.navrowText1);
        TextView nav_row2 = (TextView) drawer.findViewById(R.id.navrowText2);
        TextView nav_row3 = (TextView) drawer.findViewById(R.id.navrowText3);
        TextView nav_row4 = (TextView) drawer.findViewById(R.id.navrowText4);
        TextView nav_row5 = (TextView) drawer.findViewById(R.id.navrowText5);
        TextView nav_row6 = (TextView) drawer.findViewById(R.id.navrowText6);
        TextView nav_row7 = (TextView) drawer.findViewById(R.id.navrowText7);

        LinearLayout linearLayout1 = (LinearLayout) drawer.findViewById(R.id.shopLinearLayout);
        LinearLayout linearLayout2 = (LinearLayout) drawer.findViewById(R.id.orderLinearLayout);
        LinearLayout linearLayout3 = (LinearLayout) drawer.findViewById(R.id.custLinearLayout);
        LinearLayout linearLayout4 = (LinearLayout) drawer.findViewById(R.id.iventryLinearLayout);
        LinearLayout linearLayout5 = (LinearLayout) drawer.findViewById(R.id.analyticsLinearLayout);
        LinearLayout linearLayout6 = (LinearLayout) drawer.findViewById(R.id.askScanBeeLinearLayout);
        LinearLayout linearLayout7= (LinearLayout) drawer.findViewById(R.id.settingLinearLayout);
        linearLayout1.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);
        linearLayout3.setOnClickListener(this);
        linearLayout4.setOnClickListener(this);
        linearLayout5.setOnClickListener(this);
        linearLayout6.setOnClickListener(this);
        linearLayout7.setOnClickListener(this);

        TextView navHeaderText = (TextView) drawer.findViewById(R.id.storenametv);
        TextView nvHeaderCaption = (TextView) drawer.findViewById(R.id.userstoretv);

        Typeface Roboto=Typeface.createFromAsset(getResources().getAssets(),getString(R.string.roboto_font));
        Typeface RobotoMed=Typeface.createFromAsset(getResources().getAssets(),getString(R.string.roboto_med));
        Typeface NotoSans=Typeface.createFromAsset(getResources().getAssets(),getString(R.string.noto_sans));

        nav_row1.setTypeface(Roboto);
        nav_row2.setTypeface(Roboto);
        nav_row3.setTypeface(Roboto);
        nav_row4.setTypeface(Roboto);
        nav_row5.setTypeface(Roboto);
        nav_row6.setTypeface(Roboto);
        nav_row7.setTypeface(Roboto);
        navHeaderText.setTypeface(RobotoMed);
        nvHeaderCaption.setTypeface(NotoSans);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shopLinearLayout:
                getFragmentManager().beginTransaction().replace(R.id.content_frame,new GenerateOrderidFragment()).commit();
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                break;
            case R.id.orderLinearLayout:
                getFragmentManager().beginTransaction().replace(R.id.content_frame,new PaymentGetWayFragment()).commit();
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                break;
            case R.id.custLinearLayout:
                getFragmentManager().beginTransaction().replace(R.id.content_frame,new PaymentGetWayFragment()).commit();
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                break;
            case R.id.iventryLinearLayout:
                getFragmentManager().beginTransaction().replace(R.id.content_frame,new PaymentGetWayFragment()).commit();
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                break;
            case R.id.analyticsLinearLayout:
                getFragmentManager().beginTransaction().replace(R.id.content_frame,new AnalyticsFragment()).commit();
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                break;
            case R.id.askScanBeeLinearLayout:
                getFragmentManager().beginTransaction().replace(R.id.content_frame,new PaymentGetWayFragment()).commit();
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                break;
            case R.id.settingLinearLayout:
                getFragmentManager().beginTransaction().replace(R.id.content_frame,new SettingFragment()).commit();
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                break;
        }
    }
}
