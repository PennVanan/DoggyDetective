package edu.upenn.cis350.cancerDog;

import java.util.ArrayList;

public class TrialCalculation {

	private ArrayList<String> rawResults;
	private ArrayList<String> encodedResults;
	final int malignant;
	final int benign;
	final int Normal;
	int True_Neg_Normal = 0;
	int True_Neg_Benign = 0;
	int True_Pos = 0;
	int False_Pos_Normal = 0;
	int False_Pos_Benign = 0;
	int False_Pos_Other = 0;
	int False_Neg = 0;
	public boolean complete;

	public TrialCalculation(ArrayList<String> rawResults, int malignant,
			int benign, int Normal) {
		this.rawResults = rawResults;
		this.malignant = malignant;
		this.benign = benign;
		this.Normal = Normal;
		encodedResults = encodeAll();
		complete = true;
	}

	/**
	 * Generates an encoded result sequence for a trial, and updates True_Neg_Normal,
	 * True_Neg_Benign, True_Pos, FPN, False_Pos_Benign, FPE, and False_Neg counters.
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
					False_Neg++;
				} else if (slotNumber == Normal) {
					encoded += "TNN ";
					True_Neg_Normal++;
				} else if (slotNumber == benign) {
					encoded += "TNB ";
					True_Neg_Benign++;
				}
			} else if (raw[i].charAt(0) == 'S') {
				int slotNumber = Integer.parseInt(raw[i].substring(1));
				if (slotNumber == malignant) {
					encoded += "TP(" + slotNumber + ")";
					True_Pos++;
				} else if (slotNumber == Normal) {
					encoded += "FPN(" + slotNumber + ")";
					False_Pos_Normal++;
				} else if (slotNumber == benign) {
					encoded += "FPB(" + slotNumber + ")";
					False_Pos_Benign++;
				} else {
					encoded += "FPE(" + slotNumber + ")";
					False_Pos_Other++;
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
		if(True_Pos == 0 && False_Neg == 0) return 0.0;
		return ((double)True_Pos) / ((double)(True_Pos + False_Neg));
	}
	

	/**
	 * Calculates the successRate from the list of tests.
	 * 
	 * @return sensitivity
	 */
	public double getSuccess() {
		if(True_Pos == 0 && False_Pos_Other == 0 && False_Pos_Benign == 0) return 0.0;
		return ((double)True_Pos) / ((double)(True_Pos + False_Pos_Other + False_Pos_Benign + False_Pos_Normal));
	}

	/**
	 * Calculates specificity Normal from the list of tests.
	 * 
	 * @return specificity Normal
	 */
	public double getSpecificityNormal() {
		if(True_Neg_Normal == 0 && False_Pos_Normal == 0) return 0.0;
		return ((double)True_Neg_Normal) / ((double)(True_Neg_Normal + False_Pos_Normal));
	}
	
	/**
	 * Calculates specificity benign from the list of tests.
	 * 
	 * @return specificity benign
	 */
	public double getSpecificityBenign() {
		if(True_Neg_Benign == 0 && False_Pos_Benign == 0) return 0.0;
		return ((double)True_Neg_Benign) / ((double)(True_Neg_Benign + False_Pos_Benign));
	}
	
	/**
	 * Calculates spec total from the list of tests.
	 * 
	 * @return spec total
	 */
	public double getSpecTotal() {
		if(True_Neg_Normal == 0 && True_Neg_Benign == 0 && False_Pos_Normal == 0  && False_Pos_Benign == 0) return 0.0;
		return ((double)(True_Neg_Normal + True_Neg_Benign)) / ((double)(True_Neg_Normal + True_Neg_Benign + False_Pos_Normal + False_Pos_Benign + False_Pos_Other));
	}
	
	/**
	 * Getter for list of encoded results
	 * 
	 * @return encoded results
	 */
	public ArrayList<String> getEncodedResults() {
		return encodedResults;
	}
	
	public int getTrue_Neg_Normal() {
		return True_Neg_Normal;
	}
	
	public int getTrue_Neg_Benign() {
		return True_Neg_Benign;
	}
	
	public int getTrue_Pos() {
		return True_Pos;
	}
	
	public int getFalse_Pos_Normal() {
		return False_Pos_Normal;
	}
	
	public int getFalse_Pos_Benign() {
		return False_Pos_Benign;
	}

	public int getFPE() {
		return False_Pos_Other;
	}
	
	public int getFalse_Neg() {
		return False_Neg;
	}
	
}
