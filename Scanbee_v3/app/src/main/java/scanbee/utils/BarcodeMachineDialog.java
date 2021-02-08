package scanbee.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import scanbee.activities.R;

/**
 * Created by namra on 23/01/17.
 */

public class BarcodeMachineDialog {
    Context context;
    Dialog menu;
    TextView textIcon1;
    BasicSetup basicSetup;

    public BarcodeMachineDialog(Context context) {
        super();
        this.context = context;
        menu = new Dialog(context, R.style.DialogTheme);
        menu.setContentView(R.layout.barcode_dialog);
        basicSetup = new BasicSetup((Activity) context);
        setupView();
    }

    public void setupView(){
        textIcon1 = (TextView) menu.findViewById(R.id.text_icon1);

        textIcon1.setTypeface(basicSetup.getNuniEL());

    }

    public void show() {
        menu.show();
    }
}
