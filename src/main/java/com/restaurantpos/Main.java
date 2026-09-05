package com.restaurantpos;

import com.restaurantpos.ui.ConsoleUI;
import com.restaurantpos.utils.LoggerManager;

public class Main {
    public static void main(String[] args) {
        LoggerManager.logInfo("System starting...");
        try {
            ConsoleUI ui = new ConsoleUI();
            ui.start();
            LoggerManager.logInfo("System shut down normally.");
        } catch (Exception e) {
            // Permette di gestire gli errori di caricamento senza causare crash di sistema
            LoggerManager.logError("Errore imprevisto di sistema:", e);
            System.err.println("Si è verificato un errore critico nel sistema.");
            System.exit(1);
        }
    }
}
