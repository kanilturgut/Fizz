package com.kanilturgut.fizz.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.kanilturgut.fizz.R;
import com.kanilturgut.fizz.activity.MainActivity;
import com.kanilturgut.fizz.operation.PageChangeController;

/**
 * Author   : kanilturgut
 * Date     : 18.05.2014
 * Time     : 17:44
 */
public class FizzFragment extends Fragment {

    final String TAG = "FizzFragment";

    LinearLayout llBackgroundOfFizz;
    FrameLayout frameFizz;
    Context context;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setRequestedOrientation(MainActivity.orientation);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fizz, container, false);

        llBackgroundOfFizz = (LinearLayout) view.findViewById(R.id.llBackgroundOfFizz);
        frameFizz = (FrameLayout) view.findViewById(R.id.frameFizz);

        fizzToCurrent();
        fizzToLoading();

        PageChangeController pageChangeController = PageChangeController.getInstance(getFragmentManager(), llBackgroundOfFizz, frameFizz, context);
        pageChangeController.changePage();

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


    @Override
    public void onStop() {
        super.onStop();

        PageChangeController.cancelAllRunnables();
    }
}
