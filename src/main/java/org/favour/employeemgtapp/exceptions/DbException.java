package org.favour.employeemgtapp.exceptions;

public class DbException extends RuntimeException{
    private final String message;


    public DbException(String message) {
        super(message);
        this.message = message;
    }
}
