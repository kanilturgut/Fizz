package com.kanilturgut.fizz.activity;

import android.content.Context;
import android.content.IntentFilter;
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
import com.kanilturgut.fizz.fragment.SplashFragment;
import com.kanilturgut.fizz.model.SocialNetwork;
import com.kanilturgut.fizz.operation.PageChangeController;
import com.kanilturgut.fizz.task.LoginTask;
import com.kanilturgut.mylib.AlertDialogManager;
import com.kanilturgut.mylib.ConnectionDetector;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    final String TAG = "MainActivity";
    Context context = this;
    static FragmentManager fragmentManager = null;
    AQueryUtilities aQueryUtilities = null;
    static MyQueue myQueue = null;
    ConnectivityReceiver connectivityReceiver = null;

    public static List<SocialNetwork> twitterList = new LinkedList<SocialNetwork>();
    public static List<SocialNetwork> instagramList = new LinkedList<SocialNetwork>();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        hideSystemBar();
        checkConnectivity();
        registerConnectivityRegister();

        fragmentManager = getSupportFragmentManager();
        myQueue = MyQueue.getInstance();

        mainToSplash();

        aQueryUtilities = AQueryUtilities.getInstance(context);

        LoginTask loginTask = new LoginTask(true);
        loginTask.execute();
    }

    private void checkConnectivity() {
        if (!ConnectionDetector.isConnectingToInternet(context)) {
            AlertDialogManager.noInternetConnection(context);
        }
    }

    private void registerConnectivityRegister() {
        if (connectivityReceiver == null) {
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

    public static void splashToFizz() {

        for (int i = 0; i < Math.max(twitterList.size(), instagramList.size()); i++) {
            if (i < twitterList.size())
                myQueue.offer(twitterList.get(i));

            if (i < instagramList.size())
                myQueue.offer(instagramList.get(i));

            //if (i < foursquareList.size())
            //    myQueue.offer(foursquareList.get(i));
        }

        if (myQueue.size() > 0) {

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FizzFragment fizzFragment = new FizzFragment();
            fragmentTransaction.replace(R.id.frameMain, fizzFragment);
            fragmentTransaction.commit();
        } else {
            // TODO liste boş yani initialler gelmemiş demektir. Hallet

        }
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

        PageChangeController.cancelAllRunnables();
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
    }
}
