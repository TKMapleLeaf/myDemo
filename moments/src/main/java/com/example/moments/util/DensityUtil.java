package com.example.moments.util;

import android.content.Context;

/**
 * px dip 转换工具
 *
 */
public class DensityUtil {

	public static int dip2px(Context context, float dipValue) {

		final float scale = context.getResources().getDisplayMetrics().density;

		return (int) (dipValue * scale + 0.5f);

	}

	public static int px2dip(Context context, float pxValue) {

		final float scale = context.getResources().getDisplayMetrics().density;

		return (int) (pxValue / scale + 0.5f);

	}

	public static int dp2px(Context mContext, float dp) {
		final float scale = mContext.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

}