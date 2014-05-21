package com.rkm.fizz.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.rkm.fizz.R;
import com.rkm.fizz.socialnetwork.page.PageType;
import com.rkm.fizz.socialnetwork.page.SocialNetwork;

/**
 * Author   : kanilturgut
 * Date     : 20/05/14
 * Time     : 20:09
 */
public class FollowUsFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follow_us, null);

        RelativeLayout rlFollowUsFragment = (RelativeLayout) view.findViewById(R.id.rlFollowUsFragment);
        TextView tvFollowUsFragment = (TextView) view.findViewById(R.id.tvFollowUsFragment);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            SocialNetwork socialNetwork = (SocialNetwork) bundle.getSerializable(FragmentConstants.BUNDLE_SOCIAL_NETWORK_KEY);

            if (socialNetwork.getPageType() == PageType.PAGE_TYPE_TWITTER) {
                rlFollowUsFragment.setBackgroundColor(getResources().getColor(R.color.instagram_background_start));
                tvFollowUsFragment.setText("#selfie hashtagine fotoğraflarınızı yollayabilirsiniz");
            } else if (socialNetwork.getPageType() == PageType.PAGE_TYPE_INSTAGRAM) {
                rlFollowUsFragment.setBackgroundColor(getResources().getColor(R.color.twitter_background_start));
                tvFollowUsFragment.setText("#OccupyCHP hashtagine tweet atabilirsiniz");
            }

        }

        return view;
    }
}