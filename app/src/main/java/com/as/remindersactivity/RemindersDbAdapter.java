package com.as.remindersactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.security.PublicKey;

/**
 * Created by pc on 2018/2/6.
 */

public class RemindersDbAdapter {
    //列明
    public static final String COL_ID = "_id";
    public static final String COL_CONTENT = "content";
    public static final String COL_IMPORTANT = "important";

    //these are the corresponding indices这是相应的指标。
    public static final int INDEX_ID = 0;
    public static final int INDEX_CONTENT = INDEX_ID + 1;
    public static final int INDEX_IMPORTANT = INDEX_ID + 2;

    //used for logging用于记录
    private static final String TAG = "RemindersDbAdapter";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "dba_remdrs";
    private static final String TABLE_NAME = "tb1_remdrs";
    private static final int DATABASE_VERSION = 1;

    private final Context mCtx;

    //SQL statement used to create the database用于创建数据库的SQL语句
    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists" + TABLE_NAME +"(" +
                    COL_ID + "INTEGER PRIMARY KEY autoincrement,"+
                    COL_CONTENT + "TEXT," +
                    COL_IMPORTANT + "INTAGER);";

    public RemindersDbAdapter(Context ctx){
        this.mCtx = ctx;
    }
    public void open() throws SQLException{
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
    }
    public void close(){
        if (mDbHelper!=null){
            mDbHelper.close();
        }
    }
    //CREATE
    //note that the id will be created for you automatically注意，将自动为您创建id。
    public void createReminder(String name,boolean important){
        ContentValues values = new ContentValues();
        values.put(COL_CONTENT,name);
        values.put(COL_IMPORTANT,important ? 1:0);
        mDb.insert(TABLE_NAME,null,values);
    }
    //overloaded to take a remiander超载采取remiander
    public long createReminder(Reminder reminder){
        ContentValues values = new ContentValues();
        values.put(COL_CONTENT,reminder.getContent());       //contact name联系人姓名
        values.put(COL_IMPORTANT,reminder.getmImportant());  //contact phone number联系电话
        return mDb.insert(TABLE_NAME,null,values);          //Inserting Row插入行
    }
    //READ
    public Reminder fetchReminderById(int id){
        Cursor cursor = mDb.query(TABLE_NAME,new String[]{COL_ID,COL_CONTENT,COL_IMPORTANT},COL_ID + "=?",new String[]{String.valueOf(id)},null,null,null,null);
        if (cursor!=null)
            cursor.moveToFirst();

            return new Reminder(
                    cursor.getInt(INDEX_ID),
                    cursor.getString(INDEX_CONTENT),
                    cursor.getInt(INDEX_IMPORTANT)
            );
    }
    public Cursor fetchAllReminders(){
        Cursor mCursor = mDb.query(TABLE_NAME,new String[]{COL_ID,COL_CONTENT,COL_IMPORTANT},null,null,null,null,null);
        if (mCursor!=null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    //UPDATE
    public void updateReminder(Reminder reminder){
        ContentValues values = new ContentValues();
        values.put(COL_CONTENT,reminder.getContent());
        values.put(COL_IMPORTANT,reminder.getmImportant());
        mDb.update(TABLE_NAME,values,COL_ID+"=?",new String[]{String.valueOf(reminder.getId())});
    }
    //DELETE
    public void deleteReminderById(int nId){
        mDb.delete(TABLE_NAME,COL_ID+"?=",new String[]{String.valueOf(nId)});
    }
    public void deleteAllReminders(){
        mDb.delete(TABLE_NAME,null,null);
    }


    private static class DatabaseHelper extends SQLiteOpenHelper{
        DatabaseHelper(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG,DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG,"Upgrading database from version"+ oldVersion+"to"+newVersion+",which will old data");
            db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME);
            onCreate(db);
        }
    }

}
