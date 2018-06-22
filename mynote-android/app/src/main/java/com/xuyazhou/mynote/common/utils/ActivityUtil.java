package com.xuyazhou.mynote.common.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;


public class ActivityUtil {


    //附带额外信息的跳转activity
    public static void moveToActivity(Context context, Class targetClass, Bundle bundle) {

        if (context == null || ((Activity)context).isFinishing()) {
            return;
        }
        Intent intent = new Intent(context, targetClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    //跳转目标activity
    public static void moveToActivity(Context activity, Class targetClass) {
        moveToActivity(activity, targetClass, null);
    }

    //带有回调的activity
    public static void moveToActivityForResult(Activity activity, Class targetClass, int requestCode, Bundle bundle) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        Intent intent = new Intent(activity, targetClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, requestCode);
    }

    public static void moveToActivityForResult(Activity activity, Class targetClass, int resultCode) {
        moveToActivityForResult(activity, targetClass, resultCode, null);
    }

    public static void moveToActivitySetType(Activity activity, Class targetClass, int type) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        Intent intent = new Intent(activity, targetClass);

        intent.putExtra("EventType", type);

        activity.startActivity(intent);
    }


    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public static boolean isAppOnForeground(Context context) {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }


    public static int getActivityType(Intent intent) {

        return intent.getIntExtra("EventType", 0);
    }
}
