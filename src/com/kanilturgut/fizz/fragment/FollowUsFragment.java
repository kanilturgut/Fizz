package com.kanilturgut.fizz.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.kanilturgut.fizz.R;
import com.kanilturgut.fizz.model.SocialNetwork;

/**
 * Author   : kanilturgut
 * Date     : 20/05/14
 * Time     : 20:09
 */
public class FollowUsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follow_us, null);

        RelativeLayout rlFollowUsFragment = (RelativeLayout) view.findViewById(R.id.rlFollowUsFragment);
        TextView tvFollowUsFragment = (TextView) view.findViewById(R.id.tvFollowUsFragment);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            SocialNetwork socialNetwork = (SocialNetwork) bundle.getSerializable(FragmentConstants.BUNDLE_SOCIAL_NETWORK_KEY);

            if (socialNetwork != null) {

                if (socialNetwork.getType() == SocialNetwork.TYPE_TWITTER) {
                    rlFollowUsFragment.setBackgroundColor(getResources().getColor(R.color.twitter_follow_us_background));
                    tvFollowUsFragment.setText("#" + socialNetwork.getHashtag() + " hashtagine tweet atabilirsiniz");
                } else if (socialNetwork.getType() == SocialNetwork.TYPE_INSTAGRAM) {
                    rlFollowUsFragment.setBackgroundColor(getResources().getColor(R.color.instagram_follow_us_background));
                    tvFollowUsFragment.setText("#" + socialNetwork.getHashtag() + " hashtagine fotoğraflarınızı yollayabilirsiniz");
                } else if (socialNetwork.getType() == SocialNetwork.TYPE_FOURSQUARE) {
                    rlFollowUsFragment.setBackgroundColor(getResources().getColor(R.color.foursquare_follow_us_background));
                    tvFollowUsFragment.setText("Mekanımızda check-in yapın, herkes burada olduğunuz görsün");
                }

            } else {
                view.setVisibility(View.INVISIBLE);
            }
        }

        return view;
    }
}
