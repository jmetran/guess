package com.globe.games.guess.utilities;


import com.globe.games.guess.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

public class AlertUtility {

	private Activity activity;
	
	
	public AlertUtility(Activity _activity){
		this.activity = _activity;
	}
	
	public void showToast(String msg){
		Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
	}
	
	public void alertInfo(String msg) {
		this.alertInfo(null, msg);
	}
	
	public void alertInfo(String title, String msg) {
		AlertDialog.Builder alertbox = new AlertDialog.Builder(activity);
		if (title != null){
			alertbox.setTitle(title);
		}		
		alertbox.setMessage(msg);
		alertbox.setNeutralButton("Ok", null);
		alertbox.show();
	}
	
	public void alertConfirmation(String title, String msg, String btnLabelLeft, String btnLabelRight, boolean isGoBack, Class<?> _class){
		this.alertConfirmation(title, msg, btnLabelLeft, btnLabelRight, isGoBack, _class, null, null, false);
	}
	
	public void alertConfirmation(String title, String msg, String btnLabelLeft, String btnLabelRight, boolean isGoBack, boolean isExit){
		this.alertConfirmation(title, msg, btnLabelLeft, btnLabelRight, isGoBack, null, null, null, isExit);
	}
	
	public void alertConfirmation(String title, String msg, boolean isGoBack, Class<?> _class){
		this.alertConfirmation(title, msg, null, null, isGoBack, _class, null, null, false);
	}
	
	public void alertConfirmation(String title, String msg, String btnLabelLeft, String btnLabelRight, boolean isGoBack, Class<?> _class, String[] str, Object[] obj){
		this.alertConfirmation(title, msg, btnLabelLeft, btnLabelRight, isGoBack, _class, str, obj, false);
	}
	
	public void alertConfirmation(String title, String msg, String btnLabelLeft, String btnLabelRight, final boolean isGoBack, final Class<?> _class, final String[] str, final Object[] obj, final boolean isExit) {
		AlertDialog.Builder alertbox = new AlertDialog.Builder(activity);
		
		String _btnLabelLeft = btnLabelLeft;
		String _btnLabelRight = btnLabelRight;
		
		if (_btnLabelLeft == null){
			_btnLabelLeft = activity.getString(R.string.cancel);
		}
		
		if (_btnLabelRight == null){
			_btnLabelRight = activity.getString(R.string.ok);
		}
		
		if (title != null){
			alertbox.setTitle(title);
		}		
		alertbox.setMessage(msg);
		alertbox.setPositiveButton(_btnLabelLeft,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						if (isGoBack){
							activity.finish();
						}
					}
				});
		alertbox.setNegativeButton(_btnLabelRight, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				if (isExit){
					activity.finish();
				}else{
					if (str != null && obj != null){
						new IntentUtility(activity).startIntent(_class, str, obj);
					}else{
						new IntentUtility(activity).startIntent(_class);
					}
				}							
			}
		});
		alertbox.show();
	}
	
	
	
}
