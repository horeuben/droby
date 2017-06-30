package com.example.reube.droby.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.reube.droby.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity {
    //should do syncing here instead of main activity i think
    private View mContentView;



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
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls

    }




}
