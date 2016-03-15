package com.example.chav.mapsproject;

import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.chav.mapsproject.model_dao.UserDAO;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.util.List;

/**
 * Created by Chav on 3/4/2016.
 */
public class UserManager {

    public static int FRIEND_FOUND = 1;
    public static int FRIEND_ALREADY = 0;
    public static int FRIEND_NOT_FOUND = -1;

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
        mUserDAO.addUser(user);
        user.setId(mUserDAO.getID(user));
        this.mCurrentUser = user;
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

    public int findFriend(String username, Context context) {
        for (User user : mAllUsers) {
            if (user.getUsername().equals(username)) {
                if (mCurrentUser.getFriends().contains(user.getId())) {
                    int notificationId = 1;
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.add_user)
                            .setLargeIcon(BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.add_user))
                            .setContentTitle("Add Friend")
                            .setContentText("Your friend request to " + username + " has been set successfully.");

                    NotificationManager mNotifyMgr =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotifyMgr.notify(notificationId, builder.build());

                    return FRIEND_ALREADY;
                }
                return FRIEND_FOUND;
            }
        }

        return FRIEND_NOT_FOUND;
    }

}
