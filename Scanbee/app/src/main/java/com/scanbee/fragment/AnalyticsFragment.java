package com.scanbee.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.db.chart.listener.OnEntryClickListener;
import com.db.chart.model.LineSet;
import com.db.chart.view.AxisController;
import com.db.chart.view.LineChartView;
import com.db.chart.view.Tooltip;
import com.scanbee.dialog.DialogTrend;
import com.scanbee.scanbee.MainActivity;
import com.scanbee.scanbee.R;

public class AnalyticsFragment extends Fragment {
    View viewMain, tooltipView;
    TextView orderNumtv,orderSubtext,saleNumtv,salesSubtext,custNumtv,custSubtext,prodNumtv,prodSubtext,tredProd1,tredProd2,tredProd3,tredProd4,trendText;
    Typeface RobotoMed,Roboto,NotoSans;
    ImageButton speedBtn,payBtn,growthBtn,prodBtn;
    Tooltip tooltip;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewMain=inflater.inflate(R.layout.analytics_fragment,null,false);
        tooltipView = inflater.inflate(R.layout.tooltip,null,false);

        setupUi();
        LineChartView orderLinechart = (LineChartView) viewMain.findViewById(R.id.orderLinechart);
        String[] labels =  {"8","9","10","12","13","14","15","16","17","18","19","20"};
        float[] values = {(float)1.0,(float)2.0,(float)4.0,(float)8.5,(float)6.0,(float)3.0,(float)1.0,(float)2.0,(float)4.0,(float)8.5,(float)6.0,(float)3.0};
        orderLinechart(orderLinechart, labels, values, R.color.app_color, R.color.graph_shadow_blue);

        LineChartView salesLinechart = (LineChartView) viewMain.findViewById(R.id.salesLinechart);
        String[] sales_labels =  {"8","9","10","12","13","14","15","16","17","18","19","20"};
        float[] sales_values = {(float)4.0,(float)1.0,(float)2.0,(float)4.0,(float)8.5,(float)8.5,(float)6.0,(float)3.0,(float)6.0,(float)3.0,(float)1.0,(float)2.0,};
        orderLinechart(salesLinechart,sales_labels,sales_values,R.color.app_green,R.color.graph_shadow_green);

        setupActionBar();
        return viewMain;
    }

    public void orderLinechart(LineChartView lineChart, String[] labels, float[] values, int gr_color,int gr_fill_color){
        lineChart.setYAxis(false);
        lineChart.setXAxis(false);
        lineChart.setYLabels(AxisController.LabelPosition.NONE);
        lineChart.setAxisColor(getActivity().getResources().getColor(R.color.stroke_grey));
        lineChart.setLabelsColor(getActivity().getResources().getColor(R.color.grey));
        lineChart.setTypeface(NotoSans);
        LineSet dataset = new LineSet(labels, values);
        dataset.setSmooth(true);
        dataset.setThickness(getActivity().getResources().getDimension(R.dimen.lc_data_radius));
        dataset.setDotsRadius(getActivity().getResources().getDimension(R.dimen.lc_dot_radius));
        dataset.setDotsColor(getActivity().getResources().getColor(gr_color));
        dataset.setColor(getActivity().getResources().getColor(gr_color));
        dataset.setFill(getActivity().getResources().getColor(gr_fill_color));

//        lineChart.showTooltip(tooltipView, true);
        lineChart.addData(dataset);
        lineChart.show();

        lineChart.setOnEntryClickListener(new OnEntryClickListener() {
            @Override
            public void onClick(int setIndex, int entryIndex, Rect entryRect) {
                //Do things
            }
        });
    }
    public void setupUi(){

        RobotoMed=Typeface.createFromAsset(getResources().getAssets(),getString(R.string.roboto_med));
        NotoSans=Typeface.createFromAsset(getResources().getAssets(),getString(R.string.noto_sans));
        Roboto=Typeface.createFromAsset(getResources().getAssets(),getString(R.string.roboto_font));

        orderNumtv = (TextView) viewMain.findViewById(R.id.orderNumtv);
        orderSubtext = (TextView)viewMain.findViewById(R.id.orderSubtext);
        saleNumtv = (TextView)viewMain.findViewById(R.id.saleNumtv);
        salesSubtext = (TextView)viewMain.findViewById(R.id.salesSubtext);
        custNumtv = (TextView)viewMain.findViewById(R.id.custNumtv);
        custSubtext = (TextView)viewMain.findViewById(R.id.custSubtext);
        prodNumtv = (TextView)viewMain.findViewById(R.id.prodNumtv);
        prodSubtext = (TextView)viewMain.findViewById(R.id.prodSubtext);
        trendText = (TextView)viewMain.findViewById(R.id.trendText);

        speedBtn = (ImageButton)viewMain.findViewById(R.id.speedBtn);
        speedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogTrend(getActivity(),"SPEED",getActivity().getDrawable(R.drawable.timer_big)).show();
                return;
            }
        });
        payBtn = (ImageButton)viewMain.findViewById(R.id.payBtn);
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogTrend(getActivity(),"PAYMENT",getActivity().getDrawable(R.drawable.credit_g)).show();
                return;
            }
        });
        growthBtn = (ImageButton)viewMain.findViewById(R.id.growthBtn);
        growthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogTrend(getActivity(),"GROWTH",getActivity().getDrawable(R.drawable.growth_big)).show();
                return;
            }
        });
        prodBtn = (ImageButton)viewMain.findViewById(R.id.prodBtn);
        prodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogTrend(getActivity(),"PRODUCT",getActivity().getDrawable(R.drawable.idea_big)).show();
                return;
            }
        });
        orderNumtv.setTypeface(NotoSans);
        orderSubtext.setTypeface(NotoSans);
        saleNumtv.setTypeface(NotoSans);
        salesSubtext.setTypeface(NotoSans);
        custNumtv.setTypeface(NotoSans);
        custSubtext.setTypeface(NotoSans);
        prodNumtv .setTypeface(NotoSans);
        prodSubtext.setTypeface(NotoSans);
        trendText.setTypeface(RobotoMed);
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
            cancelButton.setVisibility(View.GONE);
            addMoreButton.setVisibility(View.VISIBLE);
            mTitle.setText(R.string.analytics);
        }
    }
}
