package com.example.lab05_it18202632.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.lang.UScript;
import android.provider.BaseColumns;

import com.example.tute04.Database.UsersMaster;

import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.List;


public class DBHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Database_Name.db";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UsersMaster.Users.TABLE_NAME + " (" +
                    UsersMaster.Users._ID + " INTEGER PRIMARY KEY," +
                    UsersMaster.Users.COLUMN_MAME_USERNAME + " TEXT," +
                    UsersMaster.Users.COLUMN_MAME_PASSWORD + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UsersMaster.Users.TABLE_NAME;

    public long addInfo(String userName, String password) {

        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(UsersMaster.Users.COLUMN_MAME_USERNAME, userName);
        values.put(UsersMaster.Users.COLUMN_MAME_PASSWORD, password);


        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(UsersMaster.Users.TABLE_NAME, null, values);
        return newRowId;
    }

    public boolean updateInfo(String userName, String password) {

        SQLiteDatabase db = getWritableDatabase();

// New value for one column
        String title = userName;
        ContentValues values = new ContentValues();
        values.put(UsersMaster.Users.COLUMN_MAME_PASSWORD, password);


// Which row to update, based on the title
        String selection = UsersMaster.Users.COLUMN_MAME_USERNAME + " LIKE ?";
        String[] selectionArgs = {userName};

        int count = db.update(
                UsersMaster.Users.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if (count > 0) {
            return true;
        } else
            return false;
    }

    public void deleteInfo(String userName) {

        SQLiteDatabase db = getWritableDatabase();

        // Define 'where' part of query.
        String selection = UsersMaster.Users.COLUMN_MAME_USERNAME + " LIKE ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = {userName};
// Issue SQL statement.
        int deletedRows = db.delete(UsersMaster.Users.TABLE_NAME, selection, selectionArgs);

    }



    public List readAllInfo(String userName) {


        SQLiteDatabase db = getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                UsersMaster.Users.COLUMN_MAME_USERNAME,
                UsersMaster.Users.COLUMN_MAME_PASSWORD,

        };

// Filter results WHERE "title" = 'My Title'
        String selection = UsersMaster.Users.COLUMN_MAME_USERNAME + " LIKE ?";
        String[] selectionArgs = {userName};

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                UsersMaster.Users.COLUMN_MAME_USERNAME + " ASC";

        Cursor cursor = db.query(
                UsersMaster.Users.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List userInfo = new ArrayList<>();
        while (cursor.moveToNext()) {
            String user = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_MAME_USERNAME));
            String passwd = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_MAME_PASSWORD));

            userInfo.add(user);//0
            userInfo.add(passwd);//1


        }
        cursor.close();
        return userInfo;

    }

    public Boolean loginUserInfo(String username, String password) {

        SQLiteDatabase db = getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                UsersMaster.Users.COLUMN_MAME_USERNAME,
                UsersMaster.Users.COLUMN_MAME_PASSWORD
        };

// Filter results WHERE "title" = 'My Title'
        String selection = UsersMaster.Users.COLUMN_MAME_USERNAME + " = ? AND " + UsersMaster.Users.COLUMN_MAME_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                UsersMaster.Users.COLUMN_MAME_USERNAME + " ASC";

        Cursor cursor = db.query(
                UsersMaster.Users.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        List validUser = new ArrayList();
        while (cursor.moveToNext()) {
            String man = cursor.getString((cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_MAME_USERNAME)));
            validUser.add(man);

        }
        if (validUser.isEmpty()) {
            return false;
        } else {
            return true;
        }


    }

    public ArrayList readAllInfo(){
        String userName = "avinash";
        SQLiteDatabase db = getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                UsersMaster.Users.COLUMN_MAME_USERNAME,
                UsersMaster.Users.COLUMN_MAME_PASSWORD
        };

// Filter results WHERE "title" = 'My Title'
        String selection = UsersMaster.Users.COLUMN_MAME_USERNAME + " = ?";
        String[] selectionArgs = { userName };

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                UsersMaster.Users.COLUMN_MAME_USERNAME + " ASC";

        Cursor cursor = db.query(
                UsersMaster.Users.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
        ArrayList usernames = new ArrayList();
        while (cursor.moveToNext()){
            String user= cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_MAME_USERNAME));
            usernames.add(user);
        }
        cursor.close();
        return usernames;
    }

}

