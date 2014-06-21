package com.kanilturgut.fizz.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import com.kanilturgut.fizz.model.User;

/**
 * Author   : kanilturgut
 * Date     : 13/06/14
 * Time     : 08:54
 */
public class MySharedPreferences {

    Context context = null;
    static MySharedPreferences mySharedPreferences = null;
    static SharedPreferences sp = null;

    // name of shared preferences
    public static String PREFERENCE_NAME = "fizz_preferences";

    public final static String PREF_EMAIL = "email";
    public final static String PREF_PASSWORD = "password";

    private MySharedPreferences(Context context) {
        this.context = context;
        sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static MySharedPreferences getInstance(Context context) {
        if (mySharedPreferences == null)
            mySharedPreferences = new MySharedPreferences(context);

        return mySharedPreferences;
    }

    public void saveToSharedPreferences(String email, String password) {

        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PREF_EMAIL, email);
        editor.putString(PREF_PASSWORD, password);
        editor.commit();
    }

    public User getFromSharePreferences() {
        User user = new User();
        user.setEmail(sp.getString(PREF_EMAIL, null));
        user.setPassword(sp.getString(PREF_PASSWORD, null));

        return user;
    }

}
