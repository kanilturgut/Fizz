package com.kanilturgut.fizz.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.kanilturgut.fizz.R;
import com.kanilturgut.fizz.activity.MainActivity;

/**
 * Author   : kanilturgut
 * Date     : 18.05.2014
 * Time     : 17:45
 */
public class SplashFragment extends Fragment {

    final String TAG = "SplashFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setRequestedOrientation(MainActivity.orientation);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_splash, null);

        return view;
    }
}
