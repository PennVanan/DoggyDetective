package edu.upenn.cis350.cancerDog;
import java.util.*;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import java.net.URLEncoder;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.WindowManager;
import com.google.gson.GsonBuilder;

import java.util.*;


public class calculation {
    private static final String FORM_URL = "https://docs.google.com/forms/d/1R8Oq1YvTVfrgxafxcGZaO6C_wca3Lv_JV3Su_MIEnaU/formResponse";
    private static String currentDog;
    private static final String DOG_NAME = "entry_434753845=";
    private static final String SUCCESS_RATE = "entry_1038306324=";
    private static final String SENSITIVITY = "entry_1407605094=";
    private static final String SPEC_NORMAL = "entry_494446774=";
    private static final String SPEC_BENIGN = "entry_515845283=";
    private static final String SPEC_TOTAL = "entry_921527013=";
    private static final String TOTAL_TNN = "entry_333632433=";
    private static final String TOTAL_TNB = "entry_1197022772=";
    private static final String TOTAL_FPN = "entry_216613810=";
    private static final String TOTAL_FPB = "entry_368331932=";
    private static final String TOTAL_FPE = "entry_1938978197=";
    private static final String TOTAL_FN = "entry_1347207823=";
    private static final String TOTAL_TP = "entry_918273940=";
    private static final String NOTES = "entry_1907361889=";
    private static final String TESTER = "entry_270032351=";
    private static final String TEMPERATURE = "entry_2068466040=";
    private static final String HUMIDITY = "entry_2021874211=";
    //BH1 added
    private static final String RECORDER = "entry_1725873612=";
    private static final String HANDLER = "entry_1410932431=";
    private static final String WATER_BOWL = "entry_416044467=";
    private static final String BARRIER = "entry_733206848=";
    private static final String SCREEN = "entry_1424331765=";
    //BH1
    static ArrayList<String> trialnotes = new ArrayList<String>();

    //BH2
    private static final String BENIGN_SAMPLE_INFO = "entry_75537333=";
    private static final String MALIGN_SAMPLE_INFO = "entry_47902150=";
    private static final String CONTROL_SAMPLE_INFO = "entry_964072865=";

    static ArrayList<String> results = new ArrayList<String>();


    private static final String[] TRIALS = new String[] {"entry_873369963=",
            "entry_82683764=", "entry_403485159=", "entry_1585414345=",
            "entry_809017169=", "entry_14494942=", "entry_487964326=",
            "entry_983571317=", "entry_964442581=", "entry_786666526=",
            "entry_1055685979=", "entry_560711180=", "entry_2037751422=", "entry_1516979135=",
            "entry_1970120858=", "entry_670425955=", "entry_2145047438=",
            "entry_802163021=", "entry_1561108562=", "entry_1290361149="};
    private static final String[] TRIAL_NOTES = new String[] {"entry_1440152102=",
            "entry_1922376403=", "entry_1854046377=", "entry_1970338951=",
            "entry_1225458375=", "entry_718995831=", "entry_765686634=",
            "entry_516681620=", "entry_1297993719=", "entry_1692870716=",
            "entry_113042049=", "entry_1084028390=", "entry_1740623401=", "entry_1142692779=",
            "entry_404381647=", "entry_39169021=", "entry_74263144=",
            "entry_1148593787=", "entry_1030777065=", "entry_861331498="};

