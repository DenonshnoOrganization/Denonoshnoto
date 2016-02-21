package com.example.arc.androidhomweorkonetaskone;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Chav on 2/21/2016.
 */


// This is to help visualising the processes connected with database creation and functionality. To be removed after completion of the database.
public class Message {
    public static void message(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
