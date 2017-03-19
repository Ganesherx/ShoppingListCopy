package com.example.ganesh.shoppinglist.ui.activeLists;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ganesh.shoppinglist.R;
import com.example.ganesh.shoppinglist.model.ShoppingList;
import com.example.ganesh.shoppinglist.ui.activeListDetails.ActiveListDetailsActivity;
import com.example.ganesh.shoppinglist.utils.Constants;
import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShoppingListsFragment extends Fragment {


    private ListView mListView;
    private ActiveListAdapter mActiveListAdapter;

    private DatabaseReference activeListsRef;


    public ShoppingListsFragment() {

    }


    public static ShoppingListsFragment newInstance() {
        ShoppingListsFragment fragment = new ShoppingListsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_shopping_lists, container, false);
        initializeScreen(rootView);
        Firebase.setAndroidContext(getActivity());


        activeListsRef = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_LOCATION_ACTIVE_LISTS);

        mActiveListAdapter = new ActiveListAdapter(getActivity(),
                ShoppingList.class,
                R.layout.single_active_list,
                activeListsRef);


        mListView.setAdapter(mActiveListAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShoppingList selectedList = mActiveListAdapter.getItem(position);
                if (selectedList != null) {
                    Intent intent = new Intent(getActivity(), ActiveListDetailsActivity.class);
                    String listId = mActiveListAdapter.getRef(position).getKey();
                    intent.putExtra(Constants.KEY_LIST_ID, listId);
                    startActivity(intent);
                }

                //if(selectedList !=null)
                // startActivity(new Intent(getActivity(),ActiveListDetailsActivity.class));
            }

        });


        return rootView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mActiveListAdapter.cleanup();

    }

    private void initializeScreen(View rootView) {
        mListView = (ListView) rootView.findViewById(R.id.list_view_active_lists);

    }


}
