package com.restaurantpos.exception;

//EXCEPTION SHIELDING pattern
public class DataStorageException extends SystemException {
    public DataStorageException(String safeMessage) {
        super(safeMessage);
    }
}
