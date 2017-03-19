package com.example.ganesh.shoppinglist.ui.meals;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ganesh.shoppinglist.R;
import com.example.ganesh.shoppinglist.model.ShoppingList;
import com.example.ganesh.shoppinglist.utils.Constants;
import com.firebase.client.Firebase;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMealDialogFragment extends DialogFragment {
    private EditText mEditTextMealName;

    public static AddMealDialogFragment newInstance() {
        AddMealDialogFragment addMealDialogFragment = new AddMealDialogFragment();
        Bundle bundle = new Bundle();
        addMealDialogFragment.setArguments(bundle);
        return addMealDialogFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Open the keyboard automatically when the dialog fragment is opened
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /* Use the Builder class for convenient dialog construction */
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        /* Get the layout inflater */
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_add_meal, null);
        mEditTextMealName = (EditText) rootView.findViewById(R.id.edit_text_meal_name);

        /**
         * Call addMeal() when user taps "Done" keyboard action
         */
        mEditTextMealName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    addMeal();
                }
                return true;
            }
        });

        /* Inflate and set the layout for the dialog */
        /* Pass null as the parent view because its going in the dialog layout */
        builder.setTitle(R.string.add_meal_title).setView(rootView)
                /* Add action buttons */
                .setPositiveButton(R.string.positive_button_create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        addMeal();
                    }
                })
                .setNegativeButton(R.string.dailog_cancel,null);

        setCancelable(true);
        return builder.create();
    }

    /**
     * Add new meal
     */
    public void addMeal() {

    }

}
