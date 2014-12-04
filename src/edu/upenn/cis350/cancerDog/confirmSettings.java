package edu.upenn.cis350.cancerDog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class confirmSettings extends Activity{
	BloodWheel bw;

	public static final int ButtonClickActivity_ID = 1;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_settings);
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
	
	public void onPreviousButtonClick(View v) {
		finish();
		System.exit(0);
	}
	
	public void onConfirmButtonClick (View v) {
		//setContentView(new WheelView(this));
		Trial.getNewTrial();
		Intent i = new Intent(this, TrialRunActivity.class);
		i.putExtra("Benign", bw.Benign);
		i.putExtra("Control", bw.Control);  
		i.putExtra("Malignant", bw.Malignant); 
		
		startActivityForResult(i,ButtonClickActivity_ID);
	}
	
	public void onEditDefaultButtonClick(View v) {
		Intent i = new Intent(this, EditDefaultActivityNew.class); 
		i.putExtra("Benign", bw.Benign);
		i.putExtra("Control", bw.Control);  
		i.putExtra("Malignant", bw.Malignant); 
		startActivityForResult(i, ButtonClickActivity_ID);
	}
	
	
}
