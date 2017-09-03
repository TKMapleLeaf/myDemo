package com.example.sqlitedeme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sqlitedeme.bean.UserBean;
import com.example.sqlitedeme.db.MySqliteDBManager;
import com.example.sqlitedeme.db.TestDao;

import java.util.List;
import java.util.Random;

public class SqliteActivity extends AppCompatActivity {

    private EditText delAge;
    private TextView tvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        delAge = (EditText) findViewById(R.id.et_age);
        tvList = (TextView) findViewById(R.id.list);

        final TestDao dao = new TestDao();

        findViewById(R.id.insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dao.sqlAdd(new UserBean("张三",new Random().nextInt(15)+"","133"));
                dao.apiAdd(new UserBean("李四",new Random().nextInt(25)+"","155"));

                MySqliteDBManager.getInstance().closeDB();
            }
        });

        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                dao.sqlDel(delAge.getText().toString().trim());
                dao.apiDel(delAge.getText().toString().trim());
                MySqliteDBManager.getInstance().closeDB();

            }
        });

        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                dao.sqlUpate(new UserBean("张三","99","200"));
                dao.apiUpdate(new UserBean("张三","88","199"));
                MySqliteDBManager.getInstance().closeDB();

            }
        });

        findViewById(R.id.select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuffer stringBuffer = new StringBuffer();
//                List<UserBean> list = dao.sqlQuery(delAge.getText().toString().trim());
                List<UserBean> list = dao.apiQuery(delAge.getText().toString().trim());
                for (int i = 0; i < list.size(); i++) {
                    UserBean bean = list.get(i);
                    stringBuffer.append(bean.getName()).append("   ").append(bean.getAge()).append("   ")
                            .append(bean.getPhone()).append("\n");
                }
                MySqliteDBManager.getInstance().closeDB();
                tvList.setText(stringBuffer.toString());
            }
        });

        findViewById(R.id.upgrade).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySqliteDBManager.getInstance().closeDB();
            }
        });
    }
}
