package com.example.androidnadaption.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.example.androidnadaption.R;


/**
 * Created by TK on 2017/6/12.
 */

public class CircleProgressView extends View {

    private static final String TAG = "CircleProgressBar";

    private int mMaxProgress = 100;

    private float mProgress = 30;

    private final int mCircleLineStrokeWidth = 30;

    private final int mTxtStrokeWidth = 2;

    // 画圆所在的距形区域
    private final RectF mRectF;

    private final Paint mPaint;

    private final Context mContext;

    private String mTxtHint1;

    private String mTxtHint2;

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        mRectF = new RectF();
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = this.getWidth();
        int height = this.getHeight();

        if (width != height) {
            int min = Math.min(width, height);
            width = min;
            height = min;
        }

        // 设置画笔相关属性
        mPaint.setAntiAlias(true);
        //argb()方法的参数依次为透明度,红,绿,蓝的大小,可以理解为浓度,这里组合起来的就是
        mPaint.setColor(Color.rgb(0xe9, 0xe9, 0xe9));
        canvas.drawColor(Color.TRANSPARENT);
        mPaint.setStrokeWidth(mCircleLineStrokeWidth); //画笔样式为空心时，设置空心画笔的宽度
        mPaint.setStyle(Style.STROKE);
        //Paint.Style.FILL：填充内部 Paint.Style.FILL_AND_STROKE  ：填充内部和描边  Paint.Style.STROKE  ：描边

        // 位置
        mRectF.left = mCircleLineStrokeWidth / 2; // 左上角x
        mRectF.top = mCircleLineStrokeWidth / 2; // 左上角y
        mRectF.right = width - mCircleLineStrokeWidth / 2; // 左下角x
        mRectF.bottom = height - mCircleLineStrokeWidth / 2; // 右下角y

        // 绘制圆圈，进度条背景
        //1 指定圆弧的外轮廓矩形区域。
        // 2 圆弧起始角度，单位为度。
        // 3 圆弧扫过的角度，顺时针方向，单位为度,从右中间开始为零度。
        // 4 如果为True时，在绘制圆弧时将圆心包括在内，通常用来绘制扇形
        // 5 绘制圆弧的画板属性，如颜色，是否填充等。
        canvas.drawArc(mRectF, -90, 360, false, mPaint); //drawArc  绘制圆弧  drawCircle  绘制圆形
//        mPaint.setColor(Color.rgb(0xf8, 0x60, 0x30));
        mPaint.setColor(getResources().getColor(R.color.themeMain));
        canvas.drawArc(mRectF, -90, ((float) mProgress / mMaxProgress) * 360, false, mPaint);

        // 绘制进度文案显示
        mPaint.setStrokeWidth(mTxtStrokeWidth);
        String text = mProgress + "%";
        int textHeight = height / 5;
        mPaint.setTextSize(textHeight);
        mPaint.setColor(Color.GRAY);
        int textWidth = (int) mPaint.measureText(text, 0, text.length());
        mPaint.setStyle(Style.FILL);
        canvas.drawText(text, width / 2 - textWidth / 2, height / 2 + textHeight / 2, mPaint);

        if (!TextUtils.isEmpty(mTxtHint1)) {
            mPaint.setStrokeWidth(mTxtStrokeWidth);
            text = mTxtHint1;
            textHeight = height / 8;
            mPaint.setTextSize(textHeight);
//            mPaint.setColor(Color.rgb(0x99, 0x99, 0x99));
            mPaint.setColor(Color.GRAY);
            textWidth = (int) mPaint.measureText(text, 0, text.length());
            mPaint.setStyle(Style.FILL);
            canvas.drawText(text, width / 2 - textWidth / 2, height / 4 + textHeight / 2, mPaint);
        }

        if (!TextUtils.isEmpty(mTxtHint2)) {
            mPaint.setStrokeWidth(mTxtStrokeWidth);
            text = mTxtHint2;
            textHeight = height / 8;
            mPaint.setTextSize(textHeight);
            textWidth = (int) mPaint.measureText(text, 0, text.length());
            mPaint.setStyle(Style.FILL);
            canvas.drawText(text, width / 2 - textWidth / 2, 3 * height / 4 + textHeight / 2, mPaint);
        }
    }

    public int getMaxProgress() {
        return mMaxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.mMaxProgress = maxProgress;
    }

    public void setProgress(float progress) {
        this.mProgress = progress;
        this.invalidate();
    }

    public void setProgressNotInUiThread(float progress) {
        this.mProgress = progress;
        this.postInvalidate();
    }

    public String getmTxtHint1() {
        return mTxtHint1;
    }

    public void setmTxtHint1(String mTxtHint1) {
        this.mTxtHint1 = mTxtHint1;
    }

    public String getmTxtHint2() {
        return mTxtHint2;
    }

    public void setmTxtHint2(String mTxtHint2) {
        this.mTxtHint2 = mTxtHint2;
    }
}
