package com.example.reube.droby.Activities;

import android.content.Intent;
import android.net.Uri;
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
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reube.droby.Database.Clothes;
import com.example.reube.droby.Database.DatabaseHandler;
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

public class MainActivity extends AppCompatActivity implements SocialFragment.OnFragmentInteractionListener, StylesFragment.OnFragmentInteractionListener,WardrobeFragment.OnFragmentInteractionListener,MeFragment.OnFragmentInteractionListener,TrendingFragment.OnFragmentInteractionListener,FriendsFragment.OnFragmentInteractionListener,FashionFragment.OnFragmentInteractionListener{

    private TextView mTextMessage;
    ProgressDialog pd;
    public static User user;
    public static int i1;
    public static int i2;
    public static int i3;
    public static ArrayList<Clothes> AllClothes = new ArrayList<Clothes>();


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
        String email = getIntent().getStringExtra("email");
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        user = db.getUser(email);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        disableShiftMode(navigation);


        db = new DatabaseHandler(this);
        AllClothes = db.getAllClothesTest();
        Random r = new Random();
        int min = 0;
        int max = AllClothes.size();
        i1 = r.nextInt(max - min) + min;
        i2 = r.nextInt(max - min) + min;
        i3 = r.nextInt(max - min) + min;


        //new TestDatabase().execute("SELECT * FROM Category");
//        FloatingActionButton addclothes_button = (FloatingActionButton) findViewById(R.id.addClothesButton);
//        addclothes_button.setOnClickListener(new Button.OnClickListener(){
//            public void onClick(View v) {
//                Intent intent=new Intent();
//                intent.setClass(MainActivity.this, ClothesBasketActivity.class);
//                startActivity(intent);
//                //    SplashActivity.this.finish();
//            }
//
//        });

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
    //IT WORKS!!!!!!
    private class TestDatabase extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            return DatabaseUtilities.getResult(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()) {
                pd.dismiss();
            }
            mTextMessage.setText(result);
        }


        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }
    }
}

