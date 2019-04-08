package com.example.tinder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DabaseHelper";
    public static final String DATABASE_NAME= "tinder";
    private static String TABLE_NAME = "Users";
    private static final String COL1 = "userID";
    private static final String COL2 = "firstName";
    private static final String COL3 = "lastName";
    private static final String COL4 = "email";
    private static final String COL5 = "Password";
    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME, null, 1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable;
        createTable=  "CREATE TABLE Users(userID INTEGER PRIMARY KEY AUTOINCREMENT,firstName TEXT, lastName TEXT, email TEXT, password TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String fname, String lname, String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,fname);
        contentValues.put(COL3,lname);
        contentValues.put(COL4,email);
        contentValues.put(COL5,password);

        Log.d(TAG, "addData: Adding" + fname + "to "+ TABLE_NAME);
        long result = db.insertOrThrow(TABLE_NAME, null, contentValues); // -1 if not inserted correctly
        db.close();
        if (result == -1){
            return false;
        } else
        {
            return true;
        }
    }

    public Cursor showData(){
        SQLiteDatabase db = this.getWritableDatabase();

        String query ="SELECT * FROM "+ TABLE_NAME;
        Cursor data= db.rawQuery( query, null);
        return data;
    }
}
