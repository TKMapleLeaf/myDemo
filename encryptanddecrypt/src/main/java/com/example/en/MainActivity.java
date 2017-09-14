package com.example.en;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.en.abc.RSAUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
           RSAUtils.getKeys();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String ss = "aa";
        try {
            ss = RSAUtils.encryptByPrivatKey("你好了");
            Log.e("haha","6  " + ss);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String dd = RSAUtils.decryptByePublicKey(ss);
            Log.e("haha","7  " + dd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
