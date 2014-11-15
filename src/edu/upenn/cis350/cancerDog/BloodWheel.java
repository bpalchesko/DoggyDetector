package edu.upenn.cis350.cancerDog;

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
