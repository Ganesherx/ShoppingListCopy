package com.example.ganesh.shoppinglist.model;


import com.example.ganesh.shoppinglist.utils.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firebase.client.ServerValue;

import java.util.HashMap;


public class ShoppingList {
    private String listName;
    private String owner;
    public HashMap<String, Object> timestampLastChanged;
    private HashMap<String, Object> timestampCreated;

    //EMPTY CONSTRUCTOR
    public ShoppingList() {

    }

    //CONSTRUCTOR
    public ShoppingList(String listName, String owner, HashMap<String, Object> timestampCreated) {
        this.listName = listName;
        this.owner = owner;
        this.timestampCreated = timestampCreated;
        HashMap<String, Object> timestampNowObject = new HashMap<String, Object>();
        timestampNowObject.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);
        this.timestampLastChanged = timestampNowObject;
    }

    //GETTERS
    public String getListName() {
        return listName;
    }

    public String getOwner() {
        return owner;
    }

    public HashMap<String, Object> getTimestampLastChanged() {
        return timestampLastChanged;
    }

    public HashMap<String, Object> getTimestampCreated() {
        return timestampCreated;
    }

    @JsonIgnore
    public long getTimestampLastChangedLong() {
        return (long) timestampLastChanged.get(Constants.FIREBASE_PROPERTY_TIMESTAMP);
    }

    @JsonIgnore
    public long getTimestampCreatedLong() {
        return (long) timestampLastChanged.get(Constants.FIREBASE_PROPERTY_TIMESTAMP);
    }

}