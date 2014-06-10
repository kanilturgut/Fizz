package com.rkm.fizz;

import java.util.LinkedList;

/**
 * Author   : kanilturgut
 * Date     : 20/05/14
 * Time     : 17:24
 */
public class Queue<T> {

    public LinkedList<T> linkedList;

    /**
     * Constructor of Queue
     */
    public Queue() {
        linkedList = new LinkedList<T>();
    }

    /**
     *
     * @param element T
     *
     * Adds element to end of queue
     */
    public void offer(T element) {
        linkedList.addLast(element);
    }

    /**
     *
     * @return T
     *
     * Deletes the top of the list element, and attaches it to the end of queue
     */
    public T poll() {
        T temp = linkedList.removeFirst();
        linkedList.offer(temp);

        return temp;
    }

    /**
     *
     * @return T
     *
     * Returns first elements of list, but not deletes it
     */
    public T peek() {
        return linkedList.getFirst();
    }

    /**
     *
     * @param element T
     * @return T
     *
     * Deletes given object from list and returns it
     */
    public T remove(T element) {
        if (linkedList.remove(element))
            return element;

        return null;
    }


    /**
     *
     * @return T
     *
     * Deletes the top of the list, and returns it
     */
    public T removeFirst() {
        return linkedList.removeFirst();
    }


    /**
     *
     * @param element T
     *
     *T Adds element to the second index of queue
     */
    public void offerToSecond(T element) {
        linkedList.add(1, element);
    }

    /**
     *
     * @return int
     *
     * Returns size of queue
     */
    public int size() {
        return linkedList.size();
    }

    /**
     *
     * @return boolean
     *
     * Returns whether queue is empty or not
     */
    public boolean isEmpty() {
        if (linkedList == null)
            return true;
        else
            return linkedList.size() == 0;
    }

}
