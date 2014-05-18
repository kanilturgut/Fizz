package com.rkm.fizz.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rkm.fizz.R;

/**
 * Created by kanilturgut on 18.05.2014.
 */
public class LoadingFragment extends Fragment{

    final String TAG = "LoadingFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loading, null);

        return view;
    }
}
