package com.rkm.fizz.operation;

import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.pubnub.api.PubnubException;
import com.rkm.fizz.socialnetwork.page.SocialNetwork;
import com.rkm.fizz.socialnetwork.page.model.Instagram;
import com.rkm.fizz.socialnetwork.page.model.Twitter;
import com.rkm.fizz.util.Logs;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author   : kanilturgut
 * Date     : 20/05/14
 * Time     : 11:17
 */
public class PubnubController {

    final String TAG = "PubnubController";
    final String PUBLISH_KEY = "pub-c-13b31cee-ef79-440f-b46d-e3804f3d5435";
    final String SUBSCRIBE_KEY = "sub-c-3a5a7350-b28d-11e3-b8c3-02ee2ddab7fe";
    final String CHANNEL = "fizz";

    static PubnubController pubnubController = null;
    static Pubnub pubnub = null;

    private PubnubController() {
        pubnub = new Pubnub(PUBLISH_KEY, SUBSCRIBE_KEY);
    }

    public static PubnubController getInstance() {
        if (pubnubController == null)
            pubnubController = new PubnubController();

        return pubnubController;
    }

    public void subscribeToChannel() {

        if (pubnub != null) {
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
                        Logs.i(TAG, "SUBSCRIBE : ERROR on channel : " + CHANNEL + " : " + pubnubError.getErrorString());
                    }

                    @Override
                    public void successCallback(String s, Object o) {

                        Logs.i(TAG, "SUBSCRIBE : SUCCESS_CALLBACK on channel " + CHANNEL + " : " + o.toString());

                        try {
                            Twitter twitter = Twitter.fromJSON(new JSONObject(o.toString()));
                            SocialNetwork.socialNetworkQueue.offerToSecond(twitter);
                        } catch (JSONException e) {
                            try {
                                Instagram instagram = Instagram.fromJSON(new JSONObject(o.toString()));
                                SocialNetwork.socialNetworkQueue.offer(instagram);
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }

                    }
                });
            } catch (PubnubException e) {
                Logs.e(TAG, "ERROR on attaching channel " + CHANNEL, e);
            }
        }
    }
}
