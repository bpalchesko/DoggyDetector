package edu.upenn.cis350.cancerDog;

import java.util.ArrayList;

public class TrialCalculation {

	private ArrayList<String> rawResults;
	private ArrayList<String> encodedResults;
	final int malignant;
	final int benign;
	final int normal;
	int TNN = 0;
	int TNB = 0;
	int TP = 0;
	int FPN = 0;
	int FPB = 0;
	int FPE = 0;
	int FN = 0;
	public boolean complete;

	public TrialCalculation(ArrayList<String> rawResults, int malignant,
			int benign, int normal) {
		this.rawResults = rawResults;
		this.malignant = malignant;
		this.benign = benign;
		this.normal = normal;
		encodedResults = encodeAll();
		complete = true;
	}

	/**
	 * Generates an encoded result sequence for a trial, and updates TNN, 
	 * TNB, TP, FPN, FPB, FPE, and FN counters.
	 * 
	 * @param result - raw sequence from the trial
	 * @return encoded sequence
	 */
	public String encodeResult(String result) {
		String[] raw = result.split("\\s+");
		String encoded = "";
		for (int i = 0; i < raw.length; i++) {
			if(raw[i].length() > 1){
			if (raw[i].charAt(0) == 'P') {
				int slotNumber = Integer.parseInt(raw[i].substring(1));
				if (slotNumber == malignant) {
					encoded += "FN ";
					FN++;
				} else if (slotNumber == normal) {
					encoded += "TNN ";
					TNN++;
				} else if (slotNumber == benign) {
					encoded += "TNB ";
					TNB++;
				}
			} else if (raw[i].charAt(0) == 'S') {
				int slotNumber = Integer.parseInt(raw[i].substring(1));
				if (slotNumber == malignant) {
					encoded += "TP(" + slotNumber + ")";
					TP++;
				} else if (slotNumber == normal) {
					encoded += "FPN(" + slotNumber + ")";
					FPN++;
				} else if (slotNumber == benign) {
					encoded += "FPB(" + slotNumber + ")";
					FPB++;
				} else {
					encoded += "FPE(" + slotNumber + ")";
					FPE++;
				}
			}
			}
			else if (raw[i].length() > 0){
			if (raw[i].charAt(0) == 'L') {
				encoded += "L ";
			}
		}
		}
		return encoded;

	}

	/**
	 * Encodes all raw sequences to generate a list of encoded sequences
	 * and increment result counters.
	 * 
	 * @return list of encoded sequences
	 */
	public ArrayList<String> encodeAll() {
		ArrayList<String> encoded = new ArrayList<String>();
		for (String s : rawResults) {
			encoded.add(encodeResult(s));
		}
		return encoded;
	}
	

	/**
	 * Calculates the sensitivity from the list of tests.
	 * 
	 * @return sensitivity
	 */
	public double getSensitivity() {
		if(TP == 0 && FN == 0) return 0.0;
		return ((double)TP) / ((double)(TP + FN));
	}
	

	/**
	 * Calculates the successRate from the list of tests.
	 * 
	 * @return sensitivity
	 */
	public double getSuccess() {
		if(TP == 0 && FPE == 0 && FPB == 0) return 0.0;
		return ((double)TP) / ((double)(TP + FPE + FPB + FPN));
	}

	/**
	 * Calculates specificity normal from the list of tests.
	 * 
	 * @return specificity normal
	 */
	public double getSpecificityNormal() {
		if(TNN == 0 && FPN == 0) return 0.0;
		return ((double)TNN) / ((double)(TNN + FPN));
	}
	
	/**
	 * Calculates specificity benign from the list of tests.
	 * 
	 * @return specificity benign
	 */
	public double getSpecificityBenign() {
		if(TNB == 0 && FPB == 0) return 0.0;
		return ((double)TNB) / ((double)(TNB + FPB));
	}
	
	/**
	 * Calculates spec total from the list of tests.
	 * 
	 * @return spec total
	 */
	public double getSpecTotal() {
		if(TNN == 0 && TNB == 0 && FPN == 0  && FPB == 0) return 0.0;
		return ((double)(TNN + TNB)) / ((double)(TNN + TNB + FPN + FPB + FPE));
	}
	
	/**
	 * Getter for list of encoded results
	 * 
	 * @return encoded results
	 */
	public ArrayList<String> getEncodedResults() {
		return encodedResults;
	}
	
}
