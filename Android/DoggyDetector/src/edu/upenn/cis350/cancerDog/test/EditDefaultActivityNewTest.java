package edu.upenn.cis350.cancerDog.test;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.MediumTest;
import android.text.InputType;
import android.text.method.Touch;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.ImageButton;
import edu.upenn.cis350.cancerDog.*;

import android.widget.*;
import edu.upenn.cis350.cancerDog.R;
import edu.upenn.cis350.cancerDog.trialRunActNew;

/**
 * Created by Alagiavanan on 11/30/2015.
 * Updated by BHerrero on 12/2/2015.
 */
public class EditDefaultActivityNewTest  extends ActivityInstrumentationTestCase2<EditDefaultActivityNew> {

	private EditDefaultActivityNew mTestObj;
	private EditText tempText;
	private EditText humidityText;
	private CheckBox checkBoxScreen;

	//BH!
	private Button buttonBenignlNotes;
	private Button buttonMalingNotes;
	private Button buttonControlNotes;

	private AlertDialog alertDialog;


	public EditDefaultActivityNewTest() {
		super(EditDefaultActivityNew.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mTestObj = getActivity();
		tempText = (EditText) mTestObj.findViewById(R.id.tempText);
		humidityText = (EditText) mTestObj.findViewById(R.id.humidityText);
		checkBoxScreen = (CheckBox) mTestObj.findViewById(R.id.checkBoxScreen);

		buttonBenignlNotes = (Button) mTestObj.findViewById(R.id.buttonBeningSampleInfo);
		buttonMalingNotes = (Button) mTestObj.findViewById(R.id.buttonMalignSampleInfo);
		buttonControlNotes = (Button) mTestObj.findViewById(R.id.buttonControlSampleInfo);
	}

	@MediumTest
	public void testBeningNotes() {

		TouchUtils.clickView(this, buttonBenignlNotes);

		//get previous text
		String previousText = mTestObj.getBenignSampleInfo();

		//Send text
		sendKeys("H E L L O SPACE B E N I G N  SPACE W O R L D");
		getInstrumentation().waitForIdleSync();

		//press Ok button to store result
		alertDialog = mTestObj.getAlertDialog();
		//EditText textBox = alertDialog.get
		Button oKbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
		TouchUtils.clickView(this, oKbutton);

		assertEquals("Checking for added note", "hello benign world" + previousText, mTestObj.getBenignSampleInfo());
	}

	@MediumTest
	public void testMalignNotes() {

		TouchUtils.clickView(this, buttonMalingNotes);

		//get previous text
		String previousText = mTestObj.getMalignSampleInfo();

		//Send text
		sendKeys("H E L L O SPACE M A L I G N SPACE W O R L D");
		getInstrumentation().waitForIdleSync();

		//press Ok button to store result
		alertDialog = mTestObj.getAlertDialog();
		//EditText textBox = alertDialog.get
		Button oKbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
		TouchUtils.clickView(this, oKbutton);

		assertEquals("Checking for added note", "hello malign world" + previousText, mTestObj.getMalignSampleInfo());
	}

	@MediumTest
	public void testControlNotes() {

		TouchUtils.clickView(this, buttonControlNotes);

		//get previous text
		String previousText = mTestObj.getControlSampleInfo();

		//Send text
		sendKeys("H E L L O SPACE C O N T R O L SPACE W O R L D");
		getInstrumentation().waitForIdleSync();

		//press Ok button to store result
		alertDialog = mTestObj.getAlertDialog();
		//EditText textBox = alertDialog.get
		Button oKbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
		TouchUtils.clickView(this, oKbutton);

		assertEquals("Checking for added note", "hello control world" + previousText, mTestObj.getControlSampleInfo());
	}


	public void testTemperatureText() {

		tempText.requestFocus();
		//Delete the existing entry looping through each digit
		System.out.println("Text is: " + tempText.getText().toString());
		while(tempText.getText().toString().length()!=0) {
			System.out.println("Text before deleting character: " + tempText.getText().toString());
			tempText.requestFocus();
			sendKeys(KeyEvent.KEYCODE_FORWARD_DEL);
			System.out.println("Text after deleting character: " + tempText.getText().toString());
			getInstrumentation().waitForIdleSync();
		}
		//Send a sample text for temperature and check if it is set properly
		sendKeys("7 6");
		System.out.println("Text is: " + tempText.getText().toString());
		getInstrumentation().waitForIdleSync();
		assertEquals("76", tempText.getText().toString());
	}





}
