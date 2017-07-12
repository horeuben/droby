package com.example.reube.droby.Database;

import java.util.ArrayList;

/**
 * Created by reube on 4/6/2017.
 */

public class Outfit {
    private int id;
    private int outfit_id;
    private int user_id;
    private int clothes_id;
    private String name;
    private boolean isDeleted;
//set isdeleted to true to delete the outfit
    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getClothes_id() {
        return clothes_id;
    }

    public void setClothes_id(int clothes_id) {
        this.clothes_id = clothes_id;
    }

    public int getOutfit_id() {
        return outfit_id;
    }

    public void setOutfit_id(int outfit_id) {
        this.outfit_id = outfit_id;
    }
    //clothes contains a set of clothes which is paired up to form an outfit

    public Outfit(int id,int outfit_id) {
        this.id = id;
        this.outfit_id = outfit_id;
        //outfit_id would tell us which outfit it is, and get from database all the clothes that belong to this outfit
        //get arraylist of clothes separately
    }

    public Outfit() {

    }

    private ArrayList<Clothes> clothes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Clothes> getClothes() {
        return clothes;
    }

    public void setClothes(ArrayList<Clothes> clothes) {
        this.clothes = clothes;
    }
}
