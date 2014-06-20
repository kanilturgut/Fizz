package com.kanilturgut.fizz.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.kanilturgut.mylib.Logs;
import com.kanilturgut.fizz.activity.MainActivity;

/**
 * Author   : kanilturgut
 * Date     : 18/05/14
 * Time     : 14:19
 */
public class BootFinishedReceiver extends BroadcastReceiver{

    final String TAG = "BootFinishedReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        Logs.d(TAG, "Boot finished");

        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
