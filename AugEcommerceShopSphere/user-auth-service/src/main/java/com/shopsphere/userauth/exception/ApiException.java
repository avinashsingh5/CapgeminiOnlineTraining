package com.shopsphere.userauth.exception;

import org.springframework.http.HttpStatus;

/** Simple custom exception carrying an HTTP status, so controllers can throw
 *  business errors (e.g. "email already exists") and get a clean JSON response. */
public class ApiException extends RuntimeException {
    private final HttpStatus status;

    public ApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() { return status; }
}
