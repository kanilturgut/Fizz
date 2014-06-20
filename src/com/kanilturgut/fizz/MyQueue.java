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
        if (list.size() < 5000)
            list.add(socialNetwork);
    }

    /**
     * @param socialNetwork Adds params to second index of list
     */
    public void offerToSecond(SocialNetwork socialNetwork) {
        if (list.size() == 5000)
            list.remove(4999);

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
        if (list == null)
            return true;
        else
            return list.size() == 0;
    }

    public boolean isContain(SocialNetwork socialNetwork) {
        return list.contains(socialNetwork);
    }

    public SocialNetwork findPost(String id) throws NullPointerException{
        for (SocialNetwork socialNetwork: list)
            if (id.equals(socialNetwork.getId()))
                return socialNetwork;

        return null;
    }
}
