package com.example.myapplicationdemo.example5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplicationdemo.R;
import com.example.myapplicationdemo.example5.presenter.LoginPresenter;
import com.example.myapplicationdemo.example5.view.LoginView;

public class Login5Activity extends AppCompatActivity implements LoginView, View.OnClickListener {

    private ProgressBar progressBar;
    private EditText username;
    private EditText password;
    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = (ProgressBar) findViewById(R.id.progress);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        findViewById(R.id.button).setOnClickListener(this);

        mPresenter = new LoginPresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(username.getText().toString())) {
            username.setError(getString(R.string.username_error));
            return;
        }
        if (TextUtils.isEmpty(password.getText().toString())) {
            password.setError(getString(R.string.password_error));
            return;
        }
        mPresenter.login(username.getText().toString(), password.getText().toString());
    }

    @Override
    public void setUsernameError() {
        username.setError(getString(R.string.username_error));
    }

    @Override
    public void setPasswordError() {
        password.setError(getString(R.string.password_error));
    }

    @Override
    public void success() {
        Toast.makeText(Login5Activity.this, "登录成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failure() {
        Toast.makeText(Login5Activity.this, "登录失败", Toast.LENGTH_SHORT).show();
    }
}
