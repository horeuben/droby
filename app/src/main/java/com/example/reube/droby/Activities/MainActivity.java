package com.example.reube.droby.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reube.droby.Database.Clothes;
import com.example.reube.droby.Database.DatabaseHandler;
import com.example.reube.droby.Database.Tag;
import com.example.reube.droby.Database.User;
import com.example.reube.droby.Fragments.ClothesFragment;
import com.example.reube.droby.Fragments.MeFragment;
import com.example.reube.droby.Fragments.StylesFragment;
import com.example.reube.droby.Fragments.Social.FashionFragment;
import com.example.reube.droby.Fragments.Social.FriendsFragment;
import com.example.reube.droby.Fragments.Social.TrendingFragment;
import com.example.reube.droby.Fragments.SocialFragment;
import com.example.reube.droby.Fragments.WardrobeFragment;
import com.example.reube.droby.R;
import com.example.reube.droby.Database.DatabaseUtilities;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

import static android.R.attr.max;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements SocialFragment.OnFragmentInteractionListener, StylesFragment.OnFragmentInteractionListener,WardrobeFragment.OnFragmentInteractionListener,MeFragment.OnFragmentInteractionListener,TrendingFragment.OnFragmentInteractionListener,FriendsFragment.OnFragmentInteractionListener,FashionFragment.OnFragmentInteractionListener{

    private TextView mTextMessage;
    private DatabaseHandler db;
    public static User user;
    public static ArrayList<Clothes> AllClothes = new ArrayList<Clothes>();
    ProgressDialog pd;
    public static ArrayList<String> s1 = new ArrayList<String>();
    public static ArrayList<String> s2 = new ArrayList<String>();
    public static ArrayList<String> s3 = new ArrayList<String>();


    @Override
    public void onFragmentInteraction(Uri uri) {
        //you can leave it empty
    }
        private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_social:

                    SocialFragment socialFragment = new SocialFragment();

                    transaction.replace(R.id.fragment_container, socialFragment);

                    transaction.commit();

                    return true;

                case R.id.navigation_outfits:

                    StylesFragment stylesFragment = new StylesFragment();

                    transaction.replace(R.id.fragment_container, stylesFragment);

                    transaction.commit();

                    return true;

                case R.id.navigation_wardrobe:

                    ClothesFragment clothesFragment = new ClothesFragment();

                    transaction.replace(R.id.fragment_container,clothesFragment);

                    transaction.commit();

                    return true;

                case R.id.navigation_me:

                    MeFragment meFragment = new MeFragment();

                    transaction.replace(R.id.fragment_container, meFragment);
                    //transaction.addToBackStack(null);


                    transaction.commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Social");
        getSupportActionBar().setElevation(0);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        disableShiftMode(navigation);

        //has clothes that needs editing
        db = new DatabaseHandler(this);
        ArrayList<Clothes> editList = new ArrayList<Clothes>();
        editList = db.getAllClothes(user, checkIfClothesNeedEditing(user));
        if (editList.size() > 0) {

            for(int i=0;i<editList.size();i++){
                Intent editClothesIntent = new Intent(MainActivity.this, NewClothesActivity.class);
                editClothesIntent.putExtra("clothesID",editList.get(i).getId());
                startActivity(editClothesIntent);
            }
        }


        if (isNetworkAvailable()){
            new SyncColour().execute();
        }
        else{
            Toast.makeText(this,"No network! Unable to generate recommendation!",Toast.LENGTH_SHORT).show();
        }
//        SyncColour syncColour = new SyncColour();
//        if(syncColour.getStatus() == AsyncTask.Status.FINISHED){
//            new SyncColour().execute(2);
//            Toast.makeText(this,Integer.toString(s2.size()), Toast.LENGTH_SHORT).show();
//        }
//        if(syncColour.getStatus() == AsyncTask.Status.FINISHED){
//            new SyncColour().execute(3);
//        }


        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            SocialFragment firstFragment = new SocialFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            Intent intent = this.getIntent();

  /* Obtain String from Intent  */
            if(intent !=null)
            {
                String string = intent.getStringExtra("test");
                if(string== null ){
                    // Add the fragment to the 'fragment_container' FrameLayout
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragment_container, firstFragment).commit();
                }
                else if(string.equals("Style")){
                    StylesFragment newFragment = new StylesFragment();
                    // Add the fragment to the 'fragment_container' FrameLayout
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragment_container, newFragment).commit();
                    BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);
                    navigationView.setSelectedItemId(R.id.navigation_outfits);

                }

            }

        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent = new Intent(MainActivity.this, MainActivity.class);
//        startActivity(intent);
    }

    //Method to disable the bottom navigation view from screwing up haha
    private void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    protected ArrayList<String> checkIfClothesNeedEditing(User user){
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        ArrayList<Clothes> clothes = db.getAllClothes(user);
        ArrayList<String> clothes_ids = new ArrayList<>();
        for (int i = 0; i<clothes.size();i++){
            if (TextUtils.isEmpty(clothes.get(i).getDescription())){
                clothes_ids.add(Integer.toString(clothes.get(i).getId()));
            }
            // <-- use this part on first run to set all the tags and then remove after
            ArrayList<String> tags = db.getClothesTags(clothes.get(i));
            ArrayList<Tag> addTag = new ArrayList<>();
            for(int j =0;j<tags.size();j++){
                Tag tag = new Tag();
                tag.setClothes_id(clothes.get(i).getId());
                tag.setName(tags.get(j));
                tag.setUser_id(user.getId());
                addTag.add(tag);
            }
            clothes.get(i).setTags(addTag);
            db.updateClothes(clothes.get(i));
            // until here --> //
        }
        return clothes_ids;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private class SyncColour extends AsyncTask<Integer, String, ArrayList<ArrayList<String>>> {
        int type;
        @Override
        protected ArrayList<ArrayList<String>> doInBackground(Integer... params) {
            db = new DatabaseHandler(MainActivity.this);
            ArrayList<ArrayList<String>> result = new ArrayList<>();
            for(int i = 0;i<3;i++){
                result.add(db.syncColour(-1));
            }
//            type = params[0];
            db.close();
            return result;
        }


        @Override
        protected void onPostExecute(ArrayList<ArrayList<String>> result) {

            super.onPostExecute(result);
            if (pd.isShowing()) {
                pd.dismiss();
            }
            s1.addAll(result.get(0));
            s2.addAll(result.get(1));
            s3.addAll(result.get(2));
        }


        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Loading");
            pd.setCancelable(false);
            pd.show();
        }
    }
}

