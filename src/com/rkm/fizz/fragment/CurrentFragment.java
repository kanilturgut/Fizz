package com.rkm.fizz.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rkm.fizz.R;
import com.rkm.fizz.socialnetwork.page.SocialNetwork;

/**
 * Created by kanilturgut on 18.05.2014.
 */
public class CurrentFragment extends Fragment{

    final String TAG = "CurrentFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        //SocialNetwork socialNetwork = (SocialNetwork) bundle.getSerializable(FragmentConstants.BUNDLE_SOCIAL_NETWORK_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current, null);

        return view;
    }
}
