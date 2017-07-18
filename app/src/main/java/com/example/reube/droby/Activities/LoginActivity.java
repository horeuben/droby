package com.example.reube.droby.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.reube.droby.Database.Clothes;
import com.example.reube.droby.Database.DatabaseHandler;
import com.example.reube.droby.Database.User;
import com.example.reube.droby.R;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText email = (EditText)findViewById(R.id.emailText);
        final EditText password = (EditText)findViewById(R.id.loginPasswordText);
        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                //TODO: logic to query database to see if user is valid, and then set current User to this login id, before changing new screen for login
                db = new DatabaseHandler(getApplicationContext());
                User user = db.getUser(email.getText().toString(),password.getText().toString());
                if (user!=null){
                    //login successfully!
                    Toast.makeText(getApplicationContext(),"User "+ user.getNickname()+ " has logged in!",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent();
                    //intent.putExtra("email",user.getEmail());
                    MainActivity.user = user;
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
                else{
                    Toast.makeText(getApplicationContext(),"User/Password incorrect!",Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

}
