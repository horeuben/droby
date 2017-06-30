package com.example.android.drobyapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_wardrobe:
                    ClothesFragment clothesFragment = new ClothesFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, clothesFragment).commit();
                    return true;
                case R.id.navigation_social:
                    SocialFragment socialFragment = new SocialFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, socialFragment).commit();
                    //mTextMessage.setText(R.string.title_social);
                    return true;
                case R.id.navigation_styles:
                    StylesFragment stylesFragment=new StylesFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content,stylesFragment).commit();
                    return true;
                case R.id.navigation_me:
                    MeFragment meFragment = new MeFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, meFragment).commit();
                    //mTextMessage.setText(R.string.title_me);
                    return true;
            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ClothesFragment clothesFragment = new ClothesFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content, clothesFragment).commit();

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        disableShiftMode(navigation);

    }

    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }
}


