package com.example.reube.droby.Database;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by reube on 4/6/2017.
 */
//might have to create another class for frequency, to add clothes id and date worn, then sum them up
public class Clothes implements Parcelable {
    public Clothes(int id, int user_id, byte[] image, String category_id, String name, String description, Date created_date, int location) {
        this.id = id;
        this.user_id = user_id;
        this.image = image;
        this.category_id = category_id;
        this.name = name;
        this.description = description;
        this.created_date = created_date;
        this.location = location;

        //tags get separately
        //frequency get separately
    }

    public Clothes() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public ArrayList<Date> getFrequency() {
        return frequency;
    }

    public void setFrequency(ArrayList<Date> frequency) {
        this.frequency = frequency;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    private int id,user_id;
    private String name,description,category_id;
    private ArrayList<Tag> tags;
    private Date created_date;
    private ArrayList<Date> frequency;
    private int location;
    private byte[] image;
    private boolean selected;




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeInt(image.length);
        dest.writeByteArray(image);


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
        description = in.readString();
        image = new byte[in.readInt()];
        in.readByteArray(image);
    }
}
