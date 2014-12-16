package edu.upenn.cis350.cancerDog;

import static org.junit.Assert.*;

import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;
import edu.upenn.cis350.cancerDog.R;

import org.junit.Before;
import org.junit.Test;


//public class EditDefaultActivityNewTest {
public class EditDefaultActivityNewTest extends
		ActivityInstrumentationTestCase2<EditDefaultActivityNew> {
		
	private EditDefaultActivityNew editActivity;
	EditText tempText;
	EditText humidityText;

	public EditDefaultActivityNewTest () {
        super(EditDefaultActivityNew.class);
    } 
	
	@Before
	public void setUp() throws Exception {
//		editActivity = getActivity();
//    	tempText = (EditText)editActivity.findViewById(R.id.tempText);
//    	humidityText = (EditText)editActivity.findViewById(R.id.humidityText);
//    	Log.e("fun", tempText.getText().toString());
	}

	@Test
	public void testListFromNameStringEmpty() {
		String names = "";
		ArrayList<String> namesList= EditDefaultActivityNew.listFromNameString(names);
		assertEquals(0, namesList.size());
	}
	
	@Test
	public void testListFromNameStringSingleName() {
		String names = "Bob";
		ArrayList<String> namesList= EditDefaultActivityNew.listFromNameString(names);
		assertEquals(1, namesList.size());
		assertEquals("Bob", namesList.get(0));
	}
	
	@Test
	public void testListFromNameStringMultipleNames() {
		String names = "Wacko, Yacko, Dot";
		ArrayList<String> namesList= EditDefaultActivityNew.listFromNameString(names);
		assertEquals(3, namesList.size());
		assertEquals("Wacko", namesList.get(0));
		assertEquals("Yacko", namesList.get(1));
		assertEquals("Dot", namesList.get(2));
	}
	
	@Test
	public void testListFromNameStringEndsWithComma() {
		String names = "Romeo,Juliet,";
		ArrayList<String> namesList= EditDefaultActivityNew.listFromNameString(names);
		assertEquals(2, namesList.size());
		assertEquals("Romeo", namesList.get(0));
		assertEquals("Juliet", namesList.get(1));
	}
	
	@Test
	public void testListFromNameStringStartsWithComma() {
		String names = ",Romeo, Juliet";
		ArrayList<String> namesList= EditDefaultActivityNew.listFromNameString(names);
		assertEquals(2, namesList.size());
		assertEquals("Romeo", namesList.get(0));
		assertEquals("Juliet", namesList.get(1));
	}
	
	@Test
	public void testListFromNameStringExtraCommas() {
		String names = "Romeo,,, , Juliet";
		ArrayList<String> namesList = EditDefaultActivityNew.listFromNameString(names);
		assertEquals(2, namesList.size());
		assertEquals("Romeo", namesList.get(0));
		assertEquals("Juliet", namesList.get(1));
	}
	
	@Test
	public void testNameStringFromListEmptyList() {
		ArrayList<String> namesList = new ArrayList<String>();
		String names = EditDefaultActivityNew.nameStringFromList(namesList);
		assertEquals("", names);
	}
	
	@Test
	public void testNameStringFromListSingle() {
		ArrayList<String> namesList = new ArrayList<String>();
		namesList.add("Bob");
		String names = EditDefaultActivityNew.nameStringFromList(namesList);
		assertEquals("Bob", names);
	}
	
	@Test
	public void testNameStringFromList() {
		ArrayList<String> namesList = new ArrayList<String>();
		namesList.add("Ren");
		namesList.add("Stimpy");
		namesList.add("Rocko");
		String names = EditDefaultActivityNew.nameStringFromList(namesList);
		assertEquals("Ren, Stimpy, Rocko", names);
	}

}
