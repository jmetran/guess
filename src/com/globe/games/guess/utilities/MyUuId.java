package com.globe.games.guess.utilities;

import com.androidquery.util.AQUtility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class MyUuId {

	private SharedPreferences mPrefs;
	private Activity mActivity;
	private final static String PREF_TOKEN = "guesstoken";
	private final static String PREF_SUBCRIBERS_NUMBER = "guessphone_number";
	
	public String accessToken;
	public String phoneNumber;
	
	public MyUuId(Activity a) {
		this.mActivity = a;
		this.mPrefs = mActivity.getPreferences(Context.MODE_PRIVATE);	
		getDetails();
	}
	
	public void getDetails(){
		AQUtility.debug("_token",mPrefs.getString(PREF_TOKEN, null));
		AQUtility.debug("_phoneNumber",mPrefs.getString(PREF_SUBCRIBERS_NUMBER, null));
		
		accessToken = mPrefs.getString(PREF_TOKEN, null);
		phoneNumber = mPrefs.getString(PREF_SUBCRIBERS_NUMBER, null);
		
		AQUtility.debug("_token",mPrefs.getString(PREF_TOKEN, null));
		AQUtility.debug("_phoneNumber",mPrefs.getString(PREF_SUBCRIBERS_NUMBER, null));
	}
	
	public void save(String _token, String _phoneNumber){
		AQUtility.debug("_token",_token);
		AQUtility.debug("_phoneNumber",_phoneNumber);
		SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(PREF_TOKEN, new String(_token));
        editor.putString(PREF_SUBCRIBERS_NUMBER, new String(_phoneNumber));
        editor.commit();
	}
	
}
