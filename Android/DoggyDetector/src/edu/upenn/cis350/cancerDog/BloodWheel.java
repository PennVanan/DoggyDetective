package edu.upenn.cis350.cancerDog;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.*;
import android.os.Bundle;

public class BloodWheel implements Parcelable {
	protected boolean BloodSet=false;
	protected int Control= 0;
	protected int Benign= 0;
	protected int Malignant = 0;
	protected int odorControl = 0;
	protected Integer noVials = 12;

	//ArrayList<Integer> vialState = new ArrayList<Integer>();
	int[] vialState = new int[12];
	public BloodWheel() {
		for (int i = 0; i < noVials ; i ++ ){

			vialState[i] = 4;
		}
	}

	public BloodWheel(Parcel in) {
        this.setControl(in.readInt());
		this.setBenign(in.readInt());
		this.Malignant = in.readInt();
	}

	public void setWheelData(Intent data){

		if (data==null)
			return;
		System.out.println(" In set wheel data");
			/*
			Bundle bundle = data.getExtras();
			if ( bundle != null)
			for (String key : bundle.keySet()) {
				Object value = bundle.get(key);
				System.out.println( key + " "  + value.toString());
			}
			*/
		if (data.hasExtra("Control") && data.hasExtra("Benign") && data.hasExtra("Malignant")) {
			this.setControl(data.getExtras().getInt("Control"));
			this.setBenign(data.getExtras().getInt("Benign"));
			this.setMalignant(data.getExtras().getInt("Malignant"));
			//this.odorControl = data.getExtras().getInt("OdorControl");

			System.out.println("here in setWheelData");
			System.out.println( " Malignant " + data.getExtras().getInt("Malignant"));
			System.out.println( " Benign " + data.getExtras().getInt("Benign"));
			System.out.println( " Control " + data.getExtras().getInt("Control") );
		}
		if ( data.hasExtra("Vial1")){
			for ( int j = 0 ; j < noVials ; j ++ ){
				System.out.println( (j) +" setting vialState  " + data.getExtras().getInt("Vial"+ (j)) );
				this.vialState[j] = data.getExtras().getInt("Vial"+ (j));
			}
		}
	}

	public void setVialState(Integer vialNo, Integer state){
		System.out.println( "setting " + (vialNo - 1) +  "to " + state );
		this.vialState[(vialNo - 1)] = state;


	}

	public Integer getVialState(Integer vialNo){
		return  this.vialState[(vialNo - 1)];
	}

	public void pushIntentData(Intent i) {

		System.out.println("Push Data Intent");

		for ( int j = 0 ; j < noVials ; j ++ ){
			System.out.println(" setting Intent   " + "Vial" + (j) + "- " + vialState[j]);
			i.putExtra("Vial"+(j), vialState[j]);
			Integer state = vialState[j];
			Integer vialNo = j+1 ;
			//Set control variables
			if ( state == 1 )
			{
				//RED
				this.setMalignant(vialNo);
				//this.Malignant = vialNo ;
				System.out.println(" setting " + (vialNo) + " to " + " malignant ");
			}
			else  if ( state == 3 )
			{    //GRAY
				this.setControl(vialNo);
				//this.Control = vialNo ;
				System.out.println(" setting " + (vialNo ) + " to " + " control ");
			}
			else if (state == 5){
				//GREEN
				this.setBenign( vialNo );
				//this.Benign = vialNo ;
				System.out.println(" setting " + (vialNo) + " to " + " benign ");
			}



		}
		i.putExtra("Benign", this.getBenign());
		i.putExtra("Control", this.getControl());
		i.putExtra("Malignant", this.getMalignant());
		//i.putExtra("OdorControl" ,this.getOdorControl());
		System.out.println( "Checking Intent " );
		Bundle bundle = i.getExtras();
		if ( bundle != null)
			for (String key : bundle.keySet()) {
				Object value = bundle.get(key);
				System.out.println( key + " "  + value.toString() );
			}
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(getControl());
		dest.writeInt(getBenign());
		dest.writeInt(Malignant);
	}

	public int getControl() {
		return this.Control;
	}

	public void setControl(int control) {
		this.Control = control;
	}

	public int getBenign() {
		return this.Benign;
	}

	public void setBenign(int benign) {
		this.Benign = benign;
	}

	public void setOdorControl (int odorControl) {
		this.odorControl = odorControl;
	}

	public int getOdorControl() {
		return this.odorControl;
	}

	public void setMalignant(int Malignant) {
		this.Malignant = Malignant;
	}

	public int getMalignant() {
		return this.Malignant;
	}
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public BloodWheel createFromParcel(Parcel in) {
			return new BloodWheel(in);
		}

		public BloodWheel[] newArray(int size) {
			return new BloodWheel[size];
		}
	};
}