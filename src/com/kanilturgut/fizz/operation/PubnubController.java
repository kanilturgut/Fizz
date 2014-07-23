package com.kanilturgut.fizz.operation;

import android.content.Context;
import android.content.SharedPreferences;

import com.kanilturgut.fizz.MyQueue;
import com.kanilturgut.fizz.model.SocialNetwork;
import com.kanilturgut.fizz.model.Venue;
import com.kanilturgut.fizz.task.GetOnePostTask;
import com.kanilturgut.mylib.Logs;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.pubnub.api.PubnubException;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author   : kanilturgut
 * Date     : 20/05/14
 * Time     : 11:17
 */
public class PubnubController {

    Context context;
    static PubnubController pubnubController = null;
    static Pubnub pubnub = null;
    final String TAG = "PubnubController";
    final String PUBLISH_KEY = "pub-c-79ce4f35-20dd-4972-9f8c-8f9d3a4dbe59";
    final String SUBSCRIBE_KEY = "sub-c-2706dfc2-f87a-11e3-bacb-02ee2ddab7fe";
    String CHANNEL = "fizz";
    MyQueue myQueue;

    private PubnubController(Context context) {
        pubnub = new Pubnub(PUBLISH_KEY, SUBSCRIBE_KEY);
        myQueue = MyQueue.getInstance();
        this.CHANNEL = Venue.getInstance().getHashtag();
        this.context = context;

        SharedPreferences sharedPreferences = context.getSharedPreferences("Pubnup", Context.MODE_PRIVATE);
        String uuid = sharedPreferences.getString("uuid", null);
        if (uuid == null || uuid.length() == 0) {
            uuid = pubnub.uuid();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("uuid", uuid);
            editor.apply();
        }

        pubnub.setUUID(uuid);
    }

    public static PubnubController getInstance(Context context) {
        if (pubnubController == null)
            pubnubController = new PubnubController(context);

        return pubnubController;
    }

    public void subscribeToChannel() {

        if (pubnub != null && CHANNEL != null) {
            try {
                pubnub.subscribe(CHANNEL, new Callback() {

                    @Override
                    public void connectCallback(String s, Object o) {
                        super.connectCallback(s, o);
                        Logs.i(TAG, "SUBSCRIBE : CONNECT on channel : " + CHANNEL + " : " + o.toString());
                    }

                    @Override
                    public void disconnectCallback(String s, Object o) {
                        super.disconnectCallback(s, o);
                        Logs.i(TAG, "SUBSCRIBE : DISCONNECT on channel : " + CHANNEL + " : " + o.toString());
                    }

                    @Override
                    public void errorCallback(String s, PubnubError pubnubError) {
                        super.errorCallback(s, pubnubError);
                        Logs.e(TAG, "SUBSCRIBE : ERROR on channel : " + CHANNEL + " : " + pubnubError.getErrorString());
                    }

                    @Override
                    public void successCallback(String s, Object o) {

                        Logs.i(TAG, "SUBSCRIBE : SUCCESS_CALLBACK on channel " + CHANNEL + " : " + o.toString());

                        try {
                            JSONObject pubnupResponse = new JSONObject(o.toString());
                            SocialNetwork socialNetwork = SocialNetwork.fromJSON(pubnupResponse);

                            if (pubnupResponse.has("delete_id"))
                                deleteOperation(pubnupResponse.getString("delete_id"));
                            else if (pubnupResponse.has("dont_display"))
                                dontDisplayOperation(pubnupResponse.getString("dont_display"));
                            else if (pubnupResponse.has("display"))
                                displayOperation(pubnupResponse.getString("display"));
                            else if (pubnupResponse.has("type") && pubnupResponse.has("userFullname"))
                                addOperation(socialNetwork);
                        } catch (JSONException e) {
                            Logs.e(TAG, "JSONException on successCallback", e);
                            //TODO exception olursa bir tane elle yazdığımız post gelsin
                        }
                    }
                });
            } catch (PubnubException e) {
                Logs.e(TAG, "ERROR on attaching channel " + CHANNEL, e);
            }
        }
    }

    private void deleteOperation(String id) {

        SocialNetwork socialNetwork = myQueue.findPost(id);

        if (socialNetwork != null && myQueue != null && myQueue.isContain(socialNetwork))
            myQueue.remove(socialNetwork);

    }

    private void promoteOperation(String id) {


    }

    private void addOperation(SocialNetwork socialNetwork) {
        if (myQueue != null && !myQueue.isContain(socialNetwork))
            myQueue.offerToSecond(socialNetwork);
    }

    private void dontDisplayOperation(String id) {
        deleteOperation(id);
    }

    private void displayOperation(String id) {
        if (myQueue != null && !myQueue.isContain(id))
            new GetOnePostTask().execute(id);
    }
}
