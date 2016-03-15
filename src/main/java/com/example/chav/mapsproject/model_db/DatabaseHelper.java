package com.example.chav.mapsproject.model_db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.chav.mapsproject.Message;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Database name and version
    private static final int DATABASE_VERSION = 13;
    private static final String DATABASE_NAME = "denonoshnotoDatabase";

    //Tables names
    public  static final String TABLE_USERS = "user";
    public  static final String TABLE_FAVORITE_SPOTS = "favorite_spots";
    public  static final String TABLE_COMMENTS = "comments";
    public  static final String TABLE_REPORTS = "reports";
    public  static final String TABLE_SPOTS = "spots";
    public  static final String TABLE_FRIENDS = "friends";

    //Common column names
    public  static final String KEY_ID = "_id";
    public  static final String KEY_CREATED_AT = "Created_At";

    //USERS table column names
    public  static final String KEY_USER_USERNAME = "Username";
    public  static final String KEY_USER_FIRST_NAME = "First_Name";
    public  static final String KEY_USER_LAST_NAME = "Last_Name";
    public  static final String KEY_USER_EMAIL = "Email";
    public  static final String KEY_USER_PASSWORD = "Password";

    //SPOTS table column names
    public  static final String KEY_SPOT_NAME = "Name";
    public  static final String KEY_SPOT_RATE = "Rate";
    public  static final String KEY_SPOT_USER_ID = "_user_id";
    public static final String KEY_SPOT_LAT = "latitude";
    public static final String KEY_SPOT_LONG = "longitude";
    public static final String KEY_SPOT_IMAGE = "image";

    //COMMENTS table column names
    public  static final String KEY_COMMENT_DESCRIPTION = "Description";
    public  static final String KEY_COMMENT_DATE = "Date";
    public  static final String KEY_COMMENT_USER_ID = "_user_id";
    public  static final String KEY_COMMENT_SPOT_ID = "_spot_id";

    //REPORTS table column names
    public  static final String KEY_REPORT_DESCRIPTION = "Description";
    public  static final String KEY_REPORT_DATE = "Date";
    public  static final String KEY_REPORT_USER_ID = "_user_id";
    public  static final String KEY_REPORT_SPOT_ID = "_spot_id";

    //FAVORITE_SPOTS table column names
    public  static final String KEY_FAVORITE_USER_ID = "_user_id";
    public  static final String KEY_FAVORITE_SPOT_ID = "_spot_id";

    //FRIENDS table column names
    public  static final String KEY_FRIEND_USER_ID = "_user_id";
    public  static final String KEY_FRIEND_FRIEND_ID = "_friend_id";

    //Table create statements
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + KEY_USER_USERNAME + " VARCHAR(15) NOT NULL UNIQUE," +
            KEY_USER_FIRST_NAME + " VARCHAR(100)," + KEY_USER_LAST_NAME + " VARCHAR(100),"
            + KEY_USER_EMAIL + " TEXT UNIQUE," + KEY_USER_PASSWORD + " TEXT UNIQUE" + ")";

    private static final String CREATE_TABLE_SPOTS = "CREATE TABLE " + TABLE_SPOTS
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_SPOT_NAME + " VARCHAR(15),"
            + KEY_SPOT_RATE + " INTEGER," + KEY_SPOT_LAT + " DOUBLE," + KEY_SPOT_LONG + " DOUBLE,"
            + KEY_CREATED_AT + " DATETIME," + KEY_SPOT_USER_ID
            + " INTEGER" + " REFERENCES "+TABLE_USERS+"("+KEY_ID+")"
            + ", " + KEY_SPOT_IMAGE + " INTEGER" + ")";

    private static final String CREATE_TABLE_COMMENTS = "CREATE TABLE " + TABLE_COMMENTS
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_COMMENT_DESCRIPTION + " VARCHAR(100),"
            + KEY_COMMENT_DATE + " DATETIME," + KEY_COMMENT_USER_ID + " INTEGER,"
            + KEY_COMMENT_SPOT_ID + " INTEGER" + " REFERENCES "+TABLE_USERS+"("+KEY_ID+")" + ")";

    private static final String CREATE_TABLE_REPORTS = "CREATE TABLE " + TABLE_REPORTS
            +  "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +  KEY_REPORT_DESCRIPTION + " VARCHAR(100),"
            + KEY_REPORT_DATE + " DATETIME," + KEY_REPORT_USER_ID + " INTEGER " +  " REFERENCES "+TABLE_USERS+"("+KEY_ID+"),"
            +  KEY_REPORT_SPOT_ID  + " INTEGER" + " REFERENCES "+TABLE_SPOTS+"("+KEY_ID+")" + ")";

    private static final String CREATE_TABLE_FRIENDS = "CREATE TABLE " + TABLE_FRIENDS
            + "(" + KEY_FRIEND_USER_ID + " INTEGER PRIMARY KEY REFERENCES "+TABLE_USERS+"("+KEY_ID+"),"
            + KEY_FRIEND_FRIEND_ID + " INTEGER REFERENCES "+TABLE_USERS+"("+KEY_ID+")" + ")";


    private static final String CREATE_TABLE_FAVORITE_SPOTS = "CREATE TABLE " + TABLE_FAVORITE_SPOTS
        + "(" + KEY_FAVORITE_USER_ID + " INTEGER PRIMARY KEY REFERENCES "+TABLE_USERS+"("+KEY_ID+"),"
            + KEY_FAVORITE_SPOT_ID  + " INTEGER REFERENCES "+TABLE_SPOTS+"("+KEY_ID+")" + ")";

    private Context context;

    private static DatabaseHelper instance;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        Message.message(context, "Constructor called");

    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
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

