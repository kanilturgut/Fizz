package com.kanilturgut.fizz.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;
import com.kanilturgut.fizz.R;
import com.kanilturgut.fizz.activity.MainActivity;
import com.kanilturgut.fizz.aquery.AQueryUtilities;
import com.kanilturgut.fizz.component.CircularImageView;
import com.kanilturgut.fizz.model.Advertisement;
import com.kanilturgut.fizz.model.SocialNetwork;

/**
 * Author   : kanilturgut
 * Date     : 18.05.2014
 * Time     : 17:44
 */
public class CurrentFragment extends Fragment {

    final String TAG = "CurrentFragment";
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
        getActivity().setRequestedOrientation(MainActivity.orientation);

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

                        if (socialNetwork.getImage() != null && !TextUtils.isEmpty(socialNetwork.getImage()))
                            view = inflater.inflate(R.layout.fragment_current_twitter_with_image, container, false);
                        else
                            view = inflater.inflate(R.layout.fragment_current_twitter, container, false);

                        twitterPage(socialNetwork, view);
                        break;
                    case SocialNetwork.TYPE_INSTAGRAM:
                        view = inflater.inflate(R.layout.fragment_current_instagram, container, false);
                        instagramPage(socialNetwork, view);
                        break;
                    case SocialNetwork.TYPE_FOURSQUARE:
                        view = inflater.inflate(R.layout.fragment_current_foursquare, container, false);
                        foursquarePage(socialNetwork, view);
                        break;
                    case SocialNetwork.TYPE_ADVERTISEMENT:
                        view = inflater.inflate(R.layout.fragment_advertisement, container, false);
                        advertisementPage(socialNetwork, view);
                }
            } else {
                view = inflater.inflate(R.layout.fragment_no_internet, container, false);
            }


        }

        return view;
    }

    private void twitterPage(SocialNetwork socialNetwork, View view) {

        TextView tvCurrentFragmentUserFullname = (TextView) view.findViewById(R.id.tvCurrentFragmentUserFullname);
        TextView tvCurrentFragmentTweet = (TextView) view.findViewById(R.id.tvCurrentFragmentTweet);
        CircularImageView civCurrentFragmentUserAvatar = (CircularImageView) view.findViewById(R.id.civCurrentFragmentUserAvatar);
        ImageView ivCurrentFragmentImageOfPost = (ImageView) view.findViewById(R.id.ivCurrentFragmentImageOfPost);

        tvCurrentFragmentUserFullname.setText(socialNetwork.getUserFullname());

        String tweetPost = socialNetwork.getText();
        if (tweetPost != null) {
            String[] twitterText = tweetPost.split(" ");
            StringBuilder stringBuilder = new StringBuilder();
            for (String str : twitterText) {
                if (str.contains("http://t.co/"))
                    continue;
                else
                    stringBuilder.append(str + " ");
            }

            tvCurrentFragmentTweet.setText(stringBuilder);
        }


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
                ivCurrentFragmentImageOfPost.setImageBitmap(bmp);
            } else {
                aQuery.id(ivCurrentFragmentImageOfPost).image(socialNetwork.getImage(), options);
            }

        }

    }

    private void foursquarePage(SocialNetwork socialNetwork, View view) {

        TextView tvCurrentFragmentUserFullname = (TextView) view.findViewById(R.id.tvCurrentFragmentUserFullname);
        TextView tvCurrentFragmentFoursquare = (TextView) view.findViewById(R.id.tvCurrentFragmentFoursquare);
        CircularImageView civCurrentFragmentUserAvatar = (CircularImageView) view.findViewById(R.id.civCurrentFragmentUserAvatar);

        tvCurrentFragmentUserFullname.setText(socialNetwork.getUserFullname());
        tvCurrentFragmentFoursquare.setText(getResources().getString(R.string.welcome) + socialNetwork.getUserFullname());

//        adjustFontSize(tvCurrentFragmentTweet, socialNetwork.getText());

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
//        tvCurrentFragmentInstagramPost.setText(socialNetwork.getText());
        adjustFontSize(tvCurrentFragmentInstagramPost, socialNetwork.getText());

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

    private void advertisementPage(SocialNetwork socialNetwork, View view) {

        Advertisement advertisement = (Advertisement) socialNetwork;
        String imageURL = "";
        if (MainActivity.orientation == Configuration.ORIENTATION_PORTRAIT)
            imageURL = advertisement.getVerticalImageUrl();
        else
            imageURL = advertisement.getHorizontalImageUrl();

        ImageView ivAdvertisementFragment = (ImageView) view.findViewById(R.id.ivAdvertisementFragment);

        ImageOptions options = new ImageOptions();
        options.memCache = true;
        options.targetWidth = 0;

        if (imageURL != null && !imageURL.isEmpty()) {
            Bitmap bitmap = aQuery.getCachedImage(imageURL);
            if (bitmap == null) {
                aQuery.id(ivAdvertisementFragment).image(imageURL, options);
            } else {
                ivAdvertisementFragment.setImageBitmap(bitmap);
            }
        }
    }

    private void adjustFontSize(TextView textView, String text) {
        if (textView != null && !text.isEmpty()) {

            float size = textView.getTextSize();

            if (text.length() >= 130)
                size -= 5;

            textView.setText(text);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);

        }
    }
}
