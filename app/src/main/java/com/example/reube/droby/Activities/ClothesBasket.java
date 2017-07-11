package com.example.reube.droby.Activities;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import static com.example.reube.droby.Fragments.ClothesFragment.clothes;

/**
 * Created by Family on 14/6/2017.
 */

public class ClothesBasket extends AppCompatActivity{

    public static ArrayList<Clothes> clothes_basket_list;
    public static ArrayList<Clothes> basketList = new ArrayList<Clothes>();
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
        ClothesBasketAdapter adapter = new ClothesBasketAdapter(this, basketList);

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
