package com.globe.games.guess.utilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GmDbHelper extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "guess.db";
	private static final int DATABASE_VERSION = 1;
	
	
	//APP SETTINGS TABLE and FIELDS
	public static final String TBL_SETTINGS = "app_settings";
	public static final String SETTINGS_FLD_TOKEN = "accesstoken";
	public static final String SETTINGS_FLD_PHONE = "phone_number";
	
	public GmDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	//This method can be used to rebuild the entire database file and thus reclaim unused disk space
	public void runVacuum(SQLiteDatabase db){
		db.execSQL("VACUUM");
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub CREATE TABLE theaters (name TEXT, address TEXT, id INTEGER PRIMARY KEY, theater_id TEXT, is_fave NUMERIC);
		db.execSQL("CREATE TABLE IF NOT EXISTS "+ TBL_SETTINGS + "("+ SETTINGS_FLD_TOKEN +" BLOB, "+ SETTINGS_FLD_PHONE +" TEXT);");		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
//		db.execSQL("ALTER TABLE "+ TBL_THEATERS + " ADD COLUMN "+ THEATER_FLD_SUPPORTED +" NUMERIC;");
	}
	

}
