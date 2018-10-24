package com.example.asus1.remoteservice.serilization;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;

public class User implements Parcelable {

    public int userId;
    public String userName;
    public boolean isMale;


    public User(int userId, String userName, boolean isMale) {
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
    }

    private User(Parcel in){
        this.userId = in.readInt();
        this.userName = in.readString();
        this.isMale = in.readInt() == 1;
    }


    public static final Parcelable.Creator<User> CREATOR = new
            Parcelable.Creator<User>(){

                @Override
                public User createFromParcel(Parcel source) {
                    return new User(source);
                }

                @Override
                public User[] newArray(int size) {
                    return new User[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(userId);
        dest.writeString(userName);
        dest.writeInt(isMale?1:0);
    }

    public static void putUser(Context context,Class activity){
        Intent intent = new Intent(context,activity);
        Bundle bundle = new Bundle();
        bundle.putParcelable("user",new User(1,"vivian",false));
        intent.putExtra("user",bundle);
    }
}
