package com.example.ganesh.shoppinglist.ui.activeListDetails;


import android.app.Dialog;
import android.os.Bundle;

import com.example.ganesh.shoppinglist.R;
import com.example.ganesh.shoppinglist.model.ShoppingList;
import com.example.ganesh.shoppinglist.utils.Constants;
import com.firebase.client.Firebase;
import com.firebase.client.ServerValue;

import java.util.HashMap;

public class EditListNameDialogFragment extends EditListDialogFragment {
    private static final String LOG_TAG = ActiveListDetailsActivity.class.getSimpleName();
    String mListName;

    public static EditListNameDialogFragment newInstance(ShoppingList shoppingList, String listId,String encodedEmail) {
        EditListNameDialogFragment editListNameDialogFragment = new EditListNameDialogFragment();
        Bundle bundle = EditListDialogFragment.newInstanceHelper(shoppingList,
                R.layout.dialog_edit_list, listId,encodedEmail);
        bundle.putString(Constants.KEY_LIST_NAME, shoppingList.getListName());
        editListNameDialogFragment.setArguments(bundle);
        return editListNameDialogFragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListName = getArguments().getString(Constants.KEY_LIST_NAME);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.createDialogHelper(R.string.positive_button_edit_item);
        helpSetDefaultValueEditText(mListName);
        return dialog;
    }

    @Override
    protected void doListEdit() {

        final String inputListName = mEditTextForList.getText().toString();
        if (!inputListName.equals("")) {
            if (mListName != null && mListId != null) {
                if (!inputListName.equals(mListName)) {
                    Firebase shoppingListRef = new Firebase(Constants.FIREBASE_URL_ACTIVE_LISTS).child(mListId);
                    HashMap<String, Object> updatedProperties = new HashMap<String, Object>();
                    updatedProperties.put(Constants.FIREBASE_PROPERTY_LIST_NAME, inputListName);

                    HashMap<String, Object> changedTimestampMap = new HashMap<>();
                    changedTimestampMap.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);


                    updatedProperties.put(Constants.FIREBASE_PROPERTY_TIMESTAMP_LAST_CHANGED, changedTimestampMap);

                    shoppingListRef.updateChildren(updatedProperties);

                }
            }
        }
    }
}