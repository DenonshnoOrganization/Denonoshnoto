package com.example.arc.androidhomweorkonetaskone;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper datahelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datahelper = new DatabaseHelper(this);

        SQLiteDatabase denonoshnotoSQL  = datahelper.getWritableDatabase();
    }
}
