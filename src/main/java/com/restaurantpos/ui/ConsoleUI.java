package com.restaurantpos.ui;

import com.restaurantpos.exception.DataStorageException;
import com.restaurantpos.factory.MenuItemFactory;
import com.restaurantpos.utils.MenuStorageUtil;
import com.restaurantpos.iterator.DietaryIterator;
import com.restaurantpos.manager.RestaurantManager;
import com.restaurantpos.model.MenuCategory;
import com.restaurantpos.model.MenuComponent;
import com.restaurantpos.model.MenuItem;
import com.restaurantpos.model.Order;
import com.restaurantpos.strategy.HappyHourStrategy;
import com.restaurantpos.utils.InputValidator;

import java.util.Scanner;

public class ConsoleUI {
    private Scanner scanner;
    private static final String DATA_FILE = "menu_data.dat";

    public ConsoleUI() {
        scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Benvenuto nel Sistema POS del Ristorante");
        loadMenu();

        boolean running = true;
        while (running) {
            System.out.println("\n--- MENU PRINCIPALE ---");
            System.out.println("1. Visualizza Menù");
            System.out.println("2. Visualizza Solo Piatti Vegetariani (Iterator)");
            System.out.println("3. Aggiungi nuovo Piatto (Factory)");
            System.out.println("4. Salva Menù (I/O)");
            System.out.println("5. Simula un Ordine");
            System.out.println("6. Attiva/Disattiva Happy Hour");
            System.out.println("0. Esci");

            int choice = InputValidator.readIntSafely(scanner, "Scegli un'opzione: ");

            switch (choice) {
                case 1: printMenu(); break;
                case 2: printVegetarianMenu(); break;
                case 3: createDish(); break;
                case 4: saveMenu(); break;
                case 5: simulateOrder(); break;
                case 6: toggleHappyHour(); break;
                case 0: running = false; break;
                default: System.out.println("Opzione non valida.");
            }
        }
        System.out.println("Chiusura del sistema in corso...");
        scanner.close();
    }

    private void loadMenu() {
        try {
            MenuComponent loadedMenu = MenuStorageUtil.<MenuComponent>load(DATA_FILE);
            if (loadedMenu instanceof MenuCategory) {
                RestaurantManager.getInstance().setMainMenu((MenuCategory) loadedMenu);
            }
        } catch (DataStorageException e) {
            System.out.println(e.getMessage());
            System.out.println("Creazione di un nuovo menù vuoto.");

            MenuCategory root = new MenuCategory("Menù Principale");
            root.add(MenuItemFactory.createItem("Spaghetti al pomodoro", 10.0, true));
            root.add(MenuItemFactory.createItem("Bistecca alla fiorentina", 25.0, false));
            root.add(MenuItemFactory.createItem("Gnocchi burro e salvia", 9.0, true));
            RestaurantManager.getInstance().setMainMenu(root);
        }
    }

    private void saveMenu() {
        try {
            MenuStorageUtil.save(RestaurantManager.getInstance().getMainMenu(), DATA_FILE);
            System.out.println("Menù salvato con successo!");
        } catch (DataStorageException e) {
            System.out.println(e.getMessage());
        }
    }

    private void printMenu() {
        System.out.println("\n--- MENÙ ATTUALE ---");
        RestaurantManager.getInstance().getMainMenu().print("");
    }

    private void printVegetarianMenu() {
        System.out.println("\n--- MENÙ VEGETARIANO ---");
        DietaryIterator iterator = new DietaryIterator(RestaurantManager.getInstance().getMainMenu(), true);
        while (iterator.hasNext()) {
            MenuComponent item = iterator.next();
            System.out.println("- " + item.getName() + " (" + item.getPrice() + "€)");
        }
    }

    private void createDish() {
        String name = InputValidator.readStringSafely(scanner, "Nome Piatto: ");
        int priceInt = InputValidator.readIntSafely(scanner, "Prezzo Piatto (intero, es. 12): ");
        int vegInt = InputValidator.readIntSafely(scanner, "E' vegetariano? (1=Si, 0=No): ");
        
        try {
            MenuItem newItem = MenuItemFactory.createItem(name, (double) priceInt, vegInt == 1);
            RestaurantManager.getInstance().getMainMenu().add(newItem);
            System.out.println("Piatto aggiunto con successo.");
        } catch (IllegalArgumentException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    private void simulateOrder() {
        Order order = new Order(1);
        // Aggiungiamo automaticamente i primi due piatti del menù per simulare un ordine
        MenuCategory menu = RestaurantManager.getInstance().getMainMenu();
        if (!menu.getComponents().isEmpty()) {
            order.addItem(menu.getComponents().get(0));
            if(menu.getComponents().size() > 1) {
                order.addItem(menu.getComponents().get(1));
            }
        }

        if (RestaurantManager.getInstance().isHappyHourActive()) {
            order.setDiscountStrategy(new HappyHourStrategy());
            System.out.println("Sconto HAPPY HOUR -20% applicato all'ordine.");
        }

        order.printReceipt();
    }

    private void toggleHappyHour() {
        boolean currentState = RestaurantManager.getInstance().isHappyHourActive();
        RestaurantManager.getInstance().setHappyHourActive(!currentState);
        System.out.println("Happy Hour è ora: " + (RestaurantManager.getInstance().isHappyHourActive() ? "ATTIVO (Sconto 20%)" : "DISATTIVO"));
    }
}
