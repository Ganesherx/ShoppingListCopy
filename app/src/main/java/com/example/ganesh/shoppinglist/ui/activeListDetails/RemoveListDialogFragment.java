package com.example.ganesh.shoppinglist.ui.activeListDetails;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.example.ganesh.shoppinglist.R;
import com.example.ganesh.shoppinglist.model.ShoppingList;
import com.example.ganesh.shoppinglist.utils.Constants;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;

public class RemoveListDialogFragment extends android.support.v4.app.DialogFragment {

    String mListId;
    final static String LOG_TAG = RemoveListDialogFragment.class.getSimpleName();

    public static RemoveListDialogFragment newInstance(ShoppingList shoppingList, String listId) {
        RemoveListDialogFragment removeListDialogFragment = new RemoveListDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_LIST_ID, listId);
        removeListDialogFragment.setArguments(bundle);
        return removeListDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListId = getArguments().getString(Constants.KEY_LIST_ID);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(getActivity().getResources().getString(R.string.action_remove_list))
                .setMessage(getString(R.string.dialog_message_are_you_sure_remove_list))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        removeList();
                         /* Dismiss the dialog */
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                         /* Dismiss the dialog */
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);

        return builder.create();
    }

    private void removeList() {
        HashMap<String, Object> removeListData = new HashMap<String, Object>();
        removeListData.put("/" + Constants.FIREBASE_LOCATION_ACTIVE_LISTS + "/"+ mListId, null);
        removeListData.put("/" + Constants.FIREBASE_LOCATION_SHOPPING_LIST_ITEMS + "/"+ mListId, null);

        Firebase firebaseRef = new Firebase(Constants.FIREBASE_URL);

        firebaseRef.updateChildren(removeListData, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {

                if (firebaseError != null) {
                    Log.e(LOG_TAG, getString(R.string.log_error_updating_data) + firebaseError.getMessage());
                }
            }
        });
    }
}

