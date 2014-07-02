package com.kanilturgut.fizz.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.kanilturgut.fizz.R;
import com.kanilturgut.fizz.activity.MainActivity;
import com.kanilturgut.fizz.aquery.AQueryUtilities;
import com.kanilturgut.fizz.model.Advertisement;

/**
 * Author   : kanilturgut
 * Date     : 02/07/14
 * Time     : 16:38
 */
public class AdvertisementFragment extends Fragment {

    final String TAG = "AdvertisementFragment";
    Context context = null;
    AQuery aQuery = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setRequestedOrientation(MainActivity.orientation);

        AQueryUtilities aQueryUtilities = AQueryUtilities.getInstance(context);
        aQuery = aQueryUtilities.aQuery;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advertisement, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Advertisement advertisement = (Advertisement) bundle.getSerializable(FragmentConstants.BUNDLE_SOCIAL_NETWORK_KEY);

            String url = "http://p1.pichost.me/i/9/1321574.jpg";

            ImageView ivAdvertisementFragment = (ImageView) view.findViewById(R.id.ivAdvertisementFragment);

            aQuery.id(ivAdvertisementFragment).image(url);

        }


        return view;
    }
}