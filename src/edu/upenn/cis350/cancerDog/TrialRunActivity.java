package edu.upenn.cis350.cancerDog;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.GsonBuilder;

//import edu.upenn.cis350.cancerDog.Trial.PostJson;

//import edu.upenn.cis350.cancerDog.Trial.GetSessions;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.WindowManager;

import java.util.*;

public class TrialRunActivity extends FragmentActivity implements
		SaveNotification.NoticeDialogListener {
	ImageButton btntrailPass1;
	ImageButton btntrailPass2;
	ImageButton btntrailPass3;
	ImageButton btnleave;
	ImageButton ibTrialstop;
	ImageButton btnBack;
	ImageButton ibNotes;
	Button btnSave;
	Button btnNext;
	EditText edText;
	TextView etNumber;
	TextView etDog;
	static BloodWheel bw;
	private static String currentDog;
	private static String currentHandler;
	private static String currentRecorder;
	private static String currentTester;
	private static String currentTemp;
	private static String currentHumidity;
	private static final String FORM_URL = "https://docs.google.com/forms/d/1R8Oq1YvTVfrgxafxcGZaO6C_wca3Lv_JV3Su_MIEnaU/formResponse";
	private static final String DOG_NAME = "entry_434753845=";
	private static final String RECORDER = "entry_1725873612=";
	private static final String HANDLER = "entry_1410932431=";
	private static final String TESTER = "entry_270032351=";
	private static final String TEMPERATURE = "entry_2068466040=";
	private static final String HUMIDITY = "entry_2021874211=";
	private static final String SUCCESS_RATE = "entry_1038306324=";
	private static final String SENSITIVITY = "entry_1407605094=";
	private static final String SPEC_NORMAL = "entry_494446774=";
	private static final String SPEC_BENIGN = "entry_515845283=";
	private static final String SPEC_TOTAL = "entry_921527013=";
	private static final String TOTAL_TNN = "entry_333632433=";
	private static final String TOTAL_TNB = "entry_1197022772=";
	private static final String TOTAL_FPN = "entry_216613810=";
	private static final String TOTAL_FPB = "entry_368331932=";
	private static final String TOTAL_FPE = "entry_1938978197=";
	private static final String TOTAL_FN = "entry_1347207823=";
	private static final String TOTAL_TP = "entry_918273940=";
	private static final String[] TRIALS = new String[] { "entry_873369963=",
			"entry_82683764=", "entry_403485159=", "entry_1585414345=",
			"entry_809017169=", "entry_14494942=", "entry_487964326=",
			"entry_983571317=", "entry_964442581=", "entry_786666526=",
			"entry_1055685979=", "entry_560711180=", "entry_2037751422=",
			"entry_1516979135=", "entry_1970120858=", "entry_670425955=",
			"entry_2145047438=", "entry_802163021=", "entry_1561108562=",
			"entry_1290361149=" };
	private static final String[] TRIAL_NOTES = new String[] {
			"entry_1440152102=", "entry_1922376403=", "entry_1854046377=",
			"entry_1970338951=", "entry_1225458375=", "entry_718995831=",
			"entry_765686634=", "entry_516681620=", "entry_1297993719=",
			"entry_1692870716=", "entry_113042049=", "entry_1084028390=",
			"entry_1740623401=", "entry_1142692779=", "entry_404381647=",
			"entry_39169021=", "entry_74263144=", "entry_1148593787=",
			"entry_1030777065=", "entry_861331498=" };

	static ArrayList<String> results = new ArrayList<String>();
	String btn1Text = ""; // ### Primitive obsession, refactor this
	String btn2Text = "";
	String btn3Text = "";
	int counter = 1;
	static String notes = "";
	static ArrayList<String> trialNotes = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trial_run);
		Log.e("Loading Activity", "TrialRunActivity");
		btntrailPass1 = (ImageButton) findViewById(R.id.ibTrial1pass);
		btntrailPass2 = (ImageButton) findViewById(R.id.ibTrial2pass);
		btntrailPass3 = (ImageButton) findViewById(R.id.ibTrial3pass);
		ibTrialstop = (ImageButton) findViewById(R.id.ibTrialstop);
		ibNotes = (ImageButton) findViewById(R.id.ibNotes);
		btnleave = (ImageButton) findViewById(R.id.ibLeave);
		btnBack = (ImageButton) findViewById(R.id.ibBack);
		btnSave = (Button) findViewById(R.id.ibSave);
		btnNext = (Button) findViewById(R.id.ibNext);
		edText = (EditText) findViewById(R.id.editText1);

		etNumber = (TextView) findViewById(R.id.NumberText);
		etDog = (TextView) findViewById(R.id.DogTextName);
		Intent data = (Intent) getIntent();

		if (data.hasExtra("Notes")) {
			notes = data.getExtras().getString("Notes");
		}

		if (data.hasExtra("EditText")) {
			edText.setText(data.getExtras().getString("EditText"));
		}

		bw = new BloodWheel();
		bw.setWheelData(data);

		SharedPreferences preferences = getSharedPreferences(
				"edu.upenn.cis350.cancerDog.handlers", Context.MODE_PRIVATE);
		currentHandler = preferences.getString("current_handler",
				"Go to settings to set");
		currentRecorder = preferences.getString("current_recorder",
				"Go to settings to set");
		currentTester = preferences.getString("current_tester",
				"Go to settings to set");

		preferences = getSharedPreferences("edu.upenn.cis350.cancerDog.dogs",
				Context.MODE_PRIVATE);
		currentDog = preferences.getString("current", "Go to settings to set");

		preferences = getSharedPreferences(
				"edu.upenn.cis350.cancerDog.conditions", Context.MODE_PRIVATE);

		currentTemp = preferences.getString("temp", "Go to settings to set");
		currentHumidity = preferences
				.getString("temp", "Go to settings to set");

		styleButtons();

		etDog.setText(currentDog);

		btntrailPass1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String editTextStr = edText.getText().toString() + " "
						+ btn1Text;
				edText.setText(editTextStr);
			}
		});

		btntrailPass2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String editTextStr = edText.getText().toString() + " "
						+ btn2Text;
				edText.setText(editTextStr);
			}
		});

		btntrailPass3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String editTextStr = edText.getText().toString() + " "
						+ btn3Text;
				edText.setText(editTextStr);
			}
		});

		btnleave.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String editTextStr = edText.getText().toString() + "  L";
				edText.setText(editTextStr);
			}
		});

		btnBack.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (edText.getText().toString().length() >= 3) {
					String editTextStr = edText
							.getText()
							.toString()
							.substring(0,
									edText.getText().toString().length() - 3);
					edText.setText(editTextStr);

				}
			}
		});

		btnSave.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showNoticeDialog();

			}
		});

		btnNext.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				results.add(edText.getText().toString());
				edText.setText("");
				counter++;
				etNumber.setText("" + counter);
				trialNotes.add(notes);
				notes = "";
			}
		});

		edText.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return false;
			}

		});
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

	}// end oncreate

	public void showNoticeDialog() {
		// Create an instance of the dialog fragment and show it
		if (haveNetworkConnection() == true) {
		DialogFragment dialog = new SaveNotification();
		dialog.show(getFragmentManager(), "SaveNotification");
		}
		else{
			Toast.makeText(getApplicationContext(),
					"Internet not connected, please connect to save trial",
					Toast.LENGTH_LONG).show();
		}
	}

	// The dialog fragment receives a reference to this Activity through the
	// Fragment.onAttach() callback, which it uses to call the following methods
	// defined by the NoticeDialogFragment.NoticeDialogListener interface
	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		// View v = dialog.getView();
		// EditText e = (EditText) v.findViewById(R.layout.finishedNotes);

		Toast.makeText(getApplicationContext(), "Trial Saved =)",
				Toast.LENGTH_LONG).show();
		HashMap<String, Object> trial = new HashMap<String, Object>();
		PostJson task = new PostJson();
		counter = 1;
		etNumber.setText("" + counter);
		if (edText.getText().toString() != "")
			results.add(edText.getText().toString());
		trialNotes.add(notes);
		task.execute((HashMap<String, Object>[]) (new HashMap[] { trial }));
		edText.setText("");
		notes = "";

	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// User touched the dialog's negative button
	}
	
	/**
	 * Checks network connection 
	 * Adapted from stackoverflow
	 * @return
	 */
	private boolean haveNetworkConnection() {
	    boolean haveConnectedWifi = false;
	    boolean haveConnectedMobile = false;

	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
	    for (NetworkInfo ni : netInfo) {
	        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
	            if (ni.isConnected())
	                haveConnectedWifi = true;
	        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
	            if (ni.isConnected())
	                haveConnectedMobile = true;
	    }
	    return haveConnectedWifi || haveConnectedMobile;
	}

	public void onStopClick(View v) {
		Intent i = new Intent(this, StopActivity.class);
		startActivityForResult(i, 1);
	}

	public void onNotesClick(View v) {
		Intent i = new Intent(this, NotesActivity.class);
		if (notes != "")
			i.putExtra("Notes", notes);
		startActivityForResult(i, 1);
	}

	private void styleButtons() {
		if (bw.Malignant == 0) {
			btn1Text = "";
			setButtonStyle(btntrailPass1, bw.Malignant, "Empty");
			if (bw.Control <= bw.Benign) {
				if (bw.Control != 0) {
					setButtonStyle(btntrailPass2, bw.Control, "Control");
					setButtonStyle(btntrailPass3, bw.Benign, "Benign");
					btn2Text = "P" + bw.Control;
					btn3Text = "P" + bw.Benign;
				} else {
					setButtonStyle(btntrailPass2, bw.Control, "Empty");
					setButtonStyle(btntrailPass3, bw.Benign, "Benign");
					btn3Text = "P" + bw.Benign;
				}
			} else {
				setButtonStyle(btntrailPass3, bw.Control, "Control");
				btn3Text = "P" + bw.Control;
				if (bw.Benign != 0) {
					setButtonStyle(btntrailPass2, bw.Benign, "Benign");
					btn2Text = "P" + bw.Benign;
				} else {
					setButtonStyle(btntrailPass2, bw.Benign, "Empty");

				}
			}
			return;
		}

		if (bw.Control == 0) {
			btn1Text = "";
			setButtonStyle(btntrailPass1, bw.Control, "Empty");
			btn1Text = "";
			if (bw.Malignant <= bw.Benign) {
				setButtonStyle(btntrailPass3, bw.Benign, "Benign");
				btn3Text = "P" + bw.Benign;

				if (bw.Malignant != 0) {
					setButtonStyle(btntrailPass2, bw.Malignant, "Malignant");
					btn2Text = "P" + bw.Malignant;
				} else {
					setButtonStyle(btntrailPass2, bw.Malignant, "Empty");

				}
			} else {
				setButtonStyle(btntrailPass3, bw.Malignant, "Malignant");
				btn3Text = "P" + bw.Malignant;
				if (bw.Benign != 0) {
					setButtonStyle(btntrailPass2, bw.Benign, "Benign");

					btn2Text = "P" + bw.Benign;
				} else {
					setButtonStyle(btntrailPass2, bw.Benign, "Empty");

				}
			}

			return;
		}

		if (bw.Benign == 0) {
			btn1Text = "";
			setButtonStyle(btntrailPass1, bw.Benign, "Empty");
			if (bw.Malignant <= bw.Control) {
				setButtonStyle(btntrailPass3, bw.Control, "Control");
				btn3Text = "P" + bw.Control;
				if (bw.Malignant != 0) {
					setButtonStyle(btntrailPass2, bw.Malignant, "Malignant");

					btn2Text = "P" + bw.Malignant;

				} else {
					setButtonStyle(btntrailPass2, bw.Malignant, "Empty");
				}
			} else {
				setButtonStyle(btntrailPass3, bw.Malignant, "Malignant");
				btn3Text = "P" + bw.Malignant;

				if (bw.Control != 0) {

					setButtonStyle(btntrailPass2, bw.Control, "Control");

					btn2Text = "P" + bw.Control;
				} else {
					setButtonStyle(btntrailPass2, bw.Control, "Empty");
				}
			}
			return;
		}

		if (bw.Benign < bw.Control) {
			if (bw.Benign < bw.Malignant) {
				setButtonStyle(btntrailPass1, bw.Benign, "Benign");
				btn1Text = "P" + bw.Benign;
				if (bw.Control < bw.Malignant) {
					setButtonStyle(btntrailPass2, bw.Control, "Control");
					setButtonStyle(btntrailPass3, bw.Malignant, "Malignant");
					btn2Text = "P" + bw.Control;
					btn3Text = "P" + bw.Malignant;
				} else {
					setButtonStyle(btntrailPass3, bw.Control, "Control");
					setButtonStyle(btntrailPass2, bw.Malignant, "Malignant");
					btn3Text = "P" + bw.Control;
					btn2Text = "P" + bw.Malignant;
				}
			} else {
				setButtonStyle(btntrailPass1, bw.Malignant, "Malignant");
				setButtonStyle(btntrailPass2, bw.Benign, "Benign");
				setButtonStyle(btntrailPass3, bw.Control, "Control");
				btn1Text = "P" + bw.Malignant;
				btn2Text = "P" + bw.Benign;
				btn3Text = "P" + bw.Control;
			}
		} else if (bw.Control < bw.Malignant) {
			setButtonStyle(btntrailPass1, bw.Control, "Control");
			btn1Text = "P" + bw.Control;
			if (bw.Benign < bw.Malignant) {
				setButtonStyle(btntrailPass2, bw.Benign, "Benign");
				setButtonStyle(btntrailPass3, bw.Malignant, "Malignant");
				btn2Text = "P" + bw.Benign;
				btn3Text = "P" + bw.Malignant;
			} else {
				setButtonStyle(btntrailPass2, bw.Malignant, "Malignant");
				setButtonStyle(btntrailPass3, bw.Benign, "Benign");
				btn2Text = "P" + bw.Malignant;
				btn3Text = "P" + bw.Benign;
			}
		} else {
			setButtonStyle(btntrailPass1, bw.Malignant, "Malignant");
			setButtonStyle(btntrailPass2, bw.Control, "Control");
			setButtonStyle(btntrailPass3, bw.Benign, "Benign");
			btn1Text = "P" + bw.Malignant;
			btn2Text = "P" + bw.Control;
			btn3Text = "P" + bw.Benign;
		}

	}// styleButtons

	private void setButtonStyle(ImageButton button, int buttonValue,
			String Style) {
		if (Style == "Empty") {
			button.setVisibility(100);
			return;
		}

		switch (buttonValue) {
		case 1:
			if (Style.equals("Control"))
				button.setImageResource(R.drawable.btn_g1);
			else if (Style.equals("Malignant"))
				button.setImageResource(R.drawable.btn_r1);
			else if (Style.equals("Benign"))
				button.setImageResource(R.drawable.btn_y1);
			break;
		case 2:
			if (Style.equals("Control"))
				button.setImageResource(R.drawable.btn_g2);
			else if (Style.equals("Malignant"))
				button.setImageResource(R.drawable.btn_r2);
			else if (Style.equals("Benign"))
				button.setImageResource(R.drawable.btn_y2);
			break;
		case 3:
			if (Style.equals("Control"))
				button.setImageResource(R.drawable.btn_g3);
			else if (Style.equals("Malignant"))
				button.setImageResource(R.drawable.btn_r3);
			else if (Style.equals("Benign"))
				button.setImageResource(R.drawable.btn_y3);
			break;
		case 4:
			if (Style.equals("Control"))
				button.setImageResource(R.drawable.btn_g4);
			else if (Style.equals("Malignant"))
				button.setImageResource(R.drawable.btn_r4);
			else if (Style.equals("Benign"))
				button.setImageResource(R.drawable.btn_y4);
			break;
		case 5:
			if (Style.equals("Control"))
				button.setImageResource(R.drawable.btn_g5);
			else if (Style.equals("Malignant"))
				button.setImageResource(R.drawable.btn_r5);
			else if (Style.equals("Benign"))
				button.setImageResource(R.drawable.btn_y5);
			break;
		case 6:
			if (Style.equals("Control"))
				button.setImageResource(R.drawable.btn_g6);
			else if (Style.equals("Malignant"))
				button.setImageResource(R.drawable.btn_r6);
			else if (Style.equals("Benign"))
				button.setImageResource(R.drawable.btn_y6);
			break;
		case 7:
			if (Style.equals("Control"))
				button.setImageResource(R.drawable.btn_g7);
			else if (Style.equals("Malignant"))
				button.setImageResource(R.drawable.btn_r7);
			else if (Style.equals("Benign"))
				button.setImageResource(R.drawable.btn_y7);
			break;
		case 8:
			if (Style.equals("Control"))
				button.setImageResource(R.drawable.btn_g8);
			else if (Style.equals("Malignant"))
				button.setImageResource(R.drawable.btn_r8);
			else if (Style.equals("Benign"))
				button.setImageResource(R.drawable.btn_y8);
			break;
		case 9:
			if (Style.equals("Control"))
				button.setImageResource(R.drawable.btn_g9);
			else if (Style.equals("Malignant"))
				button.setImageResource(R.drawable.btn_r9);
			else if (Style.equals("Benign"))
				button.setImageResource(R.drawable.btn_y9);
			break;
		case 10:
			if (Style.equals("Control"))
				button.setImageResource(R.drawable.btn_g10);
			else if (Style.equals("Malignant"))
				button.setImageResource(R.drawable.btn_r10);
			else if (Style.equals("Benign"))
				button.setImageResource(R.drawable.btn_y10);
			break;
		case 11:
			if (Style.equals("Control"))
				button.setImageResource(R.drawable.btn_g11);
			else if (Style.equals("Malignant"))
				button.setImageResource(R.drawable.btn_r11);
			else if (Style.equals("Benign"))
				button.setImageResource(R.drawable.btn_y11);
			break;
		case 12:
			if (Style.equals("Control"))
				button.setImageResource(R.drawable.btn_g12);
			else if (Style.equals("Malignant"))
				button.setImageResource(R.drawable.btn_r12);
			else if (Style.equals("Benign"))
				button.setImageResource(R.drawable.btn_y12);
			break;
		}
	}// setButtonStyle

	private static class PostJson extends
			AsyncTask<HashMap<String, Object>, Void, Void> {

		public void postData() {
			String fullUrl = "https://docs.google.com/forms/d/19Nh83jx9ogs4urOVIeRnC3bpBG4IOd26A8J1-NJxhu4/formResponse";
			HttpRequest mReq = new HttpRequest();
			String trialResults = "";
			TrialCalculation tc = new TrialCalculation(results, bw.Malignant,
					bw.Benign, bw.Control);
			results = tc.getEncodedResults();

			for (int i = 0; i < results.size(); i++) {
				trialResults += "&" + TRIALS[i]
						+ URLEncoder.encode(results.get(i));
			}

			for (int i = 0; i < trialNotes.size(); i++) {
				trialResults += "&" + TRIAL_NOTES[i]
						+ URLEncoder.encode(trialNotes.get(i));
			}
			Double sens = tc.getSensitivity();
			Double specNorm = tc.getSpecificityNormal();
			Double specBen = tc.getSpecificityBenign();
			Double specTot = tc.getSpecTotal();
			Double suc = tc.getSuccess();
			Integer tp = tc.TP;
			Integer tnn = tc.TNN;
			Integer tnb = tc.TNB;
			Integer fpn = tc.FPN;
			Integer fpb = tc.FPB;
			Integer fpe = tc.FPE;
			Integer fn = tc.FN;
			String data = "";
	try {
	data = DOG_NAME + URLEncoder.encode(currentDog, "UTF-8") + "&" +
			SENSITIVITY + URLEncoder.encode(sens.toString(), "UTF-8") + "&" +
			TOTAL_TP + URLEncoder.encode(tp.toString(), "UTF-8") + "&" +
			TOTAL_TNN + URLEncoder.encode(tnn.toString(), "UTF-8") + "&" +
			TOTAL_TNB + URLEncoder.encode(tnb.toString(), "UTF-8") + "&" +
			TOTAL_FPN + URLEncoder.encode(fpn.toString(), "UTF-8") + "&" +
			TOTAL_FPB + URLEncoder.encode(fpb.toString(), "UTF-8") + "&" +
			TOTAL_FPE + URLEncoder.encode(fpe.toString(), "UTF-8") + "&" +
			TOTAL_FPN + URLEncoder.encode(fpn.toString(), "UTF-8") + "&" +
			TOTAL_FPN + URLEncoder.encode(fpn.toString(), "UTF-8") + "&" +
			TOTAL_FN + URLEncoder.encode(fn.toString(), "UTF-8") + "&" + 
			SPEC_NORMAL  + URLEncoder.encode(specNorm.toString(), "UTF-8") + "&" +
			SPEC_BENIGN  + URLEncoder.encode(specBen.toString(), "UTF-8") + "&" +
			SPEC_TOTAL  + URLEncoder.encode(specTot.toString(), "UTF-8") + "&" +
			SUCCESS_RATE + URLEncoder.encode(suc.toString(), "UTF-8") + "&" +
			HANDLER + URLEncoder.encode(currentHandler, "UTF-8") + "&" +
			RECORDER + URLEncoder.encode(currentRecorder, "UTF-8") + "&" +
			TEMPERATURE + URLEncoder.encode(currentTemp, "UTF-8") + "&" +
			HUMIDITY + URLEncoder.encode(currentHumidity, "UTF-8") + "&" +
			TESTER + URLEncoder.encode(currentTester, "UTF-8") + trialResults;
			} catch (Exception e) {
				e.printStackTrace();
			}
			String response = mReq.sendPost(FORM_URL, data);
			if (response == null || response == ""){
				Log.i("PROBLEM", "PROBLEM");
			}
			Log.i("RESPONSE", response);
			results.clear();
			trialNotes.clear();
		}

		@Override
		protected Void doInBackground(HashMap<String, Object>... arg0) {
			String json = new GsonBuilder().create().toJson(arg0[0], Map.class);
			try {
				postData();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data == null) {
			Log.e("Loading Activity", "nulls datas...");
		}

		if (data != null && data.hasExtra("Stop")) {
			int stop = data.getExtras().getInt("Stop");
			String editTextStr = edText.getText().toString() + " S" + stop;
			edText.setText(editTextStr);
		}

		if (data != null && data.hasExtra("Notes")) {
			notes = data.getExtras().getString("Notes");
		}

		if (data != null && data.hasExtra("EditText")) {
			edText.setText(data.getExtras().getString("EditText"));
		}

	}// end func

	@Override
	public void finish() {
		Intent i = new Intent();
		bw.pushIntentData(i);
		i.putExtra("Notes", notes);

		if (edText.getText().toString() != "")
			i.putExtra("EditText", edText.getText().toString());
		setResult(Activity.RESULT_OK, i);
		super.finish();
	}

}
