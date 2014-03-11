package com.globe.games.guess.utilities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;

public class IntentUtility {
	
	private Context context;
	private HelperFunctions hFunction = new HelperFunctions();
	
	public IntentUtility(Context _c){
		this.context = _c;
	}
	
	public void startIntent(Class<?> showActivity) {		
		this.startIntent(showActivity, null, null, null, null, false);
	}
	
	public void startIntent(Class<?> showActivity, String parcelableName[], Parcelable parcelableValue[]) {		
		this.startIntent(showActivity, parcelableName, parcelableValue, null, null, false);
	}
	
	public void startIntent(Class<?> showActivity, String extraName, Object extraValue) {		
		this.startIntent(showActivity, null, null, new String[] { extraName } , new Object[] { extraValue }, false);
	}
	
	public void startIntent(Class<?> showActivity, String extraName[], Object extraValue[]) {		
		this.startIntent(showActivity, null, null, extraName, extraValue, false);
	}
	
	public void startIntent(Class<?> showActivity, String parcelableName, Parcelable parcelableValue) {		
		String _name[]= {parcelableName};
		Parcelable _value[]= {parcelableValue};
		this.startIntent(showActivity, _name, _value, null, null, false);
	}
	
	public void startIntent(Class<?> showActivity, String parcelableName[], Parcelable parcelableValue[], String extraName[], Object extraValue[], boolean clearTop) {
		Intent intent = new Intent(context, showActivity);
		int extraLength;
		if (parcelableName != null) {
			extraLength = parcelableName.length;
			for (int i=0; i < extraLength; i++) {
				intent.putExtra(parcelableName[i], parcelableValue[i]);
			}
		}
		if (extraName != null) {
			extraLength = extraName.length;
			for (int i=0; i < extraLength; i++) {
				if (extraValue[i] instanceof String){
					intent.putExtra(extraName[i], (String) extraValue[i]);
				}else{
					intent.putExtra(extraName[i], hFunction.objectToString(extraValue[i]));					
				}
			}
		}
		
		if(clearTop){
			intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
		}
		
		context.startActivity(intent);
	}
	
	public void openToBrowser(String _url) {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(_url));
		context.startActivity(intent);
	} 
		 
	public void call(String _phoneNo){
		Intent intent = new Intent(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:+" + _phoneNo));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	
	public void sendEmail(String _email, String _subject, String _msg){
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("message/rfc822");
		intent.putExtra(Intent.EXTRA_EMAIL, new String[]{_email});
		intent.putExtra(Intent.EXTRA_SUBJECT, _subject);
		intent.putExtra(Intent.EXTRA_TEXT, _msg);
		context.startActivity(Intent.createChooser(intent, "Select Mailer"));
	}
	
	
}
