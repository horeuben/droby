package com.example.reube.droby.Database;

import android.graphics.Bitmap;

import java.sql.Blob;

/**
 * Created by reube on 4/6/2017.
 */
//Image of the clothes itself.
public class Image {
    private int id;
    private Bitmap image;

    public Image(int id, Bitmap image) {
        this.id = id;
        this.image = image;
    }

    public Image() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
