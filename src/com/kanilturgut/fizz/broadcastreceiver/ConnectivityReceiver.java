package com.kanilturgut.fizz.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.kanilturgut.fizz.model.Venue;
import com.kanilturgut.mylib.ConnectionDetector;
import com.kanilturgut.fizz.operation.PubnubController;
import com.kanilturgut.mylib.Logs;

/**
 * Author   : kanilturgut
 * Date     : 19/06/14
 * Time     : 16:48
 */
public class ConnectivityReceiver extends BroadcastReceiver {

    final String TAG = "ConnectivityReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (ConnectionDetector.isConnectingToInternet(context)) {
            if (Venue.getInstance().getHashtag() != null)
                PubnubController.startPubnupOperation();
        } else {
            Logs.d(TAG, "Device is not connected to internet");
        }
    }
}
