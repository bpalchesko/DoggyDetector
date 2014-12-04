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
		Log.e("Loading Activity", "LauncherActivity");
		Trial.context = this;
		Trial.loadSessions();
		
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
	
	public void onLaunchButtonClick (View v) {
		//setContentView(new WheelView(this));
		Trial.getNewTrial();
		Intent i = new Intent(this, TrialRunActivity.class);
		i.putExtra("Benign", bw.Benign);
		i.putExtra("Control", bw.Control);  
		i.putExtra("Malignant", bw.Malignant); 
		
		startActivityForResult(i,ButtonClickActivity_ID);
	}
	
	public void onStartButtonClick(View v) {
		Intent i = new Intent(this, EditDefaultActivityNew.class);
		i.putExtra("Benign", bw.Benign);
		i.putExtra("Control", bw.Control);  
		i.putExtra("Malignant", bw.Malignant); 
		startActivityForResult(i, ButtonClickActivity_ID);;
	}
	
	public void onViewButtonClick(View v) {
		Intent i = new Intent(this, ViewActivity.class);
		startActivityForResult(i, ButtonClickActivity_ID);
	}
	
	public void onEditButtonClick(View v) {
		Intent i = new Intent(this, confirmSettings.class);
		i.putExtra("Benign", bw.Benign);
		i.putExtra("Control", bw.Control);  
		i.putExtra("Malignant", bw.Malignant); 
		startActivityForResult(i, ButtonClickActivity_ID);
	}
	
	public void onEditDefaultButtonClick(View v) {
		Intent i = new Intent(this, EditDefaultActivityNew.class); 
		i.putExtra("Benign", bw.Benign);
		i.putExtra("Control", bw.Control);  
		i.putExtra("Malignant", bw.Malignant); 
		startActivityForResult(i, ButtonClickActivity_ID);
	}
	
	public void onExitButtonClick (View v) {
		finish();
        System.exit(1);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.e("Loading method", "onActivityResult...");
		super.onActivityResult(requestCode, resultCode, data);
	    if (data==null)
	    {
	    	Log.e("Loading Activity", "nulls datas...");
	    	if (bw==null) bw = new BloodWheel();
	    }
	    else if (data.hasExtra("Control") && data.hasExtra("Benign") && data.hasExtra("Malignant")) {
	    	bw.Control=data.getExtras().getInt("Control");
		    bw.Benign=data.getExtras().getInt("Benign");
		    bw.Malignant=data.getExtras().getInt("Malignant");;
	  	}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.launcher, menu);
		Trial.context = this;
		Trial.loadSessions();
		return true;
	}
}
