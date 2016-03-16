package com.example.chav.mapsproject;

import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.chav.mapsproject.model_dao.UserDAO;

import java.util.List;

/**
 * Created by Chav on 3/4/2016.
 */
public class UserManager {

    static UserManager instance;

    private User mCurrentUser;
    private UserDAO mUserDAO;
    private Context mContext;
    private boolean mIsSignedIn;
    private List<User> mAllUsers;


    private UserManager(Context context) {
        this.mContext = context;
        this.mUserDAO = UserDAO.getInstance(context);
        mAllUsers = mUserDAO.getAllUsers();
    }

    public boolean isSignedUp(String username, String password) {
        for (User user : mAllUsers) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                mCurrentUser = user;
                mIsSignedIn = true;
                return true;
            }
        }
        return false;
    }

    public boolean isSignedUp(String usernameOrMail) {
        for (User user : mUserDAO.getAllUsers()) {
            if (user.getUsername().equals(usernameOrMail)) {
                mIsSignedIn = true;
                return true;
            }
            if (user.getEmail().equals(usernameOrMail)) {
                mIsSignedIn = true;
                return true;
            }
        }
        return false;
    }

    public void registerUser(User user) {
        this.mCurrentUser = mUserDAO.addUser(user);
        mAllUsers.add(user);
        Log.d("user", "" + user.getId());
        Log.d("user", "" + user.getUsername());
        mIsSignedIn = true;
    }

    public User getUser() {
        return mCurrentUser;
    }


    public void setCurrentUser(String username) {
        for (User user : mAllUsers) {
            if (user.getUsername().equals(username)) {
                this.mCurrentUser = user;
                mIsSignedIn = true;
            }
        }
    }

    public static UserManager getInstance(Context context){
        if (instance == null) {
            instance = new UserManager(context);
        }

        return instance;
    }

    public void findFriend(String username, Context context) {
        if (username.equals(mCurrentUser.getUsername())) {
            Message.message(context, "Adding yourself as a friend is forbidden in this app`s world.");
            return;
        }
        for (User user : mAllUsers) {
            if (user.getUsername().equals(username)) {
                if (mCurrentUser.getFriends().contains(user.getId())) {
                    Message.message(context, "You are already friends with " + username + ".");
                    return;
                } else {
                    int notificationId = 1;
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.main_button_small)
                            .setLargeIcon(BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.main_button_mid))
                            .setContentTitle("Add Friend")
                            .setContentText("Your friend request to " + username + " has been sent.");

                    NotificationManager mNotifyMgr =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotifyMgr.notify(notificationId, builder.build());
                    return;
                }

            }
        }
        Message.message(context, username + " does not exist.");
    }

}
