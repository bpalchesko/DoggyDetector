package edu.upenn.cis350.cancerDog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class confirmSettings extends Activity{
	BloodWheel bw;
	TextView BenignNum;
	TextView ControlNum;
	TextView MalignantNum;

	public static final int ButtonClickActivity_ID = 1;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_settings);
		Log.e("Loading Activity", "LauncherActivity");
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
		      BenignNum.setText(new Integer(bw.Benign).toString());
		      ControlNum.setText(new Integer(bw.Control).toString());
		      MalignantNum.setText(new Integer(bw.Malignant).toString());
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
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	    if (data==null)
	    {
	    	BenignNum.setText(new Integer(bw.Benign).toString());
		    ControlNum.setText(new Integer(bw.Control).toString());
		    MalignantNum.setText(new Integer(bw.Malignant).toString());
	    	Log.e("Loading Activity", "nulls datas...");
	    }
	    else if (data.hasExtra("Benign")) {
	    	int benign = data.getExtras().getInt("Benign");
	    	BenignNum.setText(new Integer(data.getExtras().getInt("Benign")).toString());
		    ControlNum.setText(new Integer(data.getExtras().getInt("Control")).toString());
		    MalignantNum.setText(new Integer(data.getExtras().getInt("Malignant")).toString());
	  	}
	}
	
	public void onConfirmButtonClick (View v) {
		//setContentView(new WheelView(this));
		Trial.getNewTrial();
		Intent i = new Intent(this, TrialRunActivity.class);
		i.putExtra("Benign", bw.Benign);
		i.putExtra("Control", bw.Control);  
		i.putExtra("Malignant", bw.Malignant); 
		Log.e("benign",new Integer(bw.Benign).toString());
		Log.e("control",new Integer(bw.Control).toString());
		Log.e("malignant",new Integer(bw.Malignant).toString());
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
