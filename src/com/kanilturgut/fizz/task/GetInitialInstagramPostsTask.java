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
 * Date     : 20/06/14
 * Time     : 23:08
 */
public class GetInitialInstagramPostsTask extends AsyncTask<Void, Void, String> {

    private String TAG = "GetInitialTweetsTask";
    String hashtag;

    public GetInitialInstagramPostsTask(String hashtag) {
        this.hashtag = hashtag;
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
            HttpResponse httpResponse = Requests.post(HttpURL.INITIAL_INSTAGRAM, tweetsObject.toString());
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
                    MainActivity.instagramList.add(MainActivity.instagramList.size(), SocialNetwork.fromJSON(jsonArray.getJSONObject(i)));
                }
            } catch (JSONException e) {
                Logs.e(TAG, "ERROR occured on reading JSON response", e);
            }
        }

        startNextProcess();
    }

    private void startNextProcess() {
        GetInitialPromotedPostsTask getInitialPromotedPostsTask = new GetInitialPromotedPostsTask(hashtag);
        getInitialPromotedPostsTask.execute();
    }
}
