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

    protected SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context mContext;
//    private List<User> allUsers;

    private UserDAO(Context context) {
        this.mContext = context;
    }

    @Override
    public void addUser(User user) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_USER_USERNAME, user.getUsername());
        values.put(DatabaseHelper.KEY_USER_FIRST_NAME, user.getFirstName());
        values.put(DatabaseHelper.KEY_USER_LAST_NAME, user.getLastName());
        values.put(DatabaseHelper.KEY_USER_EMAIL, user.getEmail());
        values.put(DatabaseHelper.KEY_USER_PASSWORD, user.getPassword());

        dbHelper = DatabaseHelper.getInstance(mContext);
        database = dbHelper.getWritableDatabase();

        user.setId(database.insert(DatabaseHelper.TABLE_USERS, null, values));
        database.close();

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
        Message.message(mContext, "returned the database information");
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
