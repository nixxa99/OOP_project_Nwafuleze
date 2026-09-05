package com.restaurantpos.exception;

//Gestione errori
public class SystemException extends Exception {
    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
