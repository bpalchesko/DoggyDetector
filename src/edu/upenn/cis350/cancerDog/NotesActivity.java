package edu.upenn.cis350.cancerDog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class NotesActivity extends Activity {
	EditText edText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notes);
		edText = (EditText) findViewById(R.id.editText1);
		 Log.e("Loading Activity", "NotesActivity");
		 Intent data = (Intent) getIntent();
		  if (data==null)
		    {// Do nothing
		    }
		    else if (data.hasExtra("Notes")) {
		   	  edText.setText(data.getExtras().getString("Notes"));
		  	}
		 
	}// oncreate

	
	
	public void onDogClick (View v) {
		String editTextStr = edText.getText().toString() + "Dog"+ " ";
  	  	edText.setText(editTextStr);
  	  	edText.setSelection(edText.length());
	}
	
	public void onTiredClick (View v) {
		String editTextStr = edText.getText().toString() + "Tired"+ " ";
  	  	edText.setText(editTextStr);
  	  	edText.setSelection(edText.length());
	}

	public void onSleepyClick (View v) {
		String editTextStr = edText.getText().toString() + "Sleepy"+ " ";
  	  	edText.setText(editTextStr);
  	  	edText.setSelection(edText.length());
	}
	
	public void onHungryClick (View v) {
		String editTextStr = edText.getText().toString() + "Hungry"+ " ";
  	  	edText.setText(editTextStr);
  	  	edText.setSelection(edText.length());
	}

	public void onFinishClick (View v) {
		Intent i = new Intent(this, TrialRunActivity.class); 
		i.putExtra("Notes",  edText.getText().toString());
		Log.e("clicked", "" +  edText.getText().toString());
		setResult(Activity.RESULT_OK, i);
		finish();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.e("Loading method", "onActivityResult...");
		super.onActivityResult(requestCode, resultCode, data);

	    if (data==null)
	    {// Do nothing
	    }
	    else if (data.hasExtra("Notes")) {
	   	  edText.setText(data.getExtras().getString("Notes"));
	  	}
	}
}
