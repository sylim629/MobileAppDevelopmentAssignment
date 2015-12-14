package com.example.leanne.mobileappdevelopmentassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Leanne on 15. 12. 6..
 */
public class DatabaseOpener {
    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 5;
    public static SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private Context context;

    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
            database.execSQL(CreateDB.CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
            database.execSQL("DROP TABLE IF EXISTS " + CreateDB.TABLE_NAME);
            onCreate(database);
        }
    }

    public DatabaseOpener(Context context) {
        this.context = context;
    }

    public DatabaseOpener open() throws SQLException {
        databaseHelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        database.close();
    }

    public long insertRecord(String id, String pw, String name, String gender, String bday, String phone, String email) {
        ContentValues values = new ContentValues();
        values.put(CreateDB.USER_ID, id);
        values.put(CreateDB.PASSWORD, pw);
        values.put(CreateDB.NAME, name);
        values.put(CreateDB.GENDER, gender);
        values.put(CreateDB.BIRTHDAY, bday);
        values.put(CreateDB.PHONE, phone);
        values.put(CreateDB.EMAIL, email);
        return database.insert(CreateDB.TABLE_NAME, null, values);
    }

    public boolean updateRecord(long id, String userID, String pw, String name, String gender,
                                String bday, String phone, String email) {
        ContentValues values = new ContentValues();
        values.put(CreateDB.USER_ID, userID);
        values.put(CreateDB.PASSWORD, pw);
        values.put(CreateDB.NAME, name);
        values.put(CreateDB.GENDER, gender);
        values.put(CreateDB.BIRTHDAY, bday);
        values.put(CreateDB.PHONE, phone);
        values.put(CreateDB.EMAIL, email);
        return database.update(CreateDB.TABLE_NAME, values, "_id=" + id, null) > 0;
    }

    public boolean deleteRecord(long id) {
        return database.delete(CreateDB.TABLE_NAME, "_id=" + id, null) > 0;
    }

    public Cursor getAllRecords() {
        return database.query(CreateDB.TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor getRecord(long id) {
        Cursor c = database.query(CreateDB.TABLE_NAME, null,
                "_id=" + id, null, null, null, null);
        if (c != null && c.getCount() != 0)
            c.moveToFirst();
        return c;
    }

    public Cursor getMatchingRecordByID(String id) {
        Cursor c = database.rawQuery("select * from " + CreateDB.TABLE_NAME
                + " where " + CreateDB.USER_ID + "=" + "'" + id + "'", null);
        return c;
    }
}
