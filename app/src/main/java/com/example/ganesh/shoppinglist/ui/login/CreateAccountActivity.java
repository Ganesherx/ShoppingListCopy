package com.example.ganesh.shoppinglist.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ganesh.shoppinglist.BaseActivity;
import com.example.ganesh.shoppinglist.MainActivity;
import com.example.ganesh.shoppinglist.R;
import com.example.ganesh.shoppinglist.model.User;
import com.example.ganesh.shoppinglist.utils.Constants;
import com.example.ganesh.shoppinglist.utils.Utils;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;

public class CreateAccountActivity extends BaseActivity {
    private static final String LOG_TAG = CreateAccountActivity.class.getSimpleName();
    private ProgressDialog mAuthProgressDialog;
    private EditText mEditTextUsernameCreate, mEditTextEmailCreate, mEditTextPasswordCreate;
    private Button mButttonSignUp;
    private String mUserName, mUserEmail, mPassword;

    private DatabaseReference activeListsRef;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mAuth = FirebaseAuth.getInstance();
        activeListsRef = FirebaseDatabase.getInstance().getReference().child("users");


        initializeScreen();

        mButttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        mUserName = mEditTextUsernameCreate.getText().toString();
        mUserEmail = mEditTextEmailCreate.getText().toString().toLowerCase();
        mPassword = mEditTextPasswordCreate.getText().toString();

        boolean validEmail = isEmailValid(mUserEmail);
        boolean validUserName = isUserNameValid(mUserName);
        boolean validPassword = isPasswordValid(mPassword);
        if (!validEmail || !validUserName || !validPassword) return;

        mAuthProgressDialog.show();

        mAuth.createUserWithEmailAndPassword(mUserEmail, mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mAuthProgressDialog.dismiss();
                    String uid = mAuth.getCurrentUser().getUid();

                    createUserInFirebaseHelper();

                    Toast.makeText(CreateAccountActivity.this, "Register successfully", Toast.LENGTH_LONG).show();

                    Intent mainIntent = new Intent(CreateAccountActivity.this, MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mainIntent);
                    finish();

                }
            }
        });

    }

    /**
     * Creates a new user in Firebase from the Java POJO
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createUserInFirebaseHelper() {
        final String encodedEmail = Utils.encodeEmail(mUserEmail);

        final Firebase userLocation = new Firebase(Constants.FIREBASE_URL_USERS).child(encodedEmail);

        userLocation.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                  /* If there is no user, make one */
                if (dataSnapshot.getValue() == null) {
                 /* Set raw version of date to the ServerValue.TIMESTAMP value and save into dateCreatedMap */
                    HashMap<String, Object> timestampJoined = new HashMap<>();
                    timestampJoined.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);

                    User newUser = new User(mUserName, encodedEmail, timestampJoined);
                    userLocation.setValue(newUser);
                }
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d(LOG_TAG, getString(R.string.log_error_occurred) + firebaseError.getMessage());
            }
        });
    }

    private boolean isEmailValid(String email) {
        boolean isGoodEmail =
                (email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail) {
            mEditTextEmailCreate.setError(String.format(getString(R.string.error_invalid_email_not_valid),
                    email));
            return false;
        }
        return isGoodEmail;
    }

    private boolean isUserNameValid(String userName) {
        if (userName.equals("")) {
            mEditTextUsernameCreate.setError(getResources().getString(R.string.error_cannot_be_empty));
            return false;
        }
        return true;
    }

    private boolean isPasswordValid(String password) {
        if (password.length() < 6) {
            mEditTextPasswordCreate.setError(getResources().getString(R.string.error_invalid_password_not_valid));
            return false;
        }
        return true;
    }

    public void initializeScreen() {
        mEditTextUsernameCreate = (EditText) findViewById(R.id.edit_text_username_create);
        mEditTextEmailCreate = (EditText) findViewById(R.id.edit_text_email_create);
        mEditTextPasswordCreate = (EditText) findViewById(R.id.edit_text_password_create);
        mButttonSignUp = (Button) findViewById(R.id.btn_create_account_final);
        LinearLayout linearLayoutCreateAccountActivity = (LinearLayout) findViewById(R.id.linear_layout_create_account_activity);
        initializeBackground(linearLayoutCreateAccountActivity);

        /* Setup the progress dialog that is displayed later when authenticating with Firebase */
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle(getResources().getString(R.string.progress_dialog_loading));
        mAuthProgressDialog.setMessage(getResources().getString(R.string.progress_dialog_creating_user_with_firebase));
        mAuthProgressDialog.setCancelable(false);
    }

    public void onSignInPressed(View view) {
        Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


}
