package com.rkm.fizz.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;
import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;
import com.rkm.fizz.R;
import com.rkm.fizz.aquery.AQueryUtilities;
import com.rkm.fizz.component.CircularImageView;
import com.rkm.fizz.socialnetwork.page.PageType;
import com.rkm.fizz.socialnetwork.page.SocialNetwork;
import com.rkm.fizz.socialnetwork.page.model.Twitter;

/**
 * Author   : kanilturgut
 * Date     : 18.05.2014
 * Time     : 17:44
 */
public class CurrentFragment extends Fragment{

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

        AQueryUtilities aQueryUtilities = AQueryUtilities.getInstance(context);
        aQuery = aQueryUtilities.aQuery;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current, null);
        TextView tvCurrentFragmentUserFullname = (TextView) view.findViewById(R.id.tvCurrentFragmentUserFullname);
        TextView tvCurrentFragmentUsername = (TextView) view.findViewById(R.id.tvCurrentFragmentUsername);
        TextView tvCurrentFragmentContentOfTweet = (TextView) view.findViewById(R.id.tvCurrentFragmentContentOfTweet);
        CircularImageView civCurrentFragmentUserAvatar = (CircularImageView) view.findViewById(R.id.civCurrentFragmentUserAvatar);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            SocialNetwork socialNetwork = (SocialNetwork) bundle.getSerializable(FragmentConstants.BUNDLE_SOCIAL_NETWORK_KEY);

            if (socialNetwork.getPageType() == PageType.PAGE_TYPE_TWITTER) {
                Twitter twitter = (Twitter) socialNetwork;

                tvCurrentFragmentUserFullname.setText(twitter.getSocialUser().getFullname() + "(" + FizzFragment.count++ + ")");
                tvCurrentFragmentUsername.setText("@" + twitter.getSocialUser().getUsername());
                tvCurrentFragmentContentOfTweet.setText(twitter.getContentOfTweet());

                ImageOptions options = new ImageOptions();
                options.memCache = true;
                options.targetWidth = 0;
                Bitmap bitmap = aQuery.getCachedImage(twitter.getSocialUser().getAvatar());
                if (bitmap == null)
                    aQuery.id(civCurrentFragmentUserAvatar).image(twitter.getSocialUser().getAvatar(), options);
                else
                    civCurrentFragmentUserAvatar.setImageBitmap(bitmap);
            }

        }

        return view;
    }
}
