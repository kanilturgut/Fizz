package com.kanilturgut.fizz.adb;

/**
 * Author   : kanilturgut
 * Date     : 01/07/14
 * Time     : 15:46
 */
public class ADB {

    public static String OP_INSTALL = "pm install -r /sdcard/Download/Fizz.apk";
    public static String OP_UNINSTALL = "pm uninstall -k %s";
    public static String OP_UNINSTALL_WITH_CACHE = "pm uninstall %s";
    public static String OP_LAUNCH_APP = "am start -n \"%s/%s.activity.MainActivity\" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER";
    public static String OP_SHOW_PROCESS = "ps | grep %s";
    public static String OP_KILL = "kill %s";


}
