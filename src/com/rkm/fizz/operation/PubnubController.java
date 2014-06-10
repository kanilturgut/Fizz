package com.rkm.fizz.operation;

import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.pubnub.api.PubnubException;
import com.rkm.fizz.Queue;
import com.rkm.fizz.model.SocialNetwork;
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
                            JSONObject pubnupResponse = new JSONObject(o.toString());
                            SocialNetwork socialNetwork = SocialNetwork.fromJSON(pubnupResponse);

                            if (pubnupResponse.has("delete_id"))
                                deleteOperation(socialNetwork);
                            else
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

    private void deleteOperation(SocialNetwork socialNetwork) {
        if (isContain(socialNetwork))
            SocialNetwork.socialNetworkQueue.remove(socialNetwork);

    }

    private void addOperation(SocialNetwork socialNetwork) {
        if (!isContain(socialNetwork))
            SocialNetwork.socialNetworkQueue.offerToSecond(socialNetwork);

    }

    private boolean isContain(SocialNetwork socialNetwork) {

        Queue<SocialNetwork> temp = SocialNetwork.socialNetworkQueue;

        for (int i = 0; i < temp.size(); i++) {
            if (temp.removeFirst().getId().equals(socialNetwork.getId()))
                return true;
        }

        return false;
    }

    /*
    private void isContain(SocialNetwork socialNetwork) {
        if (socialNetwork.getPageType() == PageType.PAGE_TYPE_TWITTER) {


        } else if (socialNetwork.getPageType() == PageType.PAGE_TYPE_INSTAGRAM) {
        }


    }

    private void checkAndAdd(SocialNetwork socialNetwork) {

        if (socialNetwork.getPageType() == PageType.PAGE_TYPE_TWITTER) {
            Twitter twitter = (Twitter) socialNetwork;
            if (!SocialNetwork.socialNetworkQueue.isContain(twitter))
                SocialNetwork.socialNetworkQueue.offerToSecond(twitter);

        } else if (socialNetwork.getPageType() == PageType.PAGE_TYPE_INSTAGRAM) {
            Instagram instagram = (Instagram) socialNetwork;
            if (!SocialNetwork.socialNetworkQueue.isContain(instagram))
                SocialNetwork.socialNetworkQueue.offerToSecond(instagram);
        }
    }

    private void chechkAndDelete(SocialNetwork socialNetwork) {

        if (socialNetwork.getPageType() == PageType.PAGE_TYPE_TWITTER) {
            Twitter twitter = (Twitter) socialNetwork;
            if (SocialNetwork.socialNetworkQueue.isContain(twitter))
                SocialNetwork.socialNetworkQueue.offerToSecond(twitter);

        } else if (socialNetwork.getPageType() == PageType.PAGE_TYPE_INSTAGRAM) {
            Instagram instagram = (Instagram) socialNetwork;
            if (!SocialNetwork.socialNetworkQueue.isContain(instagram))
                SocialNetwork.socialNetworkQueue.offerToSecond(instagram);
        }
    }
    */
}
