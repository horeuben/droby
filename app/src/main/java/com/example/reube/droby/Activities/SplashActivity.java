package com.example.reube.droby.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.reube.droby.Database.Clothes;
import com.example.reube.droby.Database.DatabaseHandler;
import com.example.reube.droby.Database.DatabaseUtilities;
import com.example.reube.droby.Database.User;
import com.example.reube.droby.R;

import java.util.ArrayList;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity {
    //should do syncing here instead of main activity i think
    private View mContentView;
    private DatabaseHandler db;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        Button login_button = (Button)findViewById(R.id.loginbutton);
        login_button.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                //SplashActivity.this.finish();
            }

        });
        Button signup_button = (Button)findViewById(R.id.signupbutton);
        signup_button.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(SplashActivity.this, SignupActivity.class);
                startActivity(intent);
            //    SplashActivity.this.finish();
            }

        });
        //Check if there's internet connection. if there is, do sync, else just resume from app
        if (isNetworkAvailable()){
//            new SyncDatabase().execute();

        }
    }
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent = new Intent(MainActivity.this, MainActivity.class);
//        startActivity(intent);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    // Class is supposed to sync all tables, but since we dont have time, just sync clothes will do
    private class SyncDatabase extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            db = new DatabaseHandler(getApplicationContext());
            //db.syncClothes();
            db.syncClothesTags();
            db.close();
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

            pd = new ProgressDialog(SplashActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }
    }


}
