package edu.upenn.cis350.cancerDog.test;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.MediumTest;
import android.text.InputType;
import android.text.method.Touch;
import android.util.Log;
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
public class trialRunActNewTest extends ActivityInstrumentationTestCase2<trialRunActNew> {

    private trialRunActNew mTestTrialRunActNewTest;
    private Button mLeave;
    private Button Set1;
    private Button buttonTrialNotes;
    private EditText edText;

    private AlertDialog alertDialog;

    public trialRunActNewTest()
    {
        super(trialRunActNew.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mTestTrialRunActNewTest = getActivity();
        mLeave = (Button) mTestTrialRunActNewTest.findViewById(R.id.button);
        edText = (EditText) mTestTrialRunActNewTest.findViewById(R.id.editText);
        buttonTrialNotes = (Button) mTestTrialRunActNewTest.findViewById(R.id.buttonTrialNotes);
        Set1 = (Button)mTestTrialRunActNewTest.findViewById(R.id.ibSet1);
    }

    @MediumTest
    public void testLeaveButton() {
        TouchUtils.clickView(this, mLeave);
        System.out.println("Text is: "  + edText.getText().toString().trim());
        assertEquals("Checking for L", "L", edText.getText().toString().trim());
    }

    @MediumTest
    public void testAddTrialNotes() {

        TouchUtils.clickView(this, buttonTrialNotes);

        //Send text
        sendKeys("H E L L O SPACE W O R L D");
        getInstrumentation().waitForIdleSync();

        //press Ok button to store result
        alertDialog = mTestTrialRunActNewTest.getAlertDialog();
        Button oKbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        TouchUtils.clickView(this, oKbutton);

        assertEquals("Checking for added note", "hello world", mTestTrialRunActNewTest.getCurrenttrialnote());
    }

    @MediumTest
    public void testText() {
        TouchUtils.clickView(this, Set1);
        assertEquals("Checking for S1 Empty", "S1", edText.getText().toString().trim());
    }

    @MediumTest
    public void testEmptyControl() {
        TouchUtils.clickView(this, Set1);
        assertEquals("Checking for S1 Empty", "S1", edText.getText().toString().trim());
    }

    @MediumTest
    public void testNormalControlPass() {
        BloodWheel bw = mTestTrialRunActNewTest.getBloodWheel();
        bw.setVialState(1,3);
        TouchUtils.clickView(this, Set1);
        assertEquals("Checking for S1 Normal Pass", "P1", edText.getText().toString().trim());
    }

    @MediumTest
    public void testNormalControlSit() {
        BloodWheel bw = mTestTrialRunActNewTest.getBloodWheel();
        bw.setVialState(1,3);
        TouchUtils.longClickView(this, Set1);
        assertEquals("Checking for S1 Normal Sit", "S1", edText.getText().toString().trim());
    }

    @MediumTest
    public void testOdorControlOnlySit() {
        BloodWheel bw = mTestTrialRunActNewTest.getBloodWheel();
        bw.setVialState(1, 4);
        TouchUtils.clickView(this, Set1);
        assertEquals("Checking for S1 Odor Sit", "S1", edText.getText().toString().trim());
    }

    @MediumTest
    public void testBenignControlPass() {
        BloodWheel bw = mTestTrialRunActNewTest.getBloodWheel();
        bw.setVialState(1,5);
        TouchUtils.clickView(this, Set1);
        assertEquals("Checking for P1 Benign Pass", "P1", edText.getText().toString().trim());
    }

    @MediumTest
    public void testBenignControlSit() {
        BloodWheel bw = mTestTrialRunActNewTest.getBloodWheel();
        bw.setVialState(1,5);
        TouchUtils.longClickView(this, Set1);
        assertEquals("Checking for P1 Benign Sit", "S1", edText.getText().toString().trim());
    }

    @MediumTest
    public void testMalignantControlPass() {
        BloodWheel bw = mTestTrialRunActNewTest.getBloodWheel();
        bw.setVialState(1,1);
        TouchUtils.clickView(this, Set1);
        assertEquals("Checking for P1 Malign Pass", "P1", edText.getText().toString().trim());
    }

    @MediumTest
    public void testMalignantControlSit() {
        BloodWheel bw = mTestTrialRunActNewTest.getBloodWheel();
        bw.setVialState(1,1);
        TouchUtils.longClickView(this, Set1);
        assertEquals("Checking for P1 Malign Sit", "S1", edText.getText().toString().trim());
    }


}
