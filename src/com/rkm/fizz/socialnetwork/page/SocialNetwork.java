package com.rkm.fizz.socialnetwork.page;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Author   : kanilturgut
 * Date     : 18/05/14
 * Time     : 16:53
 */
public class SocialNetwork implements PageType, Serializable{

    public static LinkedList<SocialNetwork> socialNetworks = new LinkedList<SocialNetwork>();

    @Override
    public int getPageType() {
        return -1;
    }
}
