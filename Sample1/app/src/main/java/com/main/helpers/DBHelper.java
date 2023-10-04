package com.main.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.main.models.User;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "AttendanceDB";
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
    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues userValues = new ContentValues();

        userValues.put(col_userName, user.getUsername());
        userValues.put(col_userPass, user.getPassword());
        userValues.put(col_firstName, user.getFirstName());
        userValues.put(col_lastName, user.getLastName());

        db.insert(TABLE_NAME, null, userValues);

        db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
