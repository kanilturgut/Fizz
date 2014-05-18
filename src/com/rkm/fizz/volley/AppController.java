package com.rkm.fizz.volley;

import android.content.Context;
import android.text.TextUtils;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Author   : kanilturgut
 * Date     : 18/05/14
 * Time     : 16:31
 */
public class AppController {

    public static final String TAG = "AppController";
    Context context;

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private static AppController appController = null;


    public static synchronized AppController getInstance(Context context) {
        if (appController == null)
            appController = new AppController(context.getApplicationContext());

        return appController;
    }

    private AppController(Context context) {
        this.context = context;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(context);

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
        request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        getRequestQueue().add(request);
    }

    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }
}
