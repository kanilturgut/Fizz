package com.kanilturgut.fizz.operation;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.LinearLayout;
import com.kanilturgut.mylib.ConnectionDetector;
import com.kanilturgut.fizz.Constant;
import com.kanilturgut.fizz.MyQueue;
import com.kanilturgut.fizz.R;
import com.kanilturgut.fizz.fragment.*;
import com.kanilturgut.fizz.model.SocialNetwork;

import java.util.Random;

/**
 * Author   : kanilturgut
 * Date     : 19/05/14
 * Time     : 19:25
 */
public class PageChangeController {

    FragmentManager fragmentManager = null;
    LinearLayout llBackgroundOfFizz;
    Context context;
    int[] colors;
    Random random;
    MyQueue myQueue;
    Drawable[] drawables;

    static PageChangeController pageChangeController = null;

    static Handler handler = null;
    static Runnable changePageRunnable = null;
    static Runnable followUsRunnable = null;

    private PageChangeController(FragmentManager fragmentManager, LinearLayout llBackgroundOfFizz, Context context) {
        this.fragmentManager = fragmentManager;
        this.llBackgroundOfFizz = llBackgroundOfFizz;
        this.context = context;

        myQueue = MyQueue.getInstance();
        createColors();

        PubnubController.getInstance().subscribeToChannel();

        handler = new Handler();
        changePageRunnable = new Runnable() {
            @Override
            public void run() {
                changePage();
            }
        };
    }

    public static PageChangeController getInstance(FragmentManager fragmentManager, LinearLayout llBackgroundOfFizz, Context context) {
        if (pageChangeController == null)
            pageChangeController = new PageChangeController(fragmentManager, llBackgroundOfFizz, context);

        return pageChangeController;
    }

    public void changePage() {

        if (ConnectionDetector.isConnectingToInternet(context)) {
            currentToCurrent(myQueue.moveToEnd());
            loadingToLoading(myQueue.peek());
        } else {
            //mainToNoInternetConnection();
            currentToCurrent(null);
            loadingToLoading(null);
        }

        Drawable newDrawable = drawables[random.nextInt(6)];
        llBackgroundOfFizz.setBackground(newDrawable);

        startApp();
    }

    public void startApp() {

        handler.postDelayed(changePageRunnable, Constant.PAGE_SHOW_TIME);
    }

    /**
     *
     * @param socialNetwork current item
     */
    public void currentToCurrent(SocialNetwork socialNetwork) {
        CurrentFragment currentFragment = new CurrentFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FragmentConstants.BUNDLE_SOCIAL_NETWORK_KEY, socialNetwork);
        currentFragment.setArguments(bundle);

        if (fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit);
            fragmentTransaction.replace(R.id.frameCurrent, currentFragment);
            fragmentTransaction.commit();
        }
    }

    /**
     *
     * @param socialNetwork next item
     */
    public void loadingToLoading(final SocialNetwork socialNetwork) {
        final LoadingFragment loadingFragment = new LoadingFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FragmentConstants.BUNDLE_SOCIAL_NETWORK_KEY, socialNetwork);
        loadingFragment.setArguments(bundle);

        if (fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLoading, loadingFragment);
            fragmentTransaction.commit();
        }



        followUsRunnable = new Runnable() {
            @Override
            public void run() {
                loadingToFollowUs(socialNetwork);
            }
        };

        handler.postDelayed(followUsRunnable, Constant.LOADING_FRAGMENT_SHOW_TIME);
    }

    public void loadingToFollowUs(SocialNetwork socialNetwork) {
        FollowUsFragment loadingToFollowUs = new FollowUsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FragmentConstants.BUNDLE_SOCIAL_NETWORK_KEY, socialNetwork);
        loadingToFollowUs.setArguments(bundle);

        if (fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.replace(R.id.frameLoading, loadingToFollowUs);
            fragmentTransaction.commit();
        }
    }

    void createColors() {
        int red = context.getResources().getColor(R.color.new_red);
        int blue = context.getResources().getColor(R.color.new_blue);
        int aqua = context.getResources().getColor(R.color.new_aqua);
        int yellow = context.getResources().getColor(R.color.new_yellow);
        int green = context.getResources().getColor(R.color.new_green);
        int purple = context.getResources().getColor(R.color.new_purple);

        int happy_blue = context.getResources().getColor(R.color.happy_blue);
        int calm_green = context.getResources().getColor(R.color.calm_green);
        int yello = context.getResources().getColor(R.color.yello);
        int thai_curry = context.getResources().getColor(R.color.thai_curry);
        int burnt_red = context.getResources().getColor(R.color.burnt_red);

        colors = new int[]{happy_blue, calm_green, yello, thai_curry, burnt_red, purple};
        random = new Random();

        Drawable bg1 = context.getResources().getDrawable(R.drawable.bg1);
        Drawable bg2 = context.getResources().getDrawable(R.drawable.bg2);
        Drawable bg3 = context.getResources().getDrawable(R.drawable.bg3);
        Drawable bg4 = context.getResources().getDrawable(R.drawable.bg4);
        Drawable bg5 = context.getResources().getDrawable(R.drawable.bg5);
        Drawable bg6 = context.getResources().getDrawable(R.drawable.bg6);

        drawables = new Drawable[]{bg1, bg2, bg3, bg4, bg5, bg6};
    }

    public static void cancelAllRunnables() {

        if (handler != null) {
            if (changePageRunnable != null)
                handler.removeCallbacks(changePageRunnable);

            if (followUsRunnable != null)
                handler.removeCallbacks(followUsRunnable);

            handler = null;
        }
    }

}
