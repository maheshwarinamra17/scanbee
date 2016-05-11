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
import android.widget.TextView;

import com.scanbee.scanbee.R;

public class DialogCustom {

    Context context;
    Dialog dialog;
    String message;
    Drawable alertIcon;
    String btnText;
    String btnAux;

    public DialogCustom(Context context, String message, Drawable alertIcon, String btnText) {
        super();
        this.context = context;
        this.message = message;
        this.alertIcon = alertIcon;
        this.btnText = btnText;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCancelable(false);
    }

    public DialogCustom(Context context, String message, Drawable alertIcon, String btnText, String btnAux) {
        super();
        this.context = context;
        this.message = message;
        this.alertIcon = alertIcon;
        this.btnText = btnText;
        this.btnAux = btnAux;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialog);
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
            TextView textTitle = (TextView) dialog.findViewById(R.id.title);
            ImageView iconImage = (ImageView) dialog.findViewById(R.id.alert_icon);
            textTitle.setText(message);
            iconImage.setImageDrawable(alertIcon);
            textTitle.setTypeface(RobotoMed);
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

            if(btnAux != null){
                Button auxBtn = (Button) dialog.findViewById(R.id.auxBtn);
                auxBtn.setVisibility(View.VISIBLE);
                auxBtn.setText(btnAux);
                auxBtn.setTypeface(Roboto);
                auxBtn.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });
            }

        }
    }

}
