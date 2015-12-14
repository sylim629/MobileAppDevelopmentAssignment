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
public class DatabaseOpener_Study {
    private static final String DATABASE_NAME = "study.db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private Context context;

    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
            database.execSQL(CreateDB_Study.CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
            database.execSQL("DROP TABLE IF EXISTS " + CreateDB_Study.TABLE_NAME);
            onCreate(database);
        }
    }

    public DatabaseOpener_Study(Context context) {
        this.context = context;
    }

    public DatabaseOpener_Study open() throws SQLException {
        databaseHelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        database.close();
    }

    public long insertRecord(String region, String pay, String period, String days,
                             String hours, String nPeople, String company, String infoDetail) {
        ContentValues values = new ContentValues();
        values.put(CreateDB_Study.REGION, region);
        values.put(CreateDB_Study.PAY, pay);
        values.put(CreateDB_Study.PERIOD, period);
        values.put(CreateDB_Study.DAYS, days);
        values.put(CreateDB_Study.HOURS, hours);
        values.put(CreateDB_Study.N_PEOPLE, nPeople);
        values.put(CreateDB_Study.COMPANY, company);
        values.put(CreateDB_Study.INFO_DETAIL, infoDetail);
        return database.insert(CreateDB_Study.TABLE_NAME, null, values);
    }

    public boolean updateRecord(long id, String region, String pay, String period, String days,
                                String hours, String nPeople, String company, String infoDetail) {
        ContentValues values = new ContentValues();
        values.put(CreateDB_Study.REGION, region);
        values.put(CreateDB_Study.PAY, pay);
        values.put(CreateDB_Study.PERIOD, period);
        values.put(CreateDB_Study.DAYS, days);
        values.put(CreateDB_Study.HOURS, hours);
        values.put(CreateDB_Study.N_PEOPLE, nPeople);
        values.put(CreateDB_Study.COMPANY, company);
        values.put(CreateDB_Study.INFO_DETAIL, infoDetail);
        return database.update(CreateDB_Study.TABLE_NAME, values, "_id=" + id, null) > 0;
    }

    public boolean deleteRecord(long id) {
        return database.delete(CreateDB_Study.TABLE_NAME, "_id=" + id, null) > 0;
    }

    public Cursor getAllRecords() {
        return database.query(CreateDB_Study.TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor getRecord(long id) {
        Cursor c = database.query(CreateDB.TABLE_NAME, null,
                "_id=" + id, null, null, null, null);
        if (c != null && c.getCount() != 0)
            c.moveToFirst();
        return c;
    }
}
