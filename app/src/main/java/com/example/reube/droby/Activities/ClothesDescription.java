package com.example.reube.droby.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.reube.droby.Adapters.ClothesBasketAdapter;
import com.example.reube.droby.R;

/**
 * Created by Family on 9/6/2017.
 */

public class ClothesDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Details");

        setContentView(R.layout.activity_clothes_description);

        ImageView imageView = (ImageView) findViewById(R.id.clothes_image);

        TextView textView = (TextView) findViewById(R.id.clothes_description);

        int image_Id = getIntent().getIntExtra("imageId",0);
        String description = getIntent().getStringExtra("imageDescription");
        imageView.setImageResource(image_Id);
        textView.setText(description);


    }

}
