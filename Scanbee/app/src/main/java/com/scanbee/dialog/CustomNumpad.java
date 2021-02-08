package com.scanbee.dialog;

/**
 * Created by namra on 4/29/2016.
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

public class CustomNumpad {

    Context context;
    Dialog dialog;

    public CustomNumpad(Context context) {
        super();
        this.context = context;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.sb_keyboard_layout);
        dialog.setCancelable(true);
    }


    public boolean isDialogVisible() {
        if (dialog.isShowing()) {
            return true;
        } else {
            return false;
        }
    }

    public void show() {

    }
}
