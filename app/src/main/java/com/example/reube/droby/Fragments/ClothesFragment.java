package com.example.reube.droby.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.TextView;


import com.example.reube.droby.Activities.Clothes;
import com.example.reube.droby.Activities.ClothesBasket;
import com.example.reube.droby.Activities.ClothesDescription;
import com.example.reube.droby.Adapters.ClothesAdapter;
import com.example.reube.droby.Database.DatabaseHandler;
import com.example.reube.droby.Database.Outfit;
import com.example.reube.droby.R;

import java.util.ArrayList;
import java.util.List;


public class ClothesFragment extends Fragment {

    public static ArrayList<Clothes> clothes = new ArrayList<Clothes>();
    public static ArrayList<Clothes> clothes2 = new ArrayList<Clothes>();
    private SearchView mSearchView;
    private GridView gridView;
    private ClothesAdapter adapter;
    private List<String> clothesList; //search bar
    private List<String> searchList;
    private int size = clothes.size();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Wardrobe");
        View rootView = inflater.inflate(R.layout.clothes_list, container, false);


        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Clothes> final_cart = adapter.clothes_cart;
                Intent clothesBasketIntent = new Intent(getActivity(), ClothesBasket.class);
                clothesBasketIntent.putExtra("ArrayList",final_cart);
                startActivity(clothesBasketIntent);
            }
        });


        clothes = new ArrayList<Clothes>();  //prevent list from multiplying everytime clothesfragment is activated
        //final ArrayList<Clothes> clothes = new ArrayList<Clothes>();
        clothes.add(new Clothes("Black Shirt", R.drawable.black_shirt));
        clothes.add(new Clothes("Brown Coat", R.drawable.brown_coat));
        clothes.add(new Clothes("Orange Dress", R.drawable.orange_dress));
        clothes.add(new Clothes("Pink T-shirt", R.drawable.pink_tshirt));
        clothes.add(new Clothes("White Shirt", R.drawable.white_shirt));
        clothes.add(new Clothes("White Shorts", R.drawable.white_shorts));
        clothes.add(new Clothes("Blue Shirt", R.drawable.blue_shirt));
        clothes.add(new Clothes("Other Black Shirt", R.drawable.black_shirt));
        clothes.add(new Clothes("Other Blue Shirt", R.drawable.blue_shirt));
        clothes.add(new Clothes("Other Brown Coat", R.drawable.brown_coat));
        clothes.add(new Clothes("Other Orange Dress", R.drawable.orange_dress));
        clothes.add(new Clothes("Other Pink T-shirt", R.drawable.pink_tshirt));
        clothes.add(new Clothes("Other White Shirt", R.drawable.white_shirt));
        clothes.add(new Clothes("Other White Shorts", R.drawable.white_shorts));

        adapter = new ClothesAdapter(getActivity(), clothes);

        gridView = (GridView) rootView.findViewById(R.id.clothes_list);

        gridView.setAdapter(adapter);



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()  {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(), "Item #" + position + " clicked", Toast.LENGTH_SHORT).show();
                Clothes pos = clothes.get(position);
                int image_id = pos.getImageResourceId();
                String clothes_description = pos.getClothesDescription();

                Intent clothesIntent = new Intent(getActivity(), ClothesDescription.class);
                clothesIntent.putExtra("imageId",image_id);
                clothesIntent.putExtra("imageDescription", clothes_description);
                startActivity(clothesIntent);
            }

        });

        setHasOptionsMenu(true);

        return rootView;
    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        // modifying the text inside edittext component
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) searchView.findViewById(id);
        textView.setHint("Search clothing...");
        textView.setHintTextColor(getResources().getColor(R.color.texthint));
        textView.setTextColor(getResources().getColor(R.color.black));

        //*** setOnQueryTextFocusChangeListener ***
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchQuery) {

                adapter.filter(searchQuery.toString().trim());
                gridView.invalidate();
                return true;
            }
        });

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;  // Return true to expand action view
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menuSearch) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}








