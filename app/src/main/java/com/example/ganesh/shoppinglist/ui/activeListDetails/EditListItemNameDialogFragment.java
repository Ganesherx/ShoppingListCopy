package com.example.ganesh.shoppinglist.ui.activeListDetails;


import android.app.Dialog;
import android.os.Bundle;

import com.example.ganesh.shoppinglist.R;
import com.example.ganesh.shoppinglist.model.ShoppingList;
import com.example.ganesh.shoppinglist.utils.Constants;
import com.firebase.client.Firebase;
import com.firebase.client.ServerValue;
import com.firebase.client.realtime.util.StringListReader;

import java.util.HashMap;

public class EditListItemNameDialogFragment extends EditListDialogFragment {
    String mItemName, mItemId;

    public static EditListItemNameDialogFragment newInstance(ShoppingList shoppingList, String itemName, String itemId, String listId ,String encodedEmail) {
        EditListItemNameDialogFragment editListItemNameDialogFragment = new EditListItemNameDialogFragment();
        Bundle bundle = EditListDialogFragment.newInstanceHelper(shoppingList, R.layout.dialog_edit_item, listId ,encodedEmail);
        bundle.putString(Constants.KEY_LIST_ITEM_NAME, itemName);
        bundle.putString(Constants.KEY_LIST_ITEM_ID, itemId);
        editListItemNameDialogFragment.setArguments(bundle);

        return editListItemNameDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItemName = getArguments().getString(Constants.KEY_LIST_ITEM_NAME);
        mItemId = getArguments().getString(Constants.KEY_LIST_ITEM_ID);
    }


    @Override

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.createDialogHelper(R.string.positive_button_edit_item);
        super.helpSetDefaultValueEditText(mItemName);
        return dialog;
    }

    @Override
    protected void doListEdit() {
        String nameInput = mEditTextForList.getText().toString();
        if (!nameInput.equals("") && !nameInput.equals(mItemName)) {
            Firebase firebaseRef = new Firebase(Constants.FIREBASE_URL);


            HashMap<String, Object> updatedItemToAddMap = new HashMap<String, Object>();


            updatedItemToAddMap.put("/" + Constants.FIREBASE_LOCATION_SHOPPING_LIST_ITEMS + "/"
                            + mListId + "/" + mItemId + "/" + Constants.FIREBASE_PROPERTY_ITEM_NAME,
                    nameInput);

            HashMap<String, Object> changedTimestampMap = new HashMap<>();
            changedTimestampMap.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);


            updatedItemToAddMap.put("/" + Constants.FIREBASE_LOCATION_ACTIVE_LISTS +
                    "/" + mListId + "/" + Constants.FIREBASE_PROPERTY_TIMESTAMP_LAST_CHANGED, changedTimestampMap);


            firebaseRef.updateChildren(updatedItemToAddMap);

        }
    }
}
