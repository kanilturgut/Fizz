package com.rkm.fizz.fragment;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;
import com.rkm.fizz.R;
import com.rkm.fizz.aquery.AQueryUtilities;
import com.rkm.fizz.component.CircularImageView;
import com.rkm.fizz.socialnetwork.page.SocialNetwork;
import com.rkm.fizz.socialnetwork.page.model.Instagram;
import com.rkm.fizz.socialnetwork.page.model.Twitter;

import java.util.Random;

/**
 * Author   : kanilturgut
 * Date     : 18.05.2014
 * Time     : 17:44
 */
public class CurrentFragment extends Fragment {

    final String TAG = "CurrentFragment";
    Context context = null;
    AQuery aQuery = null;
    LinearLayout llBackgroundOfFizz;

    public CurrentFragment(LinearLayout linearLayout) {
        this.llBackgroundOfFizz = linearLayout;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AQueryUtilities aQueryUtilities = AQueryUtilities.getInstance(context);
        aQuery = aQueryUtilities.aQuery;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = null;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            SocialNetwork socialNetwork = (SocialNetwork) bundle.getSerializable(FragmentConstants.BUNDLE_SOCIAL_NETWORK_KEY);
            if (socialNetwork != null) {
                switch (socialNetwork.getPageType()) {
                    case SocialNetwork.PAGE_TYPE_TWITTER:
                        view = inflater.inflate(R.layout.fragment_current_twitter, container, false);
                        twitterPage(socialNetwork, view);

                        break;
                    case SocialNetwork.PAGE_TYPE_INSTAGRAM:
                        view = inflater.inflate(R.layout.fragment_current_instagram, container, false);
                        instagramPage(socialNetwork, view);
                        break;
                }
            }


        }

        return view;
    }

    private void twitterPage(SocialNetwork socialNetwork, View view) {
        Twitter twitter = (Twitter) socialNetwork;

        TextView tvCurrentFragmentUserFullname = (TextView) view.findViewById(R.id.tvCurrentFragmentUserFullname);
        TextView tvCurrentFragmentTweet = (TextView) view.findViewById(R.id.tvCurrentFragmentTweet);
        CircularImageView civCurrentFragmentUserAvatar = (CircularImageView) view.findViewById(R.id.civCurrentFragmentUserAvatar);

        tvCurrentFragmentUserFullname.setText(twitter.getSocialUser().getFullname());
        tvCurrentFragmentTweet.setText(twitter.getContentOfTweet());

        ImageOptions options = new ImageOptions();
        options.memCache = true;
        options.targetWidth = 0;
        Bitmap bitmap = aQuery.getCachedImage(twitter.getSocialUser().getAvatar());
        if (bitmap == null)
            aQuery.id(civCurrentFragmentUserAvatar).image(twitter.getSocialUser().getAvatar(), options);
        else
            civCurrentFragmentUserAvatar.setImageBitmap(bitmap);


        TextView tvCounter = (TextView) view.findViewById(R.id.tvCounter);
        tvCounter.setText("(" + FizzFragment.count++ + ")");
    }


    private void instagramPage(SocialNetwork socialNetwork, View view) {
        Instagram instagram = (Instagram) socialNetwork;

        TextView tvCurrentFragmentUserFullname = (TextView) view.findViewById(R.id.tvCurrentFragmentUserFullname);
        TextView tvCurrentFragmentInstagramPost = (TextView) view.findViewById(R.id.tvCurrentFragmentInstagramPost);
        CircularImageView civCurrentFragmentUserAvatar = (CircularImageView) view.findViewById(R.id.civCurrentFragmentUserAvatar);
        ImageView ivCurrentFragmentImageOfPost = (ImageView) view.findViewById(R.id.ivCurrentFragmentImageOfPost);

        tvCurrentFragmentUserFullname.setText(instagram.getSocialUser().getFullname());
        tvCurrentFragmentInstagramPost.setText(instagram.getPost());

        ImageOptions options = new ImageOptions();
        options.memCache = true;
        options.targetWidth = 0;
        Bitmap bitmap = aQuery.getCachedImage(instagram.getSocialUser().getAvatar());
        if (bitmap == null)
            aQuery.id(civCurrentFragmentUserAvatar).image(instagram.getSocialUser().getAvatar(), options);
        else
            civCurrentFragmentUserAvatar.setImageBitmap(bitmap);

        Bitmap bmp = aQuery.getCachedImage(instagram.getImageOfInstagram());
        if (bmp != null) {
            ivCurrentFragmentImageOfPost.setVisibility(ImageView.VISIBLE);
            ivCurrentFragmentImageOfPost.setImageBitmap(bmp);
        } else {
            aQuery.id(ivCurrentFragmentImageOfPost).image(instagram.getImageOfInstagram(), options);
        }

        TextView tvCounter = (TextView) view.findViewById(R.id.tvCounter);
        tvCounter.setText("(" + FizzFragment.count++ + ")");
    }
}
