package com.example.ganesh.shoppinglist.ui.activeLists;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ganesh.shoppinglist.R;
import com.example.ganesh.shoppinglist.model.ShoppingList;
import com.example.ganesh.shoppinglist.utils.Constants;
import com.firebase.client.Firebase;
import com.firebase.client.ServerValue;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddListDialogFragment extends DialogFragment {

    EditText mEditTextListName;
    String mEncodedEmail;

    public AddListDialogFragment() {
        // Required empty public constructor
    }

    public static AddListDialogFragment newInstance(String encodedEmail) {
        AddListDialogFragment addListDialogFragment = new AddListDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_ENCODED_EMAIL , encodedEmail);

        addListDialogFragment.setArguments(bundle);
        return addListDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEncodedEmail = getArguments().getString(Constants.KEY_ENCODED_EMAIL);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Firebase.setAndroidContext(getActivity());

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //get LAyout inflater

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_add_list, null);
        mEditTextListName = (EditText) rootView.findViewById(R.id.edit_text_list_name);

        mEditTextListName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == keyEvent.ACTION_DOWN) {
                    addShoppingList();
                }
                return true;
            }

        });

        builder.setTitle(R.string.add_list_title).setView(rootView)
                .setPositiveButton(R.string.positive_button_create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        addShoppingList();
                    }
                })
                .setNegativeButton(R.string.dailog_cancel, null);

        setCancelable(true);
        return builder.create();


    }

    /**
     * Add new active list
     */
    public void addShoppingList() {


        String userEnteredName = mEditTextListName.getText().toString();
        String owner = "Ganesherx";

        if (!userEnteredName.equals("")) {
            Firebase listsRef = new Firebase(Constants.FIREBASE_URL_ACTIVE_LISTS);
            Firebase newListRef = listsRef.push();

            final String listId = newListRef.getKey();

            HashMap<String, Object> timestampCreated = new HashMap<>();
            timestampCreated.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);
            ShoppingList newShoopingList = new ShoppingList(userEnteredName, mEncodedEmail , timestampCreated);
            newListRef.setValue(newShoopingList);
            AddListDialogFragment.this.getDialog().cancel();

        }


    }

}
