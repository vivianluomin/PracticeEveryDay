package com.example.asus1.remoteservice;

import android.os.Parcel;
import android.os.Parcelable;

public class MSG implements Parcelable {

   public String msg;

    public  static final Parcelable.Creator<MSG> CREATOR = new Parcelable.Creator<MSG>(){

        @Override
        public MSG createFromParcel(Parcel source) {
            return new MSG(source);
        }

        @Override
        public MSG[] newArray(int size) {
            return new MSG[size];
        }
    };

    public MSG(String msg){
        this.msg = msg;
    }

    private MSG(Parcel parcel){
        readFromParcel(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(msg);
    }

    public void readFromParcel(Parcel in){
         msg = in.readString();
    }
}
