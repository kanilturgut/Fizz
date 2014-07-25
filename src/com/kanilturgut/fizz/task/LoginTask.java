package com.kanilturgut.fizz.task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import com.kanilturgut.fizz.activity.MainActivity;
import com.kanilturgut.fizz.backend.HttpURL;
import com.kanilturgut.fizz.backend.Requests;
import com.kanilturgut.fizz.model.Venue;
import com.kanilturgut.fizz.service.FizzService;
import com.kanilturgut.fizz.sharedpreference.MySharedPreferences;
import com.kanilturgut.mylib.Logs;
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
public class LoginTask extends AsyncTask<String, Void, String> {

    final String TAG = "LoginTask";
    Context context;
    boolean doNeedInitials;
    String email, password;

    public LoginTask(Context context, boolean bool) {
        this.doNeedInitials = bool;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {

        this.email = strings[0];
        this.password = strings[1];

        JSONObject loginObject = new JSONObject();
        try {
            loginObject.put("email", email);
            loginObject.put("password", password);
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

            MySharedPreferences mySharedPreferences = MySharedPreferences.getInstance(context);
            String[] loginInfo = mySharedPreferences.getFromSharePreferences();
            if (loginInfo[0] == null && loginInfo[1] == null) {
                mySharedPreferences.saveToSharedPreferences(email, password);

                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);

            } else {
                if (doNeedInitials) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        Logs.d(TAG, "Login finished");
                        Venue.fromJSON(jsonObject, password);

                        context.startService(new Intent(context, FizzService.class));

                        GetInitialTweetsTask getInitialTweetsTask = new GetInitialTweetsTask(Venue.getInstance().getHashtag());
                        getInitialTweetsTask.execute();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            Logs.e(TAG, "ERROR on Login");
            // TODO ne yapılacak ?
        }
    }
}
