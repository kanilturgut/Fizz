package com.kanilturgut.fizz.adb;

import android.content.Context;

import com.kanilturgut.fizz.operation.PubnubController;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Author   : kanilturgut
 * Date     : 01/07/14
 * Time     : 15:46
 */
public class ADB {

    public static String OP_INSTALL = "pm install -r /sdcard/Download/Fizz.apk";
    public static String OP_UNINSTALL = "pm uninstall -k com.kanilturgut.fizz";
    public static String OP_UNINSTALL_WITH_CACHE = "pm uninstall com.kanilturgut.fizz";
    public static String OP_LAUNCH_APP = "am start -n \"com.kanilturgut.fizz/com.kanilturgut.fizz.activity.MainActivity\" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER";
    public static String OP_SHOW_PROCESS = "ps | grep com.kanilturgut.fizz";
    public static String OP_KILL = "kill %s";

    Context context = null;
    public static ADB adb = null;

    public static ADB getInstance(Context context) {
        if (adb == null)
            adb = new ADB(context);

        return adb;
    }

    private ADB(Context context) {
        this.context = context;
    }

    public void install() {
        execute(OP_INSTALL);
    }

    public void uninstall() {
        execute(OP_UNINSTALL);
    }

    public void uninstallWithCache() {
        execute(OP_UNINSTALL_WITH_CACHE);
    }

    public void launchApp() {
        execute(OP_LAUNCH_APP);
    }

    public void showProcess() {
        execute(OP_SHOW_PROCESS);
    }

    public void kill(String pid) {
        execute(String.format(OP_KILL, pid));
    }

    private void execute(String command) {

        try {
            Process process = Runtime.getRuntime().exec("su");

            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(command + "\n");
            os.flush();
            os.close();
            process.waitFor();

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String s = null;
            StringBuilder stringBuilder = new StringBuilder();
            while ((s = stdInput.readLine()) != null) {
                stringBuilder.append(s);
            }

            PubnubController.getInstance(context).publishToChannel(stringBuilder.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
