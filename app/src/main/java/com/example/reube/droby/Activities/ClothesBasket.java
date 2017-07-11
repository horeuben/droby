package com.example.reube.droby.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reube.droby.Adapters.ClothesAdapter;
import com.example.reube.droby.Adapters.ClothesBasketAdapter;
import com.example.reube.droby.Database.Clothes;
import com.example.reube.droby.Database.DatabaseHandler;
import com.example.reube.droby.Fragments.ClothesFragment;
import com.example.reube.droby.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.id.list;
import static com.example.reube.droby.Fragments.ClothesFragment.adapter;
import static com.example.reube.droby.Fragments.ClothesFragment.clothes;

/**
 * Created by Family on 14/6/2017.
 */

public class ClothesBasket extends AppCompatActivity{

    public static ArrayList<Clothes> clothes_basket_list = new ArrayList<Clothes>();
    public static ArrayList<Clothes> basketList;
    private DatabaseHandler dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clothes_basket);
        setTitle("Clothes Basket");

        //clothes_basket_list = (ArrayList<Clothes>)getIntent().getExtras().getSerializable("ArrayList");
        //ClothesBasketAdapter adapter = new ClothesBasketAdapter(this, clothes_basket_list);

        ArrayList<String> list = (ArrayList<String>)getIntent().getExtras().getSerializable("StringList");
        dbHelper = new DatabaseHandler(this);

        basketList = dbHelper.getSelectedClothesIdTest(list);

        final ClothesBasketAdapter basketAdapter = new ClothesBasketAdapter(this, basketList);

        final ListView listView = (ListView) findViewById(R.id.basket_list);

        listView.setAdapter(basketAdapter);

        Button empty = (Button) findViewById(R.id.empty);
        empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basketList.clear();
                final ClothesBasketAdapter basketAdapter2 = new ClothesBasketAdapter(ClothesBasket.this, basketList);
                listView.setAdapter(basketAdapter2);

            }
        });

        Button styleButton = (Button) findViewById(R.id.style);
        styleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClothesBasket.this, StylingActivity.class);
                startActivity(intent);
            }
        });

   }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                onBackPressed(); //prevents parent page from reloading when press back
                Toast.makeText(this,"test",Toast.LENGTH_SHORT);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Toast.makeText(this,"test",Toast.LENGTH_SHORT);
        finish();

    }


}
