package com.kanilturgut.fizz.activity;

import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.androidquery.util.AQUtility;
import com.kanilturgut.fizz.MyQueue;
import com.kanilturgut.fizz.R;
import com.kanilturgut.fizz.aquery.AQueryUtilities;
import com.kanilturgut.fizz.broadcastreceiver.ConnectivityReceiver;
import com.kanilturgut.fizz.fragment.FizzFragment;
import com.kanilturgut.fizz.fragment.LoginFragment;
import com.kanilturgut.fizz.fragment.SplashFragment;
import com.kanilturgut.fizz.model.Advertisement;
import com.kanilturgut.fizz.model.SocialNetwork;
import com.kanilturgut.fizz.operation.PubnubController;
import com.kanilturgut.fizz.sharedpreference.MySharedPreferences;
import com.kanilturgut.fizz.task.LoginTask;
import com.kanilturgut.fizz.task.UpdateVenueLocationTask;
import com.kanilturgut.mylib.AlertDialogManager;
import com.kanilturgut.mylib.ConnectionDetector;
import com.kanilturgut.mylib.Logs;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    final String TAG = "MainActivity";
    Context context = this;
    static FragmentManager fragmentManager = null;
    AQueryUtilities aQueryUtilities = null;
    static MyQueue myQueue = null;
    ConnectivityReceiver connectivityReceiver = null;
    MySharedPreferences mySharedPreferences;

    public static List<SocialNetwork> twitterList = new LinkedList<SocialNetwork>();
    public static List<SocialNetwork> instagramList = new LinkedList<SocialNetwork>();
    public static List<SocialNetwork> promotedList = new LinkedList<SocialNetwork>();
    public static List<Advertisement> advertisementList = new LinkedList<Advertisement>();

    public static int orientation;
    private String[] loginInfo = null;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Set TAG of application
        new Logs("Fizz");

        fragmentManager = getSupportFragmentManager();
        myQueue = MyQueue.getInstance();

        checkConnectivity();

        mySharedPreferences = MySharedPreferences.getInstance(context);
        loginInfo = getUser();
        if (isPrefIsFull()) {

            hideSystemBar();
            registerConnectivityRegister();
            mainToSplash();

            aQueryUtilities = AQueryUtilities.getInstance(context);

            LoginTask loginTask = new LoginTask(context, true);
            loginTask.execute(loginInfo);
        } else {
            mainToLogin();
        }
    }

    private String[] getUser() {
        return mySharedPreferences.getFromSharePreferences();
    }

    private void checkConnectivity() {
        if (!ConnectionDetector.isConnectingToInternet(context)) {
            AlertDialogManager.noInternetConnection(context);
        }
    }

    private void registerConnectivityRegister() {
        if (connectivityReceiver == null && isPrefIsFull()) {
            connectivityReceiver = new ConnectivityReceiver();
            registerReceiver(connectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    public void mainToSplash() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SplashFragment splashFragment = new SplashFragment();
        fragmentTransaction.replace(R.id.frameMain, splashFragment);
        fragmentTransaction.commit();
    }

    public void mainToLogin() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LoginFragment loginFragment = new LoginFragment();
        fragmentTransaction.replace(R.id.frameMain, loginFragment);
        fragmentTransaction.commit();
    }

    public static void mainToFizz() {

        for (int i = 0; i < getMax(twitterList.size(), instagramList.size(), promotedList.size(), advertisementList.size()); i++) {
            if (i < twitterList.size())
                myQueue.offer(twitterList.get(i));

            if (i < instagramList.size())
                myQueue.offer(instagramList.get(i));

            if (i < promotedList.size())
                myQueue.offer(promotedList.get(i));

            if (i < advertisementList.size())
                myQueue.offer(advertisementList.get(i));

        }

        if (myQueue.size() > 0) {

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FizzFragment fizzFragment = new FizzFragment();
            fragmentTransaction.replace(R.id.frameMain, fizzFragment);
            fragmentTransaction.commit();
        } else {
            // TODO liste boş yani initialler gelmemiş demektir. Hallet

        }

        //Update venue location
        new UpdateVenueLocationTask().execute();

    }

    private static int getMax(int... integers) {
        int size = 0;
        for (int anInt : integers)
            if (anInt > size)
                size = anInt;

        return size;
    }

    @Override
    protected void onResume() {
        super.onResume();

        hideSystemBar();
        registerConnectivityRegister();
    }

    private void hideSystemBar() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    protected void onStart() {
        super.onStart();

        hideSystemBar();
    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (isTaskRoot()) {

            //clean the file cache with advance option
            long triggerSize = 3000000; //starts cleaning when cache size is larger than 3M
            long targetSize = 2000000;      //remove the least recently used files until cache size is less than 2M
            AQUtility.cleanCacheAsync(this, triggerSize, targetSize);
        }

        if (connectivityReceiver != null) {
            unregisterReceiver(connectivityReceiver);
            connectivityReceiver = null;
        }

        PubnubController.getInstance(context).unsubscribeToChannel();
        PubnubController.getInstance(context).killPubnupInstance();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private boolean isPrefIsFull() {
        return (loginInfo != null && loginInfo[0] != null && loginInfo[1] != null);
    }
}
