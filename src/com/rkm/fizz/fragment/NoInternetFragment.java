package com.rkm.fizz.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.rkm.fizz.R;

/**
 * Author   : kanilturgut
 * Date     : 20/06/14
 * Time     : 14:31
 */
public class NoInternetFragment extends Fragment {

    final String TAG = "NoInternetFragment";
    Context context = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_no_internet, container, false);
    }
}