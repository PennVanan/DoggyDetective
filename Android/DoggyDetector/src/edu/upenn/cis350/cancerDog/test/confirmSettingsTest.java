package edu.upenn.cis350.cancerDog.test;

import edu.upenn.cis350.cancerDog.R;
import edu.upenn.cis350.cancerDog.confirmSettings;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

public class confirmSettingsTest extends ActivityInstrumentationTestCase2<confirmSettings> {
	private confirmSettings mFirstTestActivity;
	private TextView myBenignTestText;
    private TextView myMalignantTestText;
    private TextView myControlTestText;

	public confirmSettingsTest() {
		super(confirmSettings.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mFirstTestActivity = getActivity();
		myBenignTestText =(TextView) mFirstTestActivity.findViewById(R.id.BenignNum);
        myMalignantTestText = (TextView) mFirstTestActivity.findViewById(R.id.MalignantNum);
        myControlTestText = (TextView) mFirstTestActivity.findViewById(R.id.ControlNum);
	}
	
	public void testBenignTextView_labelText() {
	    final String expected = Integer.valueOf(mFirstTestActivity.getBenign()).toString();
	    final String actual = myBenignTestText.getText().toString();
	    assertEquals(expected, actual);
	}

	public void testMalignantTextView_labelText() {
		final String expected = Integer.valueOf(mFirstTestActivity.getMalignant()).toString();
		final String actual = myMalignantTestText.getText().toString();
		assertEquals(expected, actual);
	}


    public void testControlTextView_labelText() {
        final String expected = Integer.valueOf(mFirstTestActivity.getControl()).toString();
        final String actual = myControlTestText.getText().toString();
        assertEquals(expected, actual);
    }
}
