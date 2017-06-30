package com.example.android.drobyapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class StylesFragment extends Fragment {

    String[] crime={"Blacklist","Crisis","Gotham","Banshee","Breaking Bad","Blacklist","Crisis","Gotham","Banshee","Breaking Bad",
            "Blacklist","Crisis","Gotham","Banshee","Breaking Bad","Blacklist","Crisis","Gotham","Banshee","Breaking Bad"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.fragment_styles,container,false);

        ListView lv= (ListView) rootView.findViewById(R.id.fragment_styles);
        ArrayAdapter adapter=new ArrayAdapter(this.getActivity(),android.R.layout.simple_list_item_1,crime);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),crime[position],Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent clothesBasketIntent = new Intent(getActivity(), ClothesBasket.class);
                startActivity(clothesBasketIntent);
            }
        });

        return rootView;
    }



}
