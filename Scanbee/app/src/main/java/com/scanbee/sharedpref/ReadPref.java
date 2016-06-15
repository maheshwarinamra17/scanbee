package com.scanbee.sharedpref;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class ReadPref {

	Context context;
	private SharedPreferences prefs;
	String myprefs = "scanbee";
	int mode = Activity.MODE_PRIVATE;
	boolean result = false;
	String TAG = "ReadPref";
	String res = "";
	int val;

	public ReadPref(Context context) {
		this.context = context;
		prefs = this.context.getSharedPreferences(myprefs, mode);
	}

	public int getOrderId() {
		val = prefs.getInt("order_id", 0);
		return val;
	}

	public String getIpAddress() {
		res = prefs.getString("ip_address", "");
		return res;
	}

	public String getOrderType() {
		res = prefs.getString("order_type", "");
		return res;
	}

	public String getOrderTimeStamp() {
		res = prefs.getString("timestamp", "");
		return res;
	}

	public String getAuthToken() {
		res = prefs.getString("auth_token", "");
		return res;
	}

	public String getAmountPaid() {
		res = prefs.getString("amount_paid", "");
		return res;
	}

	public String getUserInfo() {
		res = prefs.getString("user_info", "");
		return res;
	}

    public String getCustInfo() {
        res = prefs.getString("cust_info", "");
        return res;
    }

	public String getOrderProds() {
		res = prefs.getString("dsprods", "");
		return res;
	}

	public String getOrderQuants() {
		res = prefs.getString("dsquants", "");
		return res;
	}
    public int getItemsScanned() {
        val = prefs.getInt("items_scanned", 0);
        return val;
    }

}