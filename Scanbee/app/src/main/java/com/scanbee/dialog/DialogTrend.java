package com.scanbee.dialog;

/**
 * Created by kshitij on 4/29/2016.
 */

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.scanbee.scanbee.R;

public class DialogTrend {

    Context context;
    Dialog dialog;
    String trend;
    GradientDrawable alertIcon;
    String btnText;

    public DialogTrend(Context context, String trend, GradientDrawable alertIcon) {
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
            Typeface RobotoMed=Typeface.createFromAsset(context.getResources().getAssets(),context.getString(R.string.roboto_med));
            Typeface Roboto=Typeface.createFromAsset(context.getResources().getAssets(),context.getString(R.string.roboto_font));
            ImageView iconImage = (ImageView) dialog.findViewById(R.id.alert_icon);

            iconImage.setImageDrawable(alertIcon);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            dialog.show();
            Button okTv = (Button) dialog.findViewById(R.id.okTv);
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
