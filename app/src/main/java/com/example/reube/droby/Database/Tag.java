package com.example.reube.droby.Database;

/**
 * Created by reube on 4/6/2017.
 */

public class Tag {
    private int id;

    public Tag() {
    }

    public Tag(int id,int clothes_id, int user_id, int category_id, String name) {
        this.id = id;
        this.clothes_id=clothes_id;
        this.user_id = user_id;
        this.category_id = category_id;
        this.name = name;
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

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
    public int getClothes_id() {
        return clothes_id;
    }

    public void setClothes_id(int clothes_id) {
        this.clothes_id = clothes_id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    private int user_id;
    private int category_id;
    private String name;
    private int clothes_id;


}
