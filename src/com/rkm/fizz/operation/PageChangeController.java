package com.rkm.fizz.operation;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.rkm.fizz.Constant;
import com.rkm.fizz.R;
import com.rkm.fizz.fragment.CurrentFragment;
import com.rkm.fizz.fragment.FollowUsFragment;
import com.rkm.fizz.fragment.FragmentConstants;
import com.rkm.fizz.fragment.LoadingFragment;
import com.rkm.fizz.socialnetwork.page.SocialNetwork;

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
    static int pastColor = Color.WHITE;

    static PageChangeController pageChangeController = null;

    private PageChangeController(FragmentManager fragmentManager, LinearLayout llBackgroundOfFizz, Context context) {
        this.fragmentManager = fragmentManager;
        this.llBackgroundOfFizz = llBackgroundOfFizz;
        this.context = context;

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

        PubnubController pubnubController = PubnubController.getInstance();
        pubnubController.subscribeToChannel();
    }

    public static PageChangeController getInstance(FragmentManager fragmentManager, LinearLayout llBackgroundOfFizz, Context context) {
        if (pageChangeController == null)
            pageChangeController = new PageChangeController(fragmentManager, llBackgroundOfFizz, context);

        return pageChangeController;
    }


    public void startFirstTime() {

        currentToCurrent(SocialNetwork.socialNetworkQueue.poll());
        loadingToLoading(SocialNetwork.socialNetworkQueue.peek());

        int newColor = colors[random.nextInt(6)];
        ColorDrawable[] colorDrawables = {new ColorDrawable(pastColor), new ColorDrawable(newColor)};
        pastColor = newColor;
        TransitionDrawable trans = new TransitionDrawable(colorDrawables);
        llBackgroundOfFizz.setBackground(trans);
        llBackgroundOfFizz.setPadding(20, 20, 20, 20);
        trans.startTransition(Constant.BACKGROUND_TRANSITION_TIME);

        startApp();
    }

    public void startApp() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                currentToCurrent(SocialNetwork.socialNetworkQueue.poll());
                loadingToLoading(SocialNetwork.socialNetworkQueue.peek());

                int newColor = colors[random.nextInt(6)];
                ColorDrawable[] colorDrawables = {new ColorDrawable(pastColor), new ColorDrawable(newColor)};
                pastColor = newColor;
                TransitionDrawable trans = new TransitionDrawable(colorDrawables);
                llBackgroundOfFizz.setBackground(trans);
                llBackgroundOfFizz.setPadding(20, 20, 20, 20);
                trans.startTransition(Constant.BACKGROUND_TRANSITION_TIME);

                startApp();

            }
        }, Constant.PAGE_SHOW_TIME);

    }


    public void currentToCurrent(final SocialNetwork socialNetwork) {
        CurrentFragment currentFragment = new CurrentFragment(llBackgroundOfFizz);
        Bundle bundle = new Bundle();
        bundle.putSerializable(FragmentConstants.BUNDLE_SOCIAL_NETWORK_KEY, socialNetwork);
        currentFragment.setArguments(bundle);

        if (fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit);
            fragmentTransaction.replace(R.id.frameCurrent, currentFragment);
            fragmentTransaction.commit();
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingToFollowUs(socialNetwork);
            }
        }, Constant.LOADING_FRAGMENT_SHOW_TIME);
    }

    public void loadingToLoading(SocialNetwork socialNetwork) {
        LoadingFragment loadingFragment = new LoadingFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FragmentConstants.BUNDLE_SOCIAL_NETWORK_KEY, socialNetwork);
        loadingFragment.setArguments(bundle);

        if (fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLoading, loadingFragment);
            fragmentTransaction.commit();
        }
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

}
