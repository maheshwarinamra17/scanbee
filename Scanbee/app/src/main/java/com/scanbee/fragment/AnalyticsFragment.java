package com.scanbee.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.db.chart.view.ChartView;
import com.scanbee.scanbee.MainActivity;
import com.scanbee.scanbee.R;

public class AnalyticsFragment extends Fragment {
   View viewMain;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewMain=inflater.inflate(R.layout.analytics_fragment,null,false);


        Typeface RobotoMed=Typeface.createFromAsset(getResources().getAssets(),getString(R.string.roboto_med));
        Typeface NotoSans=Typeface.createFromAsset(getResources().getAssets(),getString(R.string.noto_sans));
        Typeface Roboto=Typeface.createFromAsset(getResources().getAssets(),getString(R.string.roboto_font));

        ChartView LineChart = (ChartView) viewMain.findViewById(R.id.linechart);
        setupActionBar();
        return viewMain;
    }

    private void setupActionBar() {
        ActionBar actionBar = ((MainActivity) getActivity())
                .getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(false);

        Activity activity = getActivity();
        if(activity != null){
            Toolbar toolbar = (Toolbar)activity.findViewById(R.id.toolbar);
            TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            ImageView cancelButton = (ImageView) activity.findViewById(R.id.cancelorder);
            ImageView addMoreButton = (ImageView) activity.findViewById(R.id.addmore);

            cancelButton.setVisibility(View.VISIBLE);
            addMoreButton.setVisibility(View.VISIBLE);
            mTitle.setText(R.string.analytics);
        }
    }
}
