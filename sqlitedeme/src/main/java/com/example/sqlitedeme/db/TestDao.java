package com.example.sqlitedeme.db;

import com.example.sqlitedeme.bean.UserBean;

import java.util.List;

/**
 * Created by tk on 17-9-3.
 */

public class TestDao {
    public static final String TABLE_NAME = "uers";
    public static final String T_NAME = "name";
    public static final String T_AGE = "age";
    public static final String T_PHONE = "phone";
    public static final String T_CITY = "city";

    public void sqlAdd(UserBean data){
        MySqliteDBManager dbManager = MySqliteDBManager.getInstance();
        dbManager.sqlAdd(data);
    }

    public void apiAdd(UserBean data){
        MySqliteDBManager.getInstance().apiAdd(data);
    }

   public void sqlDel(String name){
       MySqliteDBManager.getInstance().sqlDel(name);
   }

   public void apiDel(String name){
       MySqliteDBManager.getInstance().apiDel(name);
   }

   public void sqlUpate(UserBean userBean){
       MySqliteDBManager.getInstance().sqlUpdate(userBean);
   }

   public void apiUpdate(UserBean data){
       MySqliteDBManager.getInstance().apiUpdate(data);
   }

   public List<UserBean> sqlQuery(String age){
       List<UserBean> li = MySqliteDBManager.getInstance().sqlQuery(age);
       return li;
   }

   public List<UserBean> apiQuery(String age){
       List<UserBean> list = MySqliteDBManager.getInstance().apiQuery(age);
       return list;
   }



}
