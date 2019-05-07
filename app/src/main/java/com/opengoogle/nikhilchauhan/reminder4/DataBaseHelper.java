package com.opengoogle.nikhilchauhan.reminder4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="reminder.db";
    public static final String TABLE_NAME="reminder_table";
    public static final String COL_1="ID";
    public static final String COL_2="NAME";
    public static final String COL_3="DATE";
    public static final String COL_4="TIME";
    public static final String COL_5="DESCRIPTION";

    public DataBaseHelper( Context context  ) {
        super(context,DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,DATE TEXT,TIME TEXT,DESCRIPTION TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(db);

    }
    public boolean insertData(String name,String date,String time,String description)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,date);
        contentValues.put(COL_4,time);
        contentValues.put(COL_5,description);
        Log.d("inside insert","insert success");

        long result=db.insert(TABLE_NAME,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;

    }
    public Cursor getAllData()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+TABLE_NAME,null);
        return res;

    }
    public boolean UpdateData(String id,String name,String date,String time,String description)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,date);
        contentValues.put(COL_4,time);
        contentValues.put(COL_5,description);

        db.update(TABLE_NAME,contentValues,"ID = ?",new String[]{id});
        return true;
    }
    public Integer Deletedata(String id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID = ?",new String[]{id});
    }
    public Cursor GetNotiData(String id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select "+COL_2+  " AND "+ COL_5+" from "+TABLE_NAME+ " WHERE ID ="+id ,null);
        return res;
    }
}
