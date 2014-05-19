package com.rkm.fizz.operation;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.rkm.fizz.Constant;
import com.rkm.fizz.R;
import com.rkm.fizz.fragment.CurrentFragment;
import com.rkm.fizz.fragment.FragmentConstants;
import com.rkm.fizz.fragment.LoadingFragment;
import com.rkm.fizz.socialnetwork.page.SocialNetwork;

/**
 * Author   : kanilturgut
 * Date     : 19/05/14
 * Time     : 19:25
 */
public class PageChangeController {

    static int index = 0;
    FragmentManager fragmentManager = null;
    static PageChangeController pageChangeController = null;

    private PageChangeController(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public static PageChangeController getInstance(FragmentManager fragmentManager) {
        if (pageChangeController == null)
            pageChangeController = new PageChangeController(fragmentManager);

        return pageChangeController;
    }


    public void startApp() {

        final int sizeOfArray = SocialNetwork.socialNetworks.size();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                currentToCurrent(SocialNetwork.socialNetworks.get(index));

                if (index == (sizeOfArray - 1))
                    index = 0;
                else
                    index++;

                loadingToLoading(SocialNetwork.socialNetworks.get(index));


                startApp();
            }
        }, Constant.PAGE_SHOW_TIME);
    }


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

    public void loadingToLoading(SocialNetwork socialNetwork) {
        LoadingFragment loadingFragment = new LoadingFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FragmentConstants.BUNDLE_SOCIAL_NETWORK_KEY, socialNetwork);
        loadingFragment.setArguments(bundle);

        if (fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.replace(R.id.frameLoading, loadingFragment);
            fragmentTransaction.commit();
        }
    }

}
