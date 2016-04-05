package edu.upenn.cis350.cancerDog;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.text.InputType;

public class EditDefaultActivityNew extends Activity implements NumberPicker.OnValueChangeListener {

	BloodWheel bw;

	private ArrayList<String> dogs = new ArrayList<String>();
	private ArrayList<String> personnel = new ArrayList<String>();
	private String currentDog;
	private String currentHandler;
	private String currentTester;
	private String currentRecorder;

	private static final String PERSONNEL_FILE = "edu.upenn.cis350.cancerDog.handlers";
	private static final String DOG_FILE = "edu.upenn.cis350.cancerDog.dogs";
	private static final String CONDITIONS_FILE = "edu.upenn.cis350.cancerDog.conditions";
	private SharedPreferences personnelSettings;
	private SharedPreferences dogSettings;
	private SharedPreferences conditionsSettings;
	private SharedPreferences.Editor personnelEditor;
	private SharedPreferences.Editor dogEditor;
	private SharedPreferences.Editor conditionsEditor;

	private ArrayAdapter<String> dogAdapter;
	private ArrayAdapter<CharSequence> handlerAdapter;
	private ArrayAdapter<CharSequence> testerAdapter;
	private ArrayAdapter<CharSequence> recorderAdapter;

	private Spinner dogSpinner;
	private Spinner handlerSpinner;
	private Spinner testerSpinner;
	private Spinner recorderSpinner;

	private EditText tempText;
	private EditText humidityText;

	//BH!
	private CheckBox checkBoxWater_bowl;
	private CheckBox checkBoxBarrier;
	private CheckBox checkBoxScreen;

	private static String benignSampleInfo;
	private static String malignSampleInfo;
	private static String controlSampleInfo;
	
	//BH! for testing purposes
	AlertDialog alertDialog;

	public AlertDialog getAlertDialog() {
		return alertDialog;
	}

	public static String getBenignSampleInfo() {
		return benignSampleInfo;
	}

	public static String getMalignSampleInfo() {
		return malignSampleInfo;
	}

