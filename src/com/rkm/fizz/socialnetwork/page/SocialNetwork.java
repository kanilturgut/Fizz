package com.rkm.fizz.socialnetwork.page;

import com.rkm.fizz.Queue;

import java.io.Serializable;

/**
 * Author   : kanilturgut
 * Date     : 18/05/14
 * Time     : 16:53
 */
public class SocialNetwork implements PageType, Serializable{

    public static Queue<SocialNetwork> socialNetworkQueue = new Queue<SocialNetwork>();

    @Override
    public int getPageType() {
        return -1;
    }
}
