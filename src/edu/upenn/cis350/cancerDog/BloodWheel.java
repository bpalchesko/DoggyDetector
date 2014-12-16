package edu.upenn.cis350.cancerDog;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

public class BloodWheel implements Parcelable {
	//## This needs to be private with getters and setters
		protected boolean BloodSet=false;
		protected int Control=1;
		protected int Benign=5;
		protected int Malignant=9;
		
		public BloodWheel() {

		}
		
		public BloodWheel(Parcel in) {
		    this.Control = in.readInt();
		    this.Benign = in.readInt();
		    this.Malignant = in.readInt();
		}
		
		public void setWheelData(Intent data){
			if (data==null)
				return;
			if (data.hasExtra("Control") && data.hasExtra("Benign") && data.hasExtra("Malignant")) {
			      this.Control=data.getExtras().getInt("Control");
			      this.Benign=data.getExtras().getInt("Benign");
			      this.Malignant=data.getExtras().getInt("Malignant");
			}
		}
		
		public void pushIntentData(Intent i){
			i.putExtra("Benign", this.Benign);
			i.putExtra("Control", this.Control);  
			i.putExtra("Malignant", this.Malignant); 
		}
		
		@Override
		public int describeContents() {
			return 0;
		}
		
		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeInt(Control);
			dest.writeInt(Benign);
			dest.writeInt(Malignant);
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
