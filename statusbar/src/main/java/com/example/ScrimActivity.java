package com.example;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.example.statusbar.R;
import com.example.statusbar.statusbarutil.ScrimUtil;

public class ScrimActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrim);

        LinearLayout startBar = findViewById(R.id.startbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startBar.setBackground(
                    ScrimUtil.makeCubicGradientScrimDrawable(
                            getResources().getColor(R.color.colorStartTheme),
                            getResources().getColor(R.color.colorEndTheme),
                            //起始方向
                            Gravity.RIGHT));
        }
    }
}
