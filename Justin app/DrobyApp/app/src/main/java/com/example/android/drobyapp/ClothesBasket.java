package com.example.android.drobyapp;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

/**
 * Created by Family on 14/6/2017.
 */

public class ClothesBasket extends AppCompatActivity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clothes_basket);

        final ArrayList<Clothes> clothes_basket_list = (ArrayList<Clothes>)getIntent().getExtras().getSerializable("ArrayList");

        ClothesBasketAdapter adapter = new ClothesBasketAdapter(this, clothes_basket_list);

        ListView listView = (ListView) findViewById(R.id.basket_list);

        listView.setAdapter(adapter);

   }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                onBackPressed(); //prevents parent page from reloading when press back
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
