package com.example.timertask;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private TextView mTextView2;

    private Handler mHandler = new Handler();

    private SendSmsTimerUtil mCountDownTimerUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.tv);
        mTextView2 = (TextView) findViewById(R.id.tv2);

        mHandler.post(new MyResidualTimerTask(1 * 10));

        mCountDownTimerUtil = new SendSmsTimerUtil(mTextView2, 60000, 1000, R.color.whiteBg, R.color.whiteBg);
        mCountDownTimerUtil.start();
    }

    private class MyResidualTimerTask implements Runnable {

        private int time;

        public MyResidualTimerTask(int time) {
            this.time = time;
        }

        @Override
        public void run() {
            time = time - 1;

            int m = time / 60;
            int s = time % 60;
            mTextView.setText("支付剩余时间:" + m + "分" + s + "秒");
            if (time == 0) {
                mTextView.setEnabled(false);
                return;
            }
            mHandler.postDelayed(this, 999);
        }
    }
}
