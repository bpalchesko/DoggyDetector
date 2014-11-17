package edu.upenn.cis350.cancerDog;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

public class EditDefaultActivityNew extends Activity implements NumberPicker.OnValueChangeListener {
	
	public static final int ButtonClickActivity_ID = 1;
	BloodWheel bw;
	NumberPicker settingsControlNumberPicker;
	NumberPicker settingsBenignNumberPicker;
	NumberPicker settingsMalignantNumberPicker;

	static ArrayList<String> dogs = new ArrayList<String>();
	static ArrayList<String> handlers = new ArrayList<String>();

	private ArrayAdapter<String> dogAdapter;
	private ArrayAdapter<CharSequence> handlerAdapter;

	private ArrayList<Boolean> set = new ArrayList<Boolean>();
	private Spinner dogSpinner;
	private Spinner handlerSpinner;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editdefaults_new);
		Log.e("Loading Activity", "EditDefaultActivityNew");
		Intent data = (Intent) getIntent();
		dogs = getGroup(this, "dogs");
		handlers = getGroup(this, "handlers");

		for (int i = 0; i < 4; i++) {
			set.add(false);
		}
		
		if (data.hasExtra("Control") && data.hasExtra("Benign") && data.hasExtra("Malignant")) {
		      bw = new BloodWheel();
		      bw.Control=data.getExtras().getInt("Control");
		      bw.Benign=data.getExtras().getInt("Benign");
		      bw.Malignant=data.getExtras().getInt("Malignant");
		}
		else
		{
			Log.e("Loading Activity", "bw data not passed to activity");
			bw = new BloodWheel();
		}
		      
		settingsControlNumberPicker = (NumberPicker) findViewById(R.id.settingsControlNumberPicker);
		settingsControlNumberPicker.setMinValue(1);
		settingsControlNumberPicker.setMaxValue(12);
		//settingsControlNumberPicker.setValue(1);
		settingsControlNumberPicker.setValue(bw.Control);
		settingsControlNumberPicker.setOnValueChangedListener(this);
		// -16777216 is the number for black
		setNumberPickerTextColor(settingsControlNumberPicker, -16777216);
		
		settingsBenignNumberPicker = (NumberPicker) findViewById(R.id.settingsBenignNumberPicker);
		settingsBenignNumberPicker.setMinValue(1);
		settingsBenignNumberPicker.setMaxValue(12);
		//settingsBenignNumberPicker.setValue(1);
		settingsBenignNumberPicker.setValue(bw.Benign);
		settingsBenignNumberPicker.setOnValueChangedListener(this);
		// -16777216 is the number for black
		setNumberPickerTextColor(settingsBenignNumberPicker, -16777216);
		
		settingsMalignantNumberPicker = (NumberPicker) findViewById(R.id.settingsMalignantNumberPicker);
		settingsMalignantNumberPicker.setMinValue(1);
		settingsMalignantNumberPicker.setMaxValue(12);
		//settingsMalignantNumberPicker.setValue(1);
		settingsMalignantNumberPicker.setValue(bw.Malignant);
		settingsMalignantNumberPicker.setOnValueChangedListener(this);
		// -16777216 is the number for black
		setNumberPickerTextColor(settingsMalignantNumberPicker, -16777216);
		
		dogAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item);
		dogAdapter.setDropDownViewResource(R.layout.dropdown_spinner_item);
		dogAdapter.add("Click to edit");
		for (String s : dogs) {
			dogAdapter.add(s);
		}
		dogAdapter.add("Add +");
		dogSpinner = (Spinner) findViewById(R.id.dogSpinner1);
		dogSpinner.setAdapter(dogAdapter);
		dogSpinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parentView,
							View selectedItemView, int position, long id) {
						selected(position, 1, dogs);
						dogSpinner.setSelection(position);
						
					}

					@Override
					public void onNothingSelected(AdapterView<?> parentView) {
						// your code here
					}
				});

		// for the edit defaults handlers section
		handlerAdapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_item);
		handlerAdapter.setDropDownViewResource(R.layout.dropdown_spinner_item);
		handlerAdapter.add("Click to edit");
		for (String s : handlers) {
			handlerAdapter.add(s);
		}
		handlerAdapter.add("Add +");

		handlerSpinner = (Spinner) findViewById(R.id.handlerSpinner1);
		handlerSpinner.setAdapter(handlerAdapter);
		handlerSpinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parentView,
							View selectedItemView, int position, long id) {
						selected(position, 2, handlers);
						handlerSpinner.setSelection(position);
						
						
					}

					@Override
					public void onNothingSelected(AdapterView<?> parentView) {
						// your code here
					}
				});
	}

	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		// TODO Auto-generated method stub

	}

	/**
	 * Credit to StackOverflow
	 * 
	 * @param numberPicker
	 * @param color
	 * @return
	 */
	public static boolean setNumberPickerTextColor(NumberPicker numberPicker,
			int color) {
		final int count = numberPicker.getChildCount();
		for (int i = 0; i < count; i++) {
			View child = numberPicker.getChildAt(i);
			if (child instanceof EditText) {
				try {
					Field selectorWheelPaintField = numberPicker.getClass()
							.getDeclaredField("mSelectorWheelPaint");
					selectorWheelPaintField.setAccessible(true);
					((Paint) selectorWheelPaintField.get(numberPicker))
							.setColor(color);
					((EditText) child).setTextColor(color);
					numberPicker.invalidate();
					return true;
				} catch (NoSuchFieldException e) {
					Log.w("setNumberPickerTextColor", e);
				} catch (IllegalAccessException e) {
					Log.w("setNumberPickerTextColor", e);
				} catch (IllegalArgumentException e) {
					Log.w("setNumberPickerTextColor", e);
				}
			}
		}
		return false;
	}

	// what is called when an option in the spinner is clicked on
	public void selected(int position, int which, ArrayList<String> list) {
		if (!set.get(0)) {
			set.remove(which);
			set.add(which, true);
		} else {
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
				EditDefaultActivityNew.this);
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
		final EditText input = new EditText(EditDefaultActivityNew.this);

		AlertDialog.Builder temp = new AlertDialog.Builder(
				EditDefaultActivityNew.this);
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
		dogAdapter.clear();
		dogAdapter.add("Click to edit");
		for (String s : dogs) {
			dogAdapter.add(s);
		}
		dogAdapter.add("Add +");
		
		handlerAdapter.clear();
		handlerAdapter.add("Click to edit");
		for (String s : handlers) {
			handlerAdapter.add(s);
		}
		handlerAdapter.add("Add +");
	}

	// when the default spinner is clicked, this is called to get the
	// information in the spinner
	public static ArrayList<String> getGroup(Context c, String group) {
		ArrayList<String> temp = new ArrayList<String>();
		if (group == "dogs") {
		String[] dogArray = c.getResources().getStringArray(R.array.dogs);
		for (String s : dogArray) {
			temp.add(s);
		}
		} else if (group == "handlers") {
			String[] handlerArray = c.getResources().getStringArray(R.array.handlers);
			for (String s : handlerArray) {
				temp.add(s);
			}
		}
		return temp;
	}

	// when you delete a default option, this method is called to ensure that
	// you save the spinner without the default method that was deleted
	public void saveGroup() {
		SharedPreferences preferences = this.getSharedPreferences(
				"edu.upenn.cis350.cancerDog.dogs", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();

		editor.clear();
		for (int i = 0; i < dogs.size(); i++) {
			editor.putString(String.valueOf(i), dogs.get(i));
		}
		editor.commit();

		preferences = this.getSharedPreferences(
				"edu.upenn.cis350.cancerDog.handlers", Context.MODE_PRIVATE);
		editor = preferences.edit();

		editor.clear();
		for (int i = 0; i < handlers.size(); i++) {
			editor.putString(String.valueOf(i), handlers.get(i));
		}
		editor.commit();
	}
	
	// when the back button is clicked, this method is called
	public void onPreviousButtonClick(View v) {
		finish();
		System.exit(0);
	}

	@Override
	public void finish(){
		Intent i = new Intent();
		bw.Benign=settingsBenignNumberPicker.getValue();
		bw.Control=settingsControlNumberPicker.getValue();
		bw.Malignant=settingsMalignantNumberPicker.getValue();
		i.putExtra("Benign", bw.Benign);
		i.putExtra("Control", bw.Control);  
		i.putExtra("Malignant", bw.Malignant); 
		setResult(Activity.RESULT_OK, i);
		super.finish();
	}
}
