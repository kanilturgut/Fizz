package com.rkm.fizz.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.kanilturgut.mylib.ConnectionDetector;
import com.rkm.fizz.operation.PubnubController;

/**
 * Author   : kanilturgut
 * Date     : 19/06/14
 * Time     : 16:48
 */
public class ConnectivityReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (ConnectionDetector.isConnectingToInternet(context)) {

        } else {
        }
    }
}
