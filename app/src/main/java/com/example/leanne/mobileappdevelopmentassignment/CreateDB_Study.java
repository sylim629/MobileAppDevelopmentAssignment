package com.example.leanne.mobileappdevelopmentassignment;

import android.provider.BaseColumns;

/**
 * Created by Leanne on 15. 12. 6..
 */
public class CreateDB_Study implements BaseColumns {
    public static final String REGION = "region";
    public static final String PAY = "pay";
    public static final String PERIOD = "period";
    public static final String DAYS = "days";
    public static final String HOURS = "hours";
    public static final String N_PEOPLE = "num_people";
    public static final String COMPANY = "company_info";
    public static final String INFO_DETAIL = "info_detail";
    public static final String TABLE_NAME = "study";
    public static final String CREATE = "create table " + TABLE_NAME + "("
            + _ID + " integer primary key autoincrement,"
            + REGION + " text not null,"
            + PAY + " text not null,"
            + PERIOD + " text not null,"
            + DAYS + " text not null,"
            + HOURS + " text not null,"
            + N_PEOPLE + " text not null,"
            + COMPANY + " text not null,"
            + INFO_DETAIL + " text not null);";
}
