package com.example.chav.mapsproject.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chav.mapsproject.Message;
import com.example.chav.mapsproject.R;
import com.example.chav.mapsproject.User;
import com.example.chav.mapsproject.UserManager;
import com.example.chav.mapsproject.model_dao.UserDAO;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    UserManager mUserManager;
    EditText userNameText;
    EditText userPassword;
    Button signIn;
    Button signUp;
    Button skip;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameText = (EditText) findViewById(R.id.username_edit_text);
        userPassword = (EditText) findViewById(R.id.password_edit_text);
        signIn = (Button) findViewById(R.id.sign_in_button);
        signUp = (Button) findViewById(R.id.sign_up_button);
        skip = (Button) findViewById(R.id.skip_button);
        mUserManager = UserManager.getInstance(this);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userNameText.getText().toString();
                String password = userPassword.getText().toString();

                boolean existsUser = mUserManager.isSignedUp(username, password);

                if (existsUser) {
                    Message.message(v.getContext(), "Hello, " +username);

                    Intent i = new Intent(v.getContext(), MapsActivity.class);
                    startActivity(i);
                } else {
                    Message.message(v.getContext(), "Incorrect username or password");
                }
                userNameText.setText("");
                userPassword.setText("");
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), RegisterActivity.class);
                startActivity(i);
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), MapsActivity.class);
                startActivity(i);
            }
        });

    }
}
