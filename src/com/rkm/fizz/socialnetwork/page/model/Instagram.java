package com.rkm.fizz.socialnetwork.page.model;

import com.rkm.fizz.socialnetwork.page.SocialNetwork;
import com.rkm.fizz.socialnetwork.user.SocialUser;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author   : kanilturgut
 * Date     : 18/05/14
 * Time     : 17:02
 */
public class Instagram extends SocialNetwork{

    SocialUser socialUser;
    String imageOfInstagram;
    String post;
    int numberOflike;

    public SocialUser getSocialUser() {
        return socialUser;
    }

    public void setSocialUser(SocialUser socialUser) {
        this.socialUser = socialUser;
    }

    public String getImageOfInstagram() {
        return imageOfInstagram;
    }

    public void setImageOfInstagram(String imageOfInstagram) {
        this.imageOfInstagram = imageOfInstagram;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public int getNumberOflike() {
        return numberOflike;
    }

    public void setNumberOflike(int numberOflike) {
        this.numberOflike = numberOflike;
    }

    @Override
    public int getPageType() {
        return PAGE_TYPE_INSTAGRAM;
    }

    public static Instagram fromJSON(JSONObject jsonObject) throws JSONException {

        Instagram instagram = new Instagram();

        SocialUser socialUser = new SocialUser();
        JSONObject userObj = jsonObject.getJSONObject("user");
        socialUser.setFullname(userObj.getString("full_name"));
        socialUser.setAvatar(userObj.getString("profile_picture"));
        socialUser.setUsername(userObj.getString("username"));
        instagram.setSocialUser(socialUser);

        instagram.setPost(jsonObject.getString("text"));
        instagram.setImageOfInstagram(jsonObject.getString("image"));

        return instagram;
    }
}
