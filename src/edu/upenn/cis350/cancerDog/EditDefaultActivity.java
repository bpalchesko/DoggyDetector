package edu.upenn.cis350.cancerDog;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class EditDefaultActivity extends Activity {
	public static final int ButtonClickActivity_ID = 1;
	static ArrayList<String> handlers = new ArrayList<String>();
	static ArrayList<String> dogs = new ArrayList<String>();
	static ArrayList<String> experimentals = new ArrayList<String>();
	static ArrayList<String> controls = new ArrayList<String>();
	private Spinner hSpinner, dSpinner, eSpinner, cSpinner;
	private ArrayAdapter<CharSequence> hAdapter, dAdapter, eAdapter, cAdapter;
	private ArrayList<Boolean> set = new ArrayList<Boolean>();

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle) Called when the
	 * activity is first created. This is where you should do all of your normal
	 * static set up: create views, bind data to lists, etc. This method also
	 * provides you with a Bundle containing the activity's previously frozen
	 * state, if there was one. this is for the edit default page
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editdefaults);
		handlers = getGroup(this, "handlers");
		dogs = getGroup(this, "dogs");
		experimentals = getGroup(this, "experimentals");
		controls = getGroup(this, "controls");

		for (int i = 0; i < 4; i++) {
			set.add(false);
		}

		// for the edit defaults handler section
		// TODO: the following code is essentially repeated 3 times for the
		// different adapters and spinners (CODE SMELL)
		hAdapter = new ArrayAdapter(this,
				android.R.layout.simple_spinner_dropdown_item);
		hAdapter.add("Click to edit");
		for (String s : handlers) {
			hAdapter.add(s);
		}
		hAdapter.add("Add +");

		hSpinner = (Spinner) findViewById(R.id.handlerSpinner);
		hSpinner.setAdapter(hAdapter);
		hSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				selected(position, 0, handlers);
				hSpinner.setSelection(0);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// your code here
			}
		});

		// for the edit defaults dogs section
		dAdapter = new ArrayAdapter(this,
				android.R.layout.simple_spinner_dropdown_item);
		dAdapter.add("Click to edit");
		for (String s : dogs) {
			dAdapter.add(s);
		}
		dAdapter.add("Add +");

		dSpinner = (Spinner) findViewById(R.id.dogSpinner);
		dSpinner.setAdapter(dAdapter);
		dSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				selected(position, 1, dogs);
				dSpinner.setSelection(0);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// your code here
			}
		});

		// for the edit defaults experimentals section
		eAdapter = new ArrayAdapter(this,
				android.R.layout.simple_spinner_dropdown_item);
		eAdapter.add("Click to edit");
		for (String s : experimentals) {
			eAdapter.add(s);
		}
		eAdapter.add("Add +");

		eSpinner = (Spinner) findViewById(R.id.experimentalSpinner);
		eSpinner.setAdapter(eAdapter);
		eSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				selected(position, 2, experimentals);
				eSpinner.setSelection(0);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// your code here
			}
		});

		// for the edit defaults controls section
		cAdapter = new ArrayAdapter(this,
				android.R.layout.simple_spinner_dropdown_item);
		cAdapter.add("Click to edit");
		for (String s : controls) {
			cAdapter.add(s);
		}
		cAdapter.add("Add +");

		cSpinner = (Spinner) findViewById(R.id.controlSpinner);
		cSpinner.setAdapter(cAdapter);
		cSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				selected(position, 3, controls);
				cSpinner.setSelection(0);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// your code here
			}
		});
	}

	// what is called when an option in the spinner is clicked on
	public void selected(int position, int which, ArrayList<String> list) {
		if (!set.get(0)) {
			set.remove(which);
			set.add(which, true);
		} else {
			// Toast.makeText(EditDefaultActivity.this,
			// String.valueOf(position), Toast.LENGTH_SHORT).show();
			if (position != 0) {
				if (position <= list.size()) {
					deletePrompt(list, position - 1, 0);
				} else {
					addPrompt(list);
				}
			}
		}
	}

	// when you click on an already created option, it will ask if you want to
	// delete it or not (yes, or no). If you answer yes, this method will be
	// called.
	public void deletePrompt(final ArrayList<String> list, final int position,
			final int drop) {
		AlertDialog.Builder temp = new AlertDialog.Builder(
				EditDefaultActivity.this);
		temp.setMessage("Are you sure you want to delete " + list.get(position));
		temp.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				list.remove(position);
				refreshAdapters();
				saveGroup();
			}
		});
		temp.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User cancelled the dialog
			}
		});
		AlertDialog tempDialog = temp.create();
		tempDialog.show();
	}

	// if you want to add a default value and click add, this method is called
	public void addPrompt(final ArrayList<String> list) {
		final EditText input = new EditText(EditDefaultActivity.this);

		AlertDialog.Builder temp = new AlertDialog.Builder(
				EditDefaultActivity.this);
		temp.setTitle("Add");

		temp.setView(input);

		temp.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String text = input.getText().toString();
				list.add(text);
				refreshAdapters();
				saveGroup();
			}
		});
		temp.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.
			}
		});

		AlertDialog tempDialog = temp.create();

		tempDialog.setView(input);

		tempDialog.show();
	}

	// this is shown as a default on the spinner for the edit default screen
	public void refreshAdapters() {
		hAdapter.clear();
		hAdapter.add("Click to edit");
		for (String s : handlers) {
			hAdapter.add(s);
		}
		hAdapter.add("Add +");

		dAdapter.clear();
		dAdapter.add("Click to edit");
		for (String s : dogs) {
			dAdapter.add(s);
		}
		dAdapter.add("Add +");

		eAdapter.clear();
		eAdapter.add("Click to edit");
		for (String s : experimentals) {
			eAdapter.add(s);
		}
		eAdapter.add("Add +");

		cAdapter.clear();
		cAdapter.add("Click to edit");
		for (String s : controls) {
			cAdapter.add(s);
		}
		cAdapter.add("Add +");
	}

	// when the default spinner is clicked, this is called to get the
	// information in the spinner
	public static ArrayList<String> getGroup(Context c, String group) {
		String defaultStr = "Null";
		SharedPreferences preferences = c.getSharedPreferences(
				"edu.upenn.cis350.cancerDog." + group, Context.MODE_PRIVATE);
		ArrayList<String> temp = new ArrayList<String>();
		int i = 0;
		String x = defaultStr;
		while ((x = preferences.getString(String.valueOf(i), defaultStr)) != defaultStr) {
			i++;
			temp.add(x);
		}
		return temp;
	}

	// when you delete a default option, this method is called to ensure that
	// you save the spinner without the default method that was deleted
	public void saveGroup() {
		SharedPreferences preferences = this.getSharedPreferences(
				"edu.upenn.cis350.cancerDog.handlers", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();

		editor.clear();
		for (int i = 0; i < handlers.size(); i++) {
			editor.putString(String.valueOf(i), handlers.get(i));
		}
		editor.commit();

		preferences = this.getSharedPreferences(
				"edu.upenn.cis350.cancerDog.dogs", Context.MODE_PRIVATE);
		editor = preferences.edit();

		editor.clear();
		for (int i = 0; i < dogs.size(); i++) {
			editor.putString(String.valueOf(i), dogs.get(i));
		}
		editor.commit();

		preferences = this.getSharedPreferences(
				"edu.upenn.cis350.cancerDog.experimentals",
				Context.MODE_PRIVATE);
		editor = preferences.edit();

		editor.clear();
		for (int i = 0; i < experimentals.size(); i++) {
			editor.putString(String.valueOf(i), experimentals.get(i));
		}
		editor.commit();

		preferences = this.getSharedPreferences(
				"edu.upenn.cis350.cancerDog.controls", Context.MODE_PRIVATE);
		editor = preferences.edit();

		editor.clear();
		for (int i = 0; i < controls.size(); i++) {
			editor.putString(String.valueOf(i), controls.get(i));
		}
		editor.commit();
	}

	// when the back button is clicked, this method is called
	public void onPreviousButtonClick(View v) {
		finish();
		System.exit(0);
	}

}
