package com.kanilturgut.fizz.update;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.kanilturgut.fizz.aquery.AQueryUtilities;
import com.kanilturgut.mylib.Logs;

import java.io.File;
import java.net.URI;

/**
 * Author   : kanilturgut
 * Date     : 22/06/14
 * Time     : 09:47
 */
public class UpdateOperation {

    Context context = null;
    public static UpdateOperation updateOperation = null;
    public static final String APK_URL = "http://kadiranilturgut.com/android/fizz/Fizz.apk";
    public static final String APK_TARGET = "fizz_apk/";
    public static final String APK_NAME = "Fizz.apk";


    private UpdateOperation(Context context) {

        this.context = context;
    }

    public static UpdateOperation getInstance(Context context) {

        if (updateOperation == null)
            updateOperation = new UpdateOperation(context);

        return updateOperation;
    }

    public void checkForUpdate() {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(APK_URL));
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public void downloadNewApk() {

        File ext = Environment.getExternalStorageDirectory();
        File file = new File(ext, APK_TARGET + APK_NAME);

        AQuery aQuery = AQueryUtilities.getInstance(context).aQuery;

        aQuery.download(APK_URL, file, new AjaxCallback<File>(){

            @Override
            public void callback(String url, File object, AjaxStatus status) {
                super.callback(url, object, status);

                Uri path = Uri.fromFile(object);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(APK_URL));
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });
    }

}
