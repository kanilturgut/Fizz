package com.rkm.fizz.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.rkm.fizz.Queue;
import com.rkm.fizz.R;
import com.rkm.fizz.aquery.AQueryUtilities;
import com.rkm.fizz.fragment.FizzFragment;
import com.rkm.fizz.fragment.SplashFragment;
import com.rkm.fizz.socialnetwork.page.SocialNetwork;
import com.rkm.fizz.socialnetwork.page.model.Instagram;
import com.rkm.fizz.socialnetwork.page.model.Twitter;
import com.rkm.fizz.util.Logs;
import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends FragmentActivity {

    final String TAG = "MainActivity";
    Context context;
    FragmentManager fragmentManager = null;
    AQueryUtilities aQueryUtilities = null;


    Queue<Instagram> instagramQueue = new Queue<Instagram>();
    Queue<Twitter> twitterQueue = new Queue<Twitter>();

    boolean twitter = false;
    boolean instagram = false;

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

        String urlForTwitter = "http://onurcansert.com:3000/initialTweets";

        aQueryUtilities = AQueryUtilities.getInstance(context);
        AQuery aQuery = aQueryUtilities.aQuery;
        aQuery.ajax(urlForTwitter, JSONArray.class, new AjaxCallback<JSONArray>() {
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
                            twitterQueue.offer(Twitter.fromJSON(responseArray.getJSONObject(i)));
                        } catch (JSONException e) {
                            Logs.e(TAG, "ERROR occured on reading JSON response", e);
                        }
                    }

                }

                twitter = true;
                splashToFizz();
            }
        });

        String urlForInstagram = "http://onurcansert.com:3000/initialInstagramPosts";
        aQuery.ajax(urlForInstagram, JSONArray.class, new AjaxCallback<JSONArray>() {
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
                            instagramQueue.offer(Instagram.fromJSON(responseArray.getJSONObject(i)));
                        } catch (JSONException e) {
                            Logs.e(TAG, "ERROR occured on reading JSON response", e);
                        }
                    }

                }

                instagram = true;
                splashToFizz();
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

        if (twitter && instagram) {

            for (int i = 0; i < Math.max(twitterQueue.size(), instagramQueue.size()); i++) {
                if (!twitterQueue.isEmpty())
                    SocialNetwork.socialNetworkQueue.offer(twitterQueue.poll());
                if (!instagramQueue.isEmpty())
                    SocialNetwork.socialNetworkQueue.offer(instagramQueue.poll());
            }

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FizzFragment fizzFragment = new FizzFragment();
            fragmentTransaction.replace(R.id.frameMain, fizzFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //to hide system bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //to hide system bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
}
