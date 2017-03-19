package com.example.ganesh.shoppinglist.ui.activeListDetails;

import android.app.Dialog;
import android.os.Bundle;

import com.example.ganesh.shoppinglist.R;
import com.example.ganesh.shoppinglist.model.ShoppingList;
import com.example.ganesh.shoppinglist.model.ShoppingListItem;
import com.example.ganesh.shoppinglist.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.client.Firebase;
import com.firebase.client.ServerValue;

import java.util.HashMap;
import java.util.Map;

public class AddListItemDialogFragment extends EditListDialogFragment {

    public static AddListItemDialogFragment newInstance(ShoppingList shoppingList, String listId,String encodedEmail) {
        AddListItemDialogFragment addListItemDialogFragment = new AddListItemDialogFragment();
        Bundle bundle = EditListDialogFragment.newInstanceHelper(shoppingList, R.layout.dialog_add_item, listId, encodedEmail);
        addListItemDialogFragment.setArguments(bundle);

        return addListItemDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /** {@link EditListDialogFragment#createDialogHelper(int)} is a
         * superclass method that creates the dialog
         **/
        return super.createDialogHelper(R.string.positive_button_add_list_item);
    }


    @Override
    protected void doListEdit() {
        String mItemName = mEditTextForList.getText().toString();
        if (!mItemName.equals("")) {

            Firebase firebaseRef = new Firebase(Constants.FIREBASE_URL);
            Firebase itemsRef = new Firebase(Constants.FIREBASE_URL_SHOPPING_LIST_ITEMS).child(mListId);


            HashMap<String, Object> updatedItemToAddMap = new HashMap<String, Object>();


            Firebase newRef = itemsRef.push();
            String itemId = newRef.getKey();


            ShoppingListItem itemToAddObject = new ShoppingListItem(mItemName,mEncodedEmail);
            HashMap<String, Object> itemToAdd =
                    (HashMap<String, Object>) new ObjectMapper().convertValue(itemToAddObject, Map.class);


            updatedItemToAddMap.put("/" + Constants.FIREBASE_LOCATION_SHOPPING_LIST_ITEMS + "/"
                    + mListId + "/" + itemId, itemToAdd);


            HashMap<String, Object> changedTimestampMap = new HashMap<>();
            changedTimestampMap.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);


            updatedItemToAddMap.put("/" + Constants.FIREBASE_LOCATION_ACTIVE_LISTS +
                    "/" + mListId + "/" + Constants.FIREBASE_PROPERTY_TIMESTAMP_LAST_CHANGED, changedTimestampMap);

            firebaseRef.updateChildren(updatedItemToAddMap);


            AddListItemDialogFragment.this.getDialog().cancel();
        }
    }
}

