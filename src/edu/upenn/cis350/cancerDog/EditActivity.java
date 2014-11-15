package edu.upenn.cis350.cancerDog;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class EditActivity extends ListActivity {
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
		if (Trial.getNumTrials() == 0)
			return;
		Trial.context = this;
		String[] trials = new String[Trial.getNumTrials()];
		for (int i = 0; i < trials.length; i++) {
			trials[i] = "Session " + (i + 1);
		}
		setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_edit,
				trials));

		ListView listView = getListView();
		listView.setTextFilterEnabled(true);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				int trialNum = Integer.parseInt(((TextView) view).getText()
						.toString().split(" ")[1]) - 1;
				Intent i = new Intent(parent.getContext(),
						DisplayEditActivity.class);
				i.putExtra("sessionNumber", trialNum);

				// Launch an activity for which you would like a result when it
				// finished. When this activity exits, your onActivityResult()
				// method will be called with the given requestCode. Only used
				// with intent protocols
				startActivityForResult(i, 1);
			}
		});
	}

	/*
	 * will exit gracefully from the window when the exit button is pressed
	 */
	public void onExitButtonClick(View v) {
		finish();
		System.exit(1);
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu) adds the
	 * buttons to the initial menu
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.launcher, menu);
		return true;
	}

}