package com.kanilturgut.fizz.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Author   : kanilturgut
 * Date     : 13/06/14
 * Time     : 08:54
 */
public class MySharedPreferences {

    Context context = null;
    static MySharedPreferences mySharedPreferences = null;
    SharedPreferences sp = null;

    // name of shared preferences
    public static String PREFERENCE_NAME = "fizz_preferences";



}
