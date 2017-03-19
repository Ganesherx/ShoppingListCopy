package com.example.ganesh.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.ganesh.shoppinglist.model.User;
import com.example.ganesh.shoppinglist.ui.activeLists.AddListDialogFragment;
import com.example.ganesh.shoppinglist.ui.activeLists.ShoppingListsFragment;
import com.example.ganesh.shoppinglist.ui.login.CreateAccountActivity;
import com.example.ganesh.shoppinglist.ui.login.LoginActivity;
import com.example.ganesh.shoppinglist.ui.meals.AddMealDialogFragment;
import com.example.ganesh.shoppinglist.ui.meals.MealsFragment;
import com.example.ganesh.shoppinglist.utils.Constants;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends BaseActivity {

    private Firebase mUserRef;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private ValueEventListener mUserRefListener;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent logintUser = new Intent(MainActivity.this, LoginActivity.class);
                    logintUser.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(logintUser);
                    finish();
                }
            }
        };

        mUserRef = new Firebase(Constants.FIREBASE_URL_USERS).child(mEncodedEmail);

        mUserRefListener =mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                /**
                 * set the activity title to current user if user is not null
                 */
                if(user != null){
                    String firstName=user.getName().split("\\s+")[0];
                    String title=firstName + "'s Lists";
                    setTitle(title);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e(LOG_TAG,getString(R.string.log_error_the_read_failed)+firebaseError.getMessage());
            }
        });



        initializeScreen();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_logout){
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        mUserRef.removeEventListener(mUserRefListener);
    }


    private void initializeScreen() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        /**
         * Create SectionPagerAdapter, set it as adapter to viewPager with setOffscreenPageLimit(2)
         **/
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
        /**
         * Setup the mTabLayout with view pager
         */
        tabLayout.setupWithViewPager(viewPager);
    }

    public void showAddListDialog(View view) {
        /* Create an instance of the dialog fragment and show it */
        DialogFragment dialog = AddListDialogFragment.newInstance(mEncodedEmail);
        dialog.show(MainActivity.this.getSupportFragmentManager(), "AddListDialogFragment");
    }

    /**
     * Create an instance of the AddMeal dialog fragment and show it
     */
    public void showAddMealDialog(View view) {
        /* Create an instance of the dialog fragment and show it */
        DialogFragment dialog = AddMealDialogFragment.newInstance();
        dialog.show(MainActivity.this.getSupportFragmentManager(), "AddMealDialogFragment");
    }


    public class SectionPagerAdapter extends FragmentStatePagerAdapter {

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            switch (position) {
                case 0:
                    fragment = ShoppingListsFragment.newInstance();
                    break;
                case 1:
                    fragment = MealsFragment.newInstance();
                    break;
                default:
                    fragment = ShoppingListsFragment.newInstance();
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {

            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.pager_title_shopping_lists);
                case 1:
                default:
                    return getString(R.string.pager_title_meals);
            }
        }
    }

    public  void logout(){
        mAuth.signOut();
    }


}
