package com.globe.games.guess.utilities;


import com.globe.games.guess.R;

import android.content.Context;
import android.widget.Toast;

public class ErrorMessageUtility {
	
	private Context context;
	private static String ERROR_MESSAGE[] = new String[2];
	
	public ErrorMessageUtility(Context c){
		this.context = c;
		ERROR_MESSAGE[0] = context.getString(R.string.network_error);
		ERROR_MESSAGE[1] = context.getString(R.string.server_error);
	}
	
	public String getErrorMessage(String msg){
		int errCode = 1;
		if (msg.equalsIgnoreCase("network error")){
			errCode = 0;
		}
		return ERROR_MESSAGE[errCode];
	}
	
	public void showMessage(String msg){
		this.showMessage(msg, false);
	}
	
	public void showMessage(String msg, boolean isCustom){
		String message = msg;
		if(!isCustom){
			message = this.getErrorMessage(msg);
		}
		showToast(message);
	}
	
	public void showToast(String msg){
		Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}

}
