package com.rkm.fizz.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rkm.fizz.R;
import com.rkm.fizz.operation.PageChangeController;
import com.rkm.fizz.socialnetwork.page.SocialNetwork;

/**
 * Author   : kanilturgut
 * Date     : 18.05.2014
 * Time     : 17:44
 */
public class FizzFragment extends Fragment{

    final String TAG = "FizzFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fizz, null);
        fizzToCurrent();
        fizzToLoading();

        PageChangeController pageChangeController = PageChangeController.getInstance(getFragmentManager());
        pageChangeController.startApp();

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

}
