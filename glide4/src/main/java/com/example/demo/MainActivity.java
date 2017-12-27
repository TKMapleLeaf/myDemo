package com.example.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String url = "http://img2.niutuku.com/desk/130220/30/30-niutuku.com-999.jpg";
    String gif = "http://img.zcool.cn/community/018dd95983d5010000002129143892.gif";
    String loading = "http://img.zcool.cn/community/018cf6554251780000019ae970ca87.jpg";
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

     /*   Button bt1 = findViewById(R.id.button);
        Button bt2 = findViewById(R.id.button2);
        Button bt3 = findViewById(R.id.button3);
        Button bt4 = findViewById(R.id.button4);
        Button bt5 = findViewById(R.id.button5);
        Button bt6 = findViewById(R.id.button6);
        Button bt7 = findViewById(R.id.button7);
        Button bt8 = findViewById(R.id.button8);
        Button bt9 = findViewById(R.id.button9);
        iv = findViewById(R.id.imageView);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);
        bt6.setOnClickListener(this);
        bt7.setOnClickListener(this);
        bt8.setOnClickListener(this);
        bt9.setOnClickListener(this);
        bt4.setText("占位图");
        bt5.setText("图片变换");
        bt6.setText("模糊黑白");
        bt7.setText("圆角");*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                Glide.with(this)
                        .load(url)
                        .into(iv);
                break;
            case R.id.button2:
               /* GlideApp.with(this)
                        .load(url)
                        .into(iv);*/
                break;
            case R.id.button3:
                Glide.with(this)
                        .load(gif)
                        .into(iv);
                break;
            case R.id.button4:
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(R.mipmap.loading)
                        .error(R.mipmap.error);
                Glide.with(this)
                        .load(url)
                        .apply(requestOptions)
                        .into(iv);
                break;
            case R.id.button5:
                RequestOptions options = new RequestOptions()
                        .placeholder(R.mipmap.loading)
                        .centerCrop()
                        .circleCrop()
                        .fitCenter()
                        .error(R.mipmap.error);
                Glide.with(this)
                        .load(url)
                        .apply(options)
                        .into(iv);
                break;
            case R.id.button6:
                RequestOptions option = new RequestOptions()
                        .transforms(new BlurTransformation(), new GrayscaleTransformation());
                Glide.with(this)
                        .load(url)
                        .apply(option)
                        .into(iv);
                break;
            case R.id.button7:
                Glide.with(this)
                        .load(url)
                        .apply(bitmapTransform(new RoundedCornersTransformation(45, 0,
                                RoundedCornersTransformation.CornerType.ALL)))
                        .into(iv);
                break;
            case R.id.button8:
                break;
            case R.id.button9:
                break;
            default:
                break;
        }
    }
}
