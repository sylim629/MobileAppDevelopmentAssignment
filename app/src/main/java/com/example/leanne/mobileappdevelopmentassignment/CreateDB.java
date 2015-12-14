package com.example.leanne.mobileappdevelopmentassignment;

import android.provider.BaseColumns;

/**
 * Created by Leanne on 15. 12. 6..
 */
public class CreateDB implements BaseColumns {
    public static final String USER_ID = "userID";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String GENDER = "gender";
    public static final String BIRTHDAY = "birthday";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public static final String TABLE_NAME = "user";
    public static final String CREATE = "create table " + TABLE_NAME + "("
            + _ID + " integer primary key autoincrement,"
            + USER_ID + " text not null,"
            + PASSWORD + " text not null,"
            + GENDER + " text not null,"
            + BIRTHDAY + " text not null,"
            + NAME + " text not null,"
            + PHONE + " text not null,"
            + EMAIL + " text not null);";
}
