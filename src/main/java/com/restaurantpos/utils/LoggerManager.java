package com.restaurantpos.utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class LoggerManager {
    private static final Logger logger = Logger.getLogger("RestaurantPOS");

    static {
        try {
            //creo un nuovo file che contenga i vari log dell'utente
            FileHandler fileHandler = new FileHandler("application.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
            //mantiene pulita l'interfaccia utente
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            System.err.println("Impossibile creare il file di gestione login.");
        }
    }

    public static void logInfo(String message) {
        logger.info(message);
    }

    public static void logError(String message, Throwable exception) {
        logger.log(Level.SEVERE, message, exception);
    }
}
