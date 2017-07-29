package com.example.reube.droby.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.reube.droby.R;

/**
 * Created by Family on 27/7/2017.
 */

public class MeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        ImageView image = (ImageView) findViewById(R.id.meImage);

        Intent intent = this.getIntent();
        String string = intent.getStringExtra("Extra");
        if(string.equals("collection") ){
            setTitle("Collection");
            image.setImageResource(R.drawable.me_collections);

        }
        else if(string.equals("friends")){
            setTitle("Friends");
            image.setImageResource(R.drawable.me_friends);

        }
        else if(string.equals("planner")){
            setTitle("Planner");
            image.setImageResource(R.drawable.me_history);

        }
    }
}
