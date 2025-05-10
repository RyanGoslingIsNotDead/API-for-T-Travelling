package ru.itis.api.security.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthMethodNotSupportedException extends AuthenticationException {

    public AuthMethodNotSupportedException(String message) {
        super(message);
    }

}
