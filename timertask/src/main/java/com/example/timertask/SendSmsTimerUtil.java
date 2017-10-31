package com.example.timertask;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/11/28 0031.
 * 倒计时功能
 */
public class SendSmsTimerUtil extends CountDownTimer {
    private int inFuture;
    private int downInterval;
    private TextView mTextView;

    public SendSmsTimerUtil(TextView textView, long millisInFuture, long countDownInterval, int inFuture, int downInterval) {
        super(millisInFuture, countDownInterval);
        this.mTextView = textView;
        this.inFuture=inFuture;
        this.downInterval=downInterval;
    }

    public void onTick(long millisUntilFinished) {
        mTextView.setClickable(false);
        mTextView.setText(millisUntilFinished / 1000 + "秒后可重发");
        mTextView.setBackgroundResource(downInterval);

        SpannableString spannableString = new SpannableString(mTextView.getText().toString());
        ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
        //设置秒数为红色
        if (millisUntilFinished/1000 > 9) {
            spannableString.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        } else {
            spannableString.setSpan(span, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        mTextView.setText(spannableString);
    }

    @Override
    public void onFinish() {
        mTextView.setText("重新获取");
        mTextView.setClickable(true);
        mTextView.setBackgroundResource(inFuture);
    }
}