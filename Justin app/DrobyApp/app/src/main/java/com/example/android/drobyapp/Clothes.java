package com.example.android.drobyapp;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Family on 9/6/2017.
 */

public class Clothes implements Parcelable {
    private String mClothesDescription;

    private int mImageResourceId;

    public Clothes(String clothesDescription, int imageResourceId)  {
        mClothesDescription = clothesDescription;
        mImageResourceId = imageResourceId;
    }

    public String getClothesDescription()    {
        return mClothesDescription;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    private boolean selected;

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mClothesDescription);
        dest.writeInt(mImageResourceId);
    }

    // Creator
    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public Clothes createFromParcel(Parcel in) {
            return new Clothes(in);
        }

        public Clothes[] newArray(int size) {
            return new Clothes[size];
        }
    };

    // "De-parcel object
    public Clothes(Parcel in) {
        mClothesDescription = in.readString();
        mImageResourceId = in.readInt();
    }

}
