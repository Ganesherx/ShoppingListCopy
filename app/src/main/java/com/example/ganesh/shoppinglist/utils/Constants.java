package com.example.ganesh.shoppinglist.utils;

import com.example.ganesh.shoppinglist.BuildConfig;

public class Constants {

    /**
     * Constants related to locations in Firebase, such as the name of the node
     * where active lists are stored (ie "activeLists")
     */
    public static final String FIREBASE_LOCATION_ACTIVE_LIST = "activeList";
     public static final String FIREBASE_LOCATION_ACTIVE_LISTS = "activeLists";
    public static final String FIREBASE_LOCATION_USERS = "users";

    /**
     * Constants for Firebase object properties
     */
    public static final String FIREBASE_PROPERTY_LIST_NAME = "listName";
    public static final String FIREBASE_PROPERTY_ITEM_NAME = "itemName";


    /**
     * constant for Firebase URL
     */
    public static final String FIREBASE_LOCATION_SHOPPING_LIST_ITEMS = "shoppingListItems";
    public static final String FIREBASE_URL = BuildConfig.UNIQUE_FIREBASE_ROOT_URL;
    public static final String FIREBASE_URL_SHOPPING_LIST_ITEMS = FIREBASE_URL + "/" + FIREBASE_LOCATION_SHOPPING_LIST_ITEMS;
    public static final String FIREBASE_URL_ACTIVE_LIST = FIREBASE_URL + "/" + FIREBASE_LOCATION_ACTIVE_LIST;
    public static final String FIREBASE_URL_ACTIVE_LISTS = FIREBASE_URL + "/" + FIREBASE_LOCATION_ACTIVE_LISTS;
    public static final String FIREBASE_URL_USERS = FIREBASE_URL + "/" + FIREBASE_LOCATION_USERS;

    /**
     * Constants for bundles, extras and shared preferences keys
     */
    public static final String KEY_LIST_NAME = "LIST_NAME";

    /**
     * Constants for bundles, extras and shared preferences keys
     */
    public static final String KEY_LAYOUT_RESOURCE = "LAYOUT_RESOURCE";

    /**
     * Constants for bundles, extras and shared preferences keys
     */
    public static final String KEY_LIST_ID = "LIST_ID";
    public static final String KEY_LIST_ITEM_NAME = "ITEM_NAME";
    public static final String KEY_LIST_ITEM_ID = "LIST_ITEM_ID";


    /**
     * Constants for bundles, extras and shared preferences keys
     */
    public static final String KEY_GOOGLE_EMAIL = "GOOGLE_EMAIL";


    /**
     * timestamp
     */
    public static final String FIREBASE_PROPERTY_TIMESTAMP = "timestamp";
    public static final String FIREBASE_PROPERTY_TIMESTAMP_LAST_CHANGED = "timestampLastChanged";
    public static final String FIREBASE_PROPERTY_TIMESTAMP_LAST_CHANGED_REVERSE = "timestampLastChangedReverse";

    /**
     * Constants for Firebase login
     */
    public static final String PASSWORD_PROVIDER = "password";
    public static final String GOOGLE_PROVIDER = "google";
    public static final String PROVIDER_DATA_DISPLAY_NAME = "displayName";



    /**
     * Constants for bundles, extras and shared preferences keys
     */
    public static final String KEY_PROVIDER = "PROVIDER";
    public static final String KEY_ENCODED_EMAIL = "ENCODED_EMAIL";
    public static final String KEY_LIST_OWNER = "LIST_OWNER";




}
