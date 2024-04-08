package org.favour.employeemgtapp.exceptions;

public class InvalidCredentialException extends RuntimeException{
    private final String message;


    public InvalidCredentialException(String message) {
        super(message);
        this.message = message;
    }
}
