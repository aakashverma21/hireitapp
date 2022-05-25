package com.innovationadda.hireit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "db_hireit";
    public static final String TABLE_NAME = "hireit";
    public static final String TABLE_BOOKING = "bookcar";
    public static final String TABLE_RETURN = "returncar";
    public static final String TABLE_SUMMARY = "summary";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "USERID";
    public static final String COL_3 = "F_NAME";
    public static final String COL_4 = "M_NAME";
    public static final String COL_5 = "L_NAME";
    public static final String COL_6 = "EMAIL";
    public static final String COL_7 = "D_LIC";
    public static final String COL_8 = "EXP_DATE";
    public static final String COL_9 = "DOB";
    public static final String COL_10 = "PHONE";
    public static final String COL_11 = "STREET";
    public static final String COL_12 = "CITY";
    public static final String COL_13 = "POSTAL";
    public static final String COL_14 = "PASS";
    public static final String COL_15 = "C_PASS";

    public static final String BOOKING_COL_1 = "ID";
    public static final String BOOKING_COL_2 = "BOOKING_ID";
    public static final String BOOKING_COL_3 = "PICKUP_DATE";
    public static final String BOOKING_COL_4 = "PICKUP_TIME";
    public static final String BOOKING_COL_5 = "PICKUP_CHECK";
    public static final String BOOKING_COL_6 = "FULL_ADDRESS";
    public static final String BOOKING_COL_7 = "PINCODE";
    public static final String BOOKING_COL_8 = "LONGITUDE";
    public static final String BOOKING_COL_9 = "LATITUDE";
    public static final String BOOKING_COL_10 = "CAR_CAT";
    public static final String BOOKING_COL_11 = "VAC_CAT";
    public static final String BOOKING_COL_12 = "EMAIL";
    public static final String BOOKING_COL_13 = "RATE";

    public static final String RETURN_COL_1 = "ID";
    public static final String RETURN_COL_2 = "BOOKING_ID";
    public static final String RETURN_COL_3 = "RETURN_DATE";
    public static final String RETURN_COL_4 = "RETURN_TIME";
    public static final String RETURN_COL_5 = "RETURN_CHECK";
    public static final String RETURN_COL_6 = "FULL_ADDRESS";
    public static final String RETURN_COL_7 = "PINCODE";
    public static final String RETURN_COL_8 = "LONGITUDE";
    public static final String RETURN_COL_9 = "LATITUDE";
    public static final String RETURN_COL_10 = "CAR_CAT";
    public static final String RETURN_COL_11 = "VAC_CAT";
    public static final String RETURN_COL_12 = "EMAIL";
    public static final String RETURN_COL_13 = "RATE";

    public static final String SUMMARY_COL_1 = "ID";
    public static final String SUMMARY_COL_2 = "BOOKING_ID";
    public static final String SUMMARY_COL_3 = "PICKUP_DATE";
    public static final String SUMMARY_COL_4 = "RETURN_DATE";
    public static final String SUMMARY_COL_5 = "VAC_CAT";
    public static final String SUMMARY_COL_6 = "COST";
    public static final String SUMMARY_COL_7 = "EMAIL";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 8);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,USERID TEXT,F_NAME TEXT,M_NAME TEXT,L_NAME TEXT" +
                ",EMAIL TEXT,D_LIC TEXT,EXP_DATE TEXT,DOB TEXT,PHONE TEXT,STREET TEXT,CITY TEXT,POSTAL TEXT,PASS TEXT,C_PASS TEXT)");
        db.execSQL("create table " + TABLE_BOOKING + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,BOOKING_ID TEXT,PICKUP_DATE TEXT,PICKUP_TIME TEXT,PICKUP_CHECK TEXT" +
                ",FULL_ADDRESS TEXT,PINCODE TEXT,LONGITUDE TEXT,LATITUDE TEXT,CAR_CAT TEXT,VAC_CAT TEXT,EMAIL TEXT,RATE TEXT)");
        db.execSQL("create table " + TABLE_RETURN + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,BOOKING_ID TEXT,RETURN_DATE TEXT,RETURN_TIME TEXT,RETURN_CHECK TEXT" +
                ",FULL_ADDRESS TEXT,PINCODE TEXT,LONGITUDE TEXT,LATITUDE TEXT,CAR_CAT TEXT,VAC_CAT TEXT,EMAIL TEXT,RATE TEXT)");
        db.execSQL("create table " + TABLE_SUMMARY + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,BOOKING_ID TEXT,PICKUP_DATE TEXT,RETURN_DATE TEXT,VAC_CAT TEXT,COST TEXT,EMAIL TEXT)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RETURN);
        onCreate(db);
    }

    public boolean insertData(String userid, String fname, String mname, String lname,String email, String dlic, String elic, String dob,String phone, String street, String city, String postal, String pass, String cpass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, userid);
        contentValues.put(COL_3, fname);
        contentValues.put(COL_4, mname);
        contentValues.put(COL_5, lname);
        contentValues.put(COL_6, email);
        contentValues.put(COL_7, dlic);
        contentValues.put(COL_8, elic);
        contentValues.put(COL_9, dob);
        contentValues.put(COL_10, phone);
        contentValues.put(COL_11, street);
        contentValues.put(COL_12, city);
        contentValues.put(COL_13, postal);
        contentValues.put(COL_14, pass);
        contentValues.put(COL_15, cpass);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertDataBooking(String bookingid,String pickupdate, String pickuptime,String pickcheck, String address, String pin,String lon, String lat,String car, String vac, String email,String rate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOKING_COL_2, bookingid);
        contentValues.put(BOOKING_COL_3, pickupdate);
        contentValues.put(BOOKING_COL_4, pickuptime);
        contentValues.put(BOOKING_COL_5, pickcheck);
        contentValues.put(BOOKING_COL_6, address);
        contentValues.put(BOOKING_COL_7, pin);
        contentValues.put(BOOKING_COL_8, lon);
        contentValues.put(BOOKING_COL_9, lat);
        contentValues.put(BOOKING_COL_10, car);
        contentValues.put(BOOKING_COL_11, vac);
        contentValues.put(BOOKING_COL_12, email);
        contentValues.put(BOOKING_COL_13, rate);

        long result = db.insert(TABLE_BOOKING, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertDataReturn(String bookingid,String retundate, String retuntime,String retuncheck, String address, String pin,String lon, String lat,String car, String vac, String email,String rate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RETURN_COL_2, bookingid);
        contentValues.put(RETURN_COL_3, retundate);
        contentValues.put(RETURN_COL_4, retuntime);
        contentValues.put(RETURN_COL_5, retuncheck);
        contentValues.put(RETURN_COL_6, address);
        contentValues.put(RETURN_COL_7, pin);
        contentValues.put(RETURN_COL_8, lon);
        contentValues.put(RETURN_COL_9, lat);
        contentValues.put(RETURN_COL_10, car);
        contentValues.put(RETURN_COL_11, vac);
        contentValues.put(RETURN_COL_12, email);
        contentValues.put(RETURN_COL_13, rate);

        long result = db.insert(TABLE_RETURN, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }


    public boolean insertDataSummary(String bookingid,String pickupdate, String returndate,String vac,String cost,String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SUMMARY_COL_2, bookingid);
        contentValues.put(SUMMARY_COL_3, pickupdate);
        contentValues.put(SUMMARY_COL_4, returndate);
        contentValues.put(SUMMARY_COL_5, vac);
        contentValues.put(SUMMARY_COL_6, cost);
        contentValues.put(SUMMARY_COL_7, email);

        long result = db.insert(TABLE_SUMMARY, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public Cursor getAllDataBooking() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_BOOKING, null);
        return res;
    }

    public Cursor getAllDataRetun() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_RETURN, null);
        return res;
    }

    public Cursor getAllDataSummary() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_SUMMARY, null);
        return res;
    }

    public boolean updateData(String id, String pickcheck) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOKING_COL_5, pickcheck);
        db.update(TABLE_BOOKING, contentValues, "BOOKING_ID = ?", new String[]{id});
        return true;
    }

    public Integer deleteData(String userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "USERID = ?", new String[]{userid});
    }

    public Cursor getData(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where ID=" + id + "", null);
        return res;
    }

    public Cursor getPass(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where ID=" + id + "", null);
        return res;
    }

    public Cursor checktable(){
        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("select * from " + TABLE_BOOKING, null);
        Cursor res = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+TABLE_BOOKING+"'", null);
        return res;
    }

}