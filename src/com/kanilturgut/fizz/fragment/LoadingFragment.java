package com.kanilturgut.fizz.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;
import com.kanilturgut.fizz.R;
import com.kanilturgut.fizz.activity.MainActivity;
import com.kanilturgut.fizz.aquery.AQueryUtilities;
import com.kanilturgut.fizz.component.CircularImageView;
import com.kanilturgut.fizz.model.SocialNetwork;

/**
 * Author   : kanilturgut
 * Date     : 18.05.2014
 * Time     : 17:44
 */
public class LoadingFragment extends Fragment {

    final String TAG = "LoadingFragment";
    Context context = null;
    AQuery aQuery = null;
    Animation animation;

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

        if (MainActivity.orientation == Configuration.ORIENTATION_PORTRAIT)
            animation = AnimationUtils.loadAnimation(context, R.anim.move);
        else
            animation = AnimationUtils.loadAnimation(context, R.anim.move_yatay);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loading, container, false);


        TextView tvLoadingFragmentNextUserName = (TextView) view.findViewById(R.id.tvLoadingFragmentNextUserName);
        CircularImageView circularImageView = (CircularImageView) view.findViewById(R.id.civLoadingFragmentNextUserAvatar);
        ImageView ivLoadingFragmentSocialMediaIcon = (ImageView) view.findViewById(R.id.ivLoadingFragmentSocialMediaIcon);
        RelativeLayout rlLoadingFragmentBackground = (RelativeLayout) view.findViewById(R.id.rlLoadingFragmentBackground);

        LinearLayout animationView = (LinearLayout) view.findViewById(R.id.animateView);
        animationView.startAnimation(animation);

        View animationViewDevami = view.findViewById(R.id.animationViewDevami);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            final SocialNetwork socialNetwork = (SocialNetwork) bundle.getSerializable(FragmentConstants.BUNDLE_SOCIAL_NETWORK_KEY);

            if (socialNetwork != null) {
                //Generic
                tvLoadingFragmentNextUserName.setText(socialNetwork.getUserFullname());
                ivLoadingFragmentSocialMediaIcon.setImageResource(R.drawable.triangle_twitter);

                final ImageOptions options = new ImageOptions();
                options.memCache = true;
                options.targetWidth = 0;
                Bitmap bitmap = aQuery.getCachedImage(socialNetwork.getProfileImage());
                if (bitmap == null) {
                    aQuery.id(circularImageView).image(socialNetwork.getProfileImage(), options);
                } else {
                    circularImageView.setImageBitmap(bitmap);
                }


                if (socialNetwork.getType() == SocialNetwork.TYPE_TWITTER) {
                    animationViewDevami.setBackgroundColor(getResources().getColor(R.color.twitter_blue));
                    rlLoadingFragmentBackground.setBackground(getResources().getDrawable(R.drawable.loading_fragment_twitter_background));
                    ivLoadingFragmentSocialMediaIcon.setImageResource(R.drawable.triangle_twitter);

                } else if (socialNetwork.getType() == SocialNetwork.TYPE_INSTAGRAM) {

                    final CircularImageView civFake = (CircularImageView) view.findViewById(R.id.civFake);

                    animationViewDevami.setBackgroundColor(getResources().getColor(R.color.instagram_blue));
                    rlLoadingFragmentBackground.setBackground(getResources().getDrawable(R.drawable.loading_fragment_instagram_background));
                    ivLoadingFragmentSocialMediaIcon.setImageResource(R.drawable.triangle_instagram);

                    if (!socialNetwork.getImage().equals("")) {
                        Bitmap instagramImage = aQuery.getCachedImage(socialNetwork.getImage());
                        if (instagramImage == null)
                            aQuery.id(civFake).image(socialNetwork.getImage(), options);
                    }
                } else if (socialNetwork.getType() == SocialNetwork.TYPE_FOURSQUARE) {
                    animationViewDevami.setBackgroundColor(getResources().getColor(R.color.foursquare_blue));
                    rlLoadingFragmentBackground.setBackground(getResources().getDrawable(R.drawable.loading_fragment_foursquare_background));
                    ivLoadingFragmentSocialMediaIcon.setImageResource(R.drawable.triangle_foursquare);
                }

            } else {
                view.setVisibility(View.INVISIBLE);
            }
        }

        return view;
    }
}
