package com.rkm.fizz.util;

import android.util.Log;
import com.rkm.fizz.BuildConfig;

/**
 * Author   : kanilturgut
 * Date     : 18/05/14
 * Time     : 14:19
 */
public class Logs {

    public static void i(String TAG, String message) {
        if (BuildConfig.DEBUG)
            Log.i(TAG, message);
    }

    public static void e(String TAG, String message, Throwable throwable) {
        if (BuildConfig.DEBUG)
            Log.e(TAG, message, throwable);
    }

    public static void e(String TAG, String message) {
        if (BuildConfig.DEBUG)
            Log.e(TAG, message);
    }


    public static void d(String TAG, String message) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, message);
    }

}
