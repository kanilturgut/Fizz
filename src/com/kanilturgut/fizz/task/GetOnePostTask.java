package com.kanilturgut.fizz.task;

import android.os.AsyncTask;
import com.kanilturgut.fizz.MyQueue;
import com.kanilturgut.fizz.backend.HttpURL;
import com.kanilturgut.fizz.backend.Requests;
import com.kanilturgut.fizz.model.SocialNetwork;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Author   : kanilturgut
 * Date     : 22/06/14
 * Time     : 23:03
 */
public class GetOnePostTask extends AsyncTask<String, Void, String>{

    @Override
    protected String doInBackground(String... strings) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("postId", strings[0]);

            HttpResponse httpResponse = Requests.post(HttpURL.GET_ONE_POST, jsonObject.toString());
            if (Requests.checkStatusCode(httpResponse, HttpStatus.SC_OK))
                return Requests.readResponse(httpResponse);
            else
                return null;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);

        if (response != null) {

            try {
                MyQueue myQueue = MyQueue.getInstance();
                JSONObject jsonObject = new JSONObject(response);
                SocialNetwork socialNetwork = SocialNetwork.fromJSON(jsonObject);

                if (myQueue != null && !myQueue.isContain(socialNetwork))
                    myQueue.offerToSecond(socialNetwork);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
