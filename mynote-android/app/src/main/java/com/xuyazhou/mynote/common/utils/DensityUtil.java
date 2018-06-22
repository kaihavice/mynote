package com.xuyazhou.mynote.common.utils;

import android.content.Context;
import android.util.TypedValue;

public class DensityUtil {

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dp2px(Context context, int dpValue) {

		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dpValue, context.getResources().getDisplayMetrics());
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dp(Context context, int pxValue) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,
				pxValue, context.getResources().getDisplayMetrics());
	}

	/**
	 * 根据手机的分辨率从 sp 的单位 转成为 px(像素)
	 */
	public static int sp2px(Context context, int dpValue) {

		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				dpValue, context.getResources().getDisplayMetrics());
	}

}
