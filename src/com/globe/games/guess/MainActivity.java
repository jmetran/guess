package com.globe.games.guess;

import com.androidquery.AQuery;
import com.androidquery.util.AQUtility;
import com.globe.games.guess.utilities.AppSettingsDataSource;
import com.globe.games.guess.utilities.MyUuId;
import com.globe.games.guess.utilities.IntentUtility;
import com.globe.games.guess.utilities.SoundsHelper;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {
	
	private AQuery aq;
	
	private MyUuId appDetails;
	private IntentUtility iUtil;
	
	private AppSettingsDataSource datasource;
	private String token = "";
	
	private SoundsHelper soundHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		soundHelper.playBeat();
		aq.id(R.id.btn_play).clicked(new OnClickListener() {

			@Override
			public void onClick(View v) {	
				soundHelper.playBeep();
				if(token.equals("")){
					iUtil.startIntent(RegistrationActivity.class);
				}else{
					iUtil.startIntent(MechanicsActivity.class);
				}
			}
		});
	}	
	
	private void init(){
		AQUtility.setDebug(true);
		aq = new AQuery(this);
		appDetails = new MyUuId(this);
		soundHelper = new SoundsHelper(this);
		datasource = new AppSettingsDataSource(this);
		datasource.open();
		datasource.getDetails();
		AQUtility.debug("THIS", datasource.accessToken);
		token = datasource.accessToken;
		datasource.close();
		iUtil = new IntentUtility(this);

	}

}
