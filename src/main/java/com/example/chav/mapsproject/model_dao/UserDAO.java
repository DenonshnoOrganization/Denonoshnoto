package com.example.chav.mapsproject.model_dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.chav.mapsproject.Message;
import com.example.chav.mapsproject.User;
import com.example.chav.mapsproject.model_db.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;


public class UserDAO implements IUserDAO {

    public static UserDAO instance;
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context mContext;


    private UserDAO(Context context) {
        this.mContext = context;
    }

    @Override
    public User addUser(User user) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_USER_USERNAME, user.getUsername());
        values.put(DatabaseHelper.KEY_USER_FIRST_NAME, user.getFirstName());
        values.put(DatabaseHelper.KEY_USER_LAST_NAME, user.getLastName());
        values.put(DatabaseHelper.KEY_USER_EMAIL, user.getEmail());
        values.put(DatabaseHelper.KEY_USER_PASSWORD, user.getPassword());

        dbHelper = DatabaseHelper.getInstance(mContext);
        database = dbHelper.getWritableDatabase();

        database.insert(DatabaseHelper.TABLE_USERS, null, values);
        long lastId = 0;
        String selectQuery = " SELECT " + DatabaseHelper.KEY_ID + " FROM " + DatabaseHelper.TABLE_USERS + " ORDER BY " + DatabaseHelper.KEY_ID + " DESC LIMIT 1";
        Cursor c = database.rawQuery(selectQuery, null);
        if (c != null && c.moveToFirst()) {
            lastId = c.getLong(0);
            user.setId(lastId);
            Log.d("asd", " user added id " + lastId);
        }
        database.close();

//        Log.d("user", "returning " + user.getId());
//        Log.d("user", "returning " + user.getUsername());

        return user;

    }

    @Override
    public User getUser(int id) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        String selectQuery = " SELECT * FROM " + DatabaseHelper.TABLE_USERS;
        database = dbHelper.getInstance(mContext).getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        Log.d("chavdar", "we got here");
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(0);
                String username = cursor.getString(1);
                String firstName = cursor.getString(2);
                String lastName = cursor.getString(3);
                String eMail = cursor.getString(4);
                String password = cursor.getString(5);

                User user = new User(id, username, firstName, lastName, eMail, password);
                allUsers.add(user);
            } while (cursor.moveToNext());
        }
        database.close();
//        Message.message(mContext, "returned the database information");
        return allUsers;
    }



    @Override
    public void deleteUser() {

    }

    public static UserDAO getInstance(Context context) {
        if (instance == null) {
            instance = new UserDAO(context);
        }

        return instance;
    }
}
