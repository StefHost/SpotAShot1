package nl.stefhost.spotashot;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class _functions {

    public static void melding(String titel, String bericht, Context context){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titel)
                .setMessage(bericht)
                .setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.show();

    }

}
