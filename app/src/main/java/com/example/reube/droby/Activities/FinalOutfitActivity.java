package com.example.reube.droby.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.reube.droby.Fragments.StylesFragment;
import com.example.reube.droby.R;

/**
 * Created by Family on 10/7/2017.
 */

public class FinalOutfitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Outfit of the Day");
        setContentView(R.layout.activity_final_outfit);

        TextView textChoose = (TextView) findViewById(R.id.text_choose);
        textChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stylesFragIntent = new Intent(FinalOutfitActivity.this, MainActivity.class);
                stylesFragIntent.putExtra("test", "Style");
                startActivity(stylesFragIntent);
            }
        });

    }

}
