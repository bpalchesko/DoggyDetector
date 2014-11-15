package edu.upenn.cis350.cancerDog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;

public class TrialActivityNew extends Activity {
	Button btnBegin;
	BloodWheel bw;
	NumberPicker npControl;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trial_new);
		Log.e("Loading Activity", "TrialActivityNew");
		Intent data = (Intent) getIntent();
		
		if (data.hasExtra("Control") && data.hasExtra("Benign") && data.hasExtra("Malignant")) {
		      bw = new BloodWheel();
		      bw.Control=data.getExtras().getInt("Control");
		      bw.Benign=data.getExtras().getInt("Benign");
		      bw.Malignant=data.getExtras().getInt("Malignant");
		}
		else
		{
			Log.e("Loading Activity", "bw data not passed to activity");
		}
	}
	


	public void onBeginButtonClick (View v) {
		Intent i = new Intent(this, TrialRunActivity.class);
		i.putExtra("Benign", bw.Benign);
		i.putExtra("Control", bw.Control);  
		i.putExtra("Malignant", bw.Malignant);
		startActivityForResult(i,1);
	}
	
	@Override
	public void finish(){
		Intent i = new Intent();
		i.putExtra("Benign", bw.Benign);
		i.putExtra("Control", bw.Control);  
		i.putExtra("Malignant", bw.Malignant); 
		setResult(Activity.RESULT_OK, i);
		super.finish();
	}

}
