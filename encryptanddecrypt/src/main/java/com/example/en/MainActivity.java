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

        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDzLtcxM9+f/qH0K1e38u2gTLu+sB2AOgUmrj3pUO2Rmlb/ch9wRUU08KfHozArBIL1Dj5xgyC89mWbADjmbI/um9m8z7AGy9kGaL6J0uQ2oFmIqeiqlLr5a4s304Cb24SEikeLtWWBeE01kfIOsi32Y8w5Yuc7yv5JZSFoO9kwvQIDAQAB";

        String str = "8n4q8RwlJKrmxuktAPkAGMJDDigYlhmHIAYgxa/JHlll5RM7L90IfqgxDmQOExrmH8/kFp5Ot+ivaB5d9UKOtU5WAVsATJjJ7o0+U40kc5fLX6PRVYIB52agJXBzDRYdJnzQxovex/1aqQi6ZrLSpKTw1Ml0CIGXeJ+/bhCgesA=";

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
