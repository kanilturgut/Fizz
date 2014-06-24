package com.kanilturgut.fizz.backend;

/**
 * Author   : kanilturgut
 * Date     : 20/06/14
 * Time     : 19:53
 */
public class HttpURL {

    public static final String domain = "http://fizzapp.herokuapp.com";

    public static final String LOGIN = "/auth";

    public static final String INITIAL_TWITTER = "/venue/getInitialTweets";
    public static final String INITIAL_INSTAGRAM = "/venue/getInitialInstagramPosts";
    public static final String INITIAL_PROMOTED_POSTS = "/venue/getPromotedVenuePosts";
    public static final String INITIAL_ADVERTISEMENT = "/ads/getInitialAdvertisements";

    public static final String GET_ONE_POST = "/venue/getVenuePostDetails";
    public static final String UPDATE_VENUE_LOCATION = "/venue/updateVenueLocation";




    public static String createURL(String url) {
        return domain + url;
    }
}
