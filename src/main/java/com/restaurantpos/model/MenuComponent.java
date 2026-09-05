package com.restaurantpos.model;

import java.io.Serializable;

// COMPOSITE PATTERN: Componente base
// Permette di trattare uniformemente singoli piatti e intere categorie.
public interface MenuComponent extends Serializable {
    String getName();
    double getPrice();
    boolean isVegetarian();
    void print(String indent);
}
