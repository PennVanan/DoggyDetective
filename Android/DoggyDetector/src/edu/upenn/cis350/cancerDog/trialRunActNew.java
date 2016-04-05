package edu.upenn.cis350.cancerDog;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import android.text.*;
import java.util.Map;
import java.util.Map;
import com.google.gson.GsonBuilder;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewDebug;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.WindowManager;
import edu.upenn.cis350.cancerDog.calculation.PostJson;
import android.view.GestureDetector;
import java.util.*;

public class trialRunActNew extends  FragmentActivity implements SaveNotification.NoticeDialogListener {
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
    Button btnNext;
    EditText edText;
    TextView linkText;
    TextView etNumber;
    TextView etDog;
    private static String currentDog = "";
    private static String currentHandler = "";

    //BH1 Extra Data
    private static String currentTester = "";
    private static String currentTemperature  = "";
    private static String currentHumidity = "";
    private static String currentRecorder = "";
    private static boolean currentWater_bowl = false; //NEW
    private static boolean currentBarrier = false; //NEW
    private static boolean currentScreen = false; //NEW
    public static final int ButtonClickActivity_ID = 2;

    private static String benignSampleInfo;
    private static String malignSampleInfo;
    private static String controlSampleInfo;


    public String getCurrenttrialnote() {
        return currenttrialnote;
    }

    //Notes
    private String currenttrialnote = "";
    static ArrayList<String> trialnotes = new ArrayList<String>();

    //BH! for testing purposes
    AlertDialog alertDialog;
    EditText edTextNotes;


    static int counter;
    static ArrayList<String> results = new ArrayList<String>();
    int i;
    static BloodWheel bw;

    private GestureDetector gestureDetector;

    public BloodWheel getBloodWheel()
    {
        return this.bw;
    }


    public AlertDialog getAlertDialog() {
        return alertDialog;
    }
    public EditText getEdTextNotes() {
        return edTextNotes;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial_run);
        Log.e("Loading Activity", "TrialRunActivity");
        i=0;
        counter = 1;
        results = new ArrayList<String>();

        //BH!
        trialnotes = new ArrayList<String>();

        btnNext = (Button) findViewById(R.id.ibNext);
        edText = (EditText) findViewById(R.id.editText);
        etDog = (TextView) findViewById(R.id.dNameText);
        etNumber = (TextView) findViewById(R.id.NumberText);
        button1 = (Button) findViewById(R.id.ibSet1);
        button1.setTag("1");
        button1.setClickable(true);
        button2 = (Button) findViewById(R.id.ibSet2);
        button2.setTag("1");
        button2.setClickable(true);
        button3 = (Button) findViewById(R.id.ibSet3);
        button3.setTag("1");
        button3.setClickable(true);
        button4 = (Button) findViewById(R.id.ibSet4);
        button4.setTag("1");
        button4.setClickable(true);
        button5 = (Button) findViewById(R.id.ibSet5);
        button5.setTag("1");
        button5.setClickable(true);
        button6 = (Button) findViewById(R.id.ibSet6);
        button6.setTag("1");
        button6.setClickable(true);
        button7 = (Button) findViewById(R.id.ibSet7);
        button7.setTag("1");
        button7.setClickable(true);
        button8 = (Button) findViewById(R.id.ibSet8);
        button8.setTag("1");
        button8.setClickable(true);
        button9 = (Button) findViewById(R.id.ibSet9);
        button9.setTag("1");
        button9.setClickable(true);
        button10 = (Button) findViewById(R.id.ibSet10);
        button10.setTag("1");
        button10.setClickable(true);
        button11 = (Button) findViewById(R.id.ibSet11);
        button11.setTag("1");
        button11.setClickable(true);
        button12 = (Button) findViewById(R.id.ibSet12);
        button12.setTag("1");
        button12.setClickable(true);

        SharedPreferences preferences = getSharedPreferences(
                "edu.upenn.cis350.cancerDog.handlers", Context.MODE_PRIVATE);
        currentHandler = preferences.getString("current", "DEFAULT");

        preferences = getSharedPreferences(
                "edu.upenn.cis350.cancerDog.dogs", Context.MODE_PRIVATE);
        currentDog = preferences.getString("current", "DEFAULT");
        System.out.println("***** Dog is: " + currentDog + "******");
        etDog.setText(currentDog.trim());

        //BH! GET DATA
        //TESTER
        preferences = getSharedPreferences(
                "edu.upenn.cis350.cancerDog.handlers", Context.MODE_PRIVATE);
        currentTester = preferences.getString("current_tester", "DEFAULT");
        Log.d("BH!", "Tester " + currentTester);

        //Temp
        preferences = getSharedPreferences(
                "edu.upenn.cis350.cancerDog.conditions", Context.MODE_PRIVATE);
        currentTemperature = preferences.getString("temp", "DEFAULT");
        Log.d("BH!","Temp " + currentTemperature);

