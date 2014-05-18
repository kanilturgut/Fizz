package com.rkm.fizz.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.rkm.fizz.R;
import com.rkm.fizz.util.Logs;
import com.rkm.fizz.volley.AppController;
import org.json.JSONArray;

public class MainActivity extends Activity {

    Context context;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = this;

        String tag_json_arry = "json_array_req";
        String url = "http://onurcansert.com:3000/initialTweets";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Logs.d("bla", "bla");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Logs.e("bla", "hataa");
            }
        });

        //AppController.getInstance(context).addToRequestQueue(jsonArrayRequest, tag_json_arry);

        AQuery aQuery = new AQuery(this);
        aQuery.ajax(url, JSONArray.class, new AjaxCallback<JSONArray>() {
            @Override
            public void callback(String url, JSONArray object, AjaxStatus status) {
                super.callback(url, object, status);
                Logs.d("stri", object.toString());
            }
        });

    }
}
