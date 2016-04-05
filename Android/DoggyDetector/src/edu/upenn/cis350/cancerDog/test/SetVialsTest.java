package edu.upenn.cis350.cancerDog.test;

import edu.upenn.cis350.cancerDog.R;
import edu.upenn.cis350.cancerDog.SetVials;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.Button;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;
import android.test.TouchUtils;

public class SetVialsTest extends ActivityInstrumentationTestCase2<SetVials> {
    private SetVials setVialsActivity;
    private TextView myBenignTestText;
    private TextView myMalignantTestText;
    private TextView myControlTestText;
    Button vial;
    Button vial_odor;

    public SetVialsTest() {
        super(SetVials.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        setVialsActivity = getActivity();
        vial = (Button )setVialsActivity.findViewById(R.id.ibSet1);
        vial_odor = (Button )setVialsActivity.findViewById(R.id.ibSet10);


    }

    public void testButtonStateDefaultCheckEmpty() {
        final String expected = (String)vial.getTag();
        final String actual = "4";
        assertEquals(expected, actual);
        int color = ((ColorDrawable) vial.getBackground()).getColor();
        assertEquals(Color.rgb(255, 153, 102),color);
    }

    public void testButtonStateToggleCheckNormal() {

        final String actual = "5";
        TouchUtils.clickView(this, vial);

        final String expected = (String)vial.getTag();
        int color = ((ColorDrawable) vial.getBackground()).getColor();
        assertEquals(expected, actual);
        assertEquals( Color.rgb(0, 204, 102), color);
    }

    public void testButtonStateToggleCheckOdorContro() {

        final String actual = "1";
        TouchUtils.clickView(this, vial);
        TouchUtils.clickView(this, vial);



        final String expected = (String)vial.getTag();
        int color = ((ColorDrawable) vial.getBackground()).getColor();
        assertEquals(expected, actual);
        assertEquals( Color.rgb(255, 80, 80), color);
    }

    public void testButtonStateToggleCheckBenign() {

        final String actual = "2";
        TouchUtils.clickView(this, vial);
        TouchUtils.clickView(this, vial);
        TouchUtils.clickView(this, vial);


        final String expected = (String)vial.getTag();
        int color = ((ColorDrawable) vial.getBackground()).getColor();
        assertEquals(expected, actual);
        assertEquals( Color.rgb(102, 153, 255), color);
    }

    public void testButtonStateToggleCheckMalignant() {

        final String actual = "3";
        TouchUtils.clickView(this, vial);
        TouchUtils.clickView(this, vial);
        TouchUtils.clickView(this, vial);
        TouchUtils.clickView(this, vial);

        final String expected = (String)vial.getTag();
        int color = ((ColorDrawable) vial.getBackground()).getColor();
        assertEquals(expected, actual);
        assertEquals(Color.rgb(102, 153, 153), color);
    }

    public void testButtonStateSetting() {

        int actual_mal = 1;
        int actual_odor = 4;
        TouchUtils.clickView(this, vial);
        TouchUtils.clickView(this, vial);
        //TouchUtils.clickView(this, vial);
        //TouchUtils.clickView(this, vial);

        //TouchUtils.clickView(this, vial_odor);
        //TouchUtils.clickView(this, vial_odor);

        String expected = (String)vial.getTag();
        int color = ((ColorDrawable) vial.getBackground()).getColor();
        assertEquals( (Integer)setVialsActivity.getBw().getVialState(1), (Integer)actual_mal);
        assertEquals( (Integer)setVialsActivity.getBw().getVialState(10), (Integer)actual_odor);
    }
}