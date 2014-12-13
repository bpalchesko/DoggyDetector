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
import android.widget.Spinner;

public class EditDefaultActivityNew extends Activity implements NumberPicker.OnValueChangeListener {
	
	public static final int ButtonClickActivity_ID = 1;
	BloodWheel bw;
	NumberPicker settingsControlNumberPicker;
	NumberPicker settingsBenignNumberPicker;
	NumberPicker settingsMalignantNumberPicker;

	private ArrayList<String> dogs = new ArrayList<String>();
	private ArrayList<String> personnel = new ArrayList<String>();
	private String currentDog;
	private String currentHandler;
	private String currentTester;
	private String currentRecorder;
	
	private static final String PERSONNEL_FILE = "edu.upenn.cis350.cancerDog.handlers";
	private static final String DOG_FILE = "edu.upenn.cis350.cancerDog.dogs";
	private static final String CONDITIONS_FILE = "edu.upenn.cis350.cancerDog.conditions";
	private SharedPreferences personnelSettings;
	private SharedPreferences dogSettings;
	private SharedPreferences conditionsSettings;
	private SharedPreferences.Editor personnelEditor; 
	private SharedPreferences.Editor dogEditor; 
	private SharedPreferences.Editor conditionsEditor;
	
	private ArrayAdapter<String> dogAdapter;
	private ArrayAdapter<CharSequence> handlerAdapter;
	private ArrayAdapter<CharSequence> testerAdapter;
	private ArrayAdapter<CharSequence> recorderAdapter;
	
	private Spinner dogSpinner;
	private Spinner handlerSpinner;
	private Spinner testerSpinner;
	private Spinner recorderSpinner;
	
	private EditText tempText;
	private EditText humidityText;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {	
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editdefaults_new);
		//Log.e("Loading Activity", "EditDefaultActivityNew");
		Intent data = (Intent) getIntent();	
		dogSettings = getSharedPreferences(DOG_FILE, Context.MODE_PRIVATE);	
		personnelSettings = getSharedPreferences(PERSONNEL_FILE, Context.MODE_PRIVATE);
		conditionsSettings = getSharedPreferences(CONDITIONS_FILE, Context.MODE_PRIVATE);
		dogEditor = dogSettings.edit();
		personnelEditor = personnelSettings.edit();
		conditionsEditor = conditionsSettings.edit();
		tempText = (EditText) findViewById(R.id.tempText);
		humidityText = (EditText) findViewById(R.id.humidityText);
		getSavedSettings();		
		
		bw = new BloodWheel();
		bw.setWheelData(data);
		      
		settingsControlNumberPicker = (NumberPicker) findViewById(R.id.settingsControlNumberPicker);
		settingsControlNumberPicker.setMinValue(0);
		settingsControlNumberPicker.setMaxValue(12);
		settingsControlNumberPicker.setValue(bw.Control);
		settingsControlNumberPicker.setOnValueChangedListener(this);
		setNumberPickerTextColor(settingsControlNumberPicker, -16777216); //number for black		
		settingsBenignNumberPicker = (NumberPicker) findViewById(R.id.settingsBenignNumberPicker);
		settingsBenignNumberPicker.setMinValue(0);
		settingsBenignNumberPicker.setMaxValue(12);
		settingsBenignNumberPicker.setValue(bw.Benign);
		settingsBenignNumberPicker.setOnValueChangedListener(this);
		setNumberPickerTextColor(settingsBenignNumberPicker, -16777216); //number for black		
		settingsMalignantNumberPicker = (NumberPicker) findViewById(R.id.settingsMalignantNumberPicker);
		settingsMalignantNumberPicker.setMinValue(0);
		settingsMalignantNumberPicker.setMaxValue(12);
		settingsMalignantNumberPicker.setValue(bw.Malignant);
		settingsMalignantNumberPicker.setOnValueChangedListener(this);
		setNumberPickerTextColor(settingsMalignantNumberPicker, -16777216); //number for black
		
		dogAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item);
		dogAdapter.setDropDownViewResource(R.layout.dropdown_spinner_item);
		handlerAdapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_item);
		handlerAdapter.setDropDownViewResource(R.layout.dropdown_spinner_item);
		testerAdapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_item);
		testerAdapter.setDropDownViewResource(R.layout.dropdown_spinner_item);	
		recorderAdapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_item);
		recorderAdapter.setDropDownViewResource(R.layout.dropdown_spinner_item);	
		refreshAdapters();
		
		dogSpinner = (Spinner) findViewById(R.id.dogSpinner1);
		dogSpinner.setAdapter(dogAdapter);
		dogSpinner.setSelection(findCurrentValue("dog"));
		dogSpinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parentView,
							View selectedItemView, int position, long id) {	
						dogSpinner.setSelection(position);
						String name = dogSpinner.getSelectedItem().toString();
						if("Edit List".equals(name)) createEditDialog("dog").show();
						else currentDog = name;
					}

					@Override
					public void onNothingSelected(AdapterView<?> parentView) {}
				});

		handlerSpinner = (Spinner) findViewById(R.id.handlerSpinner1);
		handlerSpinner.setAdapter(handlerAdapter);
		handlerSpinner.setSelection(findCurrentValue("handler"));
		handlerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parentView,
							View selectedItemView, int position, long id) {
						handlerSpinner.setSelection(position);
						String name = handlerSpinner.getSelectedItem().toString();
						if ("Edit List".equals(name)) createEditDialog("personnel").show();
						else currentHandler = name;		
					}

					@Override
					public void onNothingSelected(AdapterView<?> parentView) {}
				});
		
		testerSpinner = (Spinner) findViewById(R.id.testerSpinner);
		testerSpinner.setAdapter(testerAdapter);
		testerSpinner.setSelection(findCurrentValue("tester"));
		testerSpinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parentView,
							View selectedItemView, int position, long id) {	
						testerSpinner.setSelection(position);
						String name = testerSpinner.getSelectedItem().toString();
						if ("Edit List".equals(name)) createEditDialog("personnel").show();
						else currentTester = name;					
					}

					@Override
					public void onNothingSelected(AdapterView<?> parentView) {}
				});

		recorderSpinner = (Spinner) findViewById(R.id.recorderSpinner);
		recorderSpinner.setAdapter(recorderAdapter);
		recorderSpinner.setSelection(findCurrentValue("recorder"));
		recorderSpinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parentView,
							View selectedItemView, int position, long id) {	
						recorderSpinner.setSelection(position);
						String name = recorderSpinner.getSelectedItem().toString();
						if ("Edit List".equals(name)) createEditDialog("personnel").show();
						else currentRecorder = name;						
					}

					@Override
					public void onNothingSelected(AdapterView<?> parentView) {}
				});
		

		
	}

	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {}

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


	/**
	 * Loads spinner options initially and when user edits lists
	 */
	public void refreshAdapters() {
		dogAdapter.clear();
		handlerAdapter.clear();
		testerAdapter.clear();
		recorderAdapter.clear();
		for (String s : dogs) {
			dogAdapter.add(s);
		}	
		for (String s : personnel) {
			handlerAdapter.add(s);
			testerAdapter.add(s);
			recorderAdapter.add(s);
		}
		dogAdapter.add("Edit List");
		handlerAdapter.add("Edit List");
		testerAdapter.add("Edit List");
		recorderAdapter.add("Edit List");
	}

	/**
	 * Set spinners to current selection, or first element if selection was deleted
	 * @param category i.e. dog, handler, tester, or recorder
	 * @return index of currently selected value
	 */
	public int findCurrentValue(String value) {
		if("dog".equals(value)) {
			return Math.max(dogs.indexOf(currentDog), 0);
		} else if("handler".equals(value)) {
			return Math.max(personnel.indexOf(currentHandler), 0);
		} else if("tester".equals(value)) {
			return Math.max(personnel.indexOf(currentTester), 0);			
		} else if("recorder".equals(value)) {
			return Math.max(personnel.indexOf(currentRecorder), 0);		
		}
		return 0;
	}
		
	/**
	 * Creates the dialog box to edit spinner options
	 * @param category i.e. dog, handler, tester, or recorder
	 * @return dialog box
	 */
	public AlertDialog.Builder createEditDialog(final String category) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		final EditText input = new EditText(this);
		if("dog".equals(category)) {
			dialog.setTitle("Edit Dog List");
			input.setText(nameStringFromList(dogs));
		} else {
			dialog.setTitle("Edit Personnel List");
			input.setText(nameStringFromList(personnel));
		}
		dialog.setMessage("Please enter names separated by commas \n" +
				"Example: Name1, Name2, Name3, Name4");
		dialog.setView(input);

		dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String group = input.getText().toString();
				if ("dog".equals(category)) {
					dogs = listFromNameString(group);	
				} else {
					personnel = listFromNameString(group);
				}
				refreshAdapters();			
				dogSpinner.setSelection(findCurrentValue("dog"));
				handlerSpinner.setSelection(findCurrentValue("handler"));
				testerSpinner.setSelection(findCurrentValue("tester"));
				recorderSpinner.setSelection(findCurrentValue("recorder"));
			}
		});
	
		dialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dogSpinner.setSelection(findCurrentValue("dog"));
						handlerSpinner.setSelection(findCurrentValue("handler"));
						testerSpinner.setSelection(findCurrentValue("tester"));
						recorderSpinner.setSelection(findCurrentValue("recorder"));
					}
				});
		return dialog;
	}
	
	/**
	 * Gets spinner options from saved preferences
	 * @param group i.e. dog, handlers
	 * @return list of options
	 */
	public ArrayList<String> getGroup(String group) {
		String names = "";
		if ("dogs".equals(group)) {
			String defaultDogs = "Tsunami, Ffoster, McBaine, Ohlin";			
			SharedPreferences settings = getSharedPreferences(
					DOG_FILE, Context.MODE_PRIVATE);
			names = settings.getString("dogs", defaultDogs);
		} else if ("handlers".equals(group)) {
			String defaultPersonnel = "Annemarie D, Jonathan B, Ken V, Sun, Lorenzo R";
			SharedPreferences settings = getSharedPreferences(
					PERSONNEL_FILE, Context.MODE_PRIVATE);
			names = settings.getString("handlers", defaultPersonnel);
		}
		return listFromNameString(names);
	}
	
	/**
	 * Sets variables and text fields for spinner options and current selections
	 */
	public void getSavedSettings(){
		dogs = getGroup("dogs");
		personnel= getGroup("handlers");
		currentDog = dogSettings.getString("current", "");
		currentHandler = personnelSettings.getString("current_handler", "");
		currentTester = personnelSettings.getString("current_tester", "");
		currentRecorder = personnelSettings.getString("current_recorder", "");
		tempText.setText(conditionsSettings.getString("temp","0"));
		humidityText.setText(conditionsSettings.getString("humidity","0"));
	}
	
	/**
	 * Saves spinner options and current selections to shared preferences
	 */
	public void saveCurrentSettings() {
		dogEditor.putString("current", currentDog);
		dogEditor.putString("dogs", nameStringFromList(dogs));
		dogEditor.commit();	
		personnelEditor.putString("current_handler", currentHandler);
		personnelEditor.putString("current_tester", currentTester);
		personnelEditor.putString("current_recorder", currentRecorder);
		personnelEditor.putString("handlers", nameStringFromList(personnel));
		personnelEditor.commit();
		conditionsEditor.putString("temp", tempText.getText().toString());
		conditionsEditor.putString("humidity", humidityText.getText().toString());	
		conditionsEditor.commit();
	}
	
	/**
	 * Converts comma-separated string of names to list (for spinners)
	 * @param comma-separated string of names
	 * @return list of names
	 */
	public static ArrayList<String> listFromNameString(String names) {
		ArrayList<String> temp = new ArrayList<String>();
		names = names.trim();
		String[] namesArray = names.split(",");
		for(String s: namesArray) {
			s = s.trim();
			temp.add(s);
		}
		return temp;
	}
	
	/**
	 * Converts list of names to comma-separated string (for saving to
	 * shared preferences)
	 * @param list of names
	 * @return comma-separated string of names
	 */
	public static String nameStringFromList(ArrayList<String> names) {
		String temp = "";
		for(int i = 0; i < names.size() - 1; i++) {
			temp = temp + names.get(i) + ", ";
		}
		temp += names.get(names.size() - 1);
		return temp;
	}
	
	/**
	 * Action when back button is clicked
	 * @param v
	 */
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
		bw.pushIntentData(i);
		saveCurrentSettings();
		setResult(Activity.RESULT_OK, i);
		super.finish();
	}
}
