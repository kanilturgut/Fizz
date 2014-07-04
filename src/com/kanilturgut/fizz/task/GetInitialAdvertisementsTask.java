package com.kanilturgut.fizz.task;

import android.os.AsyncTask;
import com.kanilturgut.fizz.activity.MainActivity;
import com.kanilturgut.fizz.backend.HttpURL;
import com.kanilturgut.fizz.backend.Requests;
import com.kanilturgut.fizz.model.Advertisement;
import com.kanilturgut.fizz.model.Venue;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Author   : kanilturgut
 * Date     : 24/06/14
 * Time     : 17:19
 */
public class GetInitialAdvertisementsTask extends AsyncTask<Void, Void, String> {


    @Override
    protected String doInBackground(Void... voids) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("venueId", Venue.getInstance().getId());

            HttpResponse httpResponse = Requests.post(HttpURL.INITIAL_ADVERTISEMENT, jsonObject.toString());
            if (Requests.checkStatusCode(httpResponse, HttpStatus.SC_OK))
                return Requests.readResponse(httpResponse);
            else
                return null;
        } catch (JSONException e) {
            e.printStackTrace();
            cancel(true);
        } catch (IOException e) {
            e.printStackTrace();
            cancel(true);
        }

        return null;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        startNextProcess();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (s != null) {

            try {
                JSONArray jsonArray = new JSONArray(s);

                for (int i = 0; i < jsonArray.length(); i++) {
                    MainActivity.advertisementList.add(MainActivity.advertisementList.size(), Advertisement.fromJSON(jsonArray.getJSONObject(i)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        startNextProcess();
    }

    private void startNextProcess() {
        MainActivity.mainToFizz();
    }
}
