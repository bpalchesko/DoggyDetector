package edu.upenn.cis350.cancerDog;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DisplayTrialActivity extends ListActivity {
	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle) Called when the
	 * activity is first created. This is where you should do all of your normal
	 * static set up: create views, bind data to lists, etc. This method also
	 * provides you with a Bundle containing the activity's previously frozen
	 * state, if there was one.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		Trial.context = this;
		String[] trialData = Trial.getTrial(extras.getInt("trialNum")).toString().split("\n");
		setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_displaytrial, trialData));
 
		ListView listView = getListView();
		listView.setTextFilterEnabled(true);
	}
}
