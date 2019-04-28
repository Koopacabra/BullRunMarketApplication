package com.example.bullrunmarketapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;


/*class for showing the close order dialog (prompt) for the trucks,
 * taken from https://developer.android.com/guide/topics/ui/dialogs */
public class CloseOrderDialogFragment extends DialogFragment {

    //building constructors per the android dev doc
    interface InteractionListener{
        void closeOrderClicked();

    }

    private InteractionListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (InteractionListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Ready to close this order?")
               .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       mListener.closeOrderClicked();
                   }
               })
               .setNegativeButton("Not Yet", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the dialog
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}