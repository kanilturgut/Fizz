package com.kanilturgut.fizz.task;

import android.location.Location;
import android.os.AsyncTask;
import com.kanilturgut.fizz.backend.HttpURL;
import com.kanilturgut.fizz.backend.Requests;
import com.kanilturgut.fizz.model.Venue;
import com.kanilturgut.fizz.service.FizzService;
import com.kanilturgut.mylib.Logs;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Author   : kanilturgut
 * Date     : 24/06/14
 * Time     : 16:56
 */
public class UpdateVenueLocationTask extends AsyncTask<Void, Void, Boolean>{

    final String TAG = "UpdateVenueLocationTask";

    @Override
    protected Boolean doInBackground(Void... voids) {

        Location location = FizzService.getLocation();
        if (location != null) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("venueId", Venue.getInstance().getId());
                jsonObject.put("lat", location.getLatitude());
                jsonObject.put("lng", location.getLongitude());

                HttpResponse httpResponse = Requests.post(HttpURL.UPDATE_VENUE_LOCATION, jsonObject.toString());
                if (Requests.checkStatusCode(httpResponse, HttpStatus.SC_OK))
                    return true;

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        if (aBoolean)
            Logs.d(TAG, "Venue location successfully updated");
    }
}
