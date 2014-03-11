package com.globe.games.guess.utilities;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;

public class SoundsHelper {
	
	private AssetManager assetManager;
	private Activity activity;
	
	
	private AssetFileDescriptor beep;
	private AssetFileDescriptor correct;
	private AssetFileDescriptor beat;
	private AssetFileDescriptor exit;
	
	public SoundsHelper(Activity _activity){
		this.activity = _activity;
		init();
	}
	
	private void init(){
		assetManager = activity.getAssets();
		
		try {
			beep = assetManager.openFd("beep.mp3");
			correct = assetManager.openFd("correct.mp3");
			beat = assetManager.openFd("beat.mp3");
			exit = assetManager.openFd("exit.mp3");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void playSound(AssetFileDescriptor afd){
		final MediaPlayer mp = new MediaPlayer();
		try {
			if(mp.isPlaying())
	        {  
	            mp.stop();
	            mp.reset();
	        } 
			
			mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
			mp.prepare();
	        mp.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}        
	}
	
	public void playBeep(){
		playSound(beep);
	}
	
	public void playCorrect(){
		playSound(correct);
	}
	
	public void playBeat(){
		playSound(beat);
	}
	
	public void playExit(){
		playSound(exit);
	}

}
