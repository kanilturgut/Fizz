package com.rkm.fizz.socialnetwork.page.model;

import com.rkm.fizz.socialnetwork.page.SocialNetwork;
import com.rkm.fizz.socialnetwork.user.SocialUser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Author   : kanilturgut
 * Date     : 18/05/14
 * Time     : 16:50
 */
public class Twitter extends SocialNetwork implements Serializable{

    SocialUser socialUser;
    String hashTagOfTweet;
    String contentOfTweet;
    String imageOfTweet;

    public String getImageOfTweet() {
        return imageOfTweet;
    }

    public void setImageOfTweet(String imageOfTweet) {
        this.imageOfTweet = imageOfTweet;
    }

    public String getContentOfTweet() {
        return contentOfTweet;
    }

    public void setContentOfTweet(String contentOfTweet) {
        this.contentOfTweet = contentOfTweet;
    }

    public String getHashTagOfTweet() {
        return hashTagOfTweet;
    }

    public void setHashTagOfTweet(String hashTagOfTweet) {
        this.hashTagOfTweet = hashTagOfTweet;
    }

    public SocialUser getSocialUser() {
        return socialUser;
    }

    public void setSocialUser(SocialUser socialUser) {
        this.socialUser = socialUser;
    }

    @Override
    public int getPageType() {
        return PAGE_TYPE_TWITTER;
    }

    public static Twitter fromJSON(JSONObject jsonObject) throws JSONException {

        Twitter twitter = new Twitter();

        SocialUser socialUser = new SocialUser();
        socialUser.setFullname(jsonObject.optString("name"));
        socialUser.setAvatar(jsonObject.optString("profileImage"));
        socialUser.setUsername(jsonObject.optString("screen_name"));
        twitter.setSocialUser(socialUser);

        twitter.setContentOfTweet(jsonObject.optString("text"));
        twitter.setImageOfTweet(jsonObject.optString("tweetImage"));

        JSONArray jsonArray = jsonObject.getJSONArray("hashtags");
        twitter.setHashTagOfTweet(jsonArray.getJSONObject(0).getString("text"));

        return twitter;
    }
}
