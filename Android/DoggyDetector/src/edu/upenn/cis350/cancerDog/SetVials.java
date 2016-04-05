package edu.upenn.cis350.cancerDog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SetVials extends Activity {
    int stopValue;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button button10;
    Button button11;
    Button button12;
    BloodWheel bw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickvialtoset);
        button1= (Button)findViewById(R.id.ibSet1);
        button1.setTag("1");
        button1.setClickable(true);
        button2= (Button)findViewById(R.id.ibSet2);
        button2.setTag("1");
        button2.setClickable(true);
        button3= (Button)findViewById(R.id.ibSet3);
        button3.setTag("1");
        button3.setClickable(true);
        button4= (Button)findViewById(R.id.ibSet4);
        button4.setTag("1");
        button4.setClickable(true);
        button5= (Button)findViewById(R.id.ibSet5);
        button5.setTag("1");
        button5.setClickable(true);
        button6= (Button)findViewById(R.id.ibSet6);
        button6.setTag("1");
        button6.setClickable(true);
        button7= (Button)findViewById(R.id.ibSet7);
        button7.setTag("1");
        button7.setClickable(true);
        button8= (Button)findViewById(R.id.ibSet8);
        button8.setTag("1");
        button8.setClickable(true);
        button9= (Button)findViewById(R.id.ibSet9);
        button9.setTag("1");
        button9.setClickable(true);
        button10= (Button)findViewById(R.id.ibSet10);
        button10.setTag("1");
        button10.setClickable(true);
        button11= (Button)findViewById(R.id.ibSet11);
        button11.setTag("1");
        button11.setClickable(true);
        button12= (Button)findViewById(R.id.ibSet12);
        button12.setTag("1");
        button12.setClickable(true);

        //WHY????
        Intent data = (Intent) getIntent();
        this.bw=new BloodWheel();
        this.bw.setWheelData(data);

        setVialColorSaved(button1, 1);
        setVialColorSaved(button2 , 2 );
        setVialColorSaved(button3 , 3 );
        setVialColorSaved(button4 , 4 );
        setVialColorSaved(button5 , 5 );
        setVialColorSaved(button6 , 6 );
        setVialColorSaved(button7 , 7 );
        setVialColorSaved(button8 , 8 );
        setVialColorSaved(button9 , 9 );
        setVialColorSaved(button10 , 10 );
        setVialColorSaved(button11 , 11 );
        setVialColorSaved(button12, 12);

        System.out.println( " ON CREATE ACTIVITY SET VIALS");
        for( int i=0; i < 12; i++){
            System.out.println ( " vial state " + (i+1) + " - " + bw.getVialState( i+1));
        }

    }// on create

    protected void setVialColorSaved(Button button, int vialNo){
        Integer vialState = bw.getVialState( vialNo );

        System.out.println(" Setting new color for " + vialNo + " " + bw.getVialState( vialNo ) );
        //Normal
        if(bw.getVialState(vialNo) == 2) {
            button.setBackgroundColor(Color.rgb(102, 153, 255));
            //button.setBackgroundColor(Color.rgb(0, 153, 204));
            button.setTag("2");
        }
        //Normal Control
        else if(bw.getVialState(vialNo) == 3){
            button.setBackgroundColor(Color.rgb(102, 153, 153));
            button.setTag("3");
        }
        //Odor Control
        else if(bw.getVialState(vialNo) == 4){
            //button.setBackgroundColor(Color.rgb(255, 153, 0));
            //button.setBackgroundColor(Color.rgb(153, 204, 255));
            button.setBackgroundColor(Color.rgb(255, 153, 102));
            button.setTag("4");
        }
        //Benign
        else if(bw.getVialState(vialNo) == 5){
            button.setBackgroundColor(Color.rgb(0, 204, 102));
            button.setTag("5");
        }
        //Malignant
        else if(bw.getVialState(vialNo) == 1){
            button.setBackgroundColor(Color.rgb(255, 80, 80));
            button.setTag("1");
        }
    }

    protected void setVialColor(Button button, int vialNo){
        Integer vialState = bw.getVialState( vialNo );

        System.out.println(" Setting new color for " + vialNo + " " + bw.getVialState( vialNo ) );
        //Normal
        if(button.getTag().equals("2")) {
            //button.setBackgroundColor(Color.BLUE);
            button.setBackgroundColor(Color.rgb(102, 153, 255));
            //button.setBackgroundColor(Color.rgb(0, 153, 204));
            button.setTag("2");
        }
        //Normal Control
        else if(button.getTag().equals("3")){
            //button.setBackgroundColor(Color.GRAY);
            button.setBackgroundColor(Color.rgb(102, 153, 153));
            button.setTag("3");
        }
        //Odor Control
        else if(button.getTag().equals("4")){
            //button.setBackgroundColor(Color.CYAN);
            //button.setBackgroundColor(Color.rgb(255, 153, 0));
            //button.setBackgroundColor(Color.rgb(153, 204, 255));
            button.setBackgroundColor(Color.rgb(255, 153, 102));
            button.setTag("4");
        }
        //Benign
        else if(button.getTag().equals("5")){
            //button.setBackgroundColor(Color.GREEN);
            button.setBackgroundColor(Color.rgb(0, 204, 102));
            button.setTag("5");
        }
        //Malignant
        else if(button.getTag().equals("1")){
            //button.setBackgroundColor(Color.RED);
            button.setBackgroundColor(Color.rgb(255, 80, 80));
            button.setTag("1");
        }
    }

    protected Button toggleState(Button button){
        //Normal
        if(button.getTag().equals("1")) {
            //button.setBackgroundColor(Color.BLUE);
            button.setBackgroundColor(Color.rgb(102, 153, 255));
            button.setTag("2");
        }
        //Normal Control
        else if(button.getTag().equals("2")){
            //button.setBackgroundColor(Color.GRAY);
            button.setBackgroundColor(Color.rgb(102, 153, 153));
            button.setTag("3");
        }
        //Odor Control
        else if(button.getTag().equals("3")){
            //button.setBackgroundColor(Color.CYAN);
            //button.setBackgroundColor(Color.rgb(255, 153, 0));

            //button.setBackgroundColor(Color.rgb(153, 204, 255));
            button.setBackgroundColor(Color.rgb(255, 153, 102));
            button.setTag("4");
        }
        //Benign
        else if(button.getTag().equals("4")){
            //button.setBackgroundColor(Color.GREEN);
            button.setBackgroundColor(Color.rgb(0, 204, 102));
            button.setTag("5");
        }
        //Malignant
        else if(button.getTag().equals("5")){
            //button.setBackgroundColor(Color.RED);
            button.setBackgroundColor(Color.rgb(255, 80, 80));
            button.setTag("1");
        }
        return button;
    }

    public void onSetVialClick1 (View v) {
        button1=toggleState(button1);
        proccessReturn(1, Integer.valueOf(button1.getTag().toString()));
    }

    public void onSetVialClick2 (View v) {
        button2=toggleState(button2);
        proccessReturn(2, Integer.valueOf(button2.getTag().toString()));
    }

    public void onSetVialClick3 (View v) {

        button3=toggleState(button3);
        proccessReturn(3, Integer.valueOf(button3.getTag().toString()));
    }

    public void onSetVialClick4 (View v) {
        button4=toggleState(button4);
        proccessReturn(4, Integer.valueOf(button4.getTag().toString()));
    }

    public void onSetVialClick5 (View v) {
        button5=toggleState(button5);
        proccessReturn(5, Integer.valueOf(button5.getTag().toString()));
    }

    public void onSetVialClick6 (View v) {
        button6=toggleState(button6);
        proccessReturn(6, Integer.valueOf(button6.getTag().toString()));
    }

    public void onSetVialClick7 (View v) {
        button7=toggleState(button7);
        proccessReturn(7, Integer.valueOf(button7.getTag().toString()));
    }

    public void onSetVialClick8 (View v) {
        button8=toggleState(button8);
        proccessReturn(8, Integer.valueOf(button8.getTag().toString()));
    }

    public void onSetVialClick9 (View v) {
        button9=toggleState(button9);
        proccessReturn(9, Integer.valueOf(button9.getTag().toString()));
    }

    public void onSetVialClick10 (View v) {
        button10=toggleState(button10);
        proccessReturn(10, Integer.valueOf(button10.getTag().toString()));
    }

    public void onSetVialClick11 (View v) {
        button11=toggleState(button11);
        proccessReturn(11, Integer.valueOf(button11.getTag().toString()));
    }

    public void onSetVialClick12 (View v) {
        button12=toggleState(button12);
        proccessReturn(12, Integer.valueOf(button12.getTag().toString()));
    }


    protected void proccessReturn(int vialNo, int state){
        System.out.println("TESSSST!!!!" + vialNo + state);
        bw.setVialState( vialNo, state );

    }

    public void onPreviousButtonClick(View v) {
        finish();
        System.exit(0);
    }

    public BloodWheel getBw(){
        return this.bw;
    }

    @Override
    public void finish(){
        Intent i = new Intent();
        System.out.println("Push Data Intent in SetVials");
        bw.pushIntentData(i);
        //saveCurrentSettings();
        System.out.println( "Checking Intent before finishing setVials" );
        Bundle bundle = i.getExtras();
        if ( bundle != null)
            for (String key : bundle.keySet()) {
                Object value = bundle.get(key);
                System.out.println( key + " "  + value.toString() );
            }
        setResult(Activity.RESULT_OK, i);
        super.finish();
    }

}