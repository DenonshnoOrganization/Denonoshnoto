package com.example.chav.mapsproject.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chav.mapsproject.Message;
import com.example.chav.mapsproject.R;
import com.example.chav.mapsproject.User;
import com.example.chav.mapsproject.UserManager;

public class RegisterActivity extends AppCompatActivity {

    EditText mUserNameEditText;
    EditText mFirstNameEditText;
    EditText mLastNameEditText;
    EditText mEmailEditText;
    EditText mPasswordEditText;
    EditText mConfirmPasswordEditText;

    Button mCancelButton;
    Button mSubmitButton;
    UserManager mUserManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUserManager = UserManager.getInstance(this);
        mUserNameEditText = (EditText) findViewById(R.id.username_edit_text);
        mFirstNameEditText = (EditText) findViewById(R.id.first_name_edit_text);
        mLastNameEditText = (EditText) findViewById(R.id.last_name_edit_text);
        mEmailEditText = (EditText) findViewById(R.id.e_mail_edit_text);
        mPasswordEditText = (EditText) findViewById(R.id.password_edit_text);
        mConfirmPasswordEditText = (EditText) findViewById(R.id.confirm_password_edit_text);


        class Text implements TextWatcher {

            EditText mEditText;

            Text(EditText editText) {
                this.mEditText = editText;
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEditText.setError(null);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        }

        new Text(mUserNameEditText);
        new Text(mPasswordEditText);
        new Text(mFirstNameEditText);
        new Text(mLastNameEditText);
        new Text(mPasswordEditText);
        new Text(mConfirmPasswordEditText);

        mCancelButton = (Button) findViewById(R.id.cancel_button);
        mSubmitButton = (Button) findViewById(R.id.submit_button);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = mUserNameEditText.getText().toString();
                String first = mFirstNameEditText.getText().toString();
                String last = mLastNameEditText.getText().toString();
                String email = mEmailEditText.getText().toString();
                String pass = mPasswordEditText.getText().toString();
                String confirmPass = mConfirmPasswordEditText.getText().toString();

                if (isLegalUsername(username)
                        && isLegalFirstName(first)
                        && isLegalLastName(last)
                        && isLegalEMail(email)
                        && isLegalPassword(pass, confirmPass)){

                    User user = new User(username, first, last, email, pass);
                    mUserManager.registerUser(user);
                    Message.message(v.getContext(), "Hello, " + username);
                    SavedSharedPreference.setUserName(v.getContext(), username);
                    Intent i = new Intent(v.getContext(), MapsActivity.class);
                    startActivity(i);
                }

            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), LoginActivity.class);
                startActivity(i);
            }
        });

    }

    private boolean isLegalPassword(String pass, String confirmPass) {


        if (!pass.matches(".*[A-Z].*")) {
            mPasswordEditText.setError("password must contain a small letter, a capital letter, a number and a special symbol");
            return false;
        }

        if (!pass.matches(".*[a-z].*")) {
            mPasswordEditText.setError("password must contain a small letter, a capital letter, a number and a special symbol");
            return false;
        }

        if (!pass.matches(".*\\d.*")) {
            mPasswordEditText.setError("password must contain a small letter, a capital letter, a number and a special symbol");
            return false;
        }

        if (!pass.matches(".*[`~!@#$%^&*()-_=+\\|[{]};:'\",<.>/?].*")) {
            mPasswordEditText.setError("password must contain a small letter, a capital letter, a number and a special symbol");
            return false;
        }

        if (!pass.equals(confirmPass)) {
            mPasswordEditText.setError("both passwords should match");
            return false;
        }

        return true;

    }

    private boolean isLegalUsername (String username) {
        if (username.trim().length() < 3) {
            mUserNameEditText.setError("username must be at least 3 characters in length");
            return false;
        }

        boolean userExists =(mUserManager.isSignedUp(username));
        if (userExists) {
            mUserNameEditText.setError("username already exists");
            return false;
        }

        Message.message(this, "We returned if the user existse");
        return true;

    }

    private boolean isLegalFirstName(String firstName) {

        if (firstName.length() == 0) {
            Message.message(this, "We got to first name");
            mFirstNameEditText.setError("first name is required");
            return false;
        }

        return true;

    }

    private boolean isLegalLastName(String lastName) {

        if (lastName.trim().length() == 0) {
            Message.message(this, "We got to last name");
            mLastNameEditText.setError("last name is required");
            return false;
        }

        return true;

    }

    private boolean isLegalEMail(String eMail){

        if (!eMail.contains("@") || !eMail.contains(".") || eMail.trim().length() < 8) {
            Message.message(this, "We got to email");
            mEmailEditText.setError("incorrect email entry");
            return false;
        }


        boolean userExists =(mUserManager.isSignedUp(eMail));
        if (userExists) {
            mEmailEditText.setError("user with that e-mail already exists");
            return false;
        }

//        return userExists;
//        UserDAO ud = new UserDAO(this);
//        for (User user : ud.getAllUsers() ) {
//            if (user.getEmail().equals(eMail)) {
//                mEmailEditText.setError("user with that e-mail already exists");
//                return false;
//            }
//        }
//
        return true;
    }

}
