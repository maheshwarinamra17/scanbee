package com.scanbee.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

import com.scanbee.scanbee.R;

public class LoadingBar {

	 Context context;
	 Dialog progDialog;

	 public LoadingBar(Context context) {
		super();
		this.context = context;
	}

	public  Dialog show(String msg,Context context) {
		
		if(context == null){
			return null;
		}
		if(context instanceof Activity && ((Activity)context).isFinishing()){
			return null;
		}
		progDialog = new Dialog(context, android.R.style.Theme_Translucent);
		progDialog.requestWindowFeature((int) Window.FEATURE_NO_TITLE);
		progDialog.setContentView(R.layout.loader_dialog);
		progDialog.setCancelable(false);
			
			TextView text = (TextView) progDialog.findViewById(R.id.msg);
			text.setText(msg);
			progDialog.show();
			
			return progDialog;
	}

	public  void dismiss() {
		try{
		if (progDialog != null && progDialog.isShowing()) {
			progDialog.dismiss();
			progDialog = null;
		}else{
			if(progDialog != null){
				progDialog.dismiss();
				progDialog = null;
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setCancelable(){
		
		progDialog.setCancelable(true);
	}
	
	public  void dismissAndShowDialogToast(String msg) {
		
			ToastCustom  custom = new ToastCustom(context);
			custom.show(msg);
		
	}

}