        //humidity
        preferences = getSharedPreferences(
                "edu.upenn.cis350.cancerDog.conditions", Context.MODE_PRIVATE);
        currentHumidity = preferences.getString("humidity", "DEFAULT");
        Log.d("BH!","humidity " + currentHumidity);

        //handler
        preferences = getSharedPreferences(
                "edu.upenn.cis350.cancerDog.handlers", Context.MODE_PRIVATE);
        currentHandler = preferences.getString("current_handler", "DEFAULT");
        Log.d("BH!","handler " + currentHandler);

        //recorder
        preferences = getSharedPreferences(
                "edu.upenn.cis350.cancerDog.handlers", Context.MODE_PRIVATE);
        currentRecorder = preferences.getString("current_recorder", "DEFAULT");
        Log.d("BH!","recorder " + currentRecorder);

        //Water Bowl
        preferences = getSharedPreferences(
                "edu.upenn.cis350.cancerDog.conditions", Context.MODE_PRIVATE);
        currentWater_bowl = preferences.getBoolean("water_bowl", false);
        Log.d("BH!","waterbowl " + currentWater_bowl);

        //Barrier
        preferences = getSharedPreferences(
                "edu.upenn.cis350.cancerDog.conditions", Context.MODE_PRIVATE);
        currentBarrier = preferences.getBoolean("barrier",false);
        Log.d("BH!","barrier " + currentBarrier);

        //Screen
        preferences = getSharedPreferences(
                "edu.upenn.cis350.cancerDog.conditions", Context.MODE_PRIVATE);
        currentScreen = preferences.getBoolean("screen",false);
        Log.d("BH!","screen " + currentScreen);

        //benignSampleInfo
        preferences = getSharedPreferences(
                "edu.upenn.cis350.cancerDog.conditions", Context.MODE_PRIVATE);
        benignSampleInfo = preferences.getString("BeningSampleInfo", "");
        Log.d("BH!2","Bening " + benignSampleInfo);

        //MalignSampleInf
        preferences = getSharedPreferences(
                "edu.upenn.cis350.cancerDog.conditions", Context.MODE_PRIVATE);
        malignSampleInfo = preferences.getString("MalignSampleInfo", "");
        Log.d("BH!2","Malingn " + malignSampleInfo);

