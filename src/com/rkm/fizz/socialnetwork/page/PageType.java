package com.rkm.fizz.socialnetwork.page;

/**
 * Author   : kanilturgut
 * Date     : 18/05/14
 * Time     : 16:51
 */
public interface PageType {

    public static final int PAGE_TYPE_TWITTER = 1;
    public static final int PAGE_TYPE_FOURSQUARE = 2;
    public static final int PAGE_TYPE_INSTAGRAM = 3;
    public static final int PAGE_TYPE_FACEBOOK = 3;

    public int getPageType();

}
