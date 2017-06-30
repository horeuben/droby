package com.example.reube.droby.Database;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by reube on 4/6/2017.
 */
//might have to create another class for frequency, to add clothes id and date worn, then sum them up
public class Clothes {
    public Clothes(int id, int user_id, int image_id, int category_id, String name, String description, Date created_date, char location) {
        this.id = id;
        this.user_id = user_id;
        this.image_id = image_id;
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

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
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

    public char getLocation() {
        return location;
    }

    public void setLocation(char location) {
        this.location = location;
    }

    private int id,user_id,image_id,category_id;
    private String name,description;
    private ArrayList<Tag> tags;
    private Date created_date;
    private ArrayList<Date> frequency;
    private char location;
}
