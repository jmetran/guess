package com.globe.games.guess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;
import com.globe.games.guess.utilities.AlertUtility;
import com.globe.games.guess.utilities.AppSettingsDataSource;
import com.globe.games.guess.utilities.MyUuId;
import com.globe.games.guess.utilities.ErrorMessageUtility;
import com.globe.games.guess.utilities.IntentUtility;
import com.globe.games.guess.utilities.SoundsHelper;

import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class GameActivity extends Activity {

	private AQuery aq;
	
	private List<Guess> questions = new ArrayList<GameActivity.Guess>();
	private int totalQuestions;
	
	private boolean finish = false;
	
	private int questionIdx = 0;
	private int level = 1;
	private int score = 0;
	private int hintReveal = 0;
	
	private EditText et_answer;
	
	private AlertUtility alertUtil;
	private ErrorMessageUtility errMsg;
	private ProgressDialog progress;
	private MyUuId appDetails;
	private AppSettingsDataSource datasource;
	private String accessToken = "";
	private String phoneNumber = "";
	private SoundsHelper soundHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		init();
		
		TypedArray images = getResources().obtainTypedArray(R.array.questions);
		String[] answers = getResources().getStringArray(R.array.answers);
		String[] hints = getResources().getStringArray(R.array.hints);
		
		totalQuestions = answers.length;
		for (int i = 0; i < totalQuestions; i++) {
			Guess g = new Guess(answers[i], images.getResourceId(i, -1), hints[i]);
			questions.add(g);
		}
		Collections.shuffle(questions);
		
		drawQuestion();
		
		aq.id(R.id.btn_ok).clicked(new OnClickListener() {

			@Override
			public void onClick(View v) {
				soundHelper.playBeep();
				if(!finish){
					if (et_answer.getText().toString().trim().length() != 0){
						if(validateAnswer()){
							AQUtility.debug("CORRECT");
							addScoreLevel();
							moveNext();
						}else{
							AQUtility.debug("WRONG");
							alertUtil.alertInfo("Sorry!", "Your answer is incorrect.");
						}
					}else{
						errMsg.showMessage("Please enter your answer", true);
						et_answer.findFocus();
					}					
				}
			}
		});
		
		aq.id(R.id.btn_hint).clicked(new OnClickListener() {

			@Override
			public void onClick(View v) {
				soundHelper.playBeep();
				if(hintReveal == 0){
					AlertDialog.Builder alertbox = new AlertDialog.Builder(GameActivity.this);
					alertbox.setTitle("Notice");
					alertbox.setMessage("Do you want to purchase a HINT for this question? 1 Hint = 1 peso");
					alertbox.setPositiveButton("NO",null);
					alertbox.setNegativeButton("YES", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
							getReference();			
						}
					});
					alertbox.show();
				}else{
					errMsg.showMessage("Only one hint per level", true);
				}
			}
		});
		
		aq.id(R.id.btn_cancel).clicked(new OnClickListener() {

			@Override
			public void onClick(View v) {
				soundHelper.playExit();
				new IntentUtility(GameActivity.this).startIntent(MainActivity.class, null, null, null, null, true);				
			}
		});
	}
	
	private long reference;
	
	public void getReference(){
		aq.progress(progress).ajax("http://guess-app.herokuapp.com/reference", JSONObject.class, -1, this, "referenceCb");
	}
	
	public void referenceCb(String url, JSONObject jo, AjaxStatus status) throws JSONException{
		AQUtility.debug("jo", jo);

		if (status.getCode() == 200) {
			long ref = jo.getLong("reference");
			reference = ref + 1;
			AQUtility.debug("reference", reference);
			getHint();
//			hintReveal++;
//			aq.id(R.id.tv_hint).text(questions.get(questionIdx).hint).visible();
		}else{
			errMsg.showMessage(status.getMessage());
		}
	}
	
	public void saveReference(){
		AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>();
		cb.url("http://guess-app.herokuapp.com/increment").type(JSONObject.class).fileCache(true).expire(-1);
		cb.method(AQuery.METHOD_POST);
		cb.weakHandler(this, "saveReferenceCb");
		cb.param("reference", "ok");
		aq.ajax(cb);		
	}
	
	public void saveReferenceCb(String url, JSONObject jo, AjaxStatus status) throws JSONException{
		AQUtility.debug("jo", jo);

		if (status.getCode() == 200) {
			
		}else{
			saveReference();
		}
	}
	
	public void getHint(){
		AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>();
		cb.url("http://devapi.globelabs.com.ph/payment/v1/transactions/amount?access_token="+accessToken).type(JSONObject.class).fileCache(true).expire(-1);
		cb.method(AQuery.METHOD_POST);
		cb.weakHandler(this, "hintCb");
		cb.param("amount", "0.00");
		cb.param("description", "Guess (?)");
		cb.param("endUserId", phoneNumber);
		cb.param("referenceCode", reference);
		cb.param("transactionOperationStatus", "Charged");
		aq.progress(progress).ajax(cb);
	}
	
	public void hintCb(String url, JSONObject jo, AjaxStatus status) throws JSONException{
		AQUtility.debug("jo", jo);

		if (status.getCode() == 201) {
			hintReveal++;
			aq.id(R.id.tv_hint).text(questions.get(questionIdx).hint).visible();
			saveReference();
		}else{
			errMsg.showMessage(status.getMessage());
		}
	}
	
	
	private void moveNext(){
		if(questionIdx < (totalQuestions-1)){
			questionIdx++;
			hintReveal = 0;
			et_answer.setText("");
			aq.id(R.id.tv_hint).gone();
			drawQuestion();
		}else{
			finish = true;
			AQUtility.debug("NO NEW QUESTION");
			new IntentUtility(this).startIntent(CongratsActivity.class, null, null, null, null, true);	
		}
	}
	
	private void addScoreLevel(){
		level++;
		score++;
		initLabel();
	}
	
	private void initLabel(){
//		aq.id(R.id.tv_level).text("Level: "+ level);
		aq.id(R.id.tv_score).text(String.valueOf(score));
	}
	
	private boolean validateAnswer(){
		return (et_answer.getText().toString().equalsIgnoreCase(questions.get(questionIdx).answer) ? true : false);
	}
	
	private void drawQuestion(){
		aq.id(R.id.img_question).image(questions.get(questionIdx).picture);
	}
	
	
	private void init(){
		aq = new AQuery(this);
		AQUtility.setDebug(true);
		et_answer = aq.id(R.id.et_answer).getEditText();
		alertUtil = new AlertUtility(this);
		errMsg = new ErrorMessageUtility(this);
		initLabel();
		progress = new ProgressDialog(this);
		progress.setMessage("Please wait...");
		appDetails = new MyUuId(this);
		soundHelper = new SoundsHelper(this);
		soundHelper.playBeat();
		datasource = new AppSettingsDataSource(this);
		datasource.open();
		datasource.getDetails();
		accessToken = datasource.accessToken;
		phoneNumber = datasource.phoneNumber;
		datasource.close();
	}
	
	
	private class Guess {
		public String answer;
		public int picture;
		public String hint;
		
		public Guess(String answer, int picture, String hint) {
			this.answer = answer; 
			this.picture = picture;
			this.hint = hint;
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{				
		return false; 
	}

}
