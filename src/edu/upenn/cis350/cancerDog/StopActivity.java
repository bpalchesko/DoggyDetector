package edu.upenn.cis350.cancerDog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class StopActivity extends Activity {
	int stopValue;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stop);
		
		 Log.e("Loading Activity", "StopActivity");
	}// on create
	
	
	public void onStopClick1 (View v) {
		proccessReturn(1);
	}
	
	public void onStopClick2 (View v) {
		proccessReturn(2);
	}

	public void onStopClick3 (View v) {
		proccessReturn(3);
	}
	
	public void onStopClick4 (View v) {
		proccessReturn(4);
	}
	
	public void onStopClick5 (View v) {
		proccessReturn(5);
	}
	
	public void onStopClick6 (View v) {
		proccessReturn(6);
	}
	
	public void onStopClick7 (View v) {
		proccessReturn(7);
	}
	
	public void onStopClick8 (View v) {
		proccessReturn(8);
	}
	
	public void onStopClick9 (View v) {
		proccessReturn(9);
	}
	
	public void onStopClick10 (View v) {
		proccessReturn(10);
	}
	
	public void onStopClick11 (View v) {
		proccessReturn(11);
	}
	
	public void onStopClick12 (View v) {
		proccessReturn(12);
	}
	
	protected void proccessReturn(int Stop){
		Intent i = new Intent(this, TrialRunActivity.class); 
		i.putExtra("Stop", Stop);
		//startActivityForResult(i, 1);
		Log.e("clicked", "" + Stop);
		setResult(Activity.RESULT_OK, i);
		finish();

	}
	
	

}
