package com.example.ganesh.shoppinglist.model;


public class ShoppingListItem {

    private String itemName;
    private String owner;

    public ShoppingListItem() {
    }

    public ShoppingListItem(String itemName,String owner) {
        this.itemName = itemName;
        /**
         * This is a default value until we can differentiate users.
         * Which will be soon, I promise.
         */
        this.owner = owner;
    }

    public String getItemName() { return itemName; }

    public String getOwner() {
        return owner;
    }
}
