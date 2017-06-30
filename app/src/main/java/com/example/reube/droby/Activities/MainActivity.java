package com.example.reube.droby.Activities;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.reube.droby.Database.DatabaseHandler;
import com.example.reube.droby.Fragments.MeFragment;
import com.example.reube.droby.Fragments.OutfitFragment;
import com.example.reube.droby.Fragments.Social.FashionFragment;
import com.example.reube.droby.Fragments.Social.FriendsFragment;
import com.example.reube.droby.Fragments.Social.TrendingFragment;
import com.example.reube.droby.Fragments.SocialFragment;
import com.example.reube.droby.Fragments.WardrobeFragment;
import com.example.reube.droby.R;
import com.example.reube.droby.Database.DatabaseUtilities;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity implements SocialFragment.OnFragmentInteractionListener, OutfitFragment.OnFragmentInteractionListener,WardrobeFragment.OnFragmentInteractionListener,MeFragment.OnFragmentInteractionListener,TrendingFragment.OnFragmentInteractionListener,FriendsFragment.OnFragmentInteractionListener,FashionFragment.OnFragmentInteractionListener{

    private TextView mTextMessage;
    ProgressDialog pd;
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
                    mTextMessage.setText("Social");
                    // Create fragment and give it an argument specifying the article it should show
                    SocialFragment socialFragment = new SocialFragment();
//                    Bundle args = new Bundle();
//                    args.putInt(SocialFragment.ARG_POSITION, position);
//                    newFragment.setArguments(args);



                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack so the user can navigate back
                    transaction.replace(R.id.fragment_container, socialFragment);
                    //transaction.addToBackStack(null);

                    // Commit the transaction
                    transaction.commit();
                    return true;
                case R.id.navigation_outfits:
                    mTextMessage.setText("Outfits");
                    OutfitFragment outfitFragment = new OutfitFragment();
//                    Bundle args = new Bundle();
//                    args.putInt(SocialFragment.ARG_POSITION, position);
//                    newFragment.setArguments(args);

                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack so the user can navigate back
                    transaction.replace(R.id.fragment_container, outfitFragment);
                    //transaction.addToBackStack(null);

                    // Commit the transaction
                    transaction.commit();
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    Intent intent=new Intent();
                    intent.setClass(MainActivity.this, SplashActivity.class);
                    startActivity(intent);
                    //MainActivity.this.finish();
                    return true;
                case R.id.navigation_wardrobe:
                    mTextMessage.setText("Wardrobe");
                    //TODO set to wardrobe activity
                    WardrobeFragment wardrobeFragment = new WardrobeFragment();
//                    Bundle args = new Bundle();
//                    args.putInt(SocialFragment.ARG_POSITION, position);
//                    newFragment.setArguments(args);

                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack so the user can navigate back
                    transaction.replace(R.id.fragment_container, wardrobeFragment);
                    //transaction.addToBackStack(null);

                    // Commit the transaction
                    transaction.commit();
                    return true;
                case R.id.navigation_me:
                    mTextMessage.setText("Me");
                    //TODO set to me profile screen
                    MeFragment meFragment = new MeFragment();
//                    Bundle args = new Bundle();
//                    args.putInt(SocialFragment.ARG_POSITION, position);
//                    newFragment.setArguments(args);

                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack so the user can navigate back
                    transaction.replace(R.id.fragment_container, meFragment);
                    //transaction.addToBackStack(null);

                    // Commit the transaction
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
        int userid = getIntent().getIntExtra("userid",0);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        disableShiftMode(navigation);

        new TestDatabase().execute("SELECT * FROM Category");

        FloatingActionButton addclothes_button = (FloatingActionButton) findViewById(R.id.addClothesButton);
        addclothes_button.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this, ClothesBasketActivity.class);
                startActivity(intent);
                //    SplashActivity.this.finish();
            }

        });

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

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
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
            System.out.print("Params is "+ params);
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

