package edu.upenn.cis350.cancerDog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class confirmSettings extends Activity{
	BloodWheel bw;
	TextView BenignNum;
	TextView ControlNum;
	TextView MalignantNum;
	TextView dogName;
	TextView temperature;
	TextView humidity;
	TextView handler;
	TextView tester;
	TextView recorder;
	private static String currentDog;
	private static String currentTemp;
	private static String currentHumidity;
	private static String currentHandler;
	private static String currentTester;
	private static String currentRecorder;

	public static final int ButtonClickActivity_ID = 1;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_settings);
		Log.e("Loading Activity", "confirmSettings");
		Trial.context = this;
		Trial.loadSessions();
		BenignNum = (TextView) findViewById(R.id.BenignNum);
		ControlNum = (TextView) findViewById(R.id.ControlNum);
		MalignantNum = (TextView) findViewById(R.id.MalignantNum);

		Intent data = (Intent) getIntent();
		
		if (data.hasExtra("Control") && data.hasExtra("Benign") && data.hasExtra("Malignant")) {
		      bw = new BloodWheel();
		      bw.Control=data.getExtras().getInt("Control");
		      bw.Benign=data.getExtras().getInt("Benign");
		      bw.Malignant=data.getExtras().getInt("Malignant");
		      BenignNum.setText(Integer.valueOf(bw.Benign).toString());
		      ControlNum.setText(Integer.valueOf(bw.Control).toString());
		      MalignantNum.setText(Integer.valueOf(bw.Malignant).toString());
		      Log.e("on create data:", Integer.valueOf(bw.Malignant).toString() + " " + Integer.valueOf(bw.Benign).toString() + " ");
		}
		else
		{
			bw = new BloodWheel();
			Log.e("data", "data not received");
		}
		
		//get dog from settings screen to confirm settings
		SharedPreferences preferences = getSharedPreferences(
    			"edu.upenn.cis350.cancerDog.dogs", Context.MODE_PRIVATE);
    	currentDog = preferences.getString("current", "DEFAULT");
    	dogName = (TextView) findViewById(R.id.dogName);
    	dogName.setText(currentDog);

		//get temp from settings screen to confirm settings
    	preferences = getSharedPreferences(
    			"edu.upenn.cis350.cancerDog.conditions", Context.MODE_PRIVATE);
    	currentTemp =preferences.getString("temp", "DEFAULT");
    	temperature = (TextView) findViewById(R.id.tempDeg);
    	temperature.setText(currentTemp + " F");
    	
		//get humidity from settings screen to confirm settings
    	preferences = getSharedPreferences(
    			"edu.upenn.cis350.cancerDog.conditions", Context.MODE_PRIVATE);
    	currentHumidity =preferences.getString("humidity", "DEFAULT");
    	humidity = (TextView) findViewById(R.id.HumidPercent);
    	humidity.setText(currentHumidity + " %");
    	
		//get current tester from settings screen to confirm settings
    	preferences = getSharedPreferences(
    			"edu.upenn.cis350.cancerDog.handlers", Context.MODE_PRIVATE);
    	currentTester =preferences.getString("current_tester", "DEFAULT");
    	tester = (TextView) findViewById(R.id.TesterName);
    	tester.setText(currentTester);
    	
		//get current handler from settings screen to confirm settings
    	preferences = getSharedPreferences(
    			"edu.upenn.cis350.cancerDog.handlers", Context.MODE_PRIVATE);
    	currentHandler =preferences.getString("current_handler", "DEFAULT");
    	handler = (TextView) findViewById(R.id.HandlerName);
    	handler.setText(currentHandler);
    	
		//get current recorder from settings screen to confirm settings
    	preferences = getSharedPreferences(
    			"edu.upenn.cis350.cancerDog.handlers", Context.MODE_PRIVATE);
    	currentRecorder =preferences.getString("current_recorder", "DEFAULT");
    	recorder = (TextView) findViewById(R.id.RecorderName);
    	recorder.setText(currentRecorder);
	}
	
	public void onPreviousButtonClick(View v) {
		finish();
		System.exit(0);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {//If back on phone pressed, retrieve data for intent
		super.onActivityResult(requestCode, resultCode, data);
	    if (data==null)
	    {
	    	BenignNum.setText(Integer.valueOf(bw.Benign).toString());
		    ControlNum.setText(Integer.valueOf(bw.Control).toString());
		    MalignantNum.setText(Integer.valueOf(bw.Malignant).toString());
		    BenignNum.setText(Integer.valueOf(bw.Benign).toString());
		    ControlNum.setText(Integer.valueOf(bw.Control).toString());
		    MalignantNum.setText(Integer.valueOf(bw.Malignant).toString());
	    	Log.e("Loading Activity", "nulls datas...");
	    }
	    else if (data.hasExtra("Benign")) {
	    	bw.Control=data.getExtras().getInt("Control");
		    bw.Benign=data.getExtras().getInt("Benign");
		    bw.Malignant=data.getExtras().getInt("Malignant");
	    	BenignNum.setText(Integer.valueOf(bw.Benign).toString());
		    ControlNum.setText(Integer.valueOf(bw.Control).toString());
		    MalignantNum.setText(Integer.valueOf(bw.Malignant).toString());
	  	}
	}
	
	@Override
	public void finish(){//If back on phone pressed, store data for next intent
		Intent i = new Intent();
		i.putExtra("Benign", bw.Benign);
		i.putExtra("Control", bw.Control);  
		i.putExtra("Malignant", bw.Malignant); 
		setResult(Activity.RESULT_OK, i);
		super.finish();
	}
	
	public void onConfirmButtonClick (View v) {
		Trial.getNewTrial();
		Intent i = new Intent(this, TrialRunActivity.class);
		i.putExtra("Benign", bw.Benign);
		i.putExtra("Control", bw.Control);  
		i.putExtra("Malignant", bw.Malignant);
		startActivityForResult(i,ButtonClickActivity_ID);
	}
	
	public void onChangeSettingsButtonClick(View v) {
		Intent i = new Intent(this, EditDefaultActivityNew.class); 
		i.putExtra("Benign", bw.Benign);
		i.putExtra("Control", bw.Control);  
		i.putExtra("Malignant", bw.Malignant); 
		startActivityForResult(i, ButtonClickActivity_ID);
	}
	
	@Override
	public void onRestart(){
	    super.onRestart();
	    Bundle savedInstanceState = null;
		this.onCreate(savedInstanceState);
	}
	
}
