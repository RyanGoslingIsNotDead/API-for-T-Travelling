package ru.itis.api.exception;

public class OperationNotAllowedForOwnerException extends RuntimeException {
    public OperationNotAllowedForOwnerException(String message) {
        super(message);
    }
}
