package com.scanbee.dialog;

/**
 * Created by kshitij on 4/29/2016.
 */

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import com.scanbee.scanbee.R;

public class NoConnectionDialog {

    Context context;
    Dialog dialog;

    public NoConnectionDialog(Context context) {
        super();
        this.context = context;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.no_connection_dialog);

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
            TextView textTitle = (TextView) dialog.findViewById(R.id.title);
            //		textTitle.setTypeface(Typeface.createFromAsset(context.getAssets(),
            //				"ARIAL_40.TTF"));
            if (dialog.isShowing()) {
                dialog.dismiss();

            }
            dialog.show();
            TextView okTv = (TextView) dialog.findViewById(R.id.okTv);
            //	okTv.setTypeface(Typeface.createFromAsset(context.getAssets(),
            //			"eurof55.ttf"));
            okTv.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    dialog.dismiss();

                }
            });

        }
    }

}
