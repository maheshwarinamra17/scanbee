package scanbee.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import scanbee.activities.R;

/**
 * Created by namra on 23/01/17.
 */

public class ToastCustom {
    Context context;
    View layout;
    Toast toast;
    TextView toastText;
    BasicSetup basicSetup;

    @SuppressLint("InflateParams")
    public ToastCustom(Context context) {
        super();
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        layout = inflater.inflate(R.layout.toast_dialog, null);
        basicSetup = new BasicSetup((Activity) context);
        toastText = (TextView) layout.findViewById(R.id.toast_msg);
        toastText.setTypeface(basicSetup.getNuniR());
        toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.setView(layout);
    }

    public void show(String msg) {
        if (toast != null) {
            toastText.setText(Html.fromHtml(msg));
            toast.show();
        }
    }
}
