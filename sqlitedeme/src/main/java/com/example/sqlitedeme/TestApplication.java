package com.example.sqlitedeme;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.sqlitedeme.db.MySqliteOpenHelper;
import com.example.sqlitedeme.gdb.DaoMaster;
import com.example.sqlitedeme.gdb.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by tk on 17-9-3.
 */

public class TestApplication extends Application {


    public static Context context;
    public static Context applictionCOntext;

    /** A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher. */
    public static final boolean ENCRYPTED = true;

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        applictionCOntext = getApplicationContext();

        initSqliteDb();
        initGreenDao();

    }

    private void initSqliteDb() {
        MySqliteOpenHelper helper = MySqliteOpenHelper.getInstance(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        helper.closeDB();
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
