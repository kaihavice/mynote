package com.xuyazhou.mynote.common.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 
 * 显示Toast的工具类
 */
public class ShowToast {

	/**
	 * 
	 * 显示短时间Toast
	 */
	public static void Short(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	public static void Short(Context context, int resId) {
		Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 
	 * 显示长时间Toast
	 */
	public static void Long(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

	public static void Long(Context context, int resId) {
		Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
	}

}
