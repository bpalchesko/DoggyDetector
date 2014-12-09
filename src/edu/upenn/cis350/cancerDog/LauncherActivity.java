package edu.upenn.cis350.cancerDog;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class LauncherActivity extends Activity {
	BloodWheel bw;
	
	public static final int ButtonClickActivity_ID = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher_new);
		//Log.e("Loading Activity", "LauncherActivity");
		//Trial.context = this;
		//Trial.loadSessions();
		
		Intent data = (Intent) getIntent();
		
		if (data.hasExtra("Control") && data.hasExtra("Benign") && data.hasExtra("Malignant")) {
		      bw = new BloodWheel();
		      bw.Control=data.getExtras().getInt("Control");
		      bw.Benign=data.getExtras().getInt("Benign");
		      bw.Malignant=data.getExtras().getInt("Malignant");
		}
		else
		{
			bw = new BloodWheel();
		}
	}
	
	public void onStartTrialClick(View v) {
		Intent i = new Intent(this, confirmSettings.class);
		i.putExtra("Benign", bw.Benign);
		i.putExtra("Control", bw.Control);  
		i.putExtra("Malignant", bw.Malignant); 
		startActivityForResult(i, ButtonClickActivity_ID);
	}
	
	public void onSettingsButtonClick(View v) {
		Intent i = new Intent(this, EditDefaultActivityNew.class); 
		i.putExtra("Benign", bw.Benign);
		i.putExtra("Control", bw.Control);  
		i.putExtra("Malignant", bw.Malignant); 
		startActivityForResult(i, ButtonClickActivity_ID);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {//If back on phone pressed, retrieve data for intent
		super.onActivityResult(requestCode, resultCode, data);
	    if (data==null){
	    	if (bw==null) bw = new BloodWheel();
	    }
	    else if (data.hasExtra("Control") && data.hasExtra("Benign") && data.hasExtra("Malignant")) {
	    	bw.Control=data.getExtras().getInt("Control");
		    bw.Benign=data.getExtras().getInt("Benign");
		    bw.Malignant=data.getExtras().getInt("Malignant");;
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.launcher, menu);
		//Trial.context = this;
		//Trial.loadSessions();
		return true;
	}
}
