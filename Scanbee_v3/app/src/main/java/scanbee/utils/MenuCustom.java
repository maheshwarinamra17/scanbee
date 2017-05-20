package scanbee.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import scanbee.activities.R;

/**
 * Created by namra on 23/01/17.
 */

public class MenuCustom {
    Context context;
    Dialog menu;
    TextView textIcon1, textIcon2, textIcon3, textIcon4, textIcon5, textIcon6;
    BasicSetup basicSetup;

    public MenuCustom(Context context) {
        super();
        this.context = context;
        menu = new Dialog(context, R.style.DialogTheme);
        menu.setContentView(R.layout.menu_dialog);
        basicSetup = new BasicSetup((Activity) context);
        setupView();
    }

    public void setupView(){
        textIcon1 = (TextView) menu.findViewById(R.id.text_icon1);
        textIcon2 = (TextView) menu.findViewById(R.id.text_icon2);
        textIcon3 = (TextView) menu.findViewById(R.id.text_icon3);
        textIcon4 = (TextView) menu.findViewById(R.id.text_icon4);
        textIcon5 = (TextView) menu.findViewById(R.id.text_icon5);
        textIcon6 = (TextView) menu.findViewById(R.id.text_icon6);

        textIcon1.setTypeface(basicSetup.getNuniEL());
        textIcon2.setTypeface(basicSetup.getNuniEL());
        textIcon3.setTypeface(basicSetup.getNuniEL());
        textIcon4.setTypeface(basicSetup.getNuniEL());
        textIcon5.setTypeface(basicSetup.getNuniEL());
        textIcon6.setTypeface(basicSetup.getNuniEL());

    }

    public void show() {
        menu.show();
    }
}
