package com.globe.games.guess;

import com.globe.games.guess.utilities.IntentUtility;
import com.globe.games.guess.utilities.SoundsHelper;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class MechanicsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mechanics);
	}

	public void next(View v){
		new SoundsHelper(this).playBeep();
		new IntentUtility(this).startIntent(GameActivity.class, null, null, null, null, true);	
	}
	

}
