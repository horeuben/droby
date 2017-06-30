package com.example.android.drobyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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


                CheckBox checkbox = (CheckBox) view.getTag(R.id.btn1);
                //Toast.makeText(view.getContext(), isCheckedOrNot(checkbox), Toast.LENGTH_LONG).show();

            }
            private String isCheckedOrNot(CheckBox checkbox) {
                if(checkbox.isChecked())

                    return "is checked";

                else
                    return "is not checked";
            }
        });



        setHasOptionsMenu(true);
        gridView.setTextFilterEnabled(true);



        return rootView;
    }



    private void filter(String text){

        for(int i = 0; i < size; i+=1){

            if (clothes.get(i).getClothesDescription().contains(text)){

                clothes2.add(new Clothes(clothes.get(i).getClothesDescription(),clothes.get(i).getImageResourceId()));
            }
            adapter.notifyDataSetChanged();
            gridView.setAdapter(adapter);
            gridView.invalidateViews();

            adapter = new ClothesAdapter(getActivity(), clothes2);
            //gridView = (GridView) findViewById(R.id.clothes_list);
            gridView.setAdapter(adapter);

        }



    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Implementing ActionBar Search inside a fragment
        MenuItem item = menu.add("Search");
        item.setIcon(R.drawable.ic_search_black_24dp); // sets icon
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        SearchView sv = new SearchView(getActivity());

        // modifying the text inside edittext component
        int id = sv.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) sv.findViewById(id);
        textView.setHint("Search clothing...");
        textView.setHintTextColor(getResources().getColor(R.color.black));
        textView.setTextColor(getResources().getColor(R.color.black));

        // implementing the listener
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText)
            {

                if (TextUtils.isEmpty(newText)) {
                    gridView.clearTextFilter();
                } else {
                    //gridView.setFilterText(newText);
                    filter(newText);
                }
//                if (TextUtils.isEmpty(newText)) {
//                    adapter.filter("");
//                    gridView.clearTextFilter();
//                } else {
//                    adapter.filter(newText);
//                }

                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return false;
            }
        });
        item.setActionView(sv);
    }







}

