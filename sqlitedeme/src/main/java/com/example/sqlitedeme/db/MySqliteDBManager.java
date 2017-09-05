package com.example.sqlitedeme.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sqlitedeme.TestApplication;
import com.example.sqlitedeme.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tk on 17-9-3.
 */

public class MySqliteDBManager {

    private static MySqliteDBManager dbManager = new MySqliteDBManager();
    private final MySqliteOpenHelper dbHelper;

    private MySqliteDBManager(){
        dbHelper = MySqliteOpenHelper.getInstance(TestApplication.applictionCOntext);
    }

    public static synchronized MySqliteDBManager getInstance(){
        if (dbManager == null){
            dbManager = new MySqliteDBManager();
        }
        return dbManager;
    }

    // sql api

    public void sqlAdd(UserBean data){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String sql = "insert into " + TestDao.TABLE_NAME
                + " (" + TestDao.T_NAME + "," + TestDao.T_AGE +"," + TestDao.T_PHONE + ")"
                + " values (?,?,?)";
        database.execSQL(sql,new Object[]{data.getName(),data.getAge(),data.getPhone()});
    }

    public void sqlDel(String data){
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String sql = "delete from " + TestDao.TABLE_NAME + " where " + TestDao.T_AGE + "=?";

        database.execSQL(sql,new Object[]{data});
    }

    public void sqlUpdate(UserBean data){

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String sql = "update " + TestDao.TABLE_NAME + " set "
                + TestDao.T_AGE + "=?, " + TestDao.T_PHONE + "=?"+ " where " +  TestDao.T_NAME + "=?" ;

        database.execSQL(sql,new Object[]{data.getAge(),data.getPhone(),data.getName()});
    }

    public List<UserBean> sqlQuery(String age){
        ArrayList<UserBean> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String sql = "select "
                + TestDao.T_NAME  + ","
                + TestDao.T_AGE + ","
                + TestDao.T_PHONE
                + " from " + TestDao.TABLE_NAME
                + " where " + TestDao.T_AGE + "<?";

        Cursor cursor = database.rawQuery(sql, new String[]{age});

        while (cursor.moveToNext()){
            UserBean bean = new UserBean();
            String name = cursor.getString(cursor.getColumnIndex(TestDao.T_NAME));
            String strAge = cursor.getString(cursor.getColumnIndex(TestDao.T_AGE));
            String phone = cursor.getString(cursor.getColumnIndex(TestDao.T_PHONE));

            bean.setName(name);
            bean.setAge(strAge);
            bean.setPhone(phone);

            list.add(bean);
        }

        cursor.close();

        return list;
    }


    //android api

    public void apiAdd(UserBean data){

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TestDao.T_NAME,data.getName());
        values.put(TestDao.T_AGE,data.getAge());
        values.put(TestDao.T_PHONE,data.getPhone());

        database.insert(TestDao.TABLE_NAME,null,values);
    }

    public void apiDel(String age){

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        database.delete(TestDao.TABLE_NAME, TestDao.T_AGE + "=?", new String[]{age});
    }

    public void apiUpdate(UserBean data){

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TestDao.T_AGE,data.getAge());
        values.put(TestDao.T_PHONE,data.getPhone());

        database.update(TestDao.TABLE_NAME,values, TestDao.T_NAME + "=?", new String[]{data.getName()});
    }

    public List<UserBean> apiQuery(String strAge){

        ArrayList<UserBean> list = new ArrayList<>();

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        /**
         *
         */
        Cursor cursor = database.query(TestDao.TABLE_NAME, new String[]{TestDao.T_NAME, TestDao.T_AGE, TestDao.T_PHONE},
                TestDao.T_AGE + "<?", new String[]{strAge}, null, null, null);

        while (cursor.moveToNext()){
            UserBean bean = new UserBean();

            String name = cursor.getString(cursor.getColumnIndex(TestDao.T_NAME));
            String age = cursor.getString(cursor.getColumnIndex(TestDao.T_AGE));
            String phone = cursor.getString(cursor.getColumnIndex(TestDao.T_PHONE));

            bean.setName(name);
            bean.setAge(age);
            bean.setPhone(phone);

            list.add(bean);
        }

        cursor.close();
        return list;
    }

    synchronized public void closeDB(){
        if (dbHelper != null){
            dbHelper.close();
        }
        dbManager = null;
    }

}
