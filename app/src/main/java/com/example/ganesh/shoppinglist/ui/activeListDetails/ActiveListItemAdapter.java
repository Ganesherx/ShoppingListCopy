package com.example.ganesh.shoppinglist.ui.activeListDetails;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ganesh.shoppinglist.R;
import com.example.ganesh.shoppinglist.model.ShoppingList;
import com.example.ganesh.shoppinglist.model.ShoppingListItem;
import com.example.ganesh.shoppinglist.utils.Constants;
import com.firebase.client.Firebase;
import com.firebase.client.ServerValue;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.Query;

import java.util.HashMap;


public class ActiveListItemAdapter extends FirebaseListAdapter<ShoppingListItem> {
    private ShoppingList mShoppingList;
    private String mListId;

    public ActiveListItemAdapter(Activity activity, Class<ShoppingListItem> modelClass, int modelLayout, Query ref, String listId) {
        super(activity, modelClass, modelLayout, ref);
        this.mActivity = activity;
        this.mListId = listId;
    }

    public void setShoppingList(ShoppingList shoppingList) {
        this.mShoppingList = shoppingList;
        this.notifyDataSetChanged();
    }

    @Override
    protected void populateView(View view, ShoppingListItem item, int position) {
        ImageButton buttonRemoveItem = (ImageButton) view.findViewById(R.id.button_remove_item);
        TextView textViewMealItemName = (TextView) view.findViewById(R.id.text_view_active_list_item_name);
        textViewMealItemName.setText(item.getItemName());

        final String itemToRemoveId = this.getRef(position).getKey();

        buttonRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity)
                        .setTitle(mActivity.getString(R.string.remove_item_option))
                        .setMessage(mActivity.getString(R.string.dialog_message_are_you_sure_remove_item))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                removeItem(itemToRemoveId);
                                dialog.dismiss();
                            }

                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                                    /* Dismiss the dialog */
                                dialog.dismiss();
                            }
                        });


                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });


    }

    private void removeItem(String itemId) {
        Firebase firebaseRef = new Firebase(Constants.FIREBASE_URL);
        HashMap<String, Object> updatedRemoveItemMap = new HashMap<String, Object>();
        updatedRemoveItemMap.put("/" + Constants.FIREBASE_LOCATION_SHOPPING_LIST_ITEMS + "/"
                + mListId + "/" + itemId, null);
        HashMap<String, Object> changedTimestampMap = new HashMap<>();
        changedTimestampMap.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);
        updatedRemoveItemMap.put("/" + Constants.FIREBASE_LOCATION_ACTIVE_LISTS +
                "/" + mListId + "/" + Constants.FIREBASE_PROPERTY_TIMESTAMP_LAST_CHANGED, changedTimestampMap);
        firebaseRef.updateChildren(updatedRemoveItemMap);
    }
}
