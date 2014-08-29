package com.kanilturgut.fizz;

import com.kanilturgut.fizz.model.SocialNetwork;

import java.util.LinkedList;
import java.util.List;

/**
 * Author   : kanilturgut
 * Date     : 11/06/14
 * Time     : 22:57
 */
public class MyQueue {

    private final int size = 5000;

    private static MyQueue ourInstance = new MyQueue();

    public static MyQueue getInstance() {
        return ourInstance;
    }

    private List<SocialNetwork> list = null;

    private MyQueue() {
        list = new LinkedList<SocialNetwork>();
    }

    /**
     * @param socialNetwork Adds socialNetwork object to end of list
     */
    public void offer(SocialNetwork socialNetwork) {
        if (list.size() < size)
            if (socialNetwork.getDisplayType() == SocialNetwork.DISPLAY_TYPE_NORMAL || socialNetwork.getDisplayType() == SocialNetwork.DISPLAY_TYPE_PROMOTED)
                list.add(socialNetwork);
    }

    /**
     *
     * @param socialNetwork Adds params to given index of list
     * @param index index of list
     */
    public void offerToIndex(SocialNetwork socialNetwork, int index) {
        list.add(index, socialNetwork);
    }

    /**
     * @param socialNetwork Adds params to second index of list
     */
    public void offerToSecond(SocialNetwork socialNetwork) {
        if (list.size() == size)
            list.remove(size - 1);

        if (socialNetwork.getDisplayType() == SocialNetwork.DISPLAY_TYPE_NORMAL || socialNetwork.getDisplayType() == SocialNetwork.DISPLAY_TYPE_PROMOTED)
            list.add(1, socialNetwork);
    }

    /**
     * @return Deletes the first item of list and returns it
     */
    public SocialNetwork poll() {
        return list.remove(0);
    }

    /**
     * @return Deletes first item and adds it to end of the list
     */
    public SocialNetwork moveToEnd() {

        SocialNetwork tmp = poll();

        if (tmp.getType() != SocialNetwork.TYPE_FOURSQUARE)
            offer(tmp);
        else {
            tmp.decreaseShowingCount();
            if (tmp.getShowingCount() > 0)
                offerToIndex(tmp, Constant.FOURSQUARE_POST_INDEX);
        }

        return tmp;
    }

    /**
     * @return First element of list, but not deletes
     */
    public SocialNetwork peek() {
        return list.get(0);
    }

    public SocialNetwork remove(SocialNetwork socialNetwork) {
        if (list.remove(socialNetwork))
            return socialNetwork;

        return null;
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list == null || list.size() == 0;
    }

    public boolean isContain(SocialNetwork socialNetwork) {
        return list.contains(socialNetwork);
    }

    public boolean isContain(String id) {
        for (SocialNetwork aList : list) {
            if (id.equals(aList.getId()))
                return true;
        }

        return false;
    }

    public SocialNetwork findPost(String id) throws NullPointerException{
        for (SocialNetwork socialNetwork: list)
            if (id.equals(socialNetwork.getId()))
                return socialNetwork;

        return null;
    }
}
