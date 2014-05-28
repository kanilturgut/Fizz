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
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;
import com.rkm.fizz.R;
import com.rkm.fizz.aquery.AQueryUtilities;
import com.rkm.fizz.component.CircularImageView;
import com.rkm.fizz.socialnetwork.page.PageType;
import com.rkm.fizz.socialnetwork.page.SocialNetwork;
import com.rkm.fizz.socialnetwork.page.model.Instagram;
import com.rkm.fizz.socialnetwork.page.model.Twitter;

/**
 * Author   : kanilturgut
 * Date     : 18.05.2014
 * Time     : 17:44
 */
public class LoadingFragment extends Fragment {

    final String TAG = "LoadingFragment";
    Context context = null;
    AQuery aQuery = null;


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
        View view = inflater.inflate(R.layout.fragment_loading, null);


        TextView tvLoadingFragmentNextUserName = (TextView) view.findViewById(R.id.tvLoadingFragmentNextUserName);
        CircularImageView circularImageView = (CircularImageView) view.findViewById(R.id.civLoadingFragmentNextUserAvatar);
        ImageView ivLoadingFragmentSocialMediaIcon = (ImageView) view.findViewById(R.id.ivLoadingFragmentSocialMediaIcon);
        RelativeLayout rlLoadingFragmentBackground = (RelativeLayout) view.findViewById(R.id.rlLoadingFragmentBackground);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            SocialNetwork socialNetwork = (SocialNetwork) bundle.getSerializable(FragmentConstants.BUNDLE_SOCIAL_NETWORK_KEY);

            if (socialNetwork.getPageType() == PageType.PAGE_TYPE_TWITTER) {
                Twitter twitter = (Twitter) socialNetwork;

                rlLoadingFragmentBackground.setBackground(getResources().getDrawable(R.drawable.loading_fragment_twitter_background));
                tvLoadingFragmentNextUserName.setText(twitter.getSocialUser().getFullname());

                ImageOptions options = new ImageOptions();
                options.memCache = true;
                options.targetWidth = 0;
                Bitmap bitmap = aQuery.getCachedImage(twitter.getSocialUser().getAvatar());
                if (bitmap == null)
                    aQuery.id(circularImageView).image(twitter.getSocialUser().getAvatar(), options);
                else
                    circularImageView.setImageBitmap(bitmap);

                ivLoadingFragmentSocialMediaIcon.setImageResource(R.drawable.triangle_twitter);

            } else if (socialNetwork.getPageType() == PageType.PAGE_TYPE_INSTAGRAM) {
                Instagram instagram = (Instagram) socialNetwork;

                rlLoadingFragmentBackground.setBackground(getResources().getDrawable(R.drawable.loading_fragment_instagram_background));
                tvLoadingFragmentNextUserName.setText(instagram.getSocialUser().getFullname());

                ImageOptions options = new ImageOptions();
                options.memCache = true;
                options.targetWidth = 0;
                Bitmap bitmap = aQuery.getCachedImage(instagram.getSocialUser().getAvatar());
                if (bitmap == null)
                    aQuery.id(circularImageView).image(instagram.getSocialUser().getAvatar(), options);
                else
                    circularImageView.setImageBitmap(bitmap);

                //aQuery.image(instagram.getImageOfInstagram(), options);

                ivLoadingFragmentSocialMediaIcon.setImageResource(R.drawable.triangle_instagram);
            }

        }

        return view;
    }
}
