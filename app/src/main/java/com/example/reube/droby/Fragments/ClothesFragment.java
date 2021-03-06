package com.example.reube.droby.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;


import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.reube.droby.Activities.ClothesBasket;
import com.example.reube.droby.Activities.ClothesDescription;
import com.example.reube.droby.Activities.MainActivity;
import com.example.reube.droby.Activities.SplashActivity;
import com.example.reube.droby.Adapters.ClothesAdapter;
import com.example.reube.droby.Database.Clothes;
import com.example.reube.droby.Database.DatabaseHandler;
import com.example.reube.droby.Database.DatabaseUtilities;
import com.example.reube.droby.R;

import java.util.ArrayList;


public class ClothesFragment extends Fragment {

    public static ArrayList<Clothes> clothes = new ArrayList<Clothes>();
    private GridView gridView;
    public static ClothesAdapter adapter;
    private DatabaseHandler mDbHelper;
    private static Parcelable state;
    ProgressDialog pd;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Wardrobe");
        View rootView = inflater.inflate(R.layout.clothes_list, container, false);


        if (isNetworkAvailable()){
            new SyncLocation().execute();
        }



        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<Clothes> final_cart = adapter.clothes_cart;
                ArrayList<String> clothesBasketCart = new ArrayList<String>();
                clothesBasketCart = adapter.clothes_basket_cart;

                Intent clothesBasketIntent = new Intent(getActivity(), ClothesBasket.class);
                //clothesBasketIntent.putExtra("ArrayList",final_cart);
                clothesBasketIntent.putExtra("StringList", clothesBasketCart);
                startActivity(clothesBasketIntent);
                //adapter.removeAllChecks(container);
            }
        });


        mDbHelper = new DatabaseHandler(getActivity());
        clothes = mDbHelper.getAllClothes(MainActivity.user);//.getAllClothesTest();
        for(int i = 0; i<clothes.size(); i++){                 //set grey out for items not in cupboard
            if(clothes.get(i).getLocation()== -1){
                clothes.get(i).setForegroundColour(R.color.filterGrey);
                clothes.get(i).setCheckboxClickable(true);
            }
            else{clothes.get(i).setForegroundColour(0);}
        }

        mDbHelper.syncTags();

        adapter = new ClothesAdapter(getActivity(), clothes);

        gridView = (GridView) rootView.findViewById(R.id.clothes_list);

        gridView.setAdapter(adapter);



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()  {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int clothesID = clothes.get(position).getId();
                Intent clothesIntent = new Intent(getActivity(), ClothesDescription.class);
                clothesIntent.putExtra("clothesID",clothesID);
                clothesIntent.putExtra("positionID",position);
                startActivity(clothesIntent);
//                Intent test = new Intent(getActivity(), ClothesDescription.class);
//                ArrayList<String> i = new ArrayList<String>();
//                i.add("0");
//                test.putExtra("clothesID",4);
//                startActivity(test);
            }

        });


        final SwipeRefreshLayout refresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isNetworkAvailable()){
                    new SyncLocation().execute();
                    clothes = mDbHelper.getAllClothes(MainActivity.user);
                    for(int i = 0; i<clothes.size(); i++){
                        if(clothes.get(i).getLocation()== -1){
                            clothes.get(i).setForegroundColour(R.color.filterGrey);
                            clothes.get(i).setCheckboxClickable(true);
                        }
                        else{clothes.get(i).setForegroundColour(0);}
                    }
                    adapter = new ClothesAdapter(getActivity(), clothes);
                    gridView.setAdapter(adapter);
                }
                else{Toast.makeText(getContext(), "No Connection Found",Toast.LENGTH_SHORT).show();}
                refresh.setRefreshing(false);
            }

        });


        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onPause() {
        state = gridView.onSaveInstanceState();    //saves current gridview scrolled state
        super.onPause();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if(state!=null){
            gridView.onRestoreInstanceState(state); //restores gridview scrolled state
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {

        super.onResume();

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        //setting up spinner

        MenuItem item = menu.findItem(R.id.spinner);
        final Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);


        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.spinner_data, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(spinnerAdapter);
        final String[] filterLabels = getResources().getStringArray(R.array.spinner_data);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equals(filterLabels[0])){
                    adapter.filterByCategory(0);
                }
                else if(selectedItem.equals(filterLabels[1])){
                    adapter.filterByCategory(1);
                }
                else if(selectedItem.equals(filterLabels[2])){
                    adapter.filterByCategory(2);
                }
                else if(selectedItem.equals(filterLabels[3])){
                    adapter.filterByCategory(3);
                }
                else if(selectedItem.equals(filterLabels[4])){
                    adapter.filterByCategory(4);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
                spinner.setVisibility(View.VISIBLE);
                // Do something when collapsed
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                spinner.setVisibility(View.GONE);
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

//        switch (item.getItemId()) {
//            case R.id.option1:
//                Toast.makeText(getActivity(), "Option 1 selected", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.option2:
//                Toast.makeText(getActivity(), "Option 2 selected", Toast.LENGTH_SHORT).show();
//                return true;
//        }

        return super.onOptionsItemSelected(item);
    }
//    int mCurCheckPosition = 0;
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putInt("curChoice", mCurCheckPosition);
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        if (savedInstanceState != null) {
//            // Restore last state for checked position.
//            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
//        }
//    }

    private class SyncLocation extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            mDbHelper = new DatabaseHandler(getActivity());
            mDbHelper.syncLocation();
            mDbHelper.close();
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()) {
                pd.dismiss();
            }

        }


        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(getActivity());
            pd.setMessage("Loading");
            pd.setCancelable(false);
            pd.show();
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}








