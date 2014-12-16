package edu.upenn.cis350.cancerDog;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;


public class TrialCalculationTest {
	
	TrialCalculation session;
	ArrayList<String> trials = new ArrayList<String>();

	@Before
	public void setUp() throws Exception {
		trials.add("P1 P5 P9 L S1");
	}

	@Test
	public void testEncodeResultString() {
		session = new TrialCalculation(trials, 1, 5, 9);
		assertEquals("FN TNB TNN L TP(1)", session.encodeResult(trials.get(0)));
		trials.add("P9 P5 P1 L P1 S3");
		System.out.println(session.encodeResult(trials.get(1)));
		assertEquals("TNN TNB FN L FN FPE(3)", session.encodeResult(trials.get(1)));
		trials.add("P5 P1 L P9 S9");
		assertEquals("TNB FN L TNN FPN(9)", session.encodeResult(trials.get(2)));
		trials.add("P5 L P5 L P9 S5");
		assertEquals("TNB L TNB L TNN FPB(5)", session.encodeResult(trials.get(3)));
	}

	@Test
	public void testEncodeResultCounts() {
		session = new TrialCalculation(trials, 1, 5, 9);
		assertTrue(session.TNN == 1 && session.TNB == 1 && session.TP == 1 &&
				session.FPN == 0 && session.FPB == 0 && session.FPE == 0 & session.FN == 1);
		trials.add("P9 P5 P1 L P1 S3");
		session.encodeResult(trials.get(1));
		assertTrue(session.TNN == 2 && session.TNB == 2 && session.TP == 1 &&
				session.FPN == 0 && session.FPB == 0 && session.FPE == 1 & session.FN == 3);
		trials.add("P5 P1 L P9 S9");
		session.encodeResult(trials.get(2));
		assertTrue(session.TNN == 3 && session.TNB == 3 && session.TP == 1 &&
				session.FPN == 1 && session.FPB == 0 && session.FPE == 1 & session.FN == 4);
		trials.add("P5 L P5 L P9 S5");
		session.encodeResult(trials.get(3));
		assertTrue(session.TNN == 4 && session.TNB == 5 && session.TP == 1 &&
				session.FPN == 1 && session.FPB == 1 && session.FPE == 1 & session.FN == 4);		
	}
	
	@Test
	public void testEncodeAllStrings() {
		trials.add("P9 P5 P1 L P1 S3");
		trials.add("P5 P1 L P9 S9");
		trials.add("P5 L P5 L P9 S5");
		session = new TrialCalculation(trials, 1, 5, 9);
		assertEquals("FN TNB TNN L TP(1)", session.encodeResult(trials.get(0)));
		assertEquals("TNN TNB FN L FN FPE(3)", session.encodeResult(trials.get(1)));
		assertEquals("TNB FN L TNN FPN(9)", session.encodeResult(trials.get(2)));
		assertEquals("TNB L TNB L TNN FPB(5)", session.encodeResult(trials.get(3)));
		assertEquals(4, session.getEncodedResults().size());
		assertEquals("FN TNB TNN L TP(1)", session.getEncodedResults().get(0));
		assertEquals("TNN TNB FN L FN FPE(3)", session.getEncodedResults().get(1));
		assertEquals("TNB FN L TNN FPN(9)", session.getEncodedResults().get(2));
		assertEquals("TNB L TNB L TNN FPB(5)", session.getEncodedResults().get(3));
	}
	
	@Test
	public void testEncodeAllCounts() {
		trials.add("P9 P5 P1 L P1 S3");
		trials.add("P5 P1 L P9 S9");
		trials.add("P5 L P5 L P9 S5");
		session = new TrialCalculation(trials, 1, 5, 9);
		assertTrue(session.TNN == 4 && session.TNB == 5 && session.TP == 1 &&
				session.FPN == 1 && session.FPB == 1 && session.FPE == 1 & session.FN == 4);	
	}
	
	@Test
	public void testSensitivityIndividual() { // TP / (TP + FN)
		session = new TrialCalculation(trials, 1, 5, 9);
		assertEquals(0.5, session.getSensitivity(), 0.01);
		
		trials.add("P9 P5 P1 L P1 S3");
		session.encodeResult(trials.get(1));
		assertEquals(0.25, session.getSensitivity(), 0.01);
		
		trials.add("P5 P1 L P9 S9");
		session.encodeResult(trials.get(2));
		assertEquals(0.20, session.getSensitivity(), 0.01);
		
		trials.add("P5 L P5 L P9 S5");
		session.encodeResult(trials.get(3));
		assertEquals(0.20, session.getSensitivity(), 0.01);
	}
	
