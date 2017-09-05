package com.example.myapplicationdemo.example1;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplicationdemo.R;
import com.example.myapplicationdemo.example1.base.BaseActivity;

public class Login1Activity extends BaseActivity<LoginView,LoginPresenter> implements LoginView, View.OnClickListener {

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

    }

    @Override
    public LoginPresenter createPresneter() {
        return new LoginPresenter();
    }

    @Override
    public LoginView createView() {
        return this;
    }

    @Override
    public void onLoginResult(String result) {
        Toast.makeText(Login1Activity.this, result, Toast.LENGTH_LONG).show();
    }

    @Override
    public void failure() {

    }

    @Override
    public void onClick(View view) {
        getPresneter().login(username.getText().toString(), password.getText().toString());
    }
}
