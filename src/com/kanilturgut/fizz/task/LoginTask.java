package com.kanilturgut.fizz.task;

import android.os.AsyncTask;
import com.kanilturgut.fizz.backend.HttpURL;
import com.kanilturgut.fizz.backend.Requests;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Author   : kanilturgut
 * Date     : 20/06/14
 * Time     : 20:13
 */
public class LoginTask extends AsyncTask<Void, Void, String> {

    boolean doNeedInitials;

    public LoginTask(boolean bool) {
        this.doNeedInitials = bool;
    }

    @Override
    protected String doInBackground(Void... voids) {

        JSONObject loginObject = new JSONObject();
        try {
            loginObject.put("email", "uoy1991@gmail.com");
            loginObject.put("password", "123");
        } catch (JSONException e) {
            e.printStackTrace();
            cancel(true);
        }

        try {
            HttpResponse httpResponse = Requests.post(HttpURL.LOGIN, loginObject.toString());
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
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);

        if (response != null) {
            if (doNeedInitials) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String hashtag = jsonObject.getString("hashtag");

                    GetInitialTweetsTask getInitialTweetsTask = new GetInitialTweetsTask(hashtag);
                    getInitialTweetsTask.execute();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
