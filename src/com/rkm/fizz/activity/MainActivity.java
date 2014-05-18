package com.rkm.fizz.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.rkm.fizz.R;
import com.rkm.fizz.fragment.FizzFragment;
import com.rkm.fizz.fragment.SplashFragment;
import com.rkm.fizz.util.Logs;

import org.json.JSONArray;

public class MainActivity extends FragmentActivity {

    Context context;
    FragmentManager fragmentManager = null;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = this;

        fragmentManager = getSupportFragmentManager();
        mainToSplash();

        String url = "http://onurcansert.com:3000/initialTweets";

        AQuery aQuery = new AQuery(this);
        aQuery.ajax(url, JSONArray.class, new AjaxCallback<JSONArray>() {
            @Override
            public void callback(String url, JSONArray object, AjaxStatus status) {
                super.callback(url, object, status);
                Logs.d("stri", object.toString());
            }
        });

    }

    public void mainToSplash() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SplashFragment splashFragment = new SplashFragment();
        fragmentTransaction.replace(R.id.frameMain, splashFragment);
        fragmentTransaction.commit();
    }

    public void splashToFizz() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FizzFragment fizzFragment = new FizzFragment();
        fragmentTransaction.replace(R.id.frameMain, fizzFragment);
        fragmentTransaction.commit();
    }
}
