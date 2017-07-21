package com.example.reube.droby.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.reube.droby.Database.DatabaseHandler;
import com.example.reube.droby.Database.User;
import com.example.reube.droby.R;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        final EditText nickname = (EditText)findViewById(R.id.nicknameText);
        final EditText email = (EditText)findViewById(R.id.signupEmailText);
        final EditText password = (EditText)findViewById(R.id.signupPasswordText);
        final EditText password2 = (EditText)findViewById(R.id.signupPassword2Text);

        Button signupButton = (Button) findViewById(R.id.signupButton);
        signupButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                if(password.getText().toString().equals(password2.getText().toString())){
                    //TODO: logic to query database to see if user is valid, and then set current User to this login id, before changing new screen for signup
                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                    if (db.getUser(email.getText().toString())==null){
                        User user = new User();
                        user.setEmail(email.getText().toString());
                        user.setNickname(nickname.getText().toString());
                        user.setPassword(password.getText().toString());
                        int id = (int)db.createUser(user);
                        user.setId(id);
                        Toast.makeText(getApplicationContext(),"User created! " ,Toast.LENGTH_SHORT).show();
                        MainActivity.user = user;
                        Intent intent=new Intent();
                        //intent.putExtra("email", email.getText().toString());
                        intent.setClass(SignupActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(getApplicationContext(),"User exists!",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"Password not matched!",Toast.LENGTH_SHORT).show();


            }

        });
    }

}
