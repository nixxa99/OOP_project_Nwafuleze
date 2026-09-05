package com.restaurantpos.factory;

import com.restaurantpos.model.MenuItem;

//FACTORY PATTERN
//Centralizza e nasconde la logica di creazione degli oggetti.

public class MenuItemFactory {

    public static MenuItem createItem(String name, double price, boolean isVegetarian) {
        if (price < 0) {
            throw new IllegalArgumentException("Il prezzo non può essere negativo.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Il nome non può essere vuoto.");
        }
        
        return new MenuItem(name, price, isVegetarian);
    }
}
