package com.example.arc.androidhomweorkonetaskone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Chav on 2/21/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    //Database name and version
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "denonoshnotoDatabase";

    //Tables names
    private static final String TABLE_USERS = "user";
    private static final String TABLE_FAVORITE_SPOTS = "favorite_spots";
    private static final String TABLE_COMMENTS = "comments";
    private static final String TABLE_REPORTS = "reports";
    private static final String TABLE_SPOTS = "spots";
    private static final String TABLE_FRIENDS = "friends";

    //Common column names
    private static final String KEY_ID = "_id";
    private static final String KEY_CREATED_AT = "Created_At";

    //USERS table column names
    private static final String KEY_USER_USERNAME = "Username";
    private static final String KEY_USER_FIRST_NAME = "First_Name";
    private static final String KEY_USER_LAST_NAME = "Last_Name";
    private static final String KEY_USER_EMAIL = "Email";
    private static final String KEY_USER_PASSWORD = "Password";

    //SPOTS table column names
    private static final String KEY_SPOT_COORDINATES = "Coordinates";
    private static final String KEY_SPOT_NAME = "Name";
    private static final String KEY_SPOT_RATE = "Rate";
    private static final String KEY_SPOT_USER_ID = "_user_id";

    //COMMENTS table column names
    private static final String KEY_COMMENT_DESCRIPTION = "Description";
    private static final String KEY_COMMENT_DATE = "Date";
    private static final String KEY_COMMENT_USER_ID = "_user_id";
    private static final String KEY_COMMENT_SPOT_ID = "_spot_id";

    //REPORTS table column names
    private static final String KEY_REPORT_DESCRIPTION = "Description";
    private static final String KEY_REPORT_DATE = "Date";
    private static final String KEY_REPORT_USER_ID = "_user_id";
    private static final String KEY_REPORT_SPOT_ID = "_spot_id";

    //FAVORITE_SPOTS table column names
    private static final String KEY_FAVORITE_USER_ID = "_user_id";
    private static final String KEY_FAVORITE_SPOT_ID = "_spot_id";

    //FRIENDS table column names
    private static final String KEY_FRIEND_USER_ID = "_user_id";
    private static final String KEY_FRIEND_FRIEND_ID = "_friend_id";

    //Table create statements
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USER_USERNAME + " VARCHAR(15) NOT NULL UNIQUE," +
            KEY_USER_FIRST_NAME + " VARCHAR(15)," + KEY_USER_LAST_NAME + " VARCHAR(15),"
            + KEY_USER_EMAIL + " TEXT UNIQUE" + KEY_USER_PASSWORD + " TEXT UNIQUE" + ")";

    private static final String CREATE_TABLE_SPOTS = "CREATE TABLE " + TABLE_SPOTS
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_SPOT_NAME + " VARCHAR(15),"
            + KEY_SPOT_COORDINATES + " TEXT," + KEY_SPOT_RATE + " INTEGER," + KEY_CREATED_AT + " DATETIME," + KEY_SPOT_USER_ID
            + " INTEGER" + "FOREIGN KEY("+KEY_SPOT_USER_ID+") REFERENCES "+TABLE_USERS+"("+KEY_ID+")" + ")";

    private static final String CREATE_TABLE_COMMENTS = "CREATE TABLE " + TABLE_COMMENTS
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_COMMENT_DESCRIPTION + " VARCHAR(100),"
            + KEY_COMMENT_DATE + " DATETIME," + KEY_COMMENT_USER_ID + " INTEGER,"
            + KEY_COMMENT_SPOT_ID + " INTEGER" + "FOREIGN KEY("+KEY_COMMENT_USER_ID+") REFERENCES "+TABLE_USERS+"("+KEY_ID+")" + ")";

    private static final String CREATE_TABLE_REPORTS = "CREATE TABLE " + TABLE_REPORTS
            +  "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +  KEY_REPORT_DESCRIPTION + " VARCHAR(100),"
            + KEY_REPORT_DATE + " DATETIME," + KEY_REPORT_USER_ID + " INTEGER," +  KEY_REPORT_SPOT_ID
            + " INTEGER" + "FOREIGN KEY("+KEY_REPORT_USER_ID+") REFERENCES "+TABLE_USERS+"("+KEY_ID+")"
            + "FOREIGN KEY("+KEY_REPORT_SPOT_ID+") REFERENCES "+TABLE_SPOTS+"("+KEY_ID+")" + ")";

    private static final String CREATE_TABLE_FRIENDS = "CREATE TABLE " + TABLE_FRIENDS
            + "(" + KEY_FRIEND_USER_ID + " INTEGER PRIMARY KEY," + KEY_FRIEND_FRIEND_ID + " INTEGER"
            + "FOREIGN KEY("+KEY_FRIEND_USER_ID+") REFERENCES "+TABLE_USERS+"("+KEY_ID+")"
            + "FOREIGN KEY("+KEY_FRIEND_FRIEND_ID+") REFERENCES "+TABLE_USERS+"("+KEY_ID+")" + ")";

    private static final String CREATE_TABLE_FAVORITE_SPOTS = "CREATE TABLE " + TABLE_FAVORITE_SPOTS
        + "(" + KEY_FAVORITE_USER_ID + " INTEGER PRIMARY KEY," + KEY_FAVORITE_SPOT_ID  + " INTEGER"
            + "FOREIGN KEY("+KEY_FAVORITE_USER_ID+") REFERENCES "+TABLE_USERS+"("+KEY_ID+")"
            + "FOREIGN KEY("+KEY_FAVORITE_SPOT_ID+") REFERENCES "+TABLE_SPOTS+"("+KEY_ID+")" + ")";

    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        Message.message(context, "Constructor called");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_SPOTS);
        db.execSQL(CREATE_TABLE_COMMENTS);
        db.execSQL(CREATE_TABLE_REPORTS);
        db.execSQL(CREATE_TABLE_FRIENDS);
        db.execSQL(CREATE_TABLE_FAVORITE_SPOTS);
        Message.message(context, "On create called");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPOTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPORTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIENDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE_SPOTS);
        Message.message(context, "On update called");

        onCreate(db);
    }

    //Creating a user
    public long createUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_USERNAME, user.getUsername());
        values.put(KEY_USER_FIRST_NAME, user.getFirstName());
        values.put(KEY_USER_LAST_NAME, user.getLastName());
        values.put(KEY_USER_PASSWORD, user.getPassword());
        values.put(KEY_USER_EMAIL, user.getEmail());

        //insert row
        long user_id = db.insert(TABLE_USERS, KEY_USER_USERNAME, values);

        return user_id;
    }

    public long getUser(User user){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectKey = "SELECT *FROM " + TABLE_USERS + " WHERE " + KEY_USER_USERNAME + " = " + user.getUsername();

        Cursor c = db.rawQuery(selectKey, null);

        //TODO vij kak da vzemem id`to

        long user_id = -1;

        if (c != null) {
            c.moveToFirst();
            user_id = c.getColumnIndex(KEY_ID);
        }

        return user_id;

    }

    //Creating a Spot
    public long createSpot(Spot spot, User user) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SPOT_NAME, spot.getName());
        values.put(KEY_SPOT_COORDINATES, spot.getCoordinates();
        values.put(KEY_SPOT_RATE, spot.getRate());
        values.put(KEY_CREATED_AT, String.valueOf(spot.getDateOfCreation()));
        values.put(KEY_SPOT_USER_ID, user.getUserID());

       long spot_id =  db.insert(TABLE_SPOTS, KEY_SPOT_NAME, values);

        return spot_id;
    }

    public long createReport(Report report, Spot spot, User user){

        //TODO define how to get spotID and userID
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_REPORT_DATE, String.valueOf(report.getLocalDate()));
        values.put(KEY_REPORT_DESCRIPTION, report.getText());
        values.put(KEY_REPORT_SPOT_ID, spot.getSpotID());
        values.put(KEY_REPORT_DATE, user.getUserID());

        long report_id = db.insert(TABLE_REPORTS, KEY_REPORT_DESCRIPTION, values);

        return report_id;

    }

    public long createComment(Comment comment, User user, Spot spot){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_COMMENT_DATE, String.valueOf(comment.getDateOfCreation()));
        values.put(KEY_COMMENT_DESCRIPTION, comment.getDescription());
        values.put(KEY_COMMENT_SPOT_ID,spot.getSpotID());
        values.put(KEY_COMMENT_USER_ID,user.getUserID());

        long comment_id = db.insert(TABLE_COMMENTS, KEY_COMMENT_DESCRIPTION, values);

        return comment_id;

    }

    public long createFriend(User user, User friend){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FRIEND_USER_ID, user.getUserID());
        values.put(KEY_FRIEND_FRIEND_ID, friend.getUserID());

        long friend_id = db.insert(TABLE_FRIENDS, KEY_FRIEND_FRIEND_ID, values);
        return friend_id;

    }

    public long createFavorite(User user, Spot spot){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FAVORITE_USER_ID, this.getUser(user);
        values.put(KEY_FAVORITE_SPOT_ID, spot.getSpotID());

        long favorite_id = db.insert(TABLE_FAVORITE_SPOTS, null, values);
        return favorite_id;

    }

}
