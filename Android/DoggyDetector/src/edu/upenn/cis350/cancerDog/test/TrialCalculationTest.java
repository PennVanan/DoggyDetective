package edu.upenn.cis350.cancerDog.test;
import junit.framework.TestCase;
import java.util.ArrayList;

import edu.upenn.cis350.cancerDog.TrialCalculation;


public class TrialCalculationTest extends TestCase{

	TrialCalculation session;
	ArrayList<String> trials = new ArrayList<String>();


	public void setUp() throws Exception {
		trials.add("P1 P5 P9 L S1");
	}


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


	public void testEncodeResultCounts() {
		session = new TrialCalculation(trials, 1, 5, 9);
		assertTrue(session.getTrue_Neg_Normal() == 1 && session.getTrue_Neg_Benign() == 1 && session.getTrue_Pos() == 1 &&
				session.getFalse_Pos_Normal() == 0 && session.getFalse_Pos_Benign() == 0 && session.getFPE() == 0 & session.getFalse_Neg() == 1);
		trials.add("P9 P5 P1 L P1 S3");
		session.encodeResult(trials.get(1));
		assertTrue(session.getTrue_Neg_Normal() == 2 && session.getTrue_Neg_Benign() == 2 && session.getTrue_Pos() == 1 &&
				session.getFalse_Pos_Normal() == 0 && session.getFalse_Pos_Benign() == 0 && session.getFPE() == 1 & session.getFalse_Neg() == 3);
		trials.add("P5 P1 L P9 S9");
		session.encodeResult(trials.get(2));
		assertTrue(session.getTrue_Neg_Normal() == 3 && session.getTrue_Neg_Benign() == 3 && session.getTrue_Pos() == 1 &&
				session.getFalse_Pos_Normal() == 1 && session.getFalse_Pos_Benign() == 0 && session.getFPE() == 1 & session.getFalse_Neg() == 4);
		trials.add("P5 L P5 L P9 S5");
		session.encodeResult(trials.get(3));
		assertTrue(session.getTrue_Neg_Normal() == 4 && session.getTrue_Neg_Benign() == 5 && session.getTrue_Pos() == 1 &&
				session.getFalse_Pos_Normal() == 1 && session.getFalse_Pos_Benign() == 1 && session.getFPE() == 1 & session.getFalse_Neg() == 4);
	}


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


	public void testEncodeAllCounts() {
		trials.add("P9 P5 P1 L P1 S3");
		trials.add("P5 P1 L P9 S9");
		trials.add("P5 L P5 L P9 S5");
		session = new TrialCalculation(trials, 1, 5, 9);
		assertTrue(session.getTrue_Neg_Normal() == 4 && session.getTrue_Neg_Benign() == 5 && session.getTrue_Pos() == 1 &&
				session.getFalse_Pos_Normal() == 1 && session.getFalse_Pos_Benign() == 1 && session.getFPE() == 1 & session.getFalse_Neg() == 4);
	}

	public void testSensitivityIndividual() {
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


	public void testSensitivityAll() {
		session = new TrialCalculation(new ArrayList<String>(), 1, 5, 9);
		assertEquals(0.0, session.getSensitivity(), 0.01);
		trials.add("P9 P5 P1 L P1 S3");
		trials.add("P5 P1 L P9 S9");
		trials.add("P5 L P5 L P9 S5");
		session = new TrialCalculation(trials, 1, 5, 9);
		assertEquals(0.20, session.getSensitivity(), 0.01);
	}


	public void testSpecificityNormalIndividual() {
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


	public void testSpecificityNormalAll() { // TNN / (TNN  + FPN)
		session = new TrialCalculation(new ArrayList<String>(), 1, 5, 9);
		assertEquals(0.0, session.getSpecificityNormal(), 0.01);
		trials.add("P9 P5 P1 L P1 S3");
		trials.add("P5 P1 L P9 S9");
		trials.add("P5 L P5 L P9 S5");
		session = new TrialCalculation(trials, 1, 5, 9);
		assertEquals(0.80, session.getSpecificityNormal(), 0.01);
	}


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


	public void testSpecificityBenignAll() { // TNB / (TNB + FPB)
		session = new TrialCalculation(new ArrayList<String>(), 1, 5, 9);
		assertEquals(0.0, session.getSpecificityBenign(), 0.01);
		trials.add("P9 P5 P1 L P1 S3");
		trials.add("P5 P1 L P9 S9");
		trials.add("P5 L P5 L P9 S5");
		session = new TrialCalculation(trials, 1, 5, 9);
		assertEquals(0.833, session.getSpecificityBenign(), 0.01);
	}


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


