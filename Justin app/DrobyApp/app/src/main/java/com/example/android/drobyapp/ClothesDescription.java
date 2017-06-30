package com.example.android.drobyapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import static android.R.attr.bitmap;
import static android.R.attr.y;

/**
 * Created by Family on 9/6/2017.
 */

public class ClothesDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clothes_description);

        ImageView imageView = (ImageView) findViewById(R.id.clothes_image);

        TextView textView = (TextView) findViewById(R.id.clothes_description);

        int image_Id = getIntent().getIntExtra("imageId",0);
        String description = getIntent().getStringExtra("imageDescription");
        imageView.setImageResource(image_Id);
        textView.setText(description);


    }

}
