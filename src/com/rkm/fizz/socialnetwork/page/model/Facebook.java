package com.rkm.fizz.socialnetwork.page.model;

import com.rkm.fizz.socialnetwork.page.SocialNetwork;
import com.rkm.fizz.socialnetwork.user.SocialUser;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Author   : kanilturgut
 * Date     : 18/05/14
 * Time     : 17:03
 */
public class Facebook extends SocialNetwork implements Serializable{

    SocialUser socialUser;
    String imageOfPost;
    String contentOfPost;

    public SocialUser getSocialUser() {
        return socialUser;
    }

    public void setSocialUser(SocialUser socialUser) {
        this.socialUser = socialUser;
    }

    public String getImageOfPost() {
        return imageOfPost;
    }

    public void setImageOfPost(String imageOfPost) {
        this.imageOfPost = imageOfPost;
    }

    public String getContentOfPost() {
        return contentOfPost;
    }

    public void setContentOfPost(String contentOfPost) {
        this.contentOfPost = contentOfPost;
    }

    @Override
    public int getPageType() {
        return PAGE_TYPE_FACEBOOK;
    }

    public static Facebook fromJSON(JSONObject jsonObject) {
        Facebook facebook = new Facebook();

        SocialUser socialUser = new SocialUser();
        socialUser.setFullname(jsonObject.optString("name"));
        socialUser.setAvatar(jsonObject.optString("profileImage"));
        socialUser.setUsername(jsonObject.optString("screen_name"));
        facebook.setSocialUser(socialUser);
        
        facebook.setImageOfPost(jsonObject.optString("facebook_image"));
        facebook.setContentOfPost(jsonObject.optString("text"));

        return facebook;
    }
}
