package com.retor.tedtest.main.ted;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by Admin on 13.05.15.
 */
public class DialogsBuilder {
    public static ProgressDialog createProgress(Context context, String msg){
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage(msg);
        return pd;
    }

    public static AlertDialog createAlert(Context context, String msg){
        return new AlertDialog.Builder(context).setMessage(msg).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();//TODO Error show
    }
}
