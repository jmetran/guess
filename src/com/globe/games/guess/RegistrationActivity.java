package com.globe.games.guess;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;
import com.globe.games.guess.utilities.AppSettingsDataSource;
import com.globe.games.guess.utilities.MyUuId;
import com.globe.games.guess.utilities.ErrorMessageUtility;
import com.globe.games.guess.utilities.IntentUtility;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class RegistrationActivity extends Activity {
	
	private AQuery aq;
	private ErrorMessageUtility errMsg;
	private WebView wvRegistration;
	private final static String SUCCESS_URL = "http://guess-app.herokuapp.com/callback?code="; 
	private String tokenUrl;
	private ProgressDialog progress;
	private MyUuId appDetails;
	private AppSettingsDataSource datasource;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		
		init();
		
		wvRegistration.setWebViewClient(new WebViewClient() {
		    public boolean shouldOverrideUrlLoading(WebView view, String url){
		    	
		    	AQUtility.debug(url);
		    	if (url.contains(SUCCESS_URL)) {
		    		wvRegistration.setVisibility(View.GONE);
		    		tokenUrl = url;
		    		getToken();
		    	}
		        view.loadUrl(url);
		        return true; 
		   }
		});
		
		wvRegistration.loadUrl(getString(R.string.registration_url));
		
	}

	private void init(){
		AQUtility.setDebug(true);
		aq = new AQuery(this);
		errMsg = new ErrorMessageUtility(this);
		wvRegistration = aq.id(R.id.wv_registration).getWebView();
		progress = new ProgressDialog(this);
		progress.setMessage("Please wait...");
		appDetails = new MyUuId(this);
		datasource = new AppSettingsDataSource(this);
		
	}
	
	public void getToken(){
		aq.progress(progress).ajax(tokenUrl, JSONObject.class, -1, this, "tokenCb");
	}
	
	public void tokenCb(String url, JSONObject jo, AjaxStatus status) throws JSONException{
		AQUtility.debug("jo", jo);

		if (jo != null) {
			
			datasource.open();
			datasource.create(jo.getString("access_token"), jo.getString("subscriber_number"));
			datasource.close();
			
			
			appDetails.save(jo.getString("access_token"), jo.getString("subscriber_number"));
			new IntentUtility(this).startIntent(MechanicsActivity.class);
		}else{
			getToken();
		}
	}
	
}
