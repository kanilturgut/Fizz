package com.kanilturgut.fizz.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.kanilturgut.fizz.R;
import com.kanilturgut.fizz.activity.MainActivity;
import com.kanilturgut.fizz.model.SocialNetwork;
import com.kanilturgut.fizz.model.Venue;

/**
 * Author   : kanilturgut
 * Date     : 20/05/14
 * Time     : 20:09
 */
public class FollowUsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setRequestedOrientation(MainActivity.orientation);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follow_us, container, false);

        RelativeLayout rlFollowUsFragment = (RelativeLayout) view.findViewById(R.id.rlFollowUsFragment);
        TextView tvFollowUsFragment = (TextView) view.findViewById(R.id.tvFollowUsFragment);
        ImageView ivSocialMediaIcon = (ImageView) view.findViewById(R.id.ivSocialMediaIcon);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            SocialNetwork socialNetwork = (SocialNetwork) bundle.getSerializable(FragmentConstants.BUNDLE_SOCIAL_NETWORK_KEY);

            if (socialNetwork != null) {

                Venue venue = Venue.getInstance();

                if (socialNetwork.getType() == SocialNetwork.TYPE_TWITTER) {
                    rlFollowUsFragment.setBackgroundColor(getResources().getColor(R.color.twitter_follow_us_background));
                    tvFollowUsFragment.setText("#" + venue.getHashtag());
                    ivSocialMediaIcon.setImageResource(R.drawable.triangle_twitter);
                } else if (socialNetwork.getType() == SocialNetwork.TYPE_INSTAGRAM) {
                    rlFollowUsFragment.setBackgroundColor(getResources().getColor(R.color.instagram_follow_us_background));
                    tvFollowUsFragment.setText("#" + venue.getHashtag());
                    ivSocialMediaIcon.setImageResource(R.drawable.triangle_instagram);
                } else if (socialNetwork.getType() == SocialNetwork.TYPE_FOURSQUARE) {
                    rlFollowUsFragment.setBackgroundColor(getResources().getColor(R.color.foursquare_follow_us_background));
                    tvFollowUsFragment.setText(venue.getName());
                    ivSocialMediaIcon.setImageResource(R.drawable.triangle_foursquare);
                }

            } else {
                view.setVisibility(View.INVISIBLE);
            }
        }

        return view;
    }
}
