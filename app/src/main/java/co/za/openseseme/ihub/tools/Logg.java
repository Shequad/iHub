package co.za.openseseme.ihub.tools;

import android.util.Log;

public class Logg {

    private static final boolean LOG = true;

    public static void d(String tag, String log) {
        if (LOG)
            Log.d(tag, log);
    }

    public static void w(String tag, String log, Throwable t) {
        if (LOG)
            Log.w(tag, log, t);
    }

    public static void e(String tag, String log) {
        if (LOG)
            Log.e(tag, log);
    }

    public static void e(String tag, String log, Throwable error) {
        if (LOG)
            Log.e(tag, log, error);
    }

    public static void i(String tag, String log) {
        if (LOG)
            Log.i(tag, log);
    }
}