//    //Creating a user
//    public long createUser(User user){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(KEY_USER_USERNAME, user.getUsername());
//        values.put(KEY_USER_FIRST_NAME, user.getFirstName());
//        values.put(KEY_USER_LAST_NAME, user.getLastName());
//        values.put(KEY_USER_PASSWORD, user.getPassword());
//        values.put(KEY_USER_EMAIL, user.getEmail());
//
//        //insert row
//        long user_id = db.insert(TABLE_USERS, KEY_USER_USERNAME, values);
//
//        return user_id;
//    }
//
//    public long getUser(User user){
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selectKey = "SELECT *FROM " + TABLE_USERS + " WHERE " + KEY_USER_USERNAME + " = " + user.getUsername();
//
//        Cursor c = db.rawQuery(selectKey, null);
//
//
//        long user_id = -1;
//
//        if (c != null) {
//            c.moveToFirst();
//            user_id = c.getColumnIndex(KEY_ID);
//        }
//
//        return user_id;
//
//    }

    //Creating a Spot
//    public long createSpot(Spot spot, User user) {
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(KEY_SPOT_NAME, spot.getName());
//        values.put(KEY_SPOT_COORDINATES, spot.getCoordinates());
//        values.put(KEY_SPOT_RATE, spot.getRate());
//        values.put(KEY_CREATED_AT, String.valueOf(spot.getDateOfCreation()));
//        values.put(KEY_SPOT_USER_ID, user.getUserID());
//
//       long spot_id =  db.insert(TABLE_SPOTS, KEY_SPOT_NAME, values);
//
//        return spot_id;
//    }

//    public long createReport(Report report, Spot spot, User user){
//
//        //TODO define how to get spotID and userID
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(KEY_REPORT_DATE, String.valueOf(report.getLocalDate()));
//        values.put(KEY_REPORT_DESCRIPTION, report.getText());
//        values.put(KEY_REPORT_SPOT_ID, spot.getSpotID());
//        values.put(KEY_REPORT_DATE, user.getUserID());
//
//        long report_id = db.insert(TABLE_REPORTS, KEY_REPORT_DESCRIPTION, values);
//
//        return report_id;
//
//    }
//
//    public long createComment(Comment comment, User user, Spot spot){
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(KEY_COMMENT_DATE, String.valueOf(comment.getDateOfCreation()));
//        values.put(KEY_COMMENT_DESCRIPTION, comment.getDescription());
//        values.put(KEY_COMMENT_SPOT_ID,spot.getSpotID());
//        values.put(KEY_COMMENT_USER_ID,user.getUserID());
//
//        long comment_id = db.insert(TABLE_COMMENTS, KEY_COMMENT_DESCRIPTION, values);
//
//        return comment_id;
//
//    }
//
//    public long createFriend(User user, User friend){
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(KEY_FRIEND_USER_ID, user.getUserID());
//        values.put(KEY_FRIEND_FRIEND_ID, friend.getUserID());
//
//        long friend_id = db.insert(TABLE_FRIENDS, KEY_FRIEND_FRIEND_ID, values);
//        return friend_id;
//
//    }
//
//    public long createFavorite(User user, Spot spot){
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(KEY_FAVORITE_USER_ID, this.getUser(user));
//        values.put(KEY_FAVORITE_SPOT_ID, spot.getSpotID());
//
//        long favorite_id = db.insert(TABLE_FAVORITE_SPOTS, null, values);
//        return favorite_id;
//
//    }

}
