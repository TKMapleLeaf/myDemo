package com.example.sqlitedeme;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.sqlitedeme.db.MySqliteOpenHelper;

/**
 * Created by tk on 17-9-3.
 */

public class TestApplication extends Application {
    public static Context context;
    public static Context applictionCOntext;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        applictionCOntext = getApplicationContext();

        MySqliteOpenHelper helper = MySqliteOpenHelper.getInstance(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        helper.closeDB();


    }
}
