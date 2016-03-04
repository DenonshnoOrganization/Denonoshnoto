package com.example.chav.mapsproject;

import android.content.Context;

import com.example.chav.mapsproject.model_dao.UserDAO;

/**
 * Created by Chav on 3/4/2016.
 */
public class UserManager {


    static UserManager instance;

    private User mUser;
    private UserDAO mUserDAO;
    private Context mContext;
    private boolean isSignedIn;


    private UserManager(Context context) {
        this.mContext = context;
        this.mUserDAO = UserDAO.getInstance(context);
    }

    public boolean isSignedUp(String username, String password) {
        for (User user : mUserDAO.getAllUsers()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                isSignedIn = true;
                return true;
            }
        }
        return false;
    }

    public boolean isSignedUp(String usernameOrMail) {
        for (User user : mUserDAO.getAllUsers()) {
            if (user.getUsername().equals(usernameOrMail)) {
                isSignedIn = true;
                return true;
            }
            if (user.getEmail().equals(usernameOrMail)) {
                isSignedIn = true;
                return true;
            }
        }
        return false;
    }

    public void registerUser(User user) {
        mUserDAO.addUser(user);
        this.mUser = user;
        isSignedIn = true;
    }


    public static UserManager getInstance(Context context){
        if (instance == null) {
            instance = new UserManager(context);
        }

        return instance;
    }

    public boolean isSignedIn() {
        return isSignedIn;
    }

    public void setSignedIn(boolean signedIn) {
        isSignedIn = signedIn;
    }
}
