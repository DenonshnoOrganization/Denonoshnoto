package com.example.chav.mapsproject;

import android.content.Context;
import android.widget.Toast;




// This is to help visualising the processes connected with database creation and functionality. To be removed after completion of the database.
public class Message {

    private Message(){

    }
    public static void message(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
