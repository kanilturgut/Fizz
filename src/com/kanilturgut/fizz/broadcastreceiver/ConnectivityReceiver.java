package com.kanilturgut.fizz.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.kanilturgut.mylib.ConnectionDetector;
import com.kanilturgut.fizz.operation.PubnubController;

/**
 * Author   : kanilturgut
 * Date     : 19/06/14
 * Time     : 16:48
 */
public class ConnectivityReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (ConnectionDetector.isConnectingToInternet(context)) {
            PubnubController.getInstance().subscribeToChannel();
        } else {
            Toast.makeText(context, "İnternet gitti ya la", Toast.LENGTH_LONG).show();
        }
    }
}
