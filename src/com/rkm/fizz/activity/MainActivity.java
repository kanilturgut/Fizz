package com.rkm.fizz.activity;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;
import com.kanilturgut.mylib.AlertDialogManager;
import com.kanilturgut.mylib.ConnectionDetector;
import com.kanilturgut.mylib.Logs;
import com.rkm.fizz.MyQueue;
import com.rkm.fizz.R;
import com.rkm.fizz.aquery.AQueryUtilities;
import com.rkm.fizz.broadcastreceiver.ConnectivityReceiver;
import com.rkm.fizz.fragment.FizzFragment;
import com.rkm.fizz.fragment.SplashFragment;
import com.rkm.fizz.model.SocialNetwork;
import com.rkm.fizz.operation.PageChangeController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    final String TAG = "MainActivity";
    Context context;
    FragmentManager fragmentManager = null;
    AQueryUtilities aQueryUtilities = null;
    MyQueue myQueue = null;
    ConnectivityReceiver connectivityReceiver = null;

    List<SocialNetwork> twitterList = new LinkedList<SocialNetwork>();
    List<SocialNetwork> instagramList = new LinkedList<SocialNetwork>();
    List<SocialNetwork> foursquareList = new LinkedList<SocialNetwork>();

    boolean twitter = false;
    boolean instagram = false;
    boolean foursquare = true;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = this;

        if (!ConnectionDetector.isConnectingToInternet(context)) {
            AlertDialogManager.noInternetConnection(context);

        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        fragmentManager = getSupportFragmentManager();
        mainToSplash();

        JSONObject params = new JSONObject();
        try {
            params.put("hashtag", "fizz");
        } catch (JSONException e) {
            params = null;
            e.printStackTrace();
        }

        myQueue = MyQueue.getInstance();

        String urlForTwitter = "http://fizzapp.herokuapp.com/venue/getInitialTweets";

        aQueryUtilities = AQueryUtilities.getInstance(context);
        AQuery aQuery = aQueryUtilities.aQuery;
        aQuery.post(urlForTwitter, params, JSONArray.class, new AjaxCallback<JSONArray>() {
            @Override
            public void callback(String url, JSONArray responseArray, AjaxStatus status) {
                super.callback(url, responseArray, status);

                if (responseArray != null && responseArray.length() > 0) {

                    // add cookies to cookieList
                    if (status != null && status.getCookies().size() > 0) {
                        aQueryUtilities.cookieLinkedList.addAll(status.getCookies());
                    }

                    // add posts to list
                    for (int i = 0; i < responseArray.length(); i++) {
                        try {
                            twitterList.add(twitterList.size(), SocialNetwork.fromJSON(responseArray.getJSONObject(i)));
                        } catch (JSONException e) {
                            Logs.e(TAG, "ERROR occured on reading JSON response", e);
                        }
                    }

                }

                twitter = true;
                splashToFizz();
            }
        });

        String urlForInstagram = "http://fizzapp.herokuapp.com/venue/getInitialInstagramPosts";
        aQuery.post(urlForInstagram, params, JSONArray.class, new AjaxCallback<JSONArray>() {
            @Override
            public void callback(String url, JSONArray responseArray, AjaxStatus status) {
                super.callback(url, responseArray, status);

                if (responseArray != null && responseArray.length() > 0) {

                    // add cookies to cookieList
                    if (status != null && status.getCookies().size() > 0) {
                        aQueryUtilities.cookieLinkedList.addAll(status.getCookies());
                    }

                    // add posts to list
                    for (int i = 0; i < responseArray.length(); i++) {
                        try {
                            instagramList.add(instagramList.size(), SocialNetwork.fromJSON(responseArray.getJSONObject(i)));
                        } catch (JSONException e) {
                            Logs.e(TAG, "ERROR occured on reading JSON response", e);
                        }
                    }

                }

                instagram = true;
                splashToFizz();
            }
        });

        /*
        String urlForFoursquare = "http://fizzapp.herokuapp.com/venue/getInitialInstagramPosts";
        aQuery.post(urlForInstagram, params, JSONArray.class, new AjaxCallback<JSONArray>() {
            @Override
            public void callback(String url, JSONArray responseArray, AjaxStatus status) {
                super.callback(url, responseArray, status);

                if (responseArray != null && responseArray.length() > 0) {

                    // add cookies to cookieList
                    if (status != null && status.getCookies().size() > 0) {
                        aQueryUtilities.cookieLinkedList.addAll(status.getCookies());
                    }

                    // add posts to list
                    for (int i = 0; i < responseArray.length(); i++) {
                        try {
                            foursquareList.add(foursquareList.size(), SocialNetwork.fromJSON(responseArray.getJSONObject(i)));
                        } catch (JSONException e) {
                            Logs.e(TAG, "ERROR occured on reading JSON response", e);
                        }
                    }

                }

                foursquare = true;
                splashToFizz();
            }
        });
        */
    }

    public void mainToSplash() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SplashFragment splashFragment = new SplashFragment();
        fragmentTransaction.replace(R.id.frameMain, splashFragment);
        fragmentTransaction.commit();
    }

    public void splashToFizz() {

        if (twitter && instagram && foursquare && ConnectionDetector.isConnectingToInternet(context)) {

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
                AlertDialogManager.showAlertDialog(context, "Liste Boş", "Initial gelmedi");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //to hide system bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        if (connectivityReceiver == null)
            connectivityReceiver = new ConnectivityReceiver();

        registerReceiver(connectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }

    @Override
    protected void onStart() {
        super.onStart();

        //to hide system bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
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

        if (connectivityReceiver != null)
            unregisterReceiver(connectivityReceiver);
    }
}
