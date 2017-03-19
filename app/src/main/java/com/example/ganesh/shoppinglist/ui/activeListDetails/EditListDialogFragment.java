package com.example.ganesh.shoppinglist.ui.activeListDetails;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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

public abstract class EditListDialogFragment extends DialogFragment {
    String mListId , mOwner ,mEncodedEmail;
    EditText mEditTextForList;
    int mResource;

    protected static Bundle newInstanceHelper(ShoppingList shoppingList, int resource, String listId, String encodedEmail) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_LIST_ID, listId);
        bundle.putInt(Constants.KEY_LAYOUT_RESOURCE, resource);
        bundle.putString(Constants.KEY_LIST_OWNER, shoppingList.getOwner());
        bundle.putString(Constants.KEY_ENCODED_EMAIL, encodedEmail);
        return bundle;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListId = getArguments().getString(Constants.KEY_LIST_ID);
        mResource = getArguments().getInt(Constants.KEY_LAYOUT_RESOURCE);
        mOwner = getArguments().getString(Constants.KEY_LIST_OWNER);
        mEncodedEmail = getArguments().getString(Constants.KEY_ENCODED_EMAIL);

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    protected Dialog createDialogHelper(int stringResourceForPositiveButton) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(mResource, null);
        mEditTextForList = (EditText) rootView.findViewById(R.id.edit_text_list_dialog);

        mEditTextForList.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                   // doListEdit();

                    /**
                     * Close the dialog fragment when done
                     */
                    EditListDialogFragment.this.getDialog().cancel();
                }
                return true;
            }
        });

        builder.setView(rootView)
                 /* Add action buttons */
                .setPositiveButton(stringResourceForPositiveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                       doListEdit();

                        /**
                         * Close the dialog fragment
                         */
                        EditListDialogFragment.this.getDialog().cancel();
                    }
                })
                .setNegativeButton(R.string.negative_button_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        /**
                         * Close the dialog fragment
                         */
                        EditListDialogFragment.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }

    protected void helpSetDefaultValueEditText(String defaultText) {
        mEditTextForList.setText(defaultText);
        mEditTextForList.setSelection(defaultText.length());
    }

    protected abstract void doListEdit();
}
