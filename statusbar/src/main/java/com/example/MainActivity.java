package com.example;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.statusbar.R;
import com.example.statusbar.statusbar.Eyes;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Eyes.setStatusBarLightMode(this, ContextCompat.getColor(this, R.color.colorEndTheme));
    }

    public void tv1(View view){
        startActivity(new Intent(this,ScrimActivity.class));
    }

    public void tv2(View view){
        startActivity(new Intent(this,StatusbarActivity.class));
    }
    public void button1(View view){
        startActivity(new Intent(this,StartusbarForImgActivity.class));
    }
    public void button2(View view){
        startActivity(new Intent(this,FullScreenActivity.class));
    }
}
