package edu.upenn.cis350.cancerDog;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class LauncherActivity extends Activity {
	BloodWheel bw;
	
	public static final int ButtonClickActivity_ID = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher_new);
		Intent data = (Intent) getIntent();

		bw = new BloodWheel();
		bw.setWheelData(data);
	}
	

	
	public void onStartTrialClick(View v) {
		Intent i = new Intent(this, confirmSettings.class);
		bw.pushIntentData(i);
		startActivityForResult(i, ButtonClickActivity_ID);
	}
	
	public void onSettingsButtonClick(View v) {
		Intent i = new Intent(this, EditDefaultActivityNew.class); 
		bw.pushIntentData(i);
		startActivityForResult(i, ButtonClickActivity_ID);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {//If back on phone pressed, retrieve data for intent
		super.onActivityResult(requestCode, resultCode, data);
	    if (data==null){
	    	if (bw==null) bw = new BloodWheel();
	    } else{ 
	    	bw.setWheelData(data);
	  	}
	}
	
	@Override
	public void finish(){//If back on phone pressed, store data for next intent
		Intent i = new Intent();
		bw.pushIntentData(i);
		setResult(Activity.RESULT_OK, i);
		super.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.launcher, menu);
		return true;
	}
}
