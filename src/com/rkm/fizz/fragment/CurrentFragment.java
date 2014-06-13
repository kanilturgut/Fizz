package com.rkm.fizz.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;
import com.rkm.fizz.R;
import com.rkm.fizz.aquery.AQueryUtilities;
import com.rkm.fizz.component.CircularImageView;
import com.rkm.fizz.model.SocialNetwork;

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
                switch (socialNetwork.getType()) {
                    case SocialNetwork.TYPE_TWITTER:
                        view = inflater.inflate(R.layout.fragment_current_twitter, container, false);
                        twitterPage(socialNetwork, view);

                        break;
                    case SocialNetwork.TYPE_INSTAGRAM:
                        view = inflater.inflate(R.layout.fragment_current_instagram, container, false);
                        instagramPage(socialNetwork, view);
                        break;
                }
            }


        }

        return view;
    }

    private void twitterPage(SocialNetwork socialNetwork, View view) {

        TextView tvCurrentFragmentUserFullname = (TextView) view.findViewById(R.id.tvCurrentFragmentUserFullname);
        TextView tvCurrentFragmentTweet = (TextView) view.findViewById(R.id.tvCurrentFragmentTweet);
        CircularImageView civCurrentFragmentUserAvatar = (CircularImageView) view.findViewById(R.id.civCurrentFragmentUserAvatar);

        tvCurrentFragmentUserFullname.setText(socialNetwork.getUserFullname());
        tvCurrentFragmentTweet.setText(socialNetwork.getText());

        ImageOptions options = new ImageOptions();
        options.memCache = true;
        options.targetWidth = 0;
        Bitmap bitmap = aQuery.getCachedImage(socialNetwork.getProfileImage());
        if (bitmap == null)
            aQuery.id(civCurrentFragmentUserAvatar).image(socialNetwork.getProfileImage(), options);
        else
            civCurrentFragmentUserAvatar.setImageBitmap(bitmap);
    }


    private void instagramPage(SocialNetwork socialNetwork, View view) {

        TextView tvCurrentFragmentUserFullname = (TextView) view.findViewById(R.id.tvCurrentFragmentUserFullname);
        TextView tvCurrentFragmentInstagramPost = (TextView) view.findViewById(R.id.tvCurrentFragmentInstagramPost);
        CircularImageView civCurrentFragmentUserAvatar = (CircularImageView) view.findViewById(R.id.civCurrentFragmentUserAvatar);
        ImageView ivCurrentFragmentImageOfPost = (ImageView) view.findViewById(R.id.ivCurrentFragmentImageOfPost);

        tvCurrentFragmentUserFullname.setText(socialNetwork.getUserFullname());
        tvCurrentFragmentInstagramPost.setText(socialNetwork.getText());

        ImageOptions options = new ImageOptions();
        options.memCache = true;
        options.targetWidth = 0;

        Bitmap bitmap = aQuery.getCachedImage(socialNetwork.getProfileImage());
        if (bitmap == null)
            aQuery.id(civCurrentFragmentUserAvatar).image(socialNetwork.getProfileImage(), options);
        else
            civCurrentFragmentUserAvatar.setImageBitmap(bitmap);

        if (!socialNetwork.getImage().equals("")) {
            Bitmap bmp = aQuery.getCachedImage(socialNetwork.getImage());
            if (bmp != null) {
                ivCurrentFragmentImageOfPost.setVisibility(ImageView.VISIBLE);
                ivCurrentFragmentImageOfPost.setImageBitmap(bmp);
            } else {
                aQuery.id(ivCurrentFragmentImageOfPost).image(socialNetwork.getImage(), options);
            }

        }
    }
}
