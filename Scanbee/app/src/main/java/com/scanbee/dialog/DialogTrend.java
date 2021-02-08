package com.scanbee.dialog;

/**
 * Created by kshitij on 4/29/2016.
 */

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scanbee.scanbee.R;

public class DialogTrend {

    Context context;
    Dialog dialog;
    String trend;
    Drawable alertIcon;
    String btnText;

    public DialogTrend(Context context, String trend, Drawable alertIcon) {
        super();
        this.context = context;
        this.trend = trend;
        this.alertIcon = alertIcon;
        this.btnText = "ok";
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.trends_dialog);
        dialog.setCancelable(false);
    }

    public boolean isDialogVisible() {
        if (dialog.isShowing()) {
            return true;

        } else {
            return false;
        }

    }

    public void show() {
        if (dialog != null) {
            Typeface Roboto=Typeface.createFromAsset(context.getResources().getAssets(),context.getString(R.string.roboto_font));
            Typeface NotoSans=Typeface.createFromAsset(context.getResources().getAssets(),context.getString(R.string.noto_sans));
            ImageView iconImage = (ImageView) dialog.findViewById(R.id.alert_icon);
            LinearLayout speedLayout = (LinearLayout) dialog.findViewById(R.id.speedLayout);
            LinearLayout paymentLayout = (LinearLayout) dialog.findViewById(R.id.paymentLayout);
            LinearLayout prodLayout = (LinearLayout) dialog.findViewById(R.id.prodLayout);

            if(trend=="SPEED") {
                speedLayout.setVisibility(View.VISIBLE);
                paymentLayout.setVisibility(View.GONE);
                prodLayout.setVisibility(View.GONE);

                TextView speedTv = (TextView) dialog.findViewById(R.id.speedTv);
                TextView speedId = (TextView) dialog.findViewById(R.id.speedId);
                speedTv.setTypeface(NotoSans);
                speedId.setTypeface(NotoSans);
            }else if (trend == "PAYMENT"){
                speedLayout.setVisibility(View.GONE);
                paymentLayout.setVisibility(View.VISIBLE);
                prodLayout.setVisibility(View.GONE);

                TextView cashTv = (TextView) dialog.findViewById(R.id.cashTv);
                TextView cashTxtTv = (TextView) dialog.findViewById(R.id.cashTxtTv);
                TextView cardTv = (TextView) dialog.findViewById(R.id.cardTv);
                TextView cardTxtTv = (TextView) dialog.findViewById(R.id.cardTxtTv);
                TextView creditTv = (TextView) dialog.findViewById(R.id.creditTv);
                TextView creditTxtTv = (TextView) dialog.findViewById(R.id.creditTxtTv);
                cashTv.setTypeface(NotoSans);
                cashTxtTv.setTypeface(NotoSans);
                cardTv.setTypeface(NotoSans);
                cardTxtTv.setTypeface(NotoSans);
                creditTv.setTypeface(NotoSans);
                creditTxtTv.setTypeface(NotoSans);
            }else if(trend == "PRODUCT"){
                speedLayout.setVisibility(View.GONE);
                paymentLayout.setVisibility(View.GONE);
                prodLayout.setVisibility(View.VISIBLE);
            }

            iconImage.setImageDrawable(alertIcon);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            dialog.show();
            Button okTv = (Button) dialog.findViewById(R.id.okTrendsTv);
            okTv.setText(btnText);
            okTv.setTypeface(Roboto);
            okTv.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    dialog.dismiss();

                }
            });


        }
    }

}
