package com.rkm.fizz.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rkm.fizz.R;
import com.rkm.fizz.socialnetwork.page.SocialNetwork;

/**
 * Created by kanilturgut on 18.05.2014.
 */
public class FizzFragment extends Fragment{

    final String TAG = "FizzFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fizz, null);

        fizzToCurrent();
        fizzToLoading();

        return view;
    }

    public void fizzToCurrent() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        CurrentFragment currentFragment = new CurrentFragment();
        fragmentTransaction.replace(R.id.frameCurrent, currentFragment);
        fragmentTransaction.commit();
    }

    public void fizzToLoading() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        LoadingFragment loadingFragment = new LoadingFragment();
        fragmentTransaction.replace(R.id.frameLoading, loadingFragment);
        fragmentTransaction.commit();
    }

    public void currentToCurrent() {
        CurrentFragment currentFragment = new CurrentFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FragmentConstants.BUNDLE_SOCIAL_NETWORK_KEY, new SocialNetwork());
        currentFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameCurrent, currentFragment);
        fragmentTransaction.commit();
    }

    public void loadingToLoading() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        LoadingFragment loadingFragment = new LoadingFragment();
        fragmentTransaction.replace(R.id.frameLoading, loadingFragment);
        fragmentTransaction.commit();
    }
}