        //ControlSampleInf
        preferences = getSharedPreferences(
                "edu.upenn.cis350.cancerDog.conditions", Context.MODE_PRIVATE);
        controlSampleInfo = preferences.getString("ControlSampleInfo", "");
        Log.d("BH!2","Control " + controlSampleInfo);

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
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                System.out.println("Double Tap");
                Toast.makeText(trialRunActNew.this, "double tap", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        button1.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        printLongClick(button1, 1);
                        return true;
                    }
                });
        button1.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        printShortClick(button1,1);
                    }
                });
        button2.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        printLongClick(button2,2);
                        return true;
                    }
                });
        button2.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        printShortClick(button2,2);
                    }
                });
        button3.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        printLongClick(button3,3);
                        return true;
                    }
                });
        button3.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        printShortClick(button3,3);
                    }
                });
        button4.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        printLongClick(button4,4);
                        return true;
                    }
                });
        button4.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        printShortClick(button4,4);
                    }
                });
        button5.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        printLongClick(button5,5);
                        return true;
                    }
                });
        button5.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        printShortClick(button5,5);
                    }
                });
        button6.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        printLongClick(button6,6);
                        return true;
                    }
                });
        button6.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        printShortClick(button6,6);
                    }
                });
        button7.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        printLongClick(button7,7);
                        return true;
                    }
                });
        button7.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        printShortClick(button7,7);
                    }
                });
        button8.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        printLongClick(button8,8);
                        return true;
                    }
                });
        button8.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        printShortClick(button8,8);
                    }
                });
        button9.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        printLongClick(button9,9);
                        return true;
                    }
                });
        button9.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        printShortClick(button9,9);
                    }
                });
        button10.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        printLongClick(button10,10);
                        return true;
                    }
                });
        button10.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        printShortClick(button10,10);
                    }
                });
        button11.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        printLongClick(button11,11);
                        return true;
                    }
                });
        button11.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        printShortClick(button11,11);
                    }
                });
        button12.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        printLongClick(button12,12);
                        return true;
                    }
                });
        button12.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        printShortClick(button12,12);
                    }
                });

    }

    public void onLeaveClick(View v)
    {
        String editTextStr = edText.getText().toString() + "  L";
        edText.setText(editTextStr);
    }

    public void printShortClick(Button button, int vialNo){
        Integer vialState = bw.getVialState(vialNo);
        String btn1Text;
        if((vialState==2) || (vialState==4)){
            btn1Text = "S" + vialNo;
        }
        else {
            btn1Text = "P" + vialNo;
        }
        String editTextStr = edText.getText().toString() + " " + btn1Text;
        edText.setText(editTextStr);
    }
    public void printLongClick(Button button, int vialNo){
        Integer vialState = bw.getVialState(vialNo);
        String btn1Text= "S" + vialNo;
        String editTextStr = edText.getText().toString() + " " + btn1Text;
        edText.setText(editTextStr);
    }



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

    public Button printState(Button button, int vialNo){
        Integer vialState = bw.getVialState(vialNo);
        String btn1Text="";
        if(vialState==2)
        {
            //Save 'Stop vial_no' somewhere
            btn1Text=btn1Text+"S"+vialNo;
            String editTextStr = edText.getText().toString() + " " + btn1Text;
            edText.setText(editTextStr);
        }
        else
        {
            if(button.getTag().toString()=="1"){
                //Save 'Pass vial_no somewhere
                button.setTag("2");
                System.out.println("Tag changed to 2");
                btn1Text=btn1Text+"P"+vialNo;
                String editTextStr = edText.getText().toString() + " " + btn1Text;
                edText.setText(editTextStr);

            }
            else if(button.getTag().toString()=="2") {
                //Save 'Stop vial_no somewhere
                System.out.println("Double clicked");
                button.setTag("1");
                System.out.println("Tag changed to 1");
                btn1Text = btn1Text + "S"+vialNo;
                String editTextStr = edText.getText().toString() + " " + btn1Text;
                edText.setText(editTextStr);

            }
        }
        return button;
    }


    public void onNextClick(View v) {
        results.add(edText.getText().toString());
        edText.setText("");
        //BH! add the notes
        trialnotes.add(currenttrialnote);
        currenttrialnote = ""; //reset trial notes
        counter++;
        etNumber.setText("" + counter);
        //trialNotes.add(notes);
        //   notes = "";
    }

    //BH!
    public void onTrialNotesClick(View v) {
        getTrialNote();
    }


    private void getTrialNote() {

        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
        dialogbuilder.setTitle("Trial " + counter + " Notes");

        // Set up the input
        edTextNotes = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        edTextNotes.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        edTextNotes.setText(currenttrialnote, TextView.BufferType.EDITABLE);
        dialogbuilder.setView(edTextNotes);

        // Set up the buttons
        dialogbuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (edTextNotes.length() == 0) {
                    currenttrialnote = "";
                } else {
                    currenttrialnote = edTextNotes.getText().toString();
                }

            }
        });
        dialogbuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog = dialogbuilder.show();

    }

    public void onSaveClick(View v) {
        showNoticeDialog();
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public void showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        if (haveNetworkConnection() == true) {
            DialogFragment dialog = new SaveNotification();
            System.out.println("Have Network Connection!!");
            dialog.show(getFragmentManager(), "SaveNotification");
        } else {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected, please connect to save trial",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        //BH! Save the last update
        results.add(edText.getText().toString());
        trialnotes.add(currenttrialnote);

        Toast.makeText(getApplicationContext(), "Trial Saved, Refer google docs for the report :) ",
                Toast.LENGTH_LONG).show();
        HashMap<String, Object> trial = new HashMap<String, Object>();
        calculation obj = new calculation();

        //BH! New constructor
        PostJson task = new PostJson(results, currentDog, currentHandler, bw.Malignant, bw.getBenign(), bw.getControl(), currentTester, currentTemperature, currentHumidity, currentRecorder, currentWater_bowl, currentBarrier, currentScreen, trialnotes, benignSampleInfo, malignSampleInfo, controlSampleInfo);

        counter = 1;
        etNumber.setText("" + counter);
        if (edText.getText().toString() != "")
            results.add(edText.getText().toString());
        task.execute((HashMap<String, Object>[]) (new HashMap[]{trial}));
        edText.setText("");

        //BH! we need to clean all the result before reset
        trialnotes.clear();
        results.clear();

        AlertDialog.Builder linkDialog= new AlertDialog.Builder(this);
        linkText = new TextView(this);
        //Provide Google Docs link here
        final SpannableString s =
                new SpannableString("\"https://docs.google.com/spreadsheets/d/1bDEF2GlKDSeGvzf570PTiPcKbdIC3SCCiw1HP0DVrvQ/edit?usp=sharing\"");
        Linkify.addLinks(s, Linkify.WEB_URLS);
        linkText.setText(s);
        linkText.setMovementMethod(LinkMovementMethod.getInstance());
        linkDialog.setView(linkText);
        linkDialog.setTitle(" View Results updated in this link - ");
        linkDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Do nothing
            }
        });
        linkDialog.show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
    }

}
