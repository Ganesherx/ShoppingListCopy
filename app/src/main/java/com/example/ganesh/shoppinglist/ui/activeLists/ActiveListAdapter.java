package com.example.ganesh.shoppinglist.ui.activeLists;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.example.ganesh.shoppinglist.R;
import com.example.ganesh.shoppinglist.model.ShoppingList;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.Query;



public class ActiveListAdapter extends FirebaseListAdapter<ShoppingList> {

    public ActiveListAdapter(Activity activity, Class<ShoppingList> modelClass, int modelLayout, Query ref) {
        super(activity, modelClass, modelLayout, ref);
        this.mActivity = activity;
    }

    @Override
    protected void populateView(View view, ShoppingList list, int position) {
        TextView textViewListName = (TextView) view.findViewById(R.id.text_view_list_name);
        textViewListName.setText(list.getListName());

        TextView textViewCreatedByUser = (TextView) view.findViewById(R.id.text_view_created_by_user);
        textViewCreatedByUser.setText(list.getOwner());

    }
}
