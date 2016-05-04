package com.scanbee.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
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

	@SuppressLint("InflateParams")
	public ToastCustom(Context context) {
		super();
		this.context = context;
		LayoutInflater inflater = LayoutInflater.from(context);

		layout = inflater.inflate(R.layout.toast_dialog, null);

		// Toast...
		toast = new Toast(context);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		// toast.show();
	}

	public void show(String msg) {
		if (toast != null) {
			// set a message
			TextView text = (TextView) layout.findViewById(R.id.msg);
			text.setText(Html.fromHtml(msg));
		//	text.setTypeface(Typeface.createFromAsset(context.getAssets(),
		//			"eurof55.ttf"));
			toast.show();
		}
	}

	public void showTwoLine(String msg) {
		if (toast != null) {
			// set a message
			TextView text = (TextView) layout.findViewById(R.id.msg);
			text.setText((msg));
		//	text.setTypeface(Typeface.createFromAsset(context.getAssets(),
		//			"eurof55.ttf"));
			toast.show();
		}
	}
}
