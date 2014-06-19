package com.rkm.fizz.model;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Author   : kanilturgut
 * Date     : 18/05/14
 * Time     : 16:53
 */
public class SocialNetwork implements Serializable {

    public static final int TYPE_TWITTER = 0;
    public static final int TYPE_INSTAGRAM = 1;
    public static final int TYPE_FOURSQUARE = 2;
    public static final int TYPE_FACEBOOK = 3;

    public static final int DISPLAY_TYPE_SHOW_NORMAL = 0;
    public static final int DISPLAY_TYPE_SHOW_ALLWAYS = 1;

    int type;
    int displayType;
    String text;
    String image;
    String profileImage;
    String userFullname;
    String username;
    String hashtag;
    String foreign_id;
    String id;
    String creationDate;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDisplayType() {
        return displayType;
    }

    public void setDisplayType(int displayType) {
        this.displayType = displayType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUserFullname() {
        return userFullname;
    }

    public void setUserFullname(String userFullname) {
        this.userFullname = userFullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public String getForeign_id() {
        return foreign_id;
    }

    public void setForeign_id(String foreign_id) {
        this.foreign_id = foreign_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public static SocialNetwork fromJSON(JSONObject jsonObject) {
        SocialNetwork socialNetwork = new SocialNetwork();
        socialNetwork.setType(jsonObject.optInt("type"));
        socialNetwork.setImage(jsonObject.optString("image"));
        socialNetwork.setDisplayType(jsonObject.optInt("displayImage"));
        socialNetwork.setText(jsonObject.optString("text"));
        socialNetwork.setProfileImage(jsonObject.optString("profileImage"));
        socialNetwork.setUserFullname(jsonObject.optString("userFullname"));
        socialNetwork.setUsername(jsonObject.optString("username"));
        socialNetwork.setHashtag(jsonObject.optString("hashtag"));
        socialNetwork.setForeign_id(jsonObject.optString("foreign_id"));
        socialNetwork.setId(jsonObject.optString("_id"));
        socialNetwork.setCreationDate(jsonObject.optString("creationDate"));

        return socialNetwork;
    }

}
