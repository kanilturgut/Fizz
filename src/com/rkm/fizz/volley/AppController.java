package com.rkm.fizz.volley;

import android.app.Application;
import android.text.TextUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Author   : kanilturgut
 * Date     : 18/05/14
 * Time     : 16:31
 */
public class AppController extends Application{

    public static final String TAG = "AppController";

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private static AppController appController = null;

    @Override
    public void onCreate() {
        super.onCreate();
        appController = this;
    }

    public static synchronized AppController getInstance() {
        return appController;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(getApplicationContext());

        return requestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (imageLoader == null)
            imageLoader = new ImageLoader(this.requestQueue, new LruBitmapCache());

        return imageLoader;
    }

    public <T> void addToRequestQueue(Request<T> request, String tag) {
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(request);
    }

    public <T> void addToRequestQueue(Request<T> request) {
        request.setTag(TAG);
        getRequestQueue().add(request);
    }

    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }
}
