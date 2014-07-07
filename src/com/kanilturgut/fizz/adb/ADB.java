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
    public static String OP_LAUNCH_APP = "am start -n \"com.kanilturgut.fizz/.activity.MainActivity\"";
    public static String OP_SHOW_PROCESS = "ps | grep com.kanilturgut.fizz";
    public static String OP_KILL = "am force-stop com.kanilturgut.fizz";
    public static String OP_LOGCAT = "logcat -ds \"Fizz\" > /sdcard/logcat.txt";
    public static String OP_LOGCAT_CLEAR = "logcat -c";


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

    public void kill() {

        try {
            Process process = Runtime.getRuntime().exec("su");

            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(OP_KILL + "\n");
            os.writeBytes(OP_LAUNCH_APP + "\n");
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

    public void logcatClear() {
        execute(OP_LOGCAT_CLEAR);
    }

    public void logcat() {
        try {
            Process process = Runtime.getRuntime().exec("su");

            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(OP_LOGCAT + "\n");
            os.writeBytes("logcat -c" + "\n");
            os.flush();
            os.close();
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Process process = Runtime.getRuntime().exec("su");

            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("cat /sdcard/logcat.txt" + "\n");
            os.flush();
            os.close();
            process.waitFor();

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s = null;
            StringBuilder stringBuilder = new StringBuilder();
            while ((s = stdInput.readLine()) != null) {
                stringBuilder.append(s);
            }

            int max = 1800;

            String message = stringBuilder.toString();
            message = message.replaceAll("\"", "");

            float messageLength = (float) message.length();

            //Math.ceil --> Uste yuvarlar
            int numberOfPiece = (int) Math.ceil(messageLength / max);
            String[] stringArry = new String[numberOfPiece];

            for (int i = 0; i < numberOfPiece; i++) {
                if (i == numberOfPiece - 1)
                    stringArry[i] = message.substring(max * i);
                else
                    stringArry[i] = message.substring(max * i, max * (i+1));
            }

            for (int j = numberOfPiece - 1; j >= 0; j--) {
                PubnubController.getInstance(context).publishToChannel(stringArry[j]);
            }



        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


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
