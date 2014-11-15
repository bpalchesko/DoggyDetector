package edu.upenn.cis350.cancerDog;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.Toast;

public class EditDefaultActivityNew extends Activity implements NumberPicker.OnValueChangeListener {
	
	public static final int ButtonClickActivity_ID = 1;
	BloodWheel bw;
	NumberPicker settingsControlNumberPicker;
	NumberPicker settingsBenignNumberPicker;
	NumberPicker settingsMalignantNumberPicker;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editdefaults_new);
		Log.e("Loading Activity", "EditDefaultActivityNew");
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
	}

	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Credit to StackOverflow
	 * @param numberPicker
	 * @param color
	 * @return
	 */
	public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color)
	{
	    final int count = numberPicker.getChildCount();
	    for(int i = 0; i < count; i++){
	        View child = numberPicker.getChildAt(i);
	        if(child instanceof EditText){
	            try{
	                Field selectorWheelPaintField = numberPicker.getClass()
	                    .getDeclaredField("mSelectorWheelPaint");
	                selectorWheelPaintField.setAccessible(true);
	                ((Paint)selectorWheelPaintField.get(numberPicker)).setColor(color);
	                ((EditText)child).setTextColor(color);
	                numberPicker.invalidate();
	                return true;
	            }
	            catch(NoSuchFieldException e){
	                Log.w("setNumberPickerTextColor", e);
	            }
	            catch(IllegalAccessException e){
	                Log.w("setNumberPickerTextColor", e);
	            }
	            catch(IllegalArgumentException e){
	                Log.w("setNumberPickerTextColor", e);
	            }
	        }
	    }
	    return false;
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
