package com.example.sqlitedeme.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by tk on 17-9-3.
 */

public class MySqliteOpenHelper extends SQLiteOpenHelper {

    private static MySqliteOpenHelper instance;
    private static final int DATABASE_VERSION = 1;
    public static final String SQL_NAME = "test.db";
    public static final String TAG = "MySqliteOpenHelper";

    private static final String TEST_TABLE_CREATE = "create table "
            + TestDao.TABLE_NAME + " (_id integer primary key autoincrement, "
            + TestDao.T_NAME + " varchar, "
            + TestDao.T_AGE + " varchar, "
            + TestDao.T_PHONE + " varchar)";

    public MySqliteOpenHelper(Context context){
        super(context,SQL_NAME,null,DATABASE_VERSION);
    }

    public static MySqliteOpenHelper getInstance(Context context){
        if (instance == null){
            instance = new MySqliteOpenHelper(context.getApplicationContext());
        }
        return instance;
    }

    public MySqliteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.e(TAG,"onCreate");
        sqLiteDatabase.execSQL(TEST_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        Log.e(TAG, "onUpgrade  oldVersion = " + oldVersion + " newVersion = " + newVersion);

        if (oldVersion < 2) {
            String sql = "alter table " + TestDao.TABLE_NAME + " add columu" + TestDao.T_CITY + " text";
            sqLiteDatabase.execSQL(sql);
        }
    }

    public void closeDB(){
        if (instance != null){
            try {

                SQLiteDatabase db = instance.getWritableDatabase();
                db.close();
            } catch (Exception e){
                e.printStackTrace();
            }

            instance = null;
        }
    }
}
