package com.globe.games.guess.utilities;

import java.util.ArrayList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class AppSettingsDataSource {

	private SQLiteDatabase database;
	private GmDbHelper dbHelper;

	public String accessToken = "";
	public String phoneNumber = "";
	
	
	private String[] allColumns = { GmDbHelper.SETTINGS_FLD_TOKEN, GmDbHelper.SETTINGS_FLD_PHONE };
	
	public AppSettingsDataSource(Context _context) {
		dbHelper = new GmDbHelper(_context);
	}

	public AppSettingsDataSource(SQLiteDatabase _database) {
		database = _database;
	}
	
	public SQLiteDatabase open() throws SQLException {
		database = dbHelper.getWritableDatabase();
		return database;
	}

	public void close() {
		dbHelper.close();
	}
	
		
	public void getDetails(){
		Cursor c = database.query(GmDbHelper.TBL_SETTINGS, allColumns, null, null, null, null, null, null);
		
		try {
            while (c.moveToNext()){
            	accessToken = c.getString(0);
            	phoneNumber = c.getString(1);
            	
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            c.close();
        }  
	}
	
	public long create(String _accessToken, String _phoneNumber) {
	    ContentValues values = new ContentValues();
	    
	    getDetails();
	    
	    if (!accessToken.equals("")){
	    	return 0;
	    }
	    
	    String[] mValues = { _accessToken, _phoneNumber };
	    
	    if (_accessToken != null) {
	    	int len = mValues.length;
			for (int i=0; i < len; i++) {
				values.put(allColumns[i], mValues[i]);
			}
		}
	    
	    long insertId = database.insert(GmDbHelper.TBL_SETTINGS, null, values);
	    
	    return insertId;	  
	}
	
	public void runQuery(String query) {
	    database.execSQL(query);	
	}
	

}
