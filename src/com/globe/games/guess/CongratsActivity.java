package com.globe.games.guess;

import com.globe.games.guess.utilities.IntentUtility;
import com.globe.games.guess.utilities.SoundsHelper;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;

public class CongratsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_congrats);
	}

	
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{				
		return false; 
	}
	
	public void newGame(View v){
		new IntentUtility(this).startIntent(MainActivity.class, null, null, null, null, true);	
	}

}
