package com.rkm.fizz.socialnetwork.page.model;

import com.rkm.fizz.socialnetwork.page.SocialNetwork;
import com.rkm.fizz.socialnetwork.user.SocialUser;

/**
 * Author   : kanilturgut
 * Date     : 18/05/14
 * Time     : 17:00
 */
public class Foursquare extends SocialNetwork{

    SocialUser socialUser;
    String foursquarePost;
    String imageOfPlace;
    boolean isMayor;

    public SocialUser getSocialUser() {
        return socialUser;
    }

    public void setSocialUser(SocialUser socialUser) {
        this.socialUser = socialUser;
    }

    public String getFoursquarePost() {
        return foursquarePost;
    }

    public void setFoursquarePost(String foursquarePost) {
        this.foursquarePost = foursquarePost;
    }

    public String getImageOfPlace() {
        return imageOfPlace;
    }

    public void setImageOfPlace(String imageOfPlace) {
        this.imageOfPlace = imageOfPlace;
    }

    public boolean isMayor() {
        return isMayor;
    }

    public void setMayor(boolean isMayor) {
        this.isMayor = isMayor;
    }

    @Override
    public int getPageType() {
        return PAGE_TYPE_FOURSQUARE;
    }
}
