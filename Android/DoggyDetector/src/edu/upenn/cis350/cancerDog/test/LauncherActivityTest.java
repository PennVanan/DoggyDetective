package edu.upenn.cis350.cancerDog.test;

import edu.upenn.cis350.cancerDog.BloodWheel;
import edu.upenn.cis350.cancerDog.LauncherActivity;
import android.test.ActivityInstrumentationTestCase2;

public class LauncherActivityTest extends ActivityInstrumentationTestCase2<LauncherActivity>{
	private LauncherActivity mLauncherActivity;
	BloodWheel bw;
	
	public LauncherActivityTest(Class<LauncherActivity> activityClass) {
		super(activityClass);
		// TODO Auto-generated constructor stub
	}
	
	public LauncherActivityTest() {
        super(LauncherActivity.class);
    }
	
	 @Override
	    protected void setUp() throws Exception {
	        super.setUp();
	        mLauncherActivity = getActivity();
	        bw =(BloodWheel) mLauncherActivity.getBw();
	    }
	 
	 public void testBWInstantiation() {
		    final int expected =0;
		    final int actual = bw.getControl();
		    assertEquals(expected, actual);
		    int vialState = bw.getVialState(2);
		 	int expected_vialState = 4;
		    assertEquals(vialState, expected_vialState);
		}

}
