package com.opengoogle.nikhilchauhan.reminder4;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlarmDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="upcomingAlarm.db";
    public static final String TABLE_NAME="alarm_table";
    public static final String COL_1="ID";
    public static final String COL_2="day";
    public static final String COL_3="month";
    public static final String COL_4="year";
    public static final String COL_5="hour";
    public static final String COL_6="min";
    public static final String COL_7="name";

    public AlarmDatabase( Context context  ) {
        super(context,DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,DAY TEXT,MONTH TEXT,YEAR TEXT,HOUR TEXT,MIN TEXT,NAME TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(db);

    }
    public boolean insertAlarmData(int day,int month,int year,int hour,int min,String name)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,day);
        contentValues.put(COL_3,month);
        contentValues.put(COL_4,year);
        contentValues.put(COL_5,hour);
        contentValues.put(COL_6,min);
        contentValues.put(COL_7,name);


        long result=db.insert(TABLE_NAME,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;

    }
    public boolean UpdateAlarmData(String id,String day,String month,String year,String hour,String min,String name)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,day);
        contentValues.put(COL_3,month);
        contentValues.put(COL_4,year);
        contentValues.put(COL_5,hour);
        contentValues.put(COL_6,min);
        contentValues.put(COL_7,name);

        db.update(TABLE_NAME,contentValues,"ID = ?",new String[]{id});
        return true;
    }
    public Integer DeleteAlarmdata(String id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID = ?",new String[]{id});
    }
}
