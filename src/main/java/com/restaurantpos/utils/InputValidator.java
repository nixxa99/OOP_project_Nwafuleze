package com.restaurantpos.utils;

import java.util.Scanner;

//Previene crash del sistema visibili all'utente
public class InputValidator {

    public static int readIntSafely(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                LoggerManager.logInfo("User inputted invalid integer: " + input);
                System.out.println("Input non valido. Per favore, inserisci un numero intero.");
            }
        }
    }

    public static String readStringSafely(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                // Sanitizzazione dell'input dell'utente
                return input.replace("<", "").replace(">", "");
            }
            System.out.println("L'input non può essere vuoto. Riprova.");
        }
    }
}
