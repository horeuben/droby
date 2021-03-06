package com.example.reube.droby.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.reube.droby.Adapters.ClothesBasketAdapter;
import com.example.reube.droby.Database.Clothes;
import com.example.reube.droby.Database.DatabaseHandler;
import com.example.reube.droby.Fragments.ClothesFragment;
import com.example.reube.droby.R;

import java.util.ArrayList;

import static com.example.reube.droby.Fragments.ClothesFragment.adapter;
import static java.security.AccessController.getContext;

/**
 * Created by Family on 14/6/2017.
 */

public class ClothesBasket extends AppCompatActivity{

    public static ArrayList<Clothes> clothes_basket_list = new ArrayList<Clothes>();
    public static ArrayList<Clothes> basketList;
    private DatabaseHandler dbHelper;
    public static ClothesBasketAdapter basketAdapter;
    private ArrayList<String> StringList = new ArrayList<String>();

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

        basketAdapter = new ClothesBasketAdapter(this, basketList);

        final ListView listView = (ListView) findViewById(R.id.basket_list);

        listView.setAdapter(basketAdapter);

        Button empty = (Button) findViewById(R.id.emptyAll);
        empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basketList.clear();
                adapter.clothes_basket_cart.clear();
                basketAdapter.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
//                final ClothesBasketAdapter basketAdapter2 = new ClothesBasketAdapter(ClothesBasket.this, basketList);
//                listView.setAdapter(basketAdapter2);


            }
        });

        Button styleButton = (Button) findViewById(R.id.style);
        styleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(basketAdapter.singleOnepiece.size()>0 && (basketAdapter.singleTop.size()>0 || basketAdapter.singleBottom.size()>0)){
                    Toast.makeText(getApplicationContext(), "Both one-piece and top/bottom selected!", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(basketAdapter.singleTop.size()>1 || basketAdapter.singleBottom.size()>1||basketAdapter.singleOuter.size()>1||basketAdapter.singleOnepiece.size()>1){

                        if (basketAdapter.singleTop.size()>1){
                            Toast.makeText(getApplicationContext(), "More than 1 top selected!", Toast.LENGTH_SHORT).show();
                        }
                        if (basketAdapter.singleBottom.size()>1){
                            Toast.makeText(getApplicationContext(), "More than 1 bottom selected!", Toast.LENGTH_SHORT).show();
                        }
                        if (basketAdapter.singleOuter.size()>1){
                            Toast.makeText(getApplicationContext(), "More than 1 outer wear selected!", Toast.LENGTH_SHORT).show();
                        }
                        if (basketAdapter.singleOnepiece.size()>1){
                            Toast.makeText(getApplicationContext(), "More than 1 one-piece selected!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    else{
                        StringList = ClothesBasketAdapter.finalOutfitList;
                        Intent intent = new Intent(ClothesBasket.this, FinalOutfitActivity.class);
                        intent.putExtra("OutfitList", StringList);
                        startActivity(intent);
                    }
                }


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
        if (adapter.clothes_basket_cart.isEmpty()){
            for (int i=0; i<ClothesFragment.clothes.size(); i++){
                ClothesFragment.clothes.get(i).setSelected(false);
            }
        }
        ClothesBasketAdapter.finalOutfitList.clear();
        ClothesBasketAdapter.singleTop.clear();
        ClothesBasketAdapter.singleBottom.clear();
        ClothesBasketAdapter.singleOuter.clear();
        ClothesBasketAdapter.singleOnepiece.clear();
        if(adapter!=null){
            adapter.notifyDataSetChanged();
        }
        finish();

    }


}
