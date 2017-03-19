package com.example.ganesh.shoppinglist.ui.activeListDetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ganesh.shoppinglist.BaseActivity;
import com.example.ganesh.shoppinglist.R;
import com.example.ganesh.shoppinglist.model.ShoppingList;
import com.example.ganesh.shoppinglist.model.ShoppingListItem;
import com.example.ganesh.shoppinglist.utils.Constants;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActiveListDetailsActivity extends BaseActivity {

    private static final String LOG_TAG = ActiveListDetailsActivity.class.getSimpleName();
    private ListView mListView;
    private ShoppingList mShoppingList;
    private Firebase mActiveListRef;
    private String mListId;
    private ActiveListItemAdapter mActiveListItemAdapter;
    private DatabaseReference listItemsRef;
    private ValueEventListener mActiveListRefListener;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_list_details);


        Intent intent = this.getIntent();
        mListId = intent.getStringExtra(Constants.KEY_LIST_ID);
        if (mListId == null) {
                      /* No point in continuing without a valid ID. */
            finish();
            return;
        }

        mActiveListRef = new Firebase(Constants.FIREBASE_URL_ACTIVE_LISTS).child(mListId);
        listItemsRef = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_LOCATION_SHOPPING_LIST_ITEMS).child(mListId);
        initializeScreen();

        mActiveListItemAdapter = new ActiveListItemAdapter(this, ShoppingListItem.class,
                R.layout.single_active_list_item,
                listItemsRef,
                 mListId);


            /* Create ActiveListItemAdapter and set to listView */
        mListView.setAdapter(mActiveListItemAdapter);

        mActiveListRefListener = mActiveListRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ShoppingList shoppingList = dataSnapshot.getValue(ShoppingList.class);
                if (shoppingList == null) {
                    finish();
                    return;
                }
                mShoppingList = shoppingList;
                invalidateOptionsMenu();
                setTitle(shoppingList.getListName());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e(LOG_TAG,
                        getString(R.string.log_error_the_read_failed) +
                                firebaseError.getMessage());
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                /* Check that the view is not the empty footer item */
                if (view.getId() != R.id.list_view_footer_empty) {
                    ShoppingListItem shoppingListItem = mActiveListItemAdapter.getItem(position);
                    if (shoppingListItem != null) {
                        String itemName = shoppingListItem.getItemName();
                        String itemId = mActiveListItemAdapter.getRef(position).getKey();

                        showEditListItemNameDialog(itemName, itemId);
                        return true;

                    }
                }

                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_details, menu);

        MenuItem remove = menu.findItem(R.id.action_remove_list);
        MenuItem edit = menu.findItem(R.id.action_edit_list_name);
        MenuItem share = menu.findItem(R.id.action_share_list);
        MenuItem archive = menu.findItem(R.id.action_archive);

        remove.setVisible(true);
        edit.setVisible(true);
        share.setVisible(false);
        archive.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_edit_list_name) {
            showEditListNameDialog();
            return true;
        }


        if (id == R.id.action_remove_list) {
            removeList();
            return true;
        }

        if (id == R.id.action_share_list) {
            return true;
        }

        if (id == R.id.action_archive) {
            //archiveList();
            return true;
        }


        item.setVisible(true);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActiveListItemAdapter.cleanup();
        mActiveListRef.removeEventListener(mActiveListRefListener);

    }

    private void initializeScreen() {
        mListView = (ListView) findViewById(R.id.list_view_shopping_list_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        /* Add back button to the action bar */
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        /* Inflate the footer, set root layout to null*/
        View footer = getLayoutInflater().inflate(R.layout.footer_empty, null);
        mListView.addFooterView(footer);
    }

    public void showEditListNameDialog() {
        DialogFragment dialog = EditListNameDialogFragment.newInstance(mShoppingList, mListId,mEncodedEmail);
        dialog.show(this.getSupportFragmentManager(), "EditListNameDialogFragment");
    }


    public void archiveList() {
    }

    public void addMeal(View view) {
    }

    public void removeList() {
        DialogFragment dialog = RemoveListDialogFragment.newInstance(mShoppingList, mListId);
        dialog.show(getSupportFragmentManager(), "RemoveListDialogFragment");
    }

    public void showAddListItemDialog(View view) {
        DialogFragment dialog = AddListItemDialogFragment.newInstance(mShoppingList, mListId,mEncodedEmail);
        dialog.show(getSupportFragmentManager(), "AddListItemDialogFragment");
    }

    public void showEditListItemNameDialog(String itemName, String itemId) {
        DialogFragment dialog = EditListItemNameDialogFragment.newInstance(mShoppingList,itemName,itemId, mListId,mEncodedEmail);
        dialog.show(this.getSupportFragmentManager(), "EditListItemNameDialogFragment");
    }

    /**
     * This method is called when user taps "Start/Stop shopping" button
     */
    public void toggleShopping(View view) {

    }




}
