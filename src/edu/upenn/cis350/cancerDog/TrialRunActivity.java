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
import android.widget.Toast;
import java.util.*;

public class TrialRunActivity extends Activity {
	ImageButton btntrailPass1;
	ImageButton btntrailPass2;
	ImageButton btntrailPass3;
	ImageButton btnleave;
	ImageButton ibTrialstop;
	ImageButton btnBack;
	ImageButton btnSave;
	Button btnNext;
	EditText edText;
	BloodWheel bw;
	private static final String FORM_URL = "https://docs.google.com/a/seas.upenn.edu/forms/d/1ejBueRyDQLEFrqcddkEOIWUXCJFOHQ9IBZpCyJDrAJU/formResponse";
	private static final String ADMIN_ENTRY = "entry.1749728713=";
	private static final String DOG_ENTRY = "entry.273386690=";
	private static final String T1_COMMENTS_ENTRY = "entry.2085137680=";
	private static final String T2_COMMENTS_ENTRY = "entry.1880109834=";
	static ArrayList<String> results = new ArrayList<String>();
	String btn1Text=""; //### Primitive obsession, refactor this
	String btn2Text="";
	String btn3Text="";
	
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
		
		styleButtons();
		

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
        	  Toast.makeText(getApplicationContext(), "Trial Saved =)",
        			   Toast.LENGTH_LONG).show();
        	  HashMap<String, Object> trial = new HashMap<String, Object>();
        	  PostJson task = new PostJson();
        	  task.execute((HashMap<String, Object>[]) (new HashMap[] { trial }));
        	  
        	  
          }
        });
        
        btnNext.setOnClickListener(new OnClickListener()
        {
          public void onClick(View v)
          {
        	 results.add(edText.getText().toString());
        	 edText.setText("");
        	  
          }
        });
        
        
        edText.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return false;
			}
  
          });
        
        
    }//end oncreate
	
	
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
	
	//String fullUrl = "https://docs.google.com/forms/d/19Nh83jx9ogs4urOVIeRnC3bpBG4IOd26A8J1-NJxhu4/formResponse";
	HttpRequest mReq = new HttpRequest();
	String col1 = results.get(0);
	String col2 = results.get(1);
	
	String data = T1_COMMENTS_ENTRY + URLEncoder.encode(col1) + "&" + 
				T2_COMMENTS_ENTRY + URLEncoder.encode(col2);
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
