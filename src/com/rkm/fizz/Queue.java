package com.rkm.fizz;

import java.util.LinkedList;

/**
 * Author   : kanilturgut
 * Date     : 20/05/14
 * Time     : 17:24
 */
public class Queue<T> {

    public LinkedList<T> linkedList;

    public Queue() {
        linkedList = new LinkedList<T>();
    }

    public void offer(T element) {
        linkedList.addLast(element);
    }

    public T poll() {
        T temp = linkedList.removeFirst();
        linkedList.offer(temp);

        return temp;
    }

    public T peek() {
        return linkedList.getFirst();
    }

    public T remove(T element) {
        if (linkedList.remove(element))
            return element;

        return null;
    }

    public T removeFirst() {
        return linkedList.removeFirst();
    }

    public void offerToSecond(T element) {
        linkedList.add(1, element);
    }

    public int size() {
        return linkedList.size();
    }

    public boolean isEmpty() {
        if (linkedList == null)
            return true;
        else
            return linkedList.size() == 0;
    }

}
