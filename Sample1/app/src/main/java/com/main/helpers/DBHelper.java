package com.main.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.main.LoginActivity;
import com.main.MainActivity;
import com.main.models.User;

import java.util.Arrays;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "CustomerDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "users";
    private static final String col_ID = "user_id";
    private static final String col_userName = "username";
    private static final String col_userPass = "password";
    private static final String col_firstName = "user_firstname";
    private static final String col_lastName = "user_lastname";

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + col_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + col_userName + " TEXT, "
                + col_userPass + " TEXT, "
                + col_firstName + " TEXT, "
                + col_lastName + " TEXT)";
        db.execSQL(query);
    }
    public void addUser(User user) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            if (db != null && user != null) {
                ContentValues userValues = new ContentValues();

                // Add values to userValues
                String username = user.getUsername();
                String password = user.getPassword();
                String firstName = user.getFirstName();
                String lastName = user.getLastName();

                // Perform null checks on user attributes
                if (username != null) {
                    userValues.put(col_userName, username);
                }
                if (password != null) {
                    userValues.put(col_userPass, password);
                }
                if (firstName != null) {
                    userValues.put(col_firstName, firstName);
                }
                if (lastName != null) {
                    userValues.put(col_lastName, lastName);
                }

                long result = db.insert(TABLE_NAME, null, userValues);

                if (result == -1) {
                    // Insert failed
                    Log.e("DB Insert", "Failed to insert data into the database.");
                } else {
                    // Insert successful
                    Log.d("DB Insert", "Data inserted successfully.");
                }

                //db.close();
            } else {
                // Handle the case where db or user is null
                Log.e("DB Insert", "DB or User is null.");
            }
        } catch (Exception e) {
            // Handle any exceptions
            Log.e("DB Insert", "Exception: " + e.getMessage());
        }
    }

    public User offlineLoginUser(Context context, User user){

        SQLiteDatabase db = new DBHelper(context).getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{col_ID, col_userName, col_userPass, col_firstName, col_lastName}, col_userName + "=?", new String[]{ user.getUsername() },null,null, null);

        if(cursor != null && cursor.moveToFirst() && cursor.getCount() > 0){
            User user1 = new User(cursor.getString(2));
            if(user.getPassword().equals(user1.getPassword())) {
                return user1;
            }
        }
        return null;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
