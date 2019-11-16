package com.example.showme;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;
import com.google.android.gms.nearby.connection.Strategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

public class MainDialog extends AppCompatDialogFragment {

    private static final String TAG = MainViewActivity.class.getSimpleName();
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Please choose device configuration")
                .setItems(new CharSequence[]
                        {"Main View", "Right View", "Left View"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                if(MainActivity.mainView < 1)
                                {
                                    MainActivity.mainView++;
                                    toMainViewActivity();
                                    Toast.makeText(getActivity(), "Main view selected", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                                else
                                {
                                    AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
                                    b.setTitle("Cannot use two devices as main view.")
                                     .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                         @Override
                                         public void onClick(DialogInterface dialog, int which) {

                                         }
                                     })
                                    .create()
                                    .show();
                                }
                            case 1:
                                if(MainActivity.rightView < 1)
                                {
                                    MainActivity.rightView++;
                                    toSideViewActivity("right");
                                    Toast.makeText(getActivity(), "Right view selected", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                                else
                                {
                                    AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
                                    b.setTitle("Cannot use two devices as right view.")
                                     .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                         @Override
                                         public void onClick(DialogInterface dialog, int which) {

                                         }
                                     })
                                     .create()
                                     .show();
                                }
                            case 2:
                                if(MainActivity.leftView < 1)
                                {
                                    MainActivity.leftView++;
                                    toSideViewActivity("left");
                                    Toast.makeText(getActivity(), "Left view selected", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                                else
                                {
                                    AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
                                    b.setTitle("Cannot use two devices as left view.")
                                     .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                         @Override
                                         public void onClick(DialogInterface dialog, int which) {

                                         }
                                     })
                                     .create()
                                     .show();
                                }
                        }
                    }
                });
        return builder.create();
    }

    public void toSideViewActivity(String  activityName)
    {
        Intent intent = new Intent(getActivity(), SideViewActivity.class);
        intent.putExtra("activity", activityName);
        startActivity(intent);
        ShowMeNearby.startAdvertising();

    }

    public void toMainViewActivity()
    {
        Intent intent = new Intent(getActivity(), MainViewActivity.class);
        startActivity(intent);
    }

}
