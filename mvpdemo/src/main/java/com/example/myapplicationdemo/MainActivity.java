package com.example.myapplicationdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.myapplicationdemo.bean.HomeBean;
import com.example.myapplicationdemo.example1.Login1Activity;
import com.example.myapplicationdemo.example2.Login.LoginActivity;
import com.example.myapplicationdemo.example5.Login5Activity;
import com.example.myapplicationdemo.example6.Login6Activity;
import com.example.myapplicationdemo.http.HttpRequests;
import com.example.myapplicationdemo.http.HttpService;
import com.example.myapplicationdemo.utils.LogUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.example1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Login1Activity.class));
            }
        });

        findViewById(R.id.example2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        findViewById(R.id.example3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Login5Activity.class));
            }
        });

        findViewById(R.id.example6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Login6Activity.class));
            }
        });

        final TextView tvCache = (TextView) findViewById(R.id.tv_cache);
        findViewById(R.id.bt_cache).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpService service = HttpRequests.getinstance(getApplicationContext()).getHttpService();
                Call<HomeBean> call = service.home();
                call.enqueue(new Callback<HomeBean>() {
                    @Override
                    public void onResponse(Call<HomeBean> call, Response<HomeBean> response) {

                        boolean successful = response.isSuccessful();
                        HomeBean body = response.body();
                        LogUtil.e(successful + "" + body);
                        if (body != null){
                            List<HomeBean.HomeTopicBean> list = body.getHomeTopic();
                            LogUtil.e(body.toString());
                            tvCache.setText("state: " + successful + ",   body:  " + body.toString());
                        }

                    }

                    @Override
                    public void onFailure(Call<HomeBean> call, Throwable t) {
                        LogUtil.e(t.getMessage() + "网络错误");
                    }
                });

            }
        });
    }
}
