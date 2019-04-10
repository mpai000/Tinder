package com.example.dogfinder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sInstance = null;

    private static final int DATABASE_VERSION = 3;

    public static final String DATABASE_NAME = "dogtinderDB.db";
    public static final String TABLE_NAME = "dog_table";
    public static final String COL1 = "dogID";
    public static final String COL2 = "dogLocation";
    public static final String COL3 = "dogBreed";
    public static final String COL4 = "dogMaturity";
    public static final String COL5 = "dogGender";
    public static final String COL6 = "dogSize";
    public static final String COL7 = "dogName";
    public static final String COL8 = "dogPictureLink";


    public static final String USER_TABLE_NAME = "user_table";
    public static final String USER_COL1 = "userID";
    public static final String USER_COL2 = "email";
    public static final String USER_COL3 = "password";
    public static final String USER_COL4 = "userLocation";
    public static final String USER_COL5 = "firstName";
    public static final String USER_COL6 = "lastName";
    public static final String USER_COL7 = "userPictureLink";


    public static final String SWIPE_TABLE_NAME = "swipe_table";
    public static final String SWIPE_COL1 = "userID";
    public static final String SWIPE_COL2 = "dogID";
    public static final String SWIPE_COL3 = "direction";

    public static final String UPLOAD_TABLE_NAME = "upload_table"; //connect user with the dog they added
    public static final String UPLOAD_COL1 = "userID";
    public static final String UPLOAD_COL2 = "dogID";

    public static final String MESSAGE_TABLE_NAME = "message_table";
    public static final String MESSAGE_COL1 = "msgID";
    public static final String MESSAGE_COL2 = "senderID";
    public static final String MESSAGE_COL3 = "receiverID";
    public static final String MESSAGE_COL4 = "message";

    public static synchronized DatabaseHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }


    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DOG_TABLE = "CREATE TABLE dog_table(dogID INTEGER PRIMARY KEY AUTOINCREMENT,dogLocation TEXT, dogBreed TEXT, dogMaturity TEXT, dogGender TEXT, dogSize TEXT, dogName TEXT, dogPictureLink TEXT)";
        db.execSQL(CREATE_DOG_TABLE);

        String CREATE_USER_TABLE = "CREATE TABLE user_table(userID INTEGER PRIMARY KEY AUTOINCREMENT,email TEXT, password TEXT, userLocation TEXT, firstName TEXT, lastName TEXT, userPictureLink TEXT)";
        db.execSQL(CREATE_USER_TABLE);

        String CREATE_SWIPE_TABLE = "CREATE TABLE swipe_table(userID INTEGER,dogID INTEGER, direction TEXT, FOREIGN KEY(userID) REFERENCES user_table(userID), FOREIGN KEY(dogID) REFERENCES dog_table(dogID), PRIMARY KEY(userID, dogID))";
        db.execSQL(CREATE_SWIPE_TABLE);

        String CREATE_UPLOAD_TABLE = "CREATE TABLE upload_table(userID INTEGER,dogID INTEGER, FOREIGN KEY(userID) REFERENCES user_table(userID), FOREIGN KEY(dogID) REFERENCES dog_table(dogID), PRIMARY KEY(userID, dogID))";
        db.execSQL(CREATE_UPLOAD_TABLE);

        String CREATE_MESSAGE_TABLE = "CREATE TABLE message_table(msgID INTEGER PRIMARY KEY AUTOINCREMENT,senderID INTEGER, receiverID INTEGER, message TEXT, FOREIGN KEY(senderID) REFERENCES user_table(userID), FOREIGN KEY(receiverID) REFERENCES user_table(userID))";
        db.execSQL(CREATE_MESSAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS dog_table");
        db.execSQL("DROP TABLE IF EXISTS user_table");
        db.execSQL("DROP TABLE IF EXISTS swipe_table");
        db.execSQL("DROP TABLE IF EXISTS upload_table");
        db.execSQL("DROP TABLE IF EXISTS message_table");
        onCreate(db);
    }

    @Override
    public void finalize() throws Throwable {
        //terminate something else here if you want
        this.close();
        super.finalize();
    }

    public boolean addDogData(String dogLocation, String dogBreed, String dogMaturity, String dogGender, String dogSize, String dogName, String  dogPictureLink) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,dogLocation);
        contentValues.put(COL3,dogBreed);
        contentValues.put(COL4,dogMaturity);
        contentValues.put(COL5,dogGender);
        contentValues.put(COL6,dogSize);
        contentValues.put(COL7,dogName);
        contentValues.put(COL8,dogPictureLink);

        long result = db.insertOrThrow(TABLE_NAME, null, contentValues);
        db.close();
        if (result == -1){
            return false;
        } else
        {
            return true;
        }
    }

    public boolean addUserData(String email, String password, String userLocation, String firstName, String lastName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COL2,email);
        contentValues.put(USER_COL3,password);
        contentValues.put(USER_COL4,userLocation);
        contentValues.put(USER_COL5,firstName);
        contentValues.put(USER_COL6,lastName);
        contentValues.put(USER_COL7,"https://i.imgur.com/bMJ6N3r.png");

        long result = db.insertOrThrow(USER_TABLE_NAME, null, contentValues);
        db.close();
        if (result == -1){
            return false;
        } else
        {
            return true;
        }
    }

    public boolean swipe(int userID, int dogID, String direction) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] search_cols = new String[]{String.valueOf(userID), String.valueOf(dogID)};
        Cursor c = db.rawQuery("SELECT * FROM " + SWIPE_TABLE_NAME + " WHERE " + SWIPE_COL1 + "= ? AND " + SWIPE_COL2 + "= ? ", search_cols);

        ContentValues contentValues = new ContentValues();

        contentValues.put(SWIPE_COL3,direction);

        if (c.moveToFirst()) {
            //Record exist
            db.update(SWIPE_TABLE_NAME, contentValues, SWIPE_COL1 + "= ? AND " + SWIPE_COL2 + "= ?", search_cols);
            c.close();

            return true;
        }
        //Record available
        contentValues.put(SWIPE_COL1,userID);
        contentValues.put(SWIPE_COL2,dogID);
        long result = db.insertOrThrow(SWIPE_TABLE_NAME, null, contentValues);
        db.close();
        if (result == -1){
            return false;
        } else
        {
            return true;
        }
    }

    public Cursor getSwipe(int userID, String direction){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] search_cols = new String[]{String.valueOf(userID), direction};

        Cursor data;
        data = db.rawQuery("SELECT * FROM swipe_table WHERE " + SWIPE_COL1 + "= ? AND " + SWIPE_COL3 + "= ?", search_cols);

        return data;

    }

    public boolean upload(int userID, int dogID){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] search_cols = new String[]{String.valueOf(userID), String.valueOf(dogID)};
        Cursor data = db.rawQuery("SELECT * FROM " + UPLOAD_TABLE_NAME + " WHERE " + UPLOAD_COL1 + "= ? AND " + UPLOAD_COL2 + "= ? ", search_cols);

        ContentValues contentValues = new ContentValues();


        if (data.moveToFirst()) {
            //Record exist
            data.close();
            return true;
        }

        //available
        contentValues.put(UPLOAD_COL1,userID);
        contentValues.put(UPLOAD_COL2,dogID);
        long result = db.insertOrThrow(UPLOAD_TABLE_NAME, null, contentValues);
        db.close();
        if (result == -1){
            return false;
        } else
        {
            return true;
        }

    }

    public Cursor getOwner(int dogID){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] search_cols = new String[]{String.valueOf(dogID)};

        Cursor data;
        data = db.rawQuery("SELECT * FROM upload_table WHERE " + UPLOAD_COL2 + "= ?", search_cols);

        return data;
    }

    public Cursor searchDogData(String dogLocation, String dogBreed, String dogMaturity, String dogGender, String dogSize){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] search_cols = new String[]{dogLocation, dogBreed, dogMaturity, dogGender, dogSize};

        Cursor data;
        data = db.rawQuery("SELECT * FROM dog_table WHERE " + COL2 + "= ? AND " + COL3 + "= ? AND " + COL4 + "= ? AND " + COL5 + "= ? AND " + COL6 + "= ?", search_cols);

        return data;
    }

    public Cursor searchDogID(String dogLocation, String dogBreed, String dogMaturity, String dogGender, String dogSize, String dogname){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] search_cols = new String[]{dogLocation, dogBreed, dogMaturity, dogGender, dogSize, dogname};

        Cursor data;
        data = db.rawQuery("SELECT * FROM dog_table WHERE " + COL2 + "= ? AND " + COL3 + "= ? AND " + COL4 + "= ? AND " + COL5 + "= ? AND " + COL6 + "= ? AND " + COL7 + "= ?", search_cols);

        return data;
    }

    public Cursor getDogInfo(int dogID){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] search_cols = new String[]{String.valueOf(dogID)};

        Cursor data;
        data = db.rawQuery("SELECT * FROM dog_table WHERE " + COL1 + "= ?", search_cols);

        return data;
    }


    public Cursor getUserInfo(int userID){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] search_cols = new String[]{String.valueOf(userID)};

        Cursor data;
        data = db.rawQuery("SELECT * FROM user_table WHERE " + USER_COL1 + "= ?", search_cols);

        return data;
    }

    public Cursor checkPW(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] search_cols = new String[]{email, password};

        Cursor data;
        data = db.rawQuery("SELECT * FROM user_table WHERE " + USER_COL2 + "= ? AND " + USER_COL3 + "= ?", search_cols);

        return data;
    }


    public Cursor checkExist(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] search_cols = new String[]{email};

        Cursor data;
        data = db.rawQuery("SELECT * FROM user_table WHERE " + USER_COL2 + "= ?", search_cols);

        return data;
    }

    public boolean updateUser(String nemail, String npass, String nloc, String nfname, String nlname, String nlink, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] search_cols=new String[]{String.valueOf(id)};
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COL1,id);
        contentValues.put(USER_COL2,nemail);
        contentValues.put(USER_COL3,npass);
        contentValues.put(USER_COL4,nloc);
        contentValues.put(USER_COL5,nfname);
        contentValues.put(USER_COL6,nlname);
        contentValues.put(USER_COL7,nlink);

        db.update(USER_TABLE_NAME, contentValues, "userID = ?",search_cols);
        return true;
    }

    public boolean sendMessage(int senderID, int receiverID, String message){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MESSAGE_COL2,senderID);
        contentValues.put(MESSAGE_COL3,receiverID);
        contentValues.put(MESSAGE_COL4,message);

        long result = db.insertOrThrow(MESSAGE_TABLE_NAME,null,contentValues);
        db.close();
        if (result == -1){
            return false;
        } else
        {
            return true;
        }
    }

    public Cursor getSentMessages(int senderID){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] search_cols = new String[]{String.valueOf(senderID)};

        Cursor data;
        data = db.rawQuery("SELECT * FROM message_table WHERE " + MESSAGE_COL2 + "= ?", search_cols);

        return data;
    }

    public Cursor getReceviedMessages(int receiverID){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] search_cols = new String[]{String.valueOf(receiverID)};

        Cursor data;
        data = db.rawQuery("SELECT * FROM message_table WHERE " + MESSAGE_COL3 + "= ?", search_cols);

        return data;
    }

    public Cursor getMessage(int msgID){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] search_cols = new String[]{String.valueOf(msgID)};

        Cursor data;
        data = db.rawQuery("SELECT * FROM message_table WHERE " + MESSAGE_COL1 + "= ?", search_cols);

        return data;
    }
}