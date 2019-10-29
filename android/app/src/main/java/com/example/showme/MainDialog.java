package com.example.showme;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class MainDialog extends AppCompatDialogFragment {
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
                                toMainViewActivity();
                                Toast.makeText(getActivity(), "Main view selected", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                toSideViewActivity();
                                Toast.makeText(getActivity(), "Right view selected", Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                toSideViewActivity();
                                Toast.makeText(getActivity(), "Left view selected", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                });
        return builder.create();
    }

    public void toSideViewActivity()
    {
        Intent intent = new Intent(getActivity(), SideViewActivity.class);
        startActivity(intent);
    }

    public void toMainViewActivity()
    {
        Intent intent = new Intent(getActivity(), MainViewActivity.class);
        startActivity(intent);
    }
}
