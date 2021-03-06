package edu.upenn.cis350.cancerDog;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class LauncherActivity extends Activity {
	private BloodWheel bw;
	
	public static final int ButtonClickActivity_ID = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher_new);
		Intent data = (Intent) getIntent();
		setBw(new BloodWheel());
		getBw().setWheelData(data);
	}
	
	public void onStartTrialClick(View v) {
		Intent i = new Intent(this, confirmSettings.class);
		getBw().pushIntentData(i);
		startActivityForResult(i, ButtonClickActivity_ID);
	}
	
	public void onSettingsButtonClick(View v) {
		Intent i = new Intent(this, EditDefaultActivityNew.class); 
		getBw().pushIntentData(i);
		startActivityForResult(i, ButtonClickActivity_ID);
	}

	public void onSettingsNewButtonClick(View v) {
		Intent i = new Intent(this,SetVials.class);
		getBw().pushIntentData(i);
		startActivityForResult(i, ButtonClickActivity_ID);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {//If back on phone pressed, retrieve data for intent
		super.onActivityResult(requestCode, resultCode, data);
	    if (data==null){
	    	if (getBw()==null) setBw(new BloodWheel());
	    } else{ 
	    	getBw().setWheelData(data);
	  	}
	}
	
	@Override
	public void finish(){//If back on phone pressed, store data for next intent
		Intent i = new Intent();
		getBw().pushIntentData(i);
		setResult(Activity.RESULT_OK, i);
		super.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.launcher, menu);
		return true;
	}

	public BloodWheel getBw() {
		return bw;
	}

	public void setBw(BloodWheel bw) {
		this.bw = bw;
	}
}
