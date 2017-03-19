package com.example.ganesh.shoppinglist;
import com.firebase.client.Firebase;
import com.firebase.client.Logger;

public class ShoppingListApplication extends android.app.Application  {


    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setLogLevel(Logger.Level.DEBUG);
    }

}
