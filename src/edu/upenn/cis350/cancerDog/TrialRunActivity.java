package edu.upenn.cis350.cancerDog;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.GsonBuilder;

//import edu.upenn.cis350.cancerDog.Trial.PostJson;


//import edu.upenn.cis350.cancerDog.Trial.GetSessions;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.app.DialogFragment;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class TrialRunActivity extends FragmentActivity  implements SaveNotification.NoticeDialogListener {
	ImageButton btntrailPass1;
	ImageButton btntrailPass2;
	ImageButton btntrailPass3;
	ImageButton btnleave;
	ImageButton ibTrialstop;
	ImageButton btnBack;
	ImageButton btnSave;
	Button btnNext;
	EditText edText;
	TextView etNumber;
	TextView etDog;
	static BloodWheel bw;
	private static String currentDog;
	private static String currentHandler;
	private static final String FORM_URL = "https://docs.google.com/forms/d/1R8Oq1YvTVfrgxafxcGZaO6C_wca3Lv_JV3Su_MIEnaU/formResponse";
	private static final String DOG_NAME = "entry_434753845=";
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
	private static final String NOTES = "entry_1907361889=";
	private static final String TESTER = "entry_270032351=";
	private static final String[] TRIALS = new String[] {"entry_873369963=",
		"entry_82683764=", "entry_403485159=", "entry_1585414345=", 
		"entry_809017169=", "entry_14494942=", "entry_487964326=",
		"entry_983571317=", "entry_964442581=", "entry_786666526=",
		"entry_1055685979=", "entry_560711180=", "entry_2037751422=", "entry_1516979135=",
		"entry_1970120858=", "entry_670425955=", "entry_2145047438=",
		"entry_802163021=", "entry_1561108562=", "entry_1290361149="};
	
	static ArrayList<String> results = new ArrayList<String>();
	String btn1Text=""; //### Primitive obsession, refactor this
	String btn2Text="";
	String btn3Text="";
	int counter=1;
	static String notes = "";

	
    
    public void calculate(){

    }
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial_run);
        Log.e("Loading Activity", "TrialRunActivity");
        btntrailPass1 = (ImageButton) findViewById(R.id.ibTrial1pass);
        btntrailPass2 = (ImageButton) findViewById(R.id.ibTrial2pass);
        btntrailPass3 = (ImageButton) findViewById(R.id.ibTrial3pass);
        ibTrialstop = (ImageButton) findViewById(R.id.ibTrialstop);
        
        btnleave = (ImageButton) findViewById(R.id.ibLeave);
        btnBack = (ImageButton) findViewById(R.id.ibBack);
        btnSave = (ImageButton) findViewById(R.id.ibSave);
        btnNext = (Button) findViewById(R.id.ibNext);
        edText = (EditText) findViewById(R.id.editText1);

        etNumber = (TextView) findViewById(R.id.NumberText);
        etDog = (TextView) findViewById(R.id.DogTextName);
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
        

        
    	SharedPreferences preferences = getSharedPreferences(
    			"edu.upenn.cis350.cancerDog.handlers", Context.MODE_PRIVATE);
    	currentHandler = preferences.getString("current", "DEFAULT");
    	preferences = getSharedPreferences(
    			"edu.upenn.cis350.cancerDog.dogs", Context.MODE_PRIVATE);
    	currentDog = preferences.getString("current", "DEFAULT");
		
		styleButtons();
		
        etDog.setText(currentDog);

        
		

        btntrailPass1.setOnClickListener(new OnClickListener()
        {
          public void onClick(View v)
          {
        	  String editTextStr = edText.getText().toString() + " " + btn1Text;
        	  edText.setText(editTextStr);
          }
        });
        
        btntrailPass2.setOnClickListener(new OnClickListener()
        {
          public void onClick(View v)
          {
        	  String editTextStr = edText.getText().toString() + " " + btn2Text;
        	  edText.setText(editTextStr);
          }
        });
        
        btntrailPass3.setOnClickListener(new OnClickListener()
        {
          public void onClick(View v)
          {
        	  String editTextStr = edText.getText().toString() + " " + btn3Text;
        	  edText.setText(editTextStr);
          }
        });
        
        
        btnleave.setOnClickListener(new OnClickListener()
        {
          public void onClick(View v)
          {
        	  String editTextStr = edText.getText().toString() + "  L";
        	  edText.setText(editTextStr);
          }
        });
        
        btnBack.setOnClickListener(new OnClickListener()
        {
          public void onClick(View v)
          {
        	  if (edText.getText().toString().length()>=3)
        	  {
        		  String editTextStr = edText.getText().toString().substring(0, edText.getText().toString().length()-3);
        		  edText.setText(editTextStr);
        		  
        	  }
          }
        });
        
        btnSave.setOnClickListener(new OnClickListener()
        {
          public void onClick(View v)
          {
        	  showNoticeDialog();
        	  
        	  
          }
        });

        btnNext.setOnClickListener(new OnClickListener()
        {
          public void onClick(View v)
          {
        	 results.add(edText.getText().toString());
        	 edText.setText("");
        	 counter++;
        	 etNumber.setText(""+counter);
          }
        });
        
        
        edText.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return false;
			}
  
          });
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        
    }//end oncreate
	
	
	 public void showNoticeDialog() {
	        // Create an instance of the dialog fragment and show it
	        DialogFragment dialog = new SaveNotification();
	        dialog.show(getFragmentManager(), "SaveNotification");
	    }

	    // The dialog fragment receives a reference to this Activity through the
	    // Fragment.onAttach() callback, which it uses to call the following methods
	    // defined by the NoticeDialogFragment.NoticeDialogListener interface
	    @Override
	    public void onDialogPositiveClick(DialogFragment dialog) {
	    	//View v = dialog.getView();
	    	//EditText e = (EditText) v.findViewById(R.layout.finishedNotes);
	    	
	    	Toast.makeText(getApplicationContext(), "Trial Saved =)",
     			   Toast.LENGTH_LONG).show();
     	  HashMap<String, Object> trial = new HashMap<String, Object>();
     	  PostJson task = new PostJson();
     	  counter=1;
     	  etNumber.setText(""+counter);
     	  if(edText.getText().toString() != "")
     		  results.add(edText.getText().toString());
     	  task.execute((HashMap<String, Object>[]) (new HashMap[] { trial }));
     	  edText.setText("");
     	  
	    }

	    @Override
	    public void onDialogNegativeClick(DialogFragment dialog) {
	        // User touched the dialog's negative button
	    }
	
	
	
	public void onStopClick (View v) {
		Intent i = new Intent(this, StopActivity.class); 
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
	
	private void styleButtons(){
		
		if (bw.Benign<bw.Control){
			if (bw.Benign<bw.Malignant){
				setButtonStyle(btntrailPass1,bw.Benign,"Benign");
				btn1Text="P" + bw.Benign;
				if (bw.Control<bw.Malignant)
				{
					setButtonStyle(btntrailPass2,bw.Control,"Control");
					setButtonStyle(btntrailPass3,bw.Malignant,"Malignant");
					btn2Text="P" + bw.Control;
					btn3Text="P" + bw.Malignant;
				}
				else
				{
					setButtonStyle(btntrailPass3,bw.Control,"Control");
					setButtonStyle(btntrailPass2,bw.Malignant,"Malignant");
					btn3Text="P" + bw.Control;
					btn2Text="P" + bw.Malignant;
				}
			}
			else
			{
				setButtonStyle(btntrailPass1,bw.Malignant,"Malignant");
				setButtonStyle(btntrailPass2,bw.Benign,"Benign");
				setButtonStyle(btntrailPass3,bw.Control,"Control");
				btn1Text="P" + bw.Malignant;
				btn2Text="P" + bw.Benign;
				btn3Text="P" + bw.Control;
			}
		}
		else if(bw.Control<bw.Malignant)
		{
			setButtonStyle(btntrailPass1,bw.Control,"Control");
			btn1Text="P" + bw.Control;
			if (bw.Benign<bw.Malignant)
			{
				setButtonStyle(btntrailPass2,bw.Benign,"Benign");
				setButtonStyle(btntrailPass3,bw.Malignant,"Malignant");
				btn2Text="P" + bw.Benign;
				btn3Text="P" + bw.Malignant;
			}
			else
			{
				setButtonStyle(btntrailPass2,bw.Malignant,"Malignant");
				setButtonStyle(btntrailPass3,bw.Benign,"Benign");
				btn2Text="P" + bw.Malignant;
				btn3Text="P" + bw.Benign;
			}
		}
		else
		{
			setButtonStyle(btntrailPass1,bw.Malignant,"Malignant");
			setButtonStyle(btntrailPass2,bw.Control,"Control");
			setButtonStyle(btntrailPass3,bw.Benign,"Benign");
			btn1Text="P" + bw.Malignant;
			btn2Text="P" + bw.Control;
			btn3Text="P" + bw.Benign;
		}
	}//styleButtons
	
	private void setButtonStyle(ImageButton button, int buttonValue, String Style)
	{
		
		switch (buttonValue){
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
	}//setButtonStyle
	
	private static class PostJson extends
	AsyncTask<HashMap<String, Object>, Void, Void> {

public void postData() {
	
	String fullUrl = "https://docs.google.com/forms/d/19Nh83jx9ogs4urOVIeRnC3bpBG4IOd26A8J1-NJxhu4/formResponse";
	HttpRequest mReq = new HttpRequest();
	String trialResults = "";
  	TrialCalculation tc = new TrialCalculation(results, bw.Malignant, bw.Benign, bw.Control);
	results = tc.getEncodedResults();
    
	for (int i = 0; i < results.size(); i++){
		trialResults += "&" + TRIALS[i] + URLEncoder.encode(results.get(i));
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
	
	String data = DOG_NAME + URLEncoder.encode(currentDog) + "&" +
			SENSITIVITY + URLEncoder.encode(sens.toString()) + "&" +
			TOTAL_TP + URLEncoder.encode(tp.toString()) + "&" +
			TOTAL_TNN + URLEncoder.encode(tnn.toString()) + "&" +
			TOTAL_TNB + URLEncoder.encode(tnb.toString()) + "&" +
			TOTAL_FPN + URLEncoder.encode(fpn.toString()) + "&" +
			TOTAL_FPB + URLEncoder.encode(fpb.toString()) + "&" +
			TOTAL_FPE + URLEncoder.encode(fpe.toString()) + "&" +
			TOTAL_FPN + URLEncoder.encode(fpn.toString()) + "&" +
			TOTAL_FPN + URLEncoder.encode(fpn.toString()) + "&" +
			TOTAL_FN + URLEncoder.encode(fn.toString()) + "&" + 
			SPEC_NORMAL  + URLEncoder.encode(specNorm.toString()) + "&" +
			SPEC_BENIGN  + URLEncoder.encode(specBen.toString()) + "&" +
			SPEC_TOTAL  + URLEncoder.encode(specTot.toString()) + "&" +
			SUCCESS_RATE + URLEncoder.encode(suc.toString()) + "&" +
			
				TESTER + URLEncoder.encode(currentHandler) + trialResults;
	Log.i("DATA", data);
	String response = mReq.sendPost(FORM_URL, data);
	results.clear();

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
protected void onPreExecute(){

	
}



}


private static class GetSessions extends AsyncTask<Void, Void, Void> {

@Override
protected Void doInBackground(Void... arg0) {
	/* try {
		HttpGet httpGet = new HttpGet("http://pennvet.herokuapp.com/");
		HttpResponse response = new DefaultHttpClient()
				.execute(httpGet);
		HttpEntity entity = response.getEntity();
		String csv = EntityUtils.toString(entity, "UTF-8");
		String[] lines = csv.split("\n");
		for (int i = 1; i < lines.length; i++) {
			Trial t = new Trial();
			String[] parts = lines[i].split(",");
			t.sessionNumber = Integer.parseInt(parts[0]);
			t.date = parts[1];
			t.time = parts[2];
			t.handler = parts[3];
			t.dog = parts[4];
			t.videographer = parts[5];
			t.observers = parts[6];
			t.expName = parts[7];
			t.expSlot = Integer.parseInt(parts[8]);
			t.controls = new HashMap<Integer, String>();
			String[] controlPairs = parts[9].split(";");
			for (String pair : controlPairs) {
				String[] keyValue = pair.split(":");
				if (keyValue.length == 2) {
					t.controls.put(Integer.parseInt(keyValue[0]),
							keyValue[1]);
				}
			}

			t.notes = new ArrayList<String>();
			t.trialResults = new ArrayList<Result[]>();
			t.topArms = new ArrayList<Integer>();
			if (t.sessionNumber == 11) {
				int debug = 1;
			}
			for (int j = 0; j < (parts.length - 9) / 3; j++) {
				ArrayList<Result> results = new ArrayList<Result>();
				Result r = new Result();
				String[] resultPairs = parts[10 + j * 3].split(";");
				for (int k = 0; k < resultPairs.length; k++) {
					String[] keyValue = resultPairs[k].split(":");
					if (keyValue[0].equals("numFalse")) {
						r.numFalse = Integer.parseInt(keyValue[1]);
					} else if (keyValue[0].equals("numMiss")) {
						r.numMiss = Integer.parseInt(keyValue[1]);
					} else if (keyValue[0].equals("numSuccess")) {
						r.numSuccess = Integer.parseInt(keyValue[1]);
						results.add(r);
						r = new Result();
					}
				}
				Result[] rs = new Result[results.size()];
				for (int k = 0; k < results.size(); k++) {
					rs[k] = results.get(k);
				}
				t.trialResults.add(rs);
				t.notes.add(parts[11 + j * 3]);
				t.topArms.add(Integer.parseInt(parts[12 + j * 3]));
			}
			t.save(true, false);
		}
	} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	} catch (ClientProtocolException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} */
	return null;
}

}



@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
	Log.e("Loading method", "onActivityResult...");
	super.onActivityResult(requestCode, resultCode, data);
    if (data==null)
    {
    	Log.e("Loading Activity", "nulls datas...");
    }
    else if (data.hasExtra("Stop")) {
    	int stop = data.getExtras().getInt("Stop");
    	 String editTextStr = edText.getText().toString() + " S" + stop;
   	  edText.setText(editTextStr);
  	}
}
	
}
