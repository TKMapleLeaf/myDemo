package com.example.androidnadaption.crop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.androidnadaption.R;

import java.io.FileOutputStream;


/**
 * 图片剪切
 */
public class CropActivity extends AppCompatActivity implements View.OnClickListener {
    private CropLayout mCropLayout;
    private static String mPath;
    protected RequestManager mImageLoader;

    public static void show(Activity activity, String path) {
        Intent intent = new Intent(activity, CropActivity.class);
        mPath = path;
        activity.startActivityForResult(intent, 0x04);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStatus(R.color.statusbar_white_bg, R.color.statusbar_bg);
        setContentView(R.layout.activity_crop);
        initView();
        initWidget();
        initData();
    }


    private void initView() {
        TextView tvCrop = (TextView) findViewById(R.id.tv_crop);
        TextView tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvCrop.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    private void initWidget() {
        setTitle("");
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        mCropLayout = (CropLayout) findViewById(R.id.cropLayout);
    }

    private void initData() {
        String url = mPath;
        getImageLoader().load(url)
                .fitCenter()
                .into(mCropLayout.getImageView());

        mCropLayout.setCropWidth(500);//设置裁剪宽度
        mCropLayout.setCropHeight(500);//设置裁剪高度
        mCropLayout.start();
    }


    private void initStatus(int color1, int color2) {
        int mode = StatusBarUtil.StatusBarLightMode(this);
        if (mode != 0) {
            StatusBarUtil.transparencyBar(this);
            StatusBarUtil.StatusBarLightMode(this, StatusBarUtil.StatusBarLightMode(this));
            StatusBarUtil.setStatusBarColor(this, color1);
        } else {
            StatusBarUtil.setStatusBarColor(this, color2);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_crop:
                Bitmap bitmap = null;
                FileOutputStream os = null;
                try {
                    bitmap = mCropLayout.cropBitmap();
                    String path = getFilesDir() + "/crop.jpg";
                    os = new FileOutputStream(path);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    os.flush();
                    os.close();

                    Intent intent = new Intent();
                    intent.putExtra("crop_path", path);
                    setResult(RESULT_OK, intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bitmap != null) bitmap.recycle();
                    StreamUtil.close(os);
                }
                break;
            case R.id.tv_cancel:
                finish();
                break;
        }
    }

    public synchronized RequestManager getImageLoader() {
        if (mImageLoader == null)
            mImageLoader = Glide.with(this);
        return mImageLoader;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