    public static class PostJson extends
            AsyncTask<HashMap<String, Object>, Void, Void> {

        ArrayList<String> results;
        int malignant, benign, control;
        String currentHandler = ""; //BH! WRONG
        String currentDog = "";
        String data = "";

        //BH1 Extra Data
        String tester = "";
        String temperature = "";
        String humidity = "";
        String recorder = "";
        Boolean water_bowl = false;
        Boolean barrier = false;
        Boolean screen = false;
        //BH2
        String benignSampleInfo;
        String malignSampleInfo;
        String controlSampleInfo;

        ArrayList<String> trialnotes;

        public PostJson(ArrayList<String> results, String currentDog, String currentHandler, int malignant, int benign, int control)
        {
            this.currentDog = currentDog;
            this.currentHandler = currentHandler;
            this.results = new ArrayList<String>(results); //have to copy the array while the thread saves it because it is going to reset it
            this.malignant = malignant;
            this.benign = benign;
            this.control = control;
        }

        //New constructor
        public PostJson(ArrayList<String> results, String currentDog, String currentHandler, int malignant, int benign, int control, String tester, String temperature, String humidity, String recorder, boolean water_bowl, boolean barrier, boolean screen, ArrayList<String> trialnotes)
        {
            this(results , currentDog, currentHandler, malignant, benign, control);

            this.tester = tester;
            this.temperature = temperature;
            this.humidity = humidity;
            this.recorder = recorder;
            this.water_bowl = water_bowl;
            this.barrier = barrier;
            this.screen = screen;
            this.trialnotes = new ArrayList<String>(trialnotes); //have to copy the array while the thread saves it because it is going to reset it
        }

        //New constructor2
        public PostJson(ArrayList<String> results, String currentDog, String currentHandler, int malignant, int benign, int control, String tester, String temperature, String humidity, String recorder, boolean water_bowl, boolean barrier, boolean screen, ArrayList<String> trialnotes, String benignSampleInfo, String malignSampleInfo, String controlSampleInfo)
        {
            this(results, currentDog, currentHandler, malignant, benign, control, tester, temperature, humidity, recorder, water_bowl, barrier, screen, trialnotes);

            this.benignSampleInfo = benignSampleInfo;
            this.malignSampleInfo = malignSampleInfo;
            this.controlSampleInfo = controlSampleInfo;
        }


        public void postData() {
           // String fullUrl = "https://docs.google.com/forms/d/19Nh83jx9ogs4urOVIeRnC3bpBG4IOd26A8J1-NJxhu4/formResponse";
            HttpRequest mReq = new HttpRequest();
            String trialResults = "";

            //Just the trials, and their values encoded
            TrialCalculation tc = new TrialCalculation(results, malignant, benign, control);
            results = tc.getEncodedResults();

            //creates from the array the String to send to the form
            for (int i = 0; i < results.size(); i++){
                trialResults += "&" + TRIALS[i] + URLEncoder.encode(results.get(i));
            }


            for (int i = 0; i < trialnotes.size(); i++){
                trialResults += "&" + TRIAL_NOTES[i] + URLEncoder.encode(trialnotes.get(i));
            }


            Double sens = tc.getSensitivity();
            Double specNorm = tc.getSpecificityNormal();
            Double specBen = tc.getSpecificityBenign();
            Double specTot = tc.getSpecTotal();
            Double suc = tc.getSuccess();
            Integer tp = tc.True_Pos;
            Integer tnn = tc.True_Neg_Normal;
            Integer tnb = tc.True_Neg_Benign;
            Integer fpn = tc.False_Pos_Normal;
            Integer fpb = tc.False_Pos_Benign;
            Integer fpe = tc.False_Pos_Other;
            Integer fn = tc.False_Neg;


            //create the DATA to send to the google form which is an string
            data = DOG_NAME + URLEncoder.encode(currentDog) + "&" +
                    SENSITIVITY + URLEncoder.encode(sens.toString()) + "&" +
                    TOTAL_TP + URLEncoder.encode(tp.toString()) + "&" +
                    TOTAL_TNN + URLEncoder.encode(tnn.toString()) + "&" +
                    TOTAL_TNB + URLEncoder.encode(tnb.toString()) + "&" +
                    TOTAL_FPN + URLEncoder.encode(fpn.toString()) + "&" +
                    TOTAL_FPB + URLEncoder.encode(fpb.toString()) + "&" +
                    TOTAL_FPE + URLEncoder.encode(fpe.toString()) + "&" +
                    TOTAL_FN + URLEncoder.encode(fn.toString()) + "&" +
                    SPEC_NORMAL  + URLEncoder.encode(specNorm.toString()) + "&" +
                    SPEC_BENIGN  + URLEncoder.encode(specBen.toString()) + "&" +
                    SPEC_TOTAL  + URLEncoder.encode(specTot.toString()) + "&" +
                    SUCCESS_RATE + URLEncoder.encode(suc.toString()) + "&" +
                    TESTER + URLEncoder.encode(tester) + "&" + //BH!
                    TEMPERATURE + URLEncoder.encode(temperature) + "&" + //BH!
                    HUMIDITY + URLEncoder.encode(humidity)+ "&" + //BH!
                    RECORDER + URLEncoder.encode(recorder) + "&" + //BH!
                    HANDLER + URLEncoder.encode(currentHandler) + "&" + //BH!
                    WATER_BOWL + URLEncoder.encode(water_bowl.toString()) + "&" + //BH!
                    BARRIER + URLEncoder.encode(barrier.toString()) + "&" + //BH!
                    SCREEN + URLEncoder.encode(screen.toString()) + "&" +
                    BENIGN_SAMPLE_INFO + URLEncoder.encode(benignSampleInfo) + "&" +
                    MALIGN_SAMPLE_INFO + URLEncoder.encode(malignSampleInfo) + "&" +
                    CONTROL_SAMPLE_INFO + URLEncoder.encode(controlSampleInfo)
                    + trialResults;

            //Post DATA
            Log.i("DATA", data);
            String response = mReq.sendPost(FORM_URL, data);
            System.out.println("Final URL is: " + FORM_URL + "?" + data);
            System.out.println("DATA POSTED!!");
            results.clear();
            //   trialNotes.clear();

        }

        @Override
        protected Void doInBackground(HashMap<String, Object>... arg0) {
            String json = new GsonBuilder().create().toJson(arg0[0], Map.class);
            try {
                postData();
            } catch (Exception e) {
                System.out.println("Exception caught");
                e.printStackTrace();
            }
            return null;
        }

        public String url_data()
        {
            return FORM_URL + "?" + data;
        }

        @Override
        protected void onPreExecute(){
        }
    }

}
