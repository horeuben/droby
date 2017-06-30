package com.example.reube.droby.Database;

import java.util.ArrayList;

/**
 * Created by reube on 4/6/2017.
 */

public class User {
    // Empty constructor
    public User(){

    }

    public User(int id, String password, String email, String nickname) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
       //arraylist of  clothes have to get from database separately
    }

    private int id;
    private String password;
    private String email;
    private String nickname;
    private ArrayList<Clothes> clothes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public ArrayList<Clothes> getClothes() {
        return clothes;
    }

    public void setClothes(ArrayList<Clothes> clothes) {
        this.clothes = clothes;
    }
}