	@Test
	public void testSensitivityAll() { // TP / (TP + FN)
		session = new TrialCalculation(new ArrayList<String>(), 1, 5, 9);
		assertEquals(0.0, session.getSensitivity(), 0.01);
		trials.add("P9 P5 P1 L P1 S3");
		trials.add("P5 P1 L P9 S9");
		trials.add("P5 L P5 L P9 S5");
		session = new TrialCalculation(trials, 1, 5, 9);
		assertEquals(0.20, session.getSensitivity(), 0.01);
	}
	
	@Test
	public void testSpecificityNormalIndividual() { // TNN / (TNN  + FPN)
		session = new TrialCalculation(trials, 1, 5, 9);
		assertEquals(1.0, session.getSpecificityNormal(), 0.01);
		
		trials.add("P9 P5 P1 L P1 S3");
		session.encodeResult(trials.get(1));
		assertEquals(1.0, session.getSpecificityNormal(), 0.01);
		
		trials.add("P5 P1 L P9 S9");
		session.encodeResult(trials.get(2));
		assertEquals(0.75, session.getSpecificityNormal(), 0.01);
		
		trials.add("P5 L P5 L P9 S5");
		session.encodeResult(trials.get(3));
		assertEquals(0.80, session.getSpecificityNormal(), 0.01);
	}
	
	@Test
	public void testSpecificityNormalAll() { // TNN / (TNN  + FPN)
		session = new TrialCalculation(new ArrayList<String>(), 1, 5, 9);
		assertEquals(0.0, session.getSpecificityNormal(), 0.01);
		trials.add("P9 P5 P1 L P1 S3");
		trials.add("P5 P1 L P9 S9");
		trials.add("P5 L P5 L P9 S5");
		session = new TrialCalculation(trials, 1, 5, 9);
		assertEquals(0.80, session.getSpecificityNormal(), 0.01);
	}
	
	@Test
	public void testSpecificityBenignIndividual() { // TNB / (TNB + FPB)
		session = new TrialCalculation(trials, 1, 5, 9);
		assertEquals(1.0, session.getSpecificityBenign(), 0.01);
		
		trials.add("P9 P5 P1 L P1 S3");
		session.encodeResult(trials.get(1));
		assertEquals(1.0, session.getSpecificityBenign(), 0.01);
		
		trials.add("P5 P1 L P9 S9");
		session.encodeResult(trials.get(2));
		assertEquals(1.0, session.getSpecificityBenign(), 0.01);
		
		trials.add("P5 L P5 L P9 S5");
		session.encodeResult(trials.get(3));
		assertEquals(0.833, session.getSpecificityBenign(), 0.01);
	}
	
	@Test
	public void testSpecificityBenignAll() { // TNB / (TNB + FPB)
		session = new TrialCalculation(new ArrayList<String>(), 1, 5, 9);
		assertEquals(0.0, session.getSpecificityBenign(), 0.01);
		trials.add("P9 P5 P1 L P1 S3");
		trials.add("P5 P1 L P9 S9");
		trials.add("P5 L P5 L P9 S5");
		session = new TrialCalculation(trials, 1, 5, 9);
		assertEquals(0.833, session.getSpecificityBenign(), 0.01);
	}
	
	@Test
	public void testSpecTotalIndividual() { // (TNN + TNB) / (TNN + TNB + FPN + FPB)
		session = new TrialCalculation(trials, 1, 5, 9);
		assertEquals(1.0, session.getSpecTotal(), 0.01);
		
		trials.add("P9 P5 P1 L P1 S3");
		session.encodeResult(trials.get(1));
		assertEquals(0.8, session.getSpecTotal(), 0.01);
		
		trials.add("P5 P1 L P9 S9");
		session.encodeResult(trials.get(2));
		assertEquals(0.75, session.getSpecTotal(), 0.01);
		
		trials.add("P5 L P5 L P9 S5");
		session.encodeResult(trials.get(3));
		assertEquals(0.75, session.getSpecTotal(), 0.01);
	}
	
	@Test
	public void testSpecTotalAll() { // (TNN + TNB) / (TNN + TNB + FPN + FPB)
		session = new TrialCalculation(new ArrayList<String>(), 1, 5, 9);
		assertEquals(0.0, session.getSpecTotal(), 0.01);
		trials.add("P9 P5 P1 L P1 S3");
		trials.add("P5 P1 L P9 S9");
		trials.add("P5 L P5 L P9 S5");
		session = new TrialCalculation(trials, 1, 5, 9);
		assertEquals(0.75, session.getSpecTotal(), 0.01);
	}
	
}
