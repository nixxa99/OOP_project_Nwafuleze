package com.restaurantpos.utils;

import com.restaurantpos.exception.DataStorageException;
import java.io.*;


// JAVA I/O & GENERICS
//Gestisce la persistenza in modo generico e centralizzato, assicurando l'Exception Shielding

public class MenuStorageUtil {

    public static <T> void save(T item, String path) throws DataStorageException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(item);
            LoggerManager.logInfo("Data saved successfully to " + path);
        } catch (IOException e) {
            LoggerManager.logError("Failed to save data to " + path, e);
            // EXCEPTION SHIELDING
            throw new DataStorageException("Errore durante il salvataggio dei dati. Ripristina lo spazio su disco o controlla i permessi.");
        }
    }

    public static <T> T load(String path) throws DataStorageException {
        File file = new File(path);
        if (!file.exists()) {
            throw new DataStorageException("Il file dati non esiste: " + path);
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            T data = (T) ois.readObject();
            LoggerManager.logInfo("Data loaded successfully from " + path);
            return data;
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            LoggerManager.logError("Failed to load data from " + path, e);
            // EXCEPTION SHIELDING
            throw new DataStorageException("File corrotto o permessi mancanti. Impossibile caricare i dati.");
        }
    }
}
