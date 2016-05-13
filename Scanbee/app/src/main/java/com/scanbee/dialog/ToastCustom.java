package com.scanbee.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.scanbee.scanbee.R;

public class ToastCustom {
	Context context;

	View layout;
	Toast toast;
	TextView toastText;

	@SuppressLint("InflateParams")
	public ToastCustom(Context context) {
		super();
		this.context = context;
		LayoutInflater inflater = LayoutInflater.from(context);
		layout = inflater.inflate(R.layout.toast_dialog, null);
		toastText = (TextView) layout.findViewById(R.id.toast_msg);
		Typeface NotoSans=Typeface.createFromAsset(context.getResources().getAssets(),context.getString(R.string.noto_sans));
		toastText.setTypeface(NotoSans);
		// Toast...
		toast = new Toast(context);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
	}

	public void show(String msg) {
		if (toast != null) {
			toastText.setText(Html.fromHtml(msg));
			toast.show();
		}
	}

	public void showTwoLine(String msg) {
		if (toast != null) {
			toastText = (TextView) layout.findViewById(R.id.toast_msg);
			toastText.setText((msg));
			toast.show();
		}
	}
}
