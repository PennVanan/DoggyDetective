package edu.upenn.cis350.cancerDog.test;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import edu.upenn.cis350.cancerDog.NotesActivity;
import edu.upenn.cis350.cancerDog.R;

public class NotesActivityTest extends ActivityInstrumentationTestCase2<NotesActivity> {
	private NotesActivity mFirstTestActivity;
	private EditText mTestText;

	public NotesActivityTest() {
		super(NotesActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Intent i = new Intent();
		i.putExtra("Notes",  "test");
		setActivityIntent(i);
		mFirstTestActivity = getActivity();
		mTestText=(EditText) mFirstTestActivity.findViewById(R.id.editText1);

	}
	
	public void testPriorNotestest() {
		final String expected = "test";
		final String actual = mTestText.getText().toString();
	    assertEquals(expected, actual);
	}


}
