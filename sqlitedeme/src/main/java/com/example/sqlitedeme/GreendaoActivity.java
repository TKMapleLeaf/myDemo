package com.example.sqlitedeme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sqlitedeme.bean.User;
import com.example.sqlitedeme.gdb.DaoSession;
import com.example.sqlitedeme.gdb.UserDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;
import java.util.Random;

public class GreendaoActivity extends AppCompatActivity {

    private EditText delAge;
    private EditText name;
    private TextView tvList;
    private UserDao mUserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greendao);


        delAge = (EditText) findViewById(R.id.et_age);
        name = (EditText) findViewById(R.id.et_name);
        tvList = (TextView) findViewById(R.id.list);

        TestApplication myApp = (TestApplication) getApplication();
        DaoSession daoSession = myApp.getDaoSession();

        mUserDao = daoSession.getUserDao();

        findViewById(R.id.insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User user = new User();
                user.setName("a" + new Random().nextInt(10));
                user.setAge(new Random().nextInt(25));

                mUserDao.insert(user);//插入

                //插入或者替换
//                userDao.insertOrReplace(user);
            }
        });

        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<User> users = mUserDao.loadAll();// 查询所有记录

                mUserDao.delete(users.get(Integer.valueOf(delAge.getText().toString())));


//                userDao.deleteByKey(1L);//id
            }
        });

        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<User> users = mUserDao.loadAll();// 查询所有记录
                User user = users.get(Integer.valueOf(delAge.getText().toString()));
                user.setName(name.getText().toString());
                user.setAge(new Random().nextInt(25));
                mUserDao.update(user);
            }
        });

        findViewById(R.id.select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<User> users = mUserDao.loadAll();// 查询所有记录
                User user = mUserDao.loadByRowId(1);//根据ID查询
                List<User> userList = mUserDao.queryRaw("where AGE>?", "10");//查询年龄大于10的用户

                StringBuffer stringBuffer = new StringBuffer();

                if (users != null) {
                    for (int i = 0; i < users.size(); i++) {
                        User bean = users.get(i);
                        stringBuffer.append(bean.getName()).append("   ").append(bean.getAge()).append("  ").append("\n");
                    }
                }
                if (user != null){
                    stringBuffer.append("根据ID查询" + user.getName() + "  " + user.getAge());
                }
                tvList.setText(stringBuffer.toString());
            }
        });

        findViewById(R.id.upgrade).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }

    //查询年龄大于10的用户
    public List query4() {
        QueryBuilder builder = mUserDao.queryBuilder();
        return builder.where(UserDao.Properties.Age.gt(10)).build().list();
    }
}
