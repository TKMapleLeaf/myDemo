package com.example.demo;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * @author luhh on 2017/12/19.
 *         desc
 */

public class GlideUtil {

    public static void load(Context context,
                            String url,
                            ImageView imageView,
                            RequestOptions options) {
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public static void load(Context context,
                            String url,
                            ImageView imageView) {

        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.loading)
                .error(R.mipmap.error);

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }
}
