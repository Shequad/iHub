package co.za.openseseme.ihub.tools;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import co.za.openseseme.ihub.MyApplication;

public class QuickDialog {

    private static final String TAG = "QuickDialog";

    private static AlertDialog alertDialog;

    private static MaterialDialog materialDialog;

    public static void show(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        alertDialog = builder.create();
        if (MyApplication.isActivityVisible())
            alertDialog.show();
    }

    public static void show(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        alertDialog = builder.create();
        if (MyApplication.isActivityVisible())
            alertDialog.show();
    }

    public static void show(Context context, String title, String message, String button, DialogInterface.OnClickListener onClickListener, boolean cancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(cancelable);
        builder.setPositiveButton(button, onClickListener);
        alertDialog = builder.create();
        if (MyApplication.isActivityVisible())
            alertDialog.show();
    }

    public static void show(Context context, View view, String button, DialogInterface.OnClickListener onClickListener, boolean cancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        builder.setCancelable(cancelable);
        builder.setPositiveButton(button, onClickListener);
        alertDialog = builder.create();
        if (MyApplication.isActivityVisible())
            alertDialog.show();
    }

    public static void show(Context context, String title, String message, String buttonPos, String buttonNeg, DialogInterface.OnClickListener onClickListener, DialogInterface.OnClickListener onClickListener2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(buttonPos, onClickListener);
        builder.setNegativeButton(buttonNeg, onClickListener2);//FFACC0DE
        alertDialog = builder.create();
        if (MyApplication.isActivityVisible())
            alertDialog.show();
    }

    public static void show(Context context, String title, String message, String button, String button2, String button3, DialogInterface.OnClickListener onClickListener, DialogInterface.OnClickListener onClickListener2, DialogInterface.OnClickListener onClickListener3) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(button, onClickListener);
        builder.setNegativeButton(button2, onClickListener2);//FFACC0DE
        builder.setNeutralButton(button3, onClickListener3);
        alertDialog = builder.create();
        if (MyApplication.isActivityVisible())
            alertDialog.show();
    }

    public static void showDialog(Context context, String title, String message) {
        if (MyApplication.isActivityVisible())
            try {
                materialDialog = new MaterialDialog.Builder(context)
                        .title(title)
                        .content(message)
                        .progress(true, 0)
                        .cancelable(false)
                        .show();
            } catch (NullPointerException n) {
                n.printStackTrace();
            }
        else
            Logg.d(TAG, "Activity is not visible");
    }

    public static void hideDialogLoading() {
        if (materialDialog != null)
            try {
                if (MyApplication.isActivityVisible())
                    materialDialog.dismiss();
            } catch (NullPointerException n) {
                n.printStackTrace();
            }
    }

    public static void hideDialog() {
        if (alertDialog != null)
            try {
                if (MyApplication.isActivityVisible())
                    alertDialog.hide();
            } catch (NullPointerException n) {
                n.printStackTrace();
            }
    }
}