	public static String getControlSampleInfo() {
		return controlSampleInfo;
	}


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editdefaults_new);
		Intent data = getIntent();
		dogSettings = getSharedPreferences(DOG_FILE, Context.MODE_PRIVATE);
		personnelSettings = getSharedPreferences(PERSONNEL_FILE, Context.MODE_PRIVATE);
		conditionsSettings = getSharedPreferences(CONDITIONS_FILE, Context.MODE_PRIVATE);
		dogEditor = dogSettings.edit();
		personnelEditor = personnelSettings.edit();
		conditionsEditor = conditionsSettings.edit();
		tempText = (EditText) findViewById(R.id.tempText);
		humidityText = (EditText) findViewById(R.id.humidityText);

		//BH1
		checkBoxWater_bowl = (CheckBox) findViewById(R.id.checkBoxWater_bowl);
		checkBoxBarrier = (CheckBox) findViewById(R.id.checkBoxBarrier);
		checkBoxScreen = (CheckBox) findViewById(R.id.checkBoxScreen);

		getSavedSettings();

		bw = new BloodWheel();
		bw.setWheelData(data);


		dogAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item);
		dogAdapter.setDropDownViewResource(R.layout.dropdown_spinner_item);
		handlerAdapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_item);
		handlerAdapter.setDropDownViewResource(R.layout.dropdown_spinner_item);
		testerAdapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_item);
		testerAdapter.setDropDownViewResource(R.layout.dropdown_spinner_item);
		recorderAdapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_item);
		recorderAdapter.setDropDownViewResource(R.layout.dropdown_spinner_item);
		refreshAdapters();

		dogSpinner = (Spinner) findViewById(R.id.dogSpinner1);
		dogSpinner.setAdapter(dogAdapter);
		dogSpinner.setSelection(findCurrentValue("dog"));
		dogSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parentView,
									   View selectedItemView, int position, long id) {
				dogSpinner.setSelection(position);
				String name = dogSpinner.getSelectedItem().toString();
				if("Edit List".equals(name)) createEditDialog("dog").show();
				else currentDog = name;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {}
		});

		handlerSpinner = (Spinner) findViewById(R.id.handlerSpinner1);
		handlerSpinner.setAdapter(handlerAdapter);
		handlerSpinner.setSelection(findCurrentValue("handler"));
		handlerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parentView,
									   View selectedItemView, int position, long id) {
				handlerSpinner.setSelection(position);
				String name = handlerSpinner.getSelectedItem().toString();
				if ("Edit List".equals(name)) createEditDialog("personnel").show();
				else currentHandler = name;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {}
		});

		testerSpinner = (Spinner) findViewById(R.id.testerSpinner);
		testerSpinner.setAdapter(testerAdapter);
		testerSpinner.setSelection(findCurrentValue("tester"));
		testerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parentView,
									   View selectedItemView, int position, long id) {
				testerSpinner.setSelection(position);
				String name = testerSpinner.getSelectedItem().toString();
				if ("Edit List".equals(name)) createEditDialog("personnel").show();
				else currentTester = name;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {}
		});

		recorderSpinner = (Spinner) findViewById(R.id.recorderSpinner);
		recorderSpinner.setAdapter(recorderAdapter);
		recorderSpinner.setSelection(findCurrentValue("recorder"));
		recorderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parentView,
									   View selectedItemView, int position, long id) {
				recorderSpinner.setSelection(position);
				String name = recorderSpinner.getSelectedItem().toString();
				if ("Edit List".equals(name)) createEditDialog("personnel").show();
				else currentRecorder = name;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {}
		});
	}

	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {}


	/**
	 * Loads spinner options initially and when user edits lists
	 */
	public void refreshAdapters() {
		dogAdapter.clear();
		handlerAdapter.clear();
		testerAdapter.clear();
		recorderAdapter.clear();
		for (String s : dogs) {
			dogAdapter.add(s);
		}
		for (String s : personnel) {
			handlerAdapter.add(s);
			testerAdapter.add(s);
			recorderAdapter.add(s);
		}
		dogAdapter.add("Edit List");
		handlerAdapter.add("Edit List");
		testerAdapter.add("Edit List");
		recorderAdapter.add("Edit List");
	}

	/**
	 * Set spinners to current selection, or first element if selection was deleted
	 * @parami.e. dog, handler, tester, or recorder
	 * @return index of currently selected value
	 */
	public int findCurrentValue(String value) {
		if("dog".equals(value)) {
			return Math.max(dogs.indexOf(currentDog), 0);
		} else if("handler".equals(value)) {
			return Math.max(personnel.indexOf(currentHandler), 0);
		} else if("tester".equals(value)) {
			return Math.max(personnel.indexOf(currentTester), 0);
		} else if("recorder".equals(value)) {
			return Math.max(personnel.indexOf(currentRecorder), 0);
		}
		return 0;
	}

	/**
	 * Creates the dialog box to edit spinner options
	 * @param category i.e. dog, handler, tester, or recorder
	 * @return dialog box
	 */
	public AlertDialog.Builder createEditDialog(final String category) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		final EditText input = new EditText(this);
		if("dog".equals(category)) {
			dialog.setTitle("Edit Dog List");
			input.setText(nameStringFromList(dogs));
		} else {
			dialog.setTitle("Edit Personnel List");
			input.setText(nameStringFromList(personnel));
		}
		dialog.setMessage("Please enter names separated by commas \n" +
				"Example: Name1, Name2, Name3, Name4");
		dialog.setView(input);

		dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String group = input.getText().toString();
				if ("dog".equals(category)) {
					dogs = listFromNameString(group);
				} else {
					personnel = listFromNameString(group);
				}
				refreshAdapters();
				dogSpinner.setSelection(findCurrentValue("dog"));
				handlerSpinner.setSelection(findCurrentValue("handler"));
				testerSpinner.setSelection(findCurrentValue("tester"));
				recorderSpinner.setSelection(findCurrentValue("recorder"));
			}
		});

		dialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dogSpinner.setSelection(findCurrentValue("dog"));
						handlerSpinner.setSelection(findCurrentValue("handler"));
						testerSpinner.setSelection(findCurrentValue("tester"));
						recorderSpinner.setSelection(findCurrentValue("recorder"));
					}
				});
		return dialog;
	}

	/**
	 * Gets spinner options from saved preferences
	 * @param group i.e. dog, handlers
	 * @return list of options
	 */
	public ArrayList<String> getGroup(String group) {
		String names = "";
		if ("dogs".equals(group)) {
			String defaultDogs = "Tsunami, Ffoster, McBaine, Ohlin";
			SharedPreferences settings = getSharedPreferences(
					DOG_FILE, Context.MODE_PRIVATE);
			names = settings.getString("dogs", defaultDogs);
		} else if ("handlers".equals(group)) {
			String defaultPersonnel = "Annemarie D, Jonathan B, Ken V, Sun, Lorenzo R";
			SharedPreferences settings = getSharedPreferences(
					PERSONNEL_FILE, Context.MODE_PRIVATE);
			names = settings.getString("handlers", defaultPersonnel);
		}
		return listFromNameString(names);
	}

	/**
	 * Sets variables and text fields for spinner options and current selections
	 */
	public void getSavedSettings(){
		dogs = getGroup("dogs");
		personnel= getGroup("handlers");
		currentDog = dogSettings.getString("current", "");
		currentHandler = personnelSettings.getString("current_handler", "");
		currentTester = personnelSettings.getString("current_tester", "");
		currentRecorder = personnelSettings.getString("current_recorder", "");
		tempText.setText(conditionsSettings.getString("temp","0"));
		humidityText.setText(conditionsSettings.getString("humidity","0"));

		//BH!
		checkBoxWater_bowl.setChecked(conditionsSettings.getBoolean("water_bowl",false));
		checkBoxBarrier.setChecked(conditionsSettings.getBoolean("barrier",false));
		checkBoxScreen.setChecked(conditionsSettings.getBoolean("screen",false));

		this.benignSampleInfo = conditionsSettings.getString("BeningSampleInfo", "");
		this.malignSampleInfo = conditionsSettings.getString("MalignSampleInfo", "");
		this.controlSampleInfo = conditionsSettings.getString("ControlSampleInfo", "");



	}

	/**
	 * Saves spinner options and current selections to shared preferences
	 */
	public void saveCurrentSettings() {
		dogEditor.putString("current", currentDog);
		dogEditor.putString("dogs", nameStringFromList(dogs));
		dogEditor.commit();
		personnelEditor.putString("current_handler", currentHandler);
		personnelEditor.putString("current_tester", currentTester);
		personnelEditor.putString("current_recorder", currentRecorder);
		personnelEditor.putString("handlers", nameStringFromList(personnel));
		personnelEditor.commit();
		conditionsEditor.putString("temp", tempText.getText().toString());
		conditionsEditor.putString("humidity", humidityText.getText().toString());

		//BH!
		conditionsEditor.putBoolean("water_bowl",checkBoxWater_bowl.isChecked());
		conditionsEditor.putBoolean("barrier",checkBoxBarrier.isChecked());
		conditionsEditor.putBoolean("screen",checkBoxScreen.isChecked());

		conditionsEditor.putString("BeningSampleInfo", this.benignSampleInfo);
		conditionsEditor.putString("MalignSampleInfo", this.malignSampleInfo);
		conditionsEditor.putString("ControlSampleInfo", this.controlSampleInfo);

		conditionsEditor.commit();
	}

	/**
	 * Converts comma-separated string of names to list (for spinners)
	 * @paramcomma-separated string of names
	 * @return list of names
	 */
	public static ArrayList<String> listFromNameString(String names) {
		ArrayList<String> temp = new ArrayList<String>();
		names = names.trim();
		String[] namesArray = names.split(",");
		for(String s: namesArray) {
			s = s.trim();
			temp.add(s);
		}
		return temp;
	}

	/**
	 * Converts list of names to comma-separated string (for saving to
	 * shared preferences)
	 * @paramlist of names
	 * @return comma-separated string of names
	 */
	public static String nameStringFromList(ArrayList<String> names) {
		String temp = "";
		for(int i = 0; i < names.size() - 1; i++) {
			temp = temp + names.get(i) + ", ";
		}
		temp += names.get(names.size() - 1);
		return temp;
	}

	/**
	 * Action when back button is clicked
	 * @param v
	 */
	public void onPreviousButtonClick(View v) {
		finish();
		System.exit(0);
	}


	public void onVialSetButtonClick(View v){
		try {
			Intent i = new Intent(this, SetVials.class);
			this.bw.pushIntentData(i);
			startActivityForResult(i, 1);
		}catch(Exception ex)
		{
			System.out.println("Exception is: " + ex.toString());
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {//If back on phone pressed, retrieve data for intent
		super.onActivityResult(requestCode, resultCode, data);
		if (data !=null)
		{
			this.bw.setWheelData(data);
		}
	}

	public void onBenignSampleSetButtonClick(View v){
		getSampleInfoBenign();
	}

	public void onMalignSampleSetButtonClick(View v){
		getSampleInfoMalign();
	}

	public void onControlSampleSetButtonClick(View v){
		getSampleInfoControl();
	}

	private void getSampleInfoBenign() {

		AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
		dialogbuilder.setTitle("Benign Type Sample Info");

		// Set up the input
		final EditText edTextNotes = new EditText(this);
		// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
		edTextNotes.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
		edTextNotes.setText(benignSampleInfo, TextView.BufferType.EDITABLE);
		dialogbuilder.setView(edTextNotes);

		// Set up the buttons
		dialogbuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				if (edTextNotes.length() == 0) {
					benignSampleInfo = "";
				} else {
					benignSampleInfo = edTextNotes.getText().toString();
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

	private void getSampleInfoMalign() {

		AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
		dialogbuilder.setTitle("Malign Type Sample Info");

		// Set up the input
		final EditText edTextNotes = new EditText(this);
		// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
		edTextNotes.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
		edTextNotes.setText(malignSampleInfo, TextView.BufferType.EDITABLE);
		dialogbuilder.setView(edTextNotes);

		// Set up the buttons
		dialogbuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				if (edTextNotes.length() == 0) {
					malignSampleInfo = "";
				} else {
					malignSampleInfo = edTextNotes.getText().toString();
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

	private void getSampleInfoControl() {

		AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
		dialogbuilder.setTitle("Control Type Sample Info");

		// Set up the input
		final EditText edTextNotes = new EditText(this);
		// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
		edTextNotes.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
		edTextNotes.setText(controlSampleInfo, TextView.BufferType.EDITABLE);
		dialogbuilder.setView(edTextNotes);

		// Set up the buttons
		dialogbuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				if (edTextNotes.length() == 0) {
					controlSampleInfo = "";
				} else {
					controlSampleInfo = edTextNotes.getText().toString();
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
	@Override
	public void finish(){
		Intent i = new Intent();
		System.out.println("Push Data Intent in EditDefault");
		bw.pushIntentData(i);
		saveCurrentSettings();


		setResult(Activity.RESULT_OK, i);
		super.finish();
	}
}
