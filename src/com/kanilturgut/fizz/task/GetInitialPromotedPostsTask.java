package com.kanilturgut.fizz.task;

import android.os.AsyncTask;
import com.kanilturgut.fizz.activity.MainActivity;
import com.kanilturgut.fizz.backend.HttpURL;
import com.kanilturgut.fizz.backend.Requests;
import com.kanilturgut.fizz.model.SocialNetwork;
import com.kanilturgut.mylib.Logs;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Author   : kanilturgut
 * Date     : 23/06/14
 * Time     : 23:20
 */
public class GetInitialPromotedPostsTask extends AsyncTask<Void, Void, String> {

    private String TAG = "GetInitialPromotedPostsTask";
    String hashtag;

    public GetInitialPromotedPostsTask(String hashtag) {
        this.hashtag = hashtag;

        Logs.i(TAG, "Constructor executed");
    }

    @Override
    protected String doInBackground(Void... voids) {

        JSONObject tweetsObject = new JSONObject();
        try {
            tweetsObject.put("hashtag", hashtag);
        } catch (JSONException e) {
            e.printStackTrace();
            cancel(true);
        }

        try {
            HttpResponse httpResponse = Requests.post(HttpURL.INITIAL_PROMOTED_POSTS, tweetsObject.toString());
            if (Requests.checkStatusCode(httpResponse, HttpStatus.SC_OK))
                return Requests.readResponse(httpResponse);
            else
                return null;

        } catch (IOException e) {
            e.printStackTrace();
            cancel(true);
        }

        return null;
    }


    @Override
    protected void onCancelled() {
        super.onCancelled();

        //TODO ne yapılacak ?
        startNextProcess();
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);

        if (response != null) {
            try {
                JSONArray jsonArray = new JSONArray(response);

                for (int i = 0; i < jsonArray.length(); i++) {
                    MainActivity.promotedList.add(MainActivity.promotedList.size(), SocialNetwork.fromJSON(jsonArray.getJSONObject(i)));
                }
            } catch (JSONException e) {
                Logs.e(TAG, "ERROR occured on reading JSON response", e);
            }
        }

        startNextProcess();
    }

    private void startNextProcess() {
        GetInitialAdvertisementsTask getInitialAdvertisementsTask = new GetInitialAdvertisementsTask();
        getInitialAdvertisementsTask.execute();
    }
}

