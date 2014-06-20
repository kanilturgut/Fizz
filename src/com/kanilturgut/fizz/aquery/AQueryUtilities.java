package com.kanilturgut.fizz.aquery;

import android.content.Context;
import com.androidquery.AQuery;
import org.apache.http.cookie.Cookie;

import java.util.LinkedList;

/**
 * Author   : kanilturgut
 * Date     : 19/05/14
 * Time     : 17:32
 */
public class AQueryUtilities {

    public AQuery aQuery = null;
    static AQueryUtilities aQueryUtility= null;
    public LinkedList<Cookie> cookieLinkedList = new LinkedList<Cookie>();

    private AQueryUtilities(Context context) {
        aQuery = new AQuery(context);
    }

    public static AQueryUtilities getInstance(Context context) {
        if (aQueryUtility == null)
            aQueryUtility = new AQueryUtilities(context);

        return aQueryUtility;
    }
}