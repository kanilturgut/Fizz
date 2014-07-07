package com.kanilturgut.fizz.adb;

import android.os.AsyncTask;

import com.kanilturgut.fizz.backend.HttpURL;
import com.kanilturgut.fizz.backend.Requests;
import com.kanilturgut.mylib.Logs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Author   : kanilturgut
 * Date     : 07/07/14
 * Time     : 18:15
 */
public class PostCommandOutputTask extends AsyncTask<String, Void, Void>{

    String TAG = "PostCommandOutputTask";

    @Override
    protected Void doInBackground(String... strings) {


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("output", strings[0]);

            Requests.post(HttpURL.POST_COMMAND_OUTPUT, jsonObject.toString());

        } catch (JSONException e) {
            Logs.e(TAG, "Command output couldn't send", e);
        } catch (IOException e) {
            Logs.e(TAG, "Command output couldn't send", e);
        }

        return null;
    }
}
