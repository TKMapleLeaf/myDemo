package com.example;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.astatusbar.StatusBarColorManager;
import com.example.statusbar.R;

public class StatusbarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statusbar);

        StatusBarColorManager statusBarColorManager = new StatusBarColorManager(this);
        statusBarColorManager.setStatusBarColor(Color.WHITE, false, true);
    }
}
