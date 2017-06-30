package com.example.reube.droby.Database;

/**
 * Created by reube on 13/6/2017.
 */

public class Category {
    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category() {

    }

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
