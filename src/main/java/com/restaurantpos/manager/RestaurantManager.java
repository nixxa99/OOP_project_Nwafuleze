package com.restaurantpos.manager;

import com.restaurantpos.model.MenuCategory;

//SINGLETON PATTERN
//Un'unica istanza che funge da "cassa centrale" per la gestione globale.

public class RestaurantManager {
    private static RestaurantManager instance;
    private MenuCategory mainMenu;
    private boolean isHappyHourActive;

    private RestaurantManager() {
        this.mainMenu = new MenuCategory("Root Menu");
        this.isHappyHourActive = false;
    }

    public static synchronized RestaurantManager getInstance() {
        if (instance == null) {
            instance = new RestaurantManager();
        }
        return instance;
    }

    public void setMainMenu(MenuCategory mainMenu) {
        this.mainMenu = mainMenu;
    }

    public MenuCategory getMainMenu() {
        return mainMenu;
    }

    public boolean isHappyHourActive() {
        return isHappyHourActive;
    }

    public void setHappyHourActive(boolean happyHourActive) {
        isHappyHourActive = happyHourActive;
    }
}
